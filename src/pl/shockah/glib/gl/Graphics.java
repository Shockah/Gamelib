package pl.shockah.glib.gl;

import static org.lwjgl.opengl.GL11.*;
import pl.shockah.glib.geom.Shape;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.geom.vector.Vector2f;
import pl.shockah.glib.geom.vector.Vector2i;
import pl.shockah.glib.gl.color.Color;

public class Graphics {
	private static boolean init = false;
	private static Color color = null;
	
	public void init() {
		if (init) return;
		init = true;
		
		if (color == null) setColor(Color.White);
	}
	
	public void setColor(Color color) {
		if (Graphics.color != null && !Graphics.color.equals(color)) color.unbind();
		Graphics.color = color;
		color.bind();
	}
	
	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public void draw(Shape shape) {draw(shape,0,0);}
	public void draw(Shape shape, boolean filled) {draw(shape,filled,0,0);}
	public void draw(Shape shape, Vector2d v) {draw(shape,v.x,v.y);}
	public void draw(Shape shape, Vector2f v) {draw(shape,v.x,v.y);}
	public void draw(Shape shape, Vector2i v) {draw(shape,v.x,v.y);}
	public void draw(Shape shape, double x, double y) {draw(shape,true,0,0);}
	public void draw(Shape shape, boolean filled, Vector2d v) {draw(shape,filled,v.x,v.y);}
	public void draw(Shape shape, boolean filled, double x, double y) {
		shape.draw(this,filled,x,y);
	}
	
	public void draw(ITextureSupplier ts) {draw(ts,0,0);}
	public void draw(ITextureSupplier ts, Vector2d v) {draw(ts,v.x,v.y);}
	public void draw(ITextureSupplier ts, Vector2f v) {draw(ts,v.x,v.y);}
	public void draw(ITextureSupplier ts, Vector2i v) {draw(ts,v.x,v.y);}
	public void draw(ITextureSupplier ts, double x, double y) {
		ts.drawTexture(this,x,y);
	}
}