package pl.shockah.glib.gl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.EXTFramebufferObject.*;
import pl.shockah.glib.Gamelib;
import pl.shockah.glib.gl.tex.Texture;

public final class GL {
	private static int boundTexture = 0, boundSurface = 0;
	
	public static void initDisplay(int width, int height) {
		glEnable(GL_TEXTURE_2D);
		glClearColor(0f,0f,0f,0f);
		
		BlendMode.Normal.apply();
		glViewport(0,0,width,height);
		glMatrixMode(GL_MODELVIEW);
	}
	
	public static void enterOrtho(int width, int height) {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0,width,height,0,1,-1);
		glMatrixMode(GL_MODELVIEW);
	}
	
	public static void bind(Texture tex) {
		if (boundTexture == tex.getID()) return;
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D,tex.getID());
		boundTexture = tex.getID();
	}
	public static void bind(Surface sur) {
		if (boundSurface == sur.getID()) return;
		unbindTexture();
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT,sur.getID());
		initDisplay(sur.image.getTextureWidth(),sur.image.getTextureHeight());
		enterOrtho(sur.image.getTextureWidth(),sur.image.getTextureHeight());
		boundSurface = sur.getID();
	}
	
	public static void unbindTexture() {
		if (boundTexture == 0) return;
		glBindTexture(GL_TEXTURE_2D,0);
		glDisable(GL_TEXTURE_2D);
		boundTexture = 0;
	}
	public static void unbindSurface() {
		if (boundSurface == 0) return;
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT,0);
		initDisplay(Gamelib.cachedDisplayMode.getWidth(),Gamelib.cachedDisplayMode.getHeight());
		enterOrtho(Gamelib.cachedDisplayMode.getWidth(),Gamelib.cachedDisplayMode.getHeight());
		boundSurface = 0;
	}
	
	public static void unbind() {
		if (boundTexture != 0) unbindTexture();
		if (boundSurface != 0) unbindSurface();
	}
}