package pl.shockah.glib.gl.tex;

import static org.lwjgl.opengl.GL11.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Hashtable;
import javax.imageio.ImageIO;
import pl.shockah.glib.geom.vector.Vector2i;
import pl.shockah.glib.gl.EResizeFilter;
import pl.shockah.glib.gl.GL;

public class ImageIOTextureLoader extends TextureLoader {
	protected static ColorModel
		glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),new int[]{8,8,8,8},true,false,ComponentColorModel.TRANSLUCENT,DataBuffer.TYPE_BYTE),
		glColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB),new int[]{8,8,8,0},false,false,ComponentColorModel.OPAQUE,DataBuffer.TYPE_BYTE);
	
	public ImageIOTextureLoader(String... formats) {
		super(formats);
	}
	
	public Texture load(InputStream is) throws IOException {
		BufferedImage bi = ImageIO.read(is);
		Vector2i fold = Texture.get2Fold(bi.getWidth(),bi.getHeight());
		ByteBuffer bb = convertImageData(bi,fold);
		
		int texId = glGenTextures();
		
		Texture texture = new Texture(texId,bi.getWidth(),bi.getHeight());
		texture.setResizeFilter(EResizeFilter.Linear);
		GL.bind(texture);
		glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA,fold.x,fold.y,0,bi.getColorModel().hasAlpha() ? GL_RGBA : GL_RGB,GL_UNSIGNED_BYTE,bb);
		GL.unbindTexture();
		
		return texture;
	}
	
	private ByteBuffer convertImageData(BufferedImage bi, Vector2i fold) {
		WritableRaster raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE,fold.x,fold.y,bi.getColorModel().hasAlpha() ? 4 : 3,null);
		BufferedImage texImage = new BufferedImage(bi.getColorModel().hasAlpha() ? glAlphaColorModel : glColorModel,raster,false,new Hashtable<>());
		
		Graphics g = texImage.getGraphics();
		g.setColor(new Color(0f,0f,0f,0f));
		g.fillRect(0,0,fold.x,fold.y);
		g.drawImage(bi,0,0,null);
		
		byte[] data = ((DataBufferByte)texImage.getRaster().getDataBuffer()).getData();
		if (bi.getColorModel().hasAlpha() && settings.containsKey("toPremultiplied") && (boolean)settings.get("toPremultiplied")) {
			for (int i = 0; i < data.length; i += 4) {
				byte a = data[i+3];
				for (int j = 0; j <= 2; j++) data[i+j] = (byte)(data[i+j]*(a/255f));
			}
		}
		
		ByteBuffer bb = ByteBuffer.allocateDirect(data.length);
		bb.order(ByteOrder.nativeOrder());
		bb.put(data,0,data.length);
		bb.flip();
		return bb;
	}
}