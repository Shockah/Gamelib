package pl.shockah.glib.geom;

import pl.shockah.glib.geom.vector.Vector2d;

public class Circle extends Shape {
	public Vector2d pos;
	public double radius;
	
	public Circle(Vector2d pos, double radius) {
		this.pos = pos;
		this.radius = radius;
	}
	
	protected boolean collides(Shape shape, boolean secondTry) {
		if (shape instanceof Circle) {
			Circle circle = (Circle)shape;
			return pos.distanceSquared(circle.pos) < Math.pow(radius+circle.radius,2);
		} else if (shape instanceof Rectangle) {
			if (secondTry) return super.collides(shape);
			return shape.collides(this,true);
		}
		return super.collides(shape);
	}
}