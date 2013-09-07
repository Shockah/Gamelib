package pl.shockah.glib.gl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.EXTFramebufferObject.*;
import java.nio.ByteBuffer;
import pl.shockah.glib.geom.vector.Vector2i;
import pl.shockah.glib.gl.tex.Texture;

public class Surface {
	public static Surface create(Vector2i v) {
		return create(v.x,v.y);
	}
	public static Surface create(int w, int h) {
		int surId = glGenFramebuffersEXT();
		int texId = glGenTextures();
		Texture tex = new Texture(texId,w,h);
		Vector2i fold = Texture.get2Fold(w,h);
		Surface sur = new Surface(surId,new Image(tex));
		
		GL.unbindTexture();
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT,surId);
		glBindTexture(GL_TEXTURE_2D,texId);
		glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_LINEAR);
		glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_LINEAR);
		glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA8,fold.x,fold.y,0,GL_RGBA,GL_UNSIGNED_BYTE,(ByteBuffer)null);
		glFramebufferTexture2DEXT(GL_FRAMEBUFFER_EXT,GL_COLOR_ATTACHMENT0_EXT,GL_TEXTURE_2D,texId,0);
		glBindTexture(GL_TEXTURE_2D,0);
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT,0);
		
		sur.graphics().clear();
		
		return sur;
	}
	
	private final int surId;
	public final Image image;
	
	public Surface(int surId, Image image) {
		this.surId = surId;
		this.image = image;
	}
	
	public boolean equals(Object other) {
		if (!(other instanceof Surface)) return false;
		Surface sur = (Surface)other;
		return sur.surId == surId;
	}
	
	public int getID() {return surId;}
	
	public Graphics graphics() {
		return new GraphicsSurface(this);
	}
}