package pl.shockah.glib.gl;

import static org.lwjgl.opengl.GL11.*;

public class GraphicsGLList extends Graphics {
	private final GLList list;
	
	public GraphicsGLList(GLList list) {
		this.list = list;
	}
	
	protected void onBind() {
		if (list.finalized) throw new IllegalStateException("GLList already finalized");
		if (list.disposed()) throw new IllegalStateException("GLList already disposed");
		glNewList(list.id,GL_COMPILE);
		list.compiling = true;
	}
	
	protected void onUnbind() {
		if (list.finalized) return;
		glEndList();
		list.finalized = true;
		list.compiling = false;
	}
}