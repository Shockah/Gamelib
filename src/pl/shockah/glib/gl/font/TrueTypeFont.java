package pl.shockah.glib.gl.font;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.BufferUtils;
import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.geom.vector.Vector2;
import pl.shockah.glib.geom.vector.Vector2i;
import pl.shockah.glib.gl.GL;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.tex.ITextureSupplier;
import pl.shockah.glib.gl.tex.Texture;

/*
 * A TrueType font implementation originally for Slick, edited for Bobjob's Engine
 * Authors: James Chambers (Jimmy), Jeremy Adams (elias4444), Kevin Glass (kevglass), Peter Korzuszek (genail), David Aaron Muhar (bobjob)
 */
public class TrueTypeFont extends pl.shockah.glib.gl.font.Font implements ITextureSupplier {
	private static IntBuffer scratch = BufferUtils.createIntBuffer(16);
	private IntObject[] charArray = new IntObject[256];
	private Map<Character,IntObject> customChars = new HashMap<>();
	private boolean antiAlias;
	private int fontSize = 0, fontHeight = 0, correctL = 9;
	private Font font;
	private FontMetrics fontMetrics;
	private Texture texture;
	
	private class IntObject {
		public int width, height, storedX, storedY;
	}
	
	public TrueTypeFont(String name, int size) {
		this(name,size,false,false);
	}
	public TrueTypeFont(String name, int size, boolean antiAlias) {
		this(name,size,false,false,antiAlias);
	}
	public TrueTypeFont(String name, int size, boolean bold, boolean italic) {
		this(name,size,bold,italic,true);
	}
	public TrueTypeFont(String name, int size, boolean bold, boolean italic, boolean antiAlias) {
		this(name,size,bold,italic,antiAlias,new char[0]);
	}
	public TrueTypeFont(String name, int size, boolean bold, boolean italic, boolean antiAlias, char[] additionalChars) {
		this(new Font(name,Font.PLAIN | (bold ? Font.BOLD : 0) | (italic ? Font.ITALIC : 0),size),antiAlias,additionalChars);
	}
	
	public TrueTypeFont(Font font, int size) {
		this(font,size,false,false);
	}
	public TrueTypeFont(Font font, int size, boolean antiAlias) {
		this(font,size,false,false,antiAlias);
	}
	public TrueTypeFont(Font font, int size, boolean bold, boolean italic) {
		this(font,size,bold,italic,true);
	}
	public TrueTypeFont(Font font, int size, boolean bold, boolean italic, boolean antiAlias) {
		this(font,size,bold,italic,antiAlias,new char[0]);
	}
	public TrueTypeFont(Font font, int size, boolean bold, boolean italic, boolean antiAlias, char[] additionalChars) {
		this(font.deriveFont(Font.PLAIN | (bold ? Font.BOLD : 0) | (italic ? Font.ITALIC : 0),size),antiAlias,additionalChars);
	}
	
	private TrueTypeFont(Font font, boolean antiAlias, char[] additionalChars) {
		this.font = font;
		this.fontSize = font.getSize()+3;
		this.antiAlias = antiAlias;

		createSet(additionalChars);
		
		fontHeight -= 1;
		if (fontHeight <= 0) fontHeight = 1;
	}
	
	private BufferedImage fontImage(char ch) {
		BufferedImage tempfontImage = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D)tempfontImage.getGraphics();
		if (antiAlias) g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(font);
		fontMetrics = g.getFontMetrics();
		int charwidth = fontMetrics.charWidth(ch)+8;

		if (charwidth <= 0) charwidth = 7;
		int charheight = fontMetrics.getHeight()+3;
		if (charheight <= 0) charheight = fontSize;
		
		BufferedImage fontImage;
		fontImage = new BufferedImage(charwidth,charheight,BufferedImage.TYPE_INT_ARGB);
		Graphics2D gt = (Graphics2D)fontImage.getGraphics();
		if (antiAlias) gt.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		gt.setFont(font);

		gt.setColor(Color.WHITE);
		int charx = 3;
		int chary = 1;
		gt.drawString(String.valueOf(ch),charx,chary+fontMetrics.getAscent());

		return fontImage;
	}

	private void createSet(char[] customCharsArray) {
		int textureWidth = 512, textureHeight = 512;
		if (customCharsArray != null && customCharsArray.length > 0) textureWidth *= 2;
		
		try {
			BufferedImage imgTemp = new BufferedImage(textureWidth,textureHeight,BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D)imgTemp.getGraphics();

			g.setColor(new Color(0,0,0,1));
			g.fillRect(0,0,textureWidth,textureHeight);
			
			int rowHeight = 0;
			int positionX = 0;
			int positionY = 0;
			
			int customCharsLength = customCharsArray != null ? customCharsArray.length : 0; 
			for (int i = 0; i < 256 + customCharsLength; i++) {
				char ch = i < 256 ? (char) i : customCharsArray[i-256];
				BufferedImage fontImage = fontImage(ch);
				IntObject newIntObject = new IntObject();

				newIntObject.width = fontImage.getWidth();
				newIntObject.height = fontImage.getHeight();

				if (positionX + newIntObject.width >= textureWidth) {
					positionX = 0;
					positionY += rowHeight;
					rowHeight = 0;
				}

				newIntObject.storedX = positionX;
				newIntObject.storedY = positionY;

				if (newIntObject.height > fontHeight) fontHeight = newIntObject.height;
				if (newIntObject.height > rowHeight) rowHeight = newIntObject.height;

				g.drawImage(fontImage,positionX,positionY,null);
				positionX += newIntObject.width;
				if (i < 256) charArray[i] = newIntObject; else customChars.put(ch,newIntObject);
				fontImage = null;
			}
			texture = new Texture(loadImage(imgTemp),textureWidth,textureHeight);
		} catch (Exception e) {
			System.err.println("Failed to create font.");
			e.printStackTrace();
		}
	}
	
	private void drawQuad(double drawX, double drawY, double drawX2, double drawY2, double srcX, double srcY, double srcX2, double srcY2) {
		double DrawWidth = drawX2-drawX;
		double DrawHeight = drawY2-drawY;
		double TextureSrcX = srcX/texture.width();
		double TextureSrcY = srcY/texture.height();
		double SrcWidth = srcX2-srcX;
		double SrcHeight = srcY2-srcY;
		double RenderWidth = SrcWidth/texture.width();
		double RenderHeight = SrcHeight/texture.height();

		glTexCoord2d(TextureSrcX,TextureSrcY+RenderHeight);
		glVertex2d(drawX,drawY);
		glTexCoord2d(TextureSrcX,TextureSrcY);
		glVertex2d(drawX,drawY+DrawHeight);
		glTexCoord2d(TextureSrcX+RenderWidth,TextureSrcY);
		glVertex2d(drawX+DrawWidth,drawY+DrawHeight);
		glTexCoord2d(TextureSrcX+RenderWidth,TextureSrcY+RenderHeight);
		glVertex2d(drawX+DrawWidth,drawY);
	}

	public int width(String whatchars) {
		IntObject intObject = null;
		int charCurrent;
		
		int startIndex = 0, endIndex = whatchars.length()-1;
		int totalwidth = 0;
		int i = startIndex;
		
		while (i >= startIndex && i <= endIndex) {
			charCurrent = whatchars.charAt(i);
			intObject = charCurrent < 256 ? charArray[charCurrent] : customChars.get((char)charCurrent);
			
			if (intObject != null) {
				totalwidth += intObject.width-correctL;
				i++;
			}
		}
		
		return totalwidth+7;
	}

	public int height() {return fontHeight;}
	public int height(String HeightString) {return fontHeight;}
	public int lineHeight() {return fontHeight;}
	
	public void draw(Graphics g, Vector2 v, CharSequence text) {draw(g,v.Xd(),v.Yd(),text);}
	public void draw(Graphics g, double x, double y, CharSequence text) {
		drawString(x,y,text,0,text.length()-1,1f,1f);
	}
	public void drawString(double x, double y, CharSequence whatchars, int startIndex, int endIndex, float scaleX, float scaleY) {
		if (disposed()) throw new IllegalStateException("Texture already disposed");
		if (whatchars == null) return;
		IntObject intObject = null;
		int charCurrent;
		
		x = Math.round(x);
		y = Math.round(y);
		
		int totalwidth = 0;
		int i = startIndex, d, c;
		float startY = 0;
		
		d = 1;
		c = correctL;
		
		GL.bind(texture);
		glBegin(GL_QUADS);
			while (i >= startIndex && i <= endIndex) {
				charCurrent = whatchars.charAt(i);
				intObject = charCurrent < 256 ? charArray[charCurrent] : customChars.get((char)charCurrent);
				
				if (intObject != null) {
					if (d < 0) totalwidth += (intObject.width-c)*d;
					drawQuad((totalwidth+intObject.width)*scaleX+x,startY*scaleY+y,totalwidth*scaleX+x,
						(startY+intObject.height)*scaleY+y,intObject.storedX+intObject.width,intObject.storedY+intObject.height,intObject.storedX,intObject.storedY);
					if (d > 0) totalwidth += (intObject.width-c)*d;
					i += d;
				}
			}
		glEnd();
	}
	
	private static int loadImage(BufferedImage bufferedImage) {
		try {
			ByteBuffer byteBuffer;
			DataBuffer db = bufferedImage.getData().getDataBuffer();
			if (db instanceof DataBufferInt) {
				int intI[] = ((DataBufferInt)(bufferedImage.getData().getDataBuffer())).getData();
				byte newI[] = new byte[intI.length*4];
				for (int i = 0; i < intI.length; i++) {
					byte b[] = intToByteArray(intI[i]);
					int newIndex = i*4;
					
					newI[newIndex] = b[1];
					newI[newIndex+1] = b[2];
					newI[newIndex+2] = b[3];
					newI[newIndex+3] = b[0];
				}
				
				byteBuffer = ByteBuffer.allocateDirect(bufferedImage.getWidth()*bufferedImage.getHeight()*(bufferedImage.getColorModel().getPixelSize()/8)).order(ByteOrder.nativeOrder()).put(newI);
			} else byteBuffer = ByteBuffer.allocateDirect(bufferedImage.getWidth()*bufferedImage.getHeight()*(bufferedImage.getColorModel().getPixelSize()/8)).order(ByteOrder.nativeOrder()).put(((DataBufferByte)(bufferedImage.getData().getDataBuffer())).getData());
			byteBuffer.flip();
			
			
			int internalFormat = GL_RGBA8,
			format = GL_RGBA;
			IntBuffer textureId = BufferUtils.createIntBuffer(1);
			glGenTextures(textureId);
			glBindTexture(GL_TEXTURE_2D,textureId.get(0));

			glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_S,GL_CLAMP);
			glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_T,GL_CLAMP);
			glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_LINEAR);
			glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_LINEAR);
			
			glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
			gluBuild2DMipmaps(GL_TEXTURE_2D,internalFormat,bufferedImage.getWidth(),bufferedImage.getHeight(),format,GL_UNSIGNED_BYTE,byteBuffer);
			return textureId.get(0);
		} catch (Exception e) {e.printStackTrace();}
		
		return -1;
	}
	private static int gluBuild2DMipmaps(int target, int components, int width, int height, int format, int type, ByteBuffer data) {
		if ((width < 1) || (height < 1)) return 100901;

		int bpp = bytesPerPixel(format,type);
		if (bpp == 0) return 100900;
		int maxSize = glGetIntegerv(GL_MAX_TEXTURE_SIZE);

		int w = nearestPower(width);
		if (w > maxSize) w = maxSize;
		int h = nearestPower(height);
		if (h > maxSize) h = maxSize;

		PixelStoreState pss = new PixelStoreState();

		glPixelStorei(GL_PACK_ROW_LENGTH,0);
		glPixelStorei(GL_PACK_ALIGNMENT,1);
		glPixelStorei(GL_PACK_SKIP_ROWS,0);
		glPixelStorei(GL_PACK_SKIP_PIXELS,0);

		int retVal = 0;
		boolean done = false;
		ByteBuffer image;
		if ((w != width) || (h != height)) {
			image = BufferUtils.createByteBuffer((w+4)*h*bpp);
			int error = gluScaleImage(format,width,height,type,data,w,h,type,image);
			if (error != 0) {
				retVal = error;
				done = true;
			}

			glPixelStorei(GL_UNPACK_ROW_LENGTH,0);
			glPixelStorei(GL_UNPACK_ALIGNMENT,1);
			glPixelStorei(GL_UNPACK_SKIP_ROWS,0);
			glPixelStorei(GL_UNPACK_SKIP_PIXELS,0);
		} else image = data;

		ByteBuffer bufferA = null;
		ByteBuffer bufferB = null;

		int level = 0;
		while (!done) {
			if (image != data) {
				glPixelStorei(GL_UNPACK_ROW_LENGTH,0);
				glPixelStorei(GL_UNPACK_ALIGNMENT,1);
				glPixelStorei(GL_UNPACK_SKIP_ROWS,0);
				glPixelStorei(GL_UNPACK_SKIP_PIXELS,0);
			}

			glTexImage2D(target,level,components,w,h,0,format,type,image);

			if ((w == 1) && (h == 1)) {
				break;
			}
			int newW = w < 2 ? 1 : w >> 1;
			int newH = h < 2 ? 1 : h >> 1;
			ByteBuffer newImage;
			if (bufferA == null) {
				newImage = bufferA = BufferUtils.createByteBuffer((newW+4)*newH*bpp);
			} else {
				if (bufferB == null) newImage = bufferB = BufferUtils.createByteBuffer((newW+4)*newH*bpp);
				else newImage = bufferB;
			}
			int error = gluScaleImage(format,w,h,type,image,newW,newH,type,newImage);
			if (error != 0) {
				retVal = error;
				done = true;
			}

			image = newImage;
			if (bufferB != null) bufferB = bufferA;
			w = newW;
			h = newH;
			level++;
		}

		pss.save();

		return retVal;
	}
	protected static int bytesPerPixel(int format, int type) {
		int n;
		switch (format) {
			case GL_COLOR_INDEX: case GL_STENCIL_INDEX: case GL_DEPTH_COMPONENT: case GL_RED: case GL_GREEN: case GL_BLUE: case GL_ALPHA: case GL_LUMINANCE: n = 1; break;
			case GL_LUMINANCE_ALPHA: n = 2; break;
			case GL_RGB: case GL_BGR: n = 3; break;
			case GL_RGBA: case GL_BGRA: n = 4; break;
			default: n = 0;
		}
		int m;
		switch (type) {
			case GL_UNSIGNED_BYTE: case GL_BYTE: case GL_BITMAP: m = 1; break;
			case GL_UNSIGNED_SHORT: case GL_SHORT: m = 2; break;
			case GL_UNSIGNED_INT: case GL_INT: case GL_FLOAT: m = 4; break;
			default: m = 0;
		}

		return n*m;
	}
	protected static int nearestPower(int value) {
		int i = 1;

		if (value == 0) return -1;
		while (true) {
			if (value == 1) return i;
			if (value == 3) return i << 2;
			value >>= 1;
			i <<= 1;
		}
	}
	protected static int glGetIntegerv(int what) {
		scratch.rewind();
		glGetInteger(what,scratch);
		return scratch.get();
	}
	protected static int gluScaleImage(int format, int widthIn, int heightIn, int typein, ByteBuffer dataIn, int widthOut, int heightOut, int typeOut, ByteBuffer dataOut) {
		int components = compPerPix(format);
		if (components == -1) return 100900;

		float[] tempIn = new float[widthIn*heightIn*components];
		float[] tempOut = new float[widthOut*heightOut*components];
		int sizein;
		switch (typein) {
			case GL_UNSIGNED_BYTE: sizein = 1; break;
			case GL_FLOAT: sizein = 4; break;
			default: return GL_INVALID_ENUM;
		}
		int sizeout;
		switch (typeOut) {
			case GL_UNSIGNED_BYTE: sizeout = 1; break;
			case GL_FLOAT: sizeout = 4; break;
			default: return GL_INVALID_ENUM;
		}

		PixelStoreState pss = new PixelStoreState();
		int rowlen;
		if (pss.unpackRowLength > 0) rowlen = pss.unpackRowLength;
		else rowlen = widthIn;
		int rowstride;
		if (sizein >= pss.unpackAlignment) rowstride = components * rowlen;
		else rowstride = pss.unpackAlignment / sizein * ceil(components * rowlen * sizein,pss.unpackAlignment);
		int k;
		int i,j;
		switch (typein) {
			case GL_UNSIGNED_BYTE:
				k = 0;
				dataIn.rewind();
				for (i = 0; i < heightIn;) {
					int ubptr = i * rowstride + pss.unpackSkipRows * rowstride + pss.unpackSkipPixels * components;
					for (j = 0; j < widthIn * components; j++)
						tempIn[(k++)] = (dataIn.get(ubptr++) & 0xFF);
					i++;
					continue;
				}
			case GL_FLOAT:
		}
		float sx = widthIn / widthOut;
		float sy = heightIn / heightOut;

		float[] c = new float[components];

		for (int iy = 0; iy < heightOut; iy++) {
			for (int ix = 0; ix < widthOut; ix++) {
				int x0 = (int)(ix * sx);
				int x1 = (int)((ix + 1) * sx);
				int y0 = (int)(iy * sy);
				int y1 = (int)((iy + 1) * sy);

				int readPix = 0;

				for (int ic = 0; ic < components; ic++) c[ic] = 0.0F;

				for (int ix0 = x0; ix0 < x1; ix0++) {
					for (int iy0 = y0; iy0 < y1; iy0++) {
						int src = (iy0 * widthIn + ix0) * components;

						for (int ic = 0; ic < components; ic++) c[ic] += tempIn[(src + ic)];
						readPix++;
					}
				}

				int dst = (iy * widthOut + ix) * components;

				if (readPix == 0) {
					int src = (y0 * widthIn + x0) * components;
					for (int ic = 0; ic < components; ic++) tempOut[(dst++)] = tempIn[(src + ic)];
				} else for (k = 0; k < components; k++) tempOut[(dst++)] = (c[k] / readPix);
			}
		}

		if (pss.packRowLength > 0) rowlen = pss.packRowLength; else rowlen = widthOut;
		if (sizeout >= pss.packAlignment) rowstride = components * rowlen; else rowstride = pss.packAlignment / sizeout * ceil(components * rowlen * sizeout,pss.packAlignment);
		i = j = k = 0;
		switch (typeOut) {
			case GL_UNSIGNED_BYTE:
				k = 0;
				for (i = 0; i < heightOut;) {
					int ubptr = i * rowstride + pss.packSkipRows * rowstride + pss.packSkipPixels * components;

					for (j = 0; j < widthOut * components; j++)
						dataOut.put(ubptr++,(byte)(int)tempOut[(k++)]);
					i++;
					continue;
				}
			case GL_FLOAT:
		}
		return 0;
	}
	protected static int compPerPix(int format) {
		switch (format) {
			case GL_COLOR_INDEX: case GL_STENCIL_INDEX: case GL_DEPTH_COMPONENT: case GL_RED: case GL_GREEN: case GL_BLUE: case GL_ALPHA: case GL_LUMINANCE: return 1;
			case GL_LUMINANCE_ALPHA: return 2;
			case GL_RGB: case GL_BGR: return 3;
			case GL_RGBA: case GL_BGRA: return 4;
		}
		return -1;
	}
	protected static int ceil(int a, int b) {
		return a % b == 0 ? a / b : a / b + 1;
	}
	private static byte[] intToByteArray(int value) {
		return new byte[] {(byte)(value >>> 24),(byte)(value >>> 16),(byte)(value >>> 8),(byte)value};
	}
	
	public Texture texture() {
		return texture;
	}
	
	public Vector2i textureSize() {
		return texture().size();
	}
	public int textureWidth() {
		return texture().width();
	}
	public int textureHeight() {
		return texture().height();
	}
	public Rectangle textureRect() {
		return new Rectangle(0,0,textureWidth(),textureHeight());
	}
	
	public void drawTexture(Graphics g) {drawTexture(g,0,0);}
	public void drawTexture(Graphics g, Vector2 v) {drawTexture(g,v.Xd(),v.Yd());}
	public void drawTexture(Graphics g, double x, double y) {
		if (disposed()) throw new IllegalStateException("Texture already disposed");
		GL.bind(texture());
		if (x != 0 || y != 0) glTranslated(x,y,0);
		
		glBegin(GL_QUADS);
		Rectangle texRect = textureRect();
		internalDrawImage(0,0,texRect.size.x,texRect.size.y,texRect.pos.x/textureWidth(),texRect.pos.y/height(),texRect.size.x/textureWidth(),texRect.size.y/height());
		glEnd();
		
		if (x != 0 || y != 0) glTranslated(-x,-y,0);
		GL.unbindTexture();
	}
	private void internalDrawImage(double x, double y, double w, double h, double tx, double ty, double tw, double th) {
		glTexCoord2d(tx,ty);
		glVertex2d(x,y);
		glTexCoord2d(tx,ty+th);
		glVertex2d(x,y+h);
		glTexCoord2d(tx+tw,ty+th);
		glVertex2d(x+w,y+h);
		glTexCoord2d(tx+tw,ty);
		glVertex2d(x+w,y);
	}
	
	protected void finalize() {dispose();}
	public boolean disposed() {return texture().disposed();}
	public void dispose() {texture().dispose();}
	
	private static class PixelStoreState {
		public int unpackRowLength;
		public int unpackAlignment;
		public int unpackSkipRows;
		public int unpackSkipPixels;
		public int packRowLength;
		public int packAlignment;
		public int packSkipRows;
		public int packSkipPixels;

		PixelStoreState() {
			load();
		}

		public void load() {
			unpackRowLength = glGetIntegerv(3314);
			unpackAlignment = glGetIntegerv(3317);
			unpackSkipRows = glGetIntegerv(3315);
			unpackSkipPixels = glGetIntegerv(3316);
			packRowLength = glGetIntegerv(3330);
			packAlignment = glGetIntegerv(3333);
			packSkipRows = glGetIntegerv(3331);
			packSkipPixels = glGetIntegerv(3332);
		}

		public void save() {
			glPixelStorei(3314,unpackRowLength);
			glPixelStorei(3317,unpackAlignment);
			glPixelStorei(3315,unpackSkipRows);
			glPixelStorei(3316,unpackSkipPixels);
			glPixelStorei(3330,packRowLength);
			glPixelStorei(3333,packAlignment);
			glPixelStorei(3331,packSkipRows);
			glPixelStorei(3332,packSkipPixels);
		}
	}
	
	@Target(ElementType.FIELD) @Retention(RetentionPolicy.RUNTIME) public static @interface Loadable {
		public String name();
		public int size();
		public boolean bold() default false;
		public boolean italic() default false;
		public boolean antiAlias() default true;
		public char[] additionalChars() default {};
	}
}