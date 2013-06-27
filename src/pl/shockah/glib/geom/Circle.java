package pl.shockah.glib.geom;

import pl.shockah.glib.geom.vector.Vector2d;

public class Circle extends Shape {
	public Vector2d pos;
	public double radius;
	
	public Circle(Vector2d pos, double radius) {
		this.pos = pos;
		this.radius = radius;
	}
	
	public boolean collides(Shape shape) {
		if (shape instanceof Circle) {
			Circle circle = (Circle)shape;
			return pos.distanceSquared(circle.pos) < Math.pow(radius+circle.radius,2);
		} else if (shape instanceof Rectangle) {
			return shape.collides(this);
		}
		return super.collides(shape);
	}
}