package pl.shockah.glib.gl;

import static org.lwjgl.opengl.GL11.*;

public enum EResizeFilter {
	Nearest(GL_NEAREST), Linear(GL_LINEAR);
	
	private final int gl;
	
	EResizeFilter(int gl) {
		this.gl = gl;
	}
	
	public int getGLConst() {
		return gl;
	}
	public void set() {
		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,gl);
		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,gl);
	}
}