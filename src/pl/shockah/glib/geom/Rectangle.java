package pl.shockah.glib.geom;

import pl.shockah.glib.geom.vector.Vector2d;

public class Rectangle extends Shape {
	public Vector2d pos, size;
	
	public Rectangle(double x, double y, double w, double h) {
		pos = new Vector2d(x,y);
		size = new Vector2d(w,h);
	}
	public Rectangle(Vector2d pos, Vector2d size) {
		this.pos = pos;
		this.size = size;
	}
}