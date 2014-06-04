package pl.shockah.glib.geom;

import pl.shockah.Math2;
import pl.shockah.glib.animfx.IEasable;
import pl.shockah.glib.animfx.Ease;
import pl.shockah.glib.geom.polygon.IPolygonable;
import pl.shockah.glib.geom.polygon.Polygon;
import pl.shockah.glib.geom.vector.Vector2;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.Graphics;

public class Ellipse extends Shape implements IPolygonable,IEasable<Ellipse> {
	public Vector2d pos, radius;
	
	protected Vector2d lastPos;
	protected int lastPrecision = -1;
	protected Polygon lastPoly = null;
	
	public Ellipse(double x, double y, double radiusH, double radiusV) {
		pos = new Vector2d(x,y);
		radius = new Vector2d(radiusH,radiusV);
	}
	public Ellipse(double x, double y, Vector2d radius) {
		pos = new Vector2d(x,y);
		this.radius = radius;
	}
	public Ellipse(Vector2 pos, double radiusH, double radiusV) {this(pos.Xd(),pos.Yd(),radiusH,radiusV);}
	public Ellipse(Vector2 pos, Vector2 radius) {this(pos.Xd(),pos.Yd(),radius.Xd(),radius.Yd());}
	public Ellipse(Ellipse ellipse) {
		pos = new Vector2d(ellipse.pos);
		radius = new Vector2d(ellipse.radius);
	}
	
	public boolean equals(Object other) {
		if (!(other instanceof Ellipse)) return false;
		Ellipse e = (Ellipse)other;
		return pos.equals(e.pos) && radius.equals(e.radius);
	}
	public String toString() {
		return String.format("[Ellipse: pos %s, radius %s]",pos,radius);
	}
	
	public Shape copy() {
		return copyMe();
	}
	public Ellipse copyMe() {
		return new Ellipse(this);
	}
	
	public Rectangle boundingBox() {
		return new Rectangle(pos.x-radius.x,pos.y-radius.y,radius.x*2,radius.y*2);
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
	
	public Polygon asPolygon() {
		return asPolygon((int)Math.ceil(Math.PI*(radius.x+radius.y)/4));
	}
	public Polygon asPolygon(int precision) {
		if (lastPoly != null && lastPoly.pointCount() == precision && lastPrecision == precision && lastPos.equals(pos)) return lastPoly;
		
		Polygon p = new Polygon.NoHoles();
		for (int i = 0; i < precision; i++) p.addPoint(new Vector2d(Math2.ldirX(radius.x,360d/precision*i),Math2.ldirY(radius.y,360d/precision*i)).add(pos));
		
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
	
	public Ellipse ease(Ellipse e, double d) {
		return ease(e,d,Ease.Linear);
	}
	public Ellipse ease(Ellipse e, double d, Ease method) {
		return new Ellipse(pos.ease(e.pos,d,method),radius.ease(e.radius,d,method));
	}
}