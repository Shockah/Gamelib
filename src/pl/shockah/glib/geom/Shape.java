package pl.shockah.glib.geom;

import pl.shockah.glib.geom.vector.Vector2;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.Graphics;

public abstract class Shape {
	public abstract Shape copy();
	
	public abstract Rectangle getBoundingBox();
	
	public Vector2d translate(Vector2 v) {return translate(v.Xd(),v.Yd());}
	public abstract Vector2d translate(double x, double y);
	public Vector2d translateTo(Vector2 v) {return translateTo(v.Xd(),v.Yd());}
	public abstract Vector2d translateTo(double x, double y);
	
	public final boolean collides(Shape shape) {
		return collides(shape,false);
	}
	protected boolean collides(Shape shape, boolean secondTry) {
		throw new UnsupportedOperationException();
	}
	
	public void draw(Graphics g) {draw(g,true);}
	public void draw(Graphics g, boolean filled) {
		throw new UnsupportedOperationException();
	}
}