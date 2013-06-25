package pl.shockah.glib.gl.texload;

import static org.lwjgl.opengl.GL11.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import pl.shockah.glib.gl.Texture;
import de.matthiasmann.twl.utils.PNGDecoder;

public class PNGTextureLoader extends TextureLoader {
	public PNGTextureLoader(String... formats) {
		super("PNG");
	}
	
	public Texture load(InputStream is) throws IOException {
		PNGDecoder decoder = new PNGDecoder(is);
		
		ByteBuffer bb = ByteBuffer.allocateDirect(4*decoder.getWidth()*decoder.getHeight());
		decoder.decode(bb,decoder.getWidth()*4,PNGDecoder.Format.RGBA);
		bb.flip();
		
		int texId = glGenTextures();
		glBindTexture(GL_TEXTURE_2D,texId);
		glPixelStorei(GL_UNPACK_ALIGNMENT,1);
		glTexImage2D(GL_TEXTURE_2D,0,GL_RGB,decoder.getWidth(),decoder.getHeight(),0,GL_RGBA,GL_UNSIGNED_BYTE,bb);
		glBindTexture(GL_TEXTURE_2D,0);
		
		return new Texture(texId,decoder.getWidth(),decoder.getHeight());
	}
}