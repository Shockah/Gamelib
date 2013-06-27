package pl.shockah.glib.geom;

import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.Graphics;

public abstract class Shape {
	public abstract Rectangle getBoundingBox();
	
	public boolean collides(Shape shape) {
		return collides(shape,false);
	}
	protected boolean collides(Shape shape, boolean secondTry) {
		throw new UnsupportedOperationException();
	}
	
	public final void draw(Graphics g) {draw(g,true,0,0);}
	public final void draw(Graphics g, Vector2d v) {draw(g,true,v.x,v.y);}
	public final void draw(Graphics g, double x, double y) {draw(g,true,x,y);}
	public final void draw(Graphics g, boolean filled) {draw(g,filled,0,0);}
	public final void draw(Graphics g, boolean filled, Vector2d v) {draw(g,filled,v.x,v.y);}
	public void draw(Graphics g, boolean filled, double x, double y) {
		throw new UnsupportedOperationException();
	}
}