package pl.shockah.glib.gl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.EXTFramebufferObject.*;
import java.nio.ByteBuffer;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.EXTPackedDepthStencil;
import pl.shockah.glib.geom.vector.Vector2i;
import pl.shockah.glib.gl.tex.Image;
import pl.shockah.glib.gl.tex.Texture;

public class Surface {
	public static Surface create(Vector2i v) {
		return create(v.x,v.y);
	}
	public static Surface create(int w, int h) {
		int surId = glGenFramebuffersEXT();
		int texId = glGenTextures();
		int rbo = glGenRenderbuffersEXT();
		Texture tex = new Texture(texId,w,h);
		Vector2i fold = Texture.get2Fold(w,h);
		Surface sur = new Surface(surId,new Image(tex));
		
		GL.unbindTexture();
		EXTFramebufferObject.glBindFramebufferEXT(GL_FRAMEBUFFER_EXT,surId);
		glBindTexture(GL_TEXTURE_2D,texId);
		glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_LINEAR);
		glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_LINEAR);
		glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA8,fold.x,fold.y,0,GL_RGBA,GL_UNSIGNED_BYTE,(ByteBuffer)null);
		EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT,EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT,GL_TEXTURE_2D,texId,0);
		EXTFramebufferObject.glBindRenderbufferEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT,rbo);
		EXTFramebufferObject.glRenderbufferStorageEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT,EXTPackedDepthStencil.GL_DEPTH_STENCIL_EXT,fold.x,fold.y);
		EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT,EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT,EXTFramebufferObject.GL_RENDERBUFFER_EXT,rbo);
		EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT,EXTFramebufferObject.GL_STENCIL_ATTACHMENT_EXT,EXTFramebufferObject.GL_RENDERBUFFER_EXT,rbo);
		glBindTexture(GL_TEXTURE_2D,0);
		EXTFramebufferObject.glBindFramebufferEXT(GL_FRAMEBUFFER_EXT,0);
		
		sur.graphics().clear();
		
		return sur;
	}
	
	private final int surId;
	private boolean disposed = false;
	public final Image image;
	public final Graphics g;
	
	public Surface(int surId, Image image) {
		this.surId = surId;
		this.image = image;
		g = new GraphicsSurface(this);
	}
	
	public boolean equals(Object other) {
		if (!(other instanceof Surface)) return false;
		Surface sur = (Surface)other;
		return sur.surId == surId;
	}
	
	public int getID() {
		if (disposed) throw new IllegalStateException("Surface already disposed");
		return surId;
	}
	
	public Image image() {
		if (disposed) throw new IllegalStateException("Surface already disposed");
		return image;
	}
	public Graphics graphics() {
		if (disposed) throw new IllegalStateException("Surface already disposed");
		return g;
	}
	
	public void dispose() {
		glDeleteFramebuffersEXT(surId);
		image.dispose();
		disposed = true;
	}
}