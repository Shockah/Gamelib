package pl.shockah.glib.geom;

import pl.shockah.glib.animfx.IEasable;
import pl.shockah.glib.animfx.Ease;
import pl.shockah.glib.geom.polygon.IPolygonable;
import pl.shockah.glib.geom.polygon.Polygon;
import pl.shockah.glib.geom.polygon.TriangleFan;
import pl.shockah.glib.geom.vector.Vector2;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;

public class Circle extends Shape implements IPolygonable,IEasable<Circle> {
	public Vector2d pos;
	public double radius;
	
	protected Vector2d lastPos = null, lastTFPos = null;
	protected int lastPrecision = -1, lastTFPrecision = -1;
	protected Polygon lastPoly = null;
	protected TriangleFan lastTriangleFan = null;
	
	public Circle(double x, double y, double radius) {
		pos = new Vector2d(x,y);
		this.radius = radius;
	}
	public Circle(Vector2 pos, double radius) {this(pos.Xd(),pos.Yd(),radius);}
	public Circle(Circle circle) {
		pos = new Vector2d(circle.pos);
		radius = circle.radius;
	}
	
	public boolean equals(Object other) {
		if (!(other instanceof Circle)) return false;
		Circle c = (Circle)other;
		return pos.equals(c.pos) && radius == c.radius;
	}
	public String toString() {
		return String.format("[Circle: pos %s, radius %s]",pos,radius);
	}
	
	public Shape copy() {
		return copyMe();
	}
	public Circle copyMe() {
		return new Circle(this);
	}
	
	public Rectangle boundingBox() {
		return new Rectangle(pos.x-radius,pos.y-radius,radius*2,radius*2);
	}
	public Vector2d center() {
		return pos.copyMe();
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
		return asPolygon((int)Math.ceil(Math.PI*radius/2));
	}
	public Polygon asPolygon(int precision) {
		if (lastPoly != null && lastPoly.pointCount() == precision && lastPrecision == precision && lastPos.equals(pos)) return lastPoly;
		
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
	
	public void drawMulticolor(Graphics g, Color cPointCenter, Color cPointOut) {
		drawMulticolor(g,cPointCenter,cPointOut,(int)Math.ceil(Math.PI*radius/2));
	}
	public void drawMulticolor(Graphics g, Color cPointCenter, Color cPointOut, int precision) {
		if (lastTriangleFan != null && lastTriangleFan.pointCount() == precision && lastTFPrecision == precision && lastTFPos.equals(pos)) lastTriangleFan.draw(g);
		
		TriangleFan tf = new TriangleFan();
		tf.addPoint(pos,cPointCenter);
		for (int i = 0; i < precision+1; i++) tf.addPoint(Vector2d.make(radius,360d/precision*i).add(pos),cPointOut);
		
		lastTFPos = pos;
		lastTFPrecision = precision;
		lastTriangleFan = tf;
		tf.draw(g);
	}
	
	public Circle ease(Circle c, double d) {
		return ease(c,d,Ease.Linear);
	}
	public Circle ease(Circle c, double d, Ease method) {
		return new Circle(pos.ease(c.pos,d,method),method.ease(radius,c.radius,d));
	}
}