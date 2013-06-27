package pl.shockah.glib.geom;

import pl.shockah.glib.geom.polygon.Polygon;
import pl.shockah.glib.geom.vector.Vector2d;

public class Circle extends Shape {
	public Vector2d pos;
	public double radius;
	
	public Circle(double x, double y, double radius) {
		pos = new Vector2d(x,y);
		this.radius = radius;
	}
	public Circle(Vector2d pos, double radius) {
		this.pos = pos;
		this.radius = radius;
	}
	public Circle(Circle circle) {
		pos = circle.pos;
		radius = circle.radius;
	}
	
	public Rectangle getBoundingBox() {
		return new Rectangle(pos.x-radius,pos.y-radius,radius*2,radius*2);
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
	
	public Polygon asPolygon() {
		return asPolygon((int)Math.ceil(Math.PI*radius/4));
	}
	public Polygon asPolygon(int precision) {
		Polygon p = new Polygon.NoHoles();
		for (int i = 0; i < precision; i++) p.addPoint(Vector2d.make(radius,360d/precision*i).add(pos));
		return p;
	}
}