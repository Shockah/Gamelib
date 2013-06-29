package pl.shockah.glib.gl.font;

import static org.lwjgl.opengl.GL11.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.glu.GLU;
import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.geom.vector.Vector2i;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.ITextureSupplier;
import pl.shockah.glib.gl.tex.Texture;

/*
 * A TrueType font implementation originally for Slick, edited for Bobjob's Engine
 * Authors: James Chambers (Jimmy), Jeremy Adams (elias4444), Kevin Glass (kevglass), Peter Korzuszek (genail), David Aaron Muhar (bobjob)
 */
public class TrueTypeFont extends pl.shockah.glib.gl.font.Font implements ITextureSupplier {
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
	
	public void setCorrection(boolean on) {
		correctL = on ? 2 : 0;
	}
	private BufferedImage getFontImage(char ch) {
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
				BufferedImage fontImage = getFontImage(ch);
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
	
	private void drawQuad(float drawX, float drawY, float drawX2, float drawY2, float srcX, float srcY, float srcX2, float srcY2) {
		float DrawWidth = drawX2-drawX;
		float DrawHeight = drawY2-drawY;
		float TextureSrcX = srcX/texture.getWidth();
		float TextureSrcY = srcY/texture.getHeight();
		float SrcWidth = srcX2-srcX;
		float SrcHeight = srcY2-srcY;
		float RenderWidth = SrcWidth/texture.getWidth();
		float RenderHeight = SrcHeight/texture.getHeight();

		glTexCoord2f(TextureSrcX,TextureSrcY+RenderHeight);
		glVertex2f(drawX,drawY);
		glTexCoord2f(TextureSrcX,TextureSrcY);
		glVertex2f(drawX,drawY+DrawHeight);
		glTexCoord2f(TextureSrcX+RenderWidth,TextureSrcY);
		glVertex2f(drawX+DrawWidth,drawY+DrawHeight);
		glTexCoord2f(TextureSrcX+RenderWidth,TextureSrcY+RenderHeight);
		glVertex2f(drawX+DrawWidth,drawY);
	}

	public int getWidth(String whatchars) {
		int totalwidth = 0;
		IntObject intObject = null;
		int currentChar = 0;
		for (int i = 0; i < whatchars.length(); i++) {
			currentChar = whatchars.charAt(i);
			intObject = currentChar < 256 ? charArray[currentChar] : customChars.get((char)currentChar);
			if (intObject != null) totalwidth += intObject.width;
		}
		return totalwidth;
	}

	public int getHeight() {
		return fontHeight;
	}
	public int getHeight(String HeightString) {
		return fontHeight;
	}
	public int getLineHeight() {
		return fontHeight;
	}
	
	public void draw(Graphics g, Vector2d v, CharSequence text) {
		draw(g,v.x,v.y,text);
	}
	public void draw(Graphics g, double x, double y, CharSequence text) {
		drawString((float)x,(float)y,text,0,text.length()-1,1f,1f);
	}
	public void drawString(float x, float y, CharSequence whatchars, int startIndex, int endIndex, float scaleX, float scaleY) {
		IntObject intObject = null;
		int charCurrent;
		
		int totalwidth = 0;
		int i = startIndex, d, c;
		float startY = 0;
		
		d = 1;
		c = correctL;
		
		texture.bind();
		glBegin(GL_QUADS);
		
		while (i >= startIndex && i <= endIndex) {
			charCurrent = whatchars.charAt(i);
			intObject = charCurrent < 256 ? charArray[charCurrent] : customChars.get((char)charCurrent);
			
			if (intObject != null) {
				if (d < 0) totalwidth += (intObject.width-c)*d;
				if (charCurrent == '\n') {
					startY -= fontHeight * d;
					totalwidth = 0;
				} else {
					drawQuad((totalwidth+intObject.width)*scaleX+x,startY*scaleY+y,totalwidth*scaleX+x,
						(startY+intObject.height)*scaleY+y,intObject.storedX+intObject.width,intObject.storedY+intObject.height,intObject.storedX,intObject.storedY);
					if (d > 0) totalwidth += (intObject.width-c)*d;
				}
				i += d;
			}
		}
		glEnd();
		texture.unbind();
	}
	
	private static int loadImage(BufferedImage bufferedImage) {
		try {
			short width	= (short)bufferedImage.getWidth();
			short height = (short)bufferedImage.getHeight();
			int bpp = (byte)bufferedImage.getColorModel().getPixelSize();
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
				
				byteBuffer = ByteBuffer.allocateDirect(width*height*(bpp/8)).order(ByteOrder.nativeOrder()).put(newI);
			} else byteBuffer = ByteBuffer.allocateDirect(width*height*(bpp/8)).order(ByteOrder.nativeOrder()).put(((DataBufferByte)(bufferedImage.getData().getDataBuffer())).getData());
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
			GLU.gluBuild2DMipmaps(GL_TEXTURE_2D,internalFormat,width,height,format,GL_UNSIGNED_BYTE,byteBuffer);
			return textureId.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	private static byte[] intToByteArray(int value) {
		return new byte[] {(byte)(value >>> 24),(byte)(value >>> 16),(byte)(value >>> 8),(byte)value};
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public Vector2i getTextureSize() {
		return getTexture().getSize();
	}
	public int getTextureWidth() {
		return getTexture().getWidth();
	}
	public int getTextureHeight() {
		return getTexture().getHeight();
	}
	public Rectangle getTextureRect() {
		return new Rectangle(0,0,getTextureWidth(),getTextureHeight());
	}
	
	public void drawTexture(Graphics g) {drawTexture(g,0,0);}
	public void drawTexture(Graphics g, Vector2d v) {drawTexture(g,v.x,v.y);}
	public void drawTexture(Graphics g, double x, double y) {
		g.init();
		getTexture().bind();
		glTranslated(x,y,0);
		
		glBegin(GL_QUADS);
		Rectangle texRect = getTextureRect();
		internalDrawImage(0,0,texRect.size.x,texRect.size.y,texRect.pos.x/getTextureWidth(),texRect.pos.y/getHeight(),texRect.size.x/getTextureWidth(),texRect.size.y/getHeight());
		glEnd();
		
		glTranslated(-x,-y,0);
		getTexture().unbind();
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
}