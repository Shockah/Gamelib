package pl.shockah.glib.gl;

import static org.lwjgl.opengl.GL11.*;

public class GraphicsGLList extends Graphics {
	private final GLList list;
	
	public GraphicsGLList(GLList list) {
		this.list = list;
	}
	
	public void preDraw() {
		if (list.finalized) throw new IllegalStateException("GLList already finalized");
		super.preDraw();
		glNewList(list.getID(),GL_COMPILE);
		list.compiling = true;
	}
	
	protected void onUnbind() {
		if (list.finalized) return;
		glEndList();
		list.finalized = true;
		list.compiling = false;
	}
}