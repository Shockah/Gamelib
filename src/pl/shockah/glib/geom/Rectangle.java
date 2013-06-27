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
	
	protected boolean collides(Shape shape, boolean secondTry) {
		if (shape instanceof Rectangle) {
			Rectangle rect = (Rectangle)shape;
			Vector2d v = ((pos+size/2)-(rect.pos+rect.size/2)).abs()-(size/2+rect.size/2);
			return v.x < 0 && v.y < 0;
		} else if (shape instanceof Circle) {
			Circle circle = (Circle)shape;
			return (circle.pos-((circle.pos-(pos+size/2))-size/2)).lengthSquared()-Math.pow(circle.radius,2) < 0;
		}
		return super.collides(shape);
	}
}