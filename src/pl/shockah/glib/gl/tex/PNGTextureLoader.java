package pl.shockah.glib.gl.tex;

import static org.lwjgl.opengl.GL11.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import de.matthiasmann.twl.utils.PNGDecoder;

public class PNGTextureLoader extends TextureLoader {
	public PNGTextureLoader() {
		super("PNG");
	}
	
	public Texture load(InputStream is) throws IOException {
		PNGDecoder decoder = new PNGDecoder(is);
		
		ByteBuffer bb = BufferUtils.createByteBuffer(decoder.getWidth()*decoder.getHeight()*(decoder.hasAlpha() ? 4 : 3));
		decoder.decode(bb,decoder.getWidth()*(decoder.hasAlpha() ? 4 : 3),decoder.hasAlpha() ? PNGDecoder.Format.RGBA : PNGDecoder.Format.RGB);
		bb.flip();
		
		int texId = glGenTextures();
		
		Texture texture = new Texture(texId,decoder.getWidth(),decoder.getHeight());
		texture.bind();
		texture.setResizeFilter(Texture.EResizeFilter.Linear);
		glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA,decoder.getWidth(),decoder.getHeight(),0,decoder.hasAlpha() ? GL_RGBA : GL_RGB,GL_UNSIGNED_BYTE,bb);
		texture.unbind();
		
		return texture;
	}
}