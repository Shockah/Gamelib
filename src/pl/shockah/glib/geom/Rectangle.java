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
	public Rectangle(Rectangle rect) {
		pos = rect.pos;
		size = rect.size;
	}
	
	public Rectangle getBoundingBox() {
		return new Rectangle(this);
	}
	
	protected boolean collides(Shape shape, boolean secondTry) {
		if (shape instanceof Rectangle) {
			Rectangle rect = (Rectangle)shape;
			Vector2d v = pos.add(size.div(2)).sub(rect.pos.add(rect.size.div(2))).abs().sub(size.div(2).add(rect.size.div(2)));
			return v.x < 0 && v.y < 0;
		} else if (shape instanceof Circle) {
			Circle circle = (Circle)shape;
			return circle.pos.sub(circle.pos.sub(pos.add(size.div(2))).sub(size.div(2))).lengthSquared()-Math.pow(circle.radius,2) < 0;
		}
		return super.collides(shape);
	}
	
	public Polygon asPolygon() {
		return new Polygon()
			.addPoint(pos)
			.addPoint(pos.add(size.justX()))
			.addPoint(pos.add(size))
			.addPoint(pos.add(size.justY()));
	}
}