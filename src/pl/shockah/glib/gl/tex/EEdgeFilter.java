package pl.shockah.glib.gl.tex;

import static org.lwjgl.opengl.GL11.*;

public enum EEdgeFilter {
	Clamp(GL_CLAMP), Repeat(GL_REPEAT);
	
	private final int gl;
	
	EEdgeFilter(int gl) {
		this.gl = gl;
	}
	
	public int getGLConst() {
		return gl;
	}
	public void set() {
		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_S,gl);
		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_T,gl);
	}
}