package pl.shockah.glib.gl;

import static org.lwjgl.opengl.GL11.*;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.geom.vector.Vector2f;
import pl.shockah.glib.geom.vector.Vector2i;

public class GLList {
	public static GLList create() {
		int listId = glGenLists(1);
		return new GLList(listId);
	}
	
	private final int listId;
	private boolean disposed = false;
	protected boolean compiling = false, finalized = false;
	public final Graphics g;
	
	public GLList(int listId) {
		this.listId = listId;
		g = new GraphicsGLList(this);
	}
	
	public boolean equals(Object other) {
		if (!(other instanceof GLList)) return false;
		GLList gll = (GLList)other;
		return gll.listId == listId;
	}
	
	public int getID() {
		if (disposed) throw new IllegalStateException("GLList already disposed");
		return listId;
	}
	
	public Graphics graphics() {
		if (disposed) throw new IllegalStateException("GLList already disposed");
		if (finalized) throw new IllegalStateException("GLList already finalized");
		return g;
	}
	
	public void draw(Graphics g) {g.draw(this);}
	public void draw(Graphics g, Vector2d v) {g.draw(this,v);}
	public void draw(Graphics g, Vector2f v) {g.draw(this,v);}
	public void draw(Graphics g, Vector2i v) {g.draw(this,v);}
	public void draw(Graphics g, double x, double y) {g.draw(this,x,y);}
	
	public void dispose() {
		if (compiling) g.onUnbind();
		glDeleteLists(getID(),1);
		disposed = true;
	}
}