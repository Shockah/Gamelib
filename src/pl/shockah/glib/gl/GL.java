package pl.shockah.glib.gl;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.EXTFramebufferObject;
import pl.shockah.glib.Debug;
import pl.shockah.glib.Gamelib;
import pl.shockah.glib.geom.vector.IVector2;
import pl.shockah.glib.gl.color.Color;
import pl.shockah.glib.gl.tex.Texture;

public final class GL {
	private static boolean flipped = false;
	private static int pushed = 0;
	private static Texture boundTexture = null;
	private static Surface boundSurface = null;
	private static Shader boundShader = null;
	private static float thickness = 1;
	private static boolean[] masking = new boolean[4];
	
	static {
		for (int i = 0; i < masking.length; i++) masking[i] = true;
	}
	
	public static void setup() {
		glEnable(GL_LINE_SMOOTH);
	}
	
	public static void initDisplay(int width, int height) {
		initDisplay(width,height,true);
	}
	public static void initDisplay(int width, int height, boolean resetBlending) {
		if (resetBlending) Graphics.getDefaultBlendMode().apply();
		glViewport(0,0,width,height);
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
	
	public static void colorMask(boolean r, boolean g, boolean b, boolean a) {
		if (masking[0] != r || masking[1] != g || masking[2] != b || masking[3] != a) {
			masking[0] = r;
			masking[1] = g;
			masking[2] = b;
			masking[3] = a;
			glColorMask(masking[0],masking[1],masking[2],masking[3]);
		}
	}
	public static void color4f(Color c) {
		Graphics.setColor(c);
	}
	public static void translated(IVector2 v) {
		translated(v.Xd(),v.Yd());
	}
	public static void translated(double x, double y) {
		glTranslated(x,y,0);
	}
	
	public static void bind(Texture tex) {
		if (tex == null) {
			unbindTexture();
			return;
		}
		if (boundTexture != null && boundTexture.getID() == tex.getID()) return;
		if (boundTexture == null) glEnable(GL_TEXTURE_2D);
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
	
	public static void pushMatrix() {
		glPushMatrix();
		pushed++;
	}
	public static void popMatrix() {
		if (pushed == 0) return;
		glPopMatrix();
		pushed--;
	}
	public static boolean pushedMatrix() {
		return pushed > 0;
	}
	public static void loadIdentity() {
		glLoadIdentity();
		pushed = 0;
	}
}