package pl.shockah.glib.gl;

import pl.shockah.glib.geom.Shape;
import pl.shockah.glib.geom.vector.Vector2d;
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
	
	public void draw(Shape shape) {draw(shape,0,0);}
	public void draw(Shape shape, boolean filled) {draw(shape,filled,0,0);}
	public void draw(Shape shape, Vector2d v) {draw(shape,v.x,v.y);}
	public void draw(Shape shape, double x, double y) {draw(shape,true,0,0);}
	public void draw(Shape shape, boolean filled, Vector2d v) {draw(shape,filled,v.x,v.y);}
	public void draw(Shape shape, boolean filled, double x, double y) {
		shape.draw(this,filled,x,y);
	}
	
	public void draw(TextureSupplier ts) {draw(ts,0,0);}
	public void draw(TextureSupplier ts, Vector2d v) {draw(ts,v.x,v.y);}
	public void draw(TextureSupplier ts, double x, double y) {
		ts.draw(this,x,y);
	}
}