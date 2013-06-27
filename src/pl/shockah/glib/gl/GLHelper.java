package pl.shockah.glib.gl;

import static org.lwjgl.opengl.GL11.*;

public final class GLHelper {
	public static void initDisplay(int width, int height) {
		glEnable(GL_TEXTURE_2D);
		glShadeModel(GL_SMOOTH);
		glDisable(GL_DEPTH_TEST);
		glDisable(GL_LIGHTING);
		
		glClearColor(0f,0f,0f,0f);
		glClearDepth(1f);
		
		BlendMode.Normal.apply();
		glViewport(0,0,width,height);
		glMatrixMode(GL_MODELVIEW);
	}
	
	public static void enterOrtho(int width, int height) {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0,width,height,0,1,-1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glViewport(0,0,width,height);
	}
}