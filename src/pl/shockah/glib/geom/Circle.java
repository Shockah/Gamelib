package pl.shockah.glib.geom;

import pl.shockah.glib.geom.polygon.IPolygonable;
import pl.shockah.glib.geom.polygon.Polygon;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.Graphics;

public class Circle extends Shape implements IPolygonable {
	public Vector2d pos;
	public double radius;
	
	protected Vector2d lastPos;
	protected int lastPrecision = -1;
	protected Polygon lastPoly = null;
	
	public Circle(double x, double y, double radius) {
		pos = new Vector2d(x,y);
		this.radius = radius;
	}
	public Circle(Vector2d pos, double radius) {
		this.pos = pos;
		this.radius = radius;
	}
	public Circle(Circle circle) {
		pos = new Vector2d(circle.pos);
		radius = circle.radius;
	}
	
	public Shape copy() {
		return copyMe();
	}
	public Circle copyMe() {
		return new Circle(this);
	}
	
	public Rectangle getBoundingBox() {
		return new Rectangle(pos.x-radius,pos.y-radius,radius*2,radius*2);
	}
	
	public Vector2d translate(double x, double y) {
		pos.x += x;
		pos.y += y;
		return new Vector2d(x,y);
	}
	public Vector2d translateTo(double x, double y) {
		Vector2d v = new Vector2d(x-pos.x,y-pos.y);
		pos.x = x;
		pos.y = y;
		return v;
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
		if (lastPoly != null && lastPoly.getPointCount() == precision && lastPrecision == precision && lastPos.equals(pos)) return lastPoly;
		
		Polygon p = new Polygon.NoHoles();
		for (int i = 0; i < precision; i++) p.addPoint(Vector2d.make(radius,360d/precision*i).add(pos));
		
		lastPos = pos;
		lastPrecision = precision;
		return lastPoly = p;
	}
	
	public void draw(Graphics g, boolean filled) {
		asPolygon().draw(g,filled);
	}
	public void draw(Graphics g, boolean filled, int precision) {
		asPolygon(precision).draw(g,filled);
	}
}