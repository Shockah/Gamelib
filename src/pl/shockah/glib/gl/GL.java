package pl.shockah.glib.gl;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.EXTFramebufferObject;

import pl.shockah.glib.Debug;
import pl.shockah.glib.Gamelib;
import pl.shockah.glib.gl.color.Color;
import pl.shockah.glib.gl.tex.Texture;

public final class GL {
	private static boolean flipped = false, pushed = false;
	private static Texture boundTexture = null;
	private static Surface boundSurface = null;
	private static Shader boundShader = null;
	private static float thickness = 1;
	
	public static void setup() {
		glEnable(GL_LINE_SMOOTH);
	}
	
	public static void initDisplay(int width, int height) {
		initDisplay(width,height,true);
	}
	public static void initDisplay(int width, int height, boolean resetBlending) {
		glEnable(GL_TEXTURE_2D);
		glClearColor(0f,0f,0f,0f);
		
		if (resetBlending) Graphics.getDefaultBlendMode().apply();
		glViewport(0,0,width,height);
		glMatrixMode(GL_MODELVIEW);
	}
	
	public static void enterOrtho(int width, int height) {
		enterOrtho(width,height,true);
	}
	public static void enterOrtho(int width, int height, boolean flipped) {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0,width,flipped ? height : 0,flipped? 0 : height,1,-1);
		glMatrixMode(GL_MODELVIEW);
		GL.flipped = flipped;
	}
	public static boolean flipped() {
		return flipped;
	}
	
	public static void color4f(Color c) {
		glColor4f(c.Rf(),c.Gf(),c.Bf(),c.Af());
	}
	
	public static void bind(Texture tex) {
		if (tex == null) {
			unbindTexture();
			return;
		}
		if (boundTexture != null && boundTexture.getID() == tex.getID()) return;
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D,tex.getID());
		if (boundShader != null) boundShader.handleTexturing(true);
		boundTexture = tex;
		Debug.current.bindTexture++;
	}
	public static void bind(Surface sur) {
		if (sur == null) {
			unbindSurface();
			return;
		}
		if (boundSurface != null && boundSurface.getID() == sur.getID()) return;
		unbindTexture();
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT,sur.getID());
		initDisplay(sur.image.getTextureWidth(),sur.image.getTextureHeight(),false);
		enterOrtho(sur.image.getTextureWidth(),sur.image.getTextureHeight(),false);
		boundSurface = sur;
		Debug.current.bindSurface++;
	}
	public static void bind(Shader sdr) {
		if (sdr == null) {
			unbindShader();
			return;
		}
		if (boundShader != null && boundShader.getID() == sdr.getID()) return;
		ARBShaderObjects.glUseProgramObjectARB(sdr.getID());
		boundShader = sdr;
		boundShader.handleTexturing(boundTexture != null);
		Debug.current.bindShader++;
	}
	
	public static void unbindTexture() {
		if (boundTexture == null) return;
		glBindTexture(GL_TEXTURE_2D,0);
		glDisable(GL_TEXTURE_2D);
		if (boundShader != null) boundShader.handleTexturing(false);
		boundTexture = null;
		Debug.current.bindTexture++;
	}
	public static void unbindSurface() {
		if (boundSurface == null) return;
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT,0);
		initDisplay(Gamelib.cachedDisplayMode.getWidth(),Gamelib.cachedDisplayMode.getHeight(),false);
		enterOrtho(Gamelib.cachedDisplayMode.getWidth(),Gamelib.cachedDisplayMode.getHeight());
		boundSurface = null;
		Debug.current.bindSurface++;
	}
	public static void unbindShader() {
		if (boundShader == null) return;
		ARBShaderObjects.glUseProgramObjectARB(0);
		boundShader = null;
		Debug.current.bindShader++;
	}
	
	public static void unbind() {
		if (boundShader != null) unbindShader();
		if (boundTexture != null) unbindTexture();
		if (boundSurface != null) unbindSurface();
	}
	
	public static Texture boundTexture() {return boundTexture;}
	public static Surface boundSurface() {return boundSurface;}
	public static Shader boundShader() {return boundShader;}
	
	public static void setThickness(float thickness) {
		glLineWidth(thickness);
		GL.thickness = thickness;
	}
	public static float getThickness() {
		return thickness;
	}
	
	public static void pushMatrixOnce() {
		if (pushed) return;
		glPushMatrix();
		pushed = true;
	}
	public static void popMatrixOnce() {
		if (!pushed) return;
		glPopMatrix();
		pushed = false;
	}
	public static boolean pushedMatrix() {
		return pushed;
	}
}