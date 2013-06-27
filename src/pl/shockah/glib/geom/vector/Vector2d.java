package pl.shockah.glib.geom.vector;

import pl.shockah.glib.Math2;

public class Vector2d implements IVector {
	public static Vector2d make(double dist, double angle) {
		return new Vector2d(Math2.ldirX(dist,angle),Math2.ldirY(dist,angle));
	}
	
	public final double x, y;
	
	public Vector2d() {
		this(0,0);
	}
	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof Vector2d)) return false;
		Vector2d v = (Vector2d)o;
		return x == v.x && y == v.y;
	}
	public String toString() {
		return "[Vector2d: "+x+" | "+y+"]";
	}
	
	public Vector2i toInt() {
		return new Vector2i((int)x,(int)y);
	}
	public Vector2f toFloat() {
		return new Vector2f((float)x,(float)y);
	}
	
	public Vector2d justX() {return new Vector2d(x,0);}
	public Vector2d justY() {return new Vector2d(0,y);}
	
	public Vector2d negate() {return new Vector2d(-x,-y);}
	public Vector2d abs() {return new Vector2d(x >= 0 ? x : -x,y >= 0 ? y : -y);}
	
	public Vector2d add(Vector2d v) {return add(v.x,v.y);}
	public Vector2d add(double x, double y) {return new Vector2d(this.x+x,this.y+y);}
	public Vector2d sub(double x, double y) {return add(-x,-y);}
	public Vector2d sub(Vector2d v) {return sub(v.x,v.y);}
	
	public Vector2d scale(double scale) {return scale(scale,scale);}
	public Vector2d scale(double scaleH, double scaleV) {return new Vector2d(x*scaleH,y*scaleV);}
	public Vector2d div(double scale) {return div(scale,scale);}
	public Vector2d div(double scaleH, double scaleV) {return scale(1/scaleH,1/scaleV);}
	
	public double lengthSquared() {
		return Math.pow(x,2)+Math.pow(y,2);
	}
	public double length() {
		return Math.sqrt(lengthSquared());
	}
	public double distanceSquared(Vector2d v) {
		return Math.pow(v.x-x,2)+Math.pow(v.y-y,2);
	}
	public double distance(Vector2d v) {
		return Math.sqrt(distanceSquared(v));
	}
	public double direction(Vector2d v) {
		return Math.toDegrees(Math.atan2(y-v.y,v.x-x));
	}
}