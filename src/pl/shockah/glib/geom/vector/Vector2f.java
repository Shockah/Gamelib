package pl.shockah.glib.geom.vector;

import pl.shockah.glib.Math2;

public class Vector2f implements IVector {
	public static Vector2f make(double dist, double angle) {
		return new Vector2f((float)Math2.ldirX(dist,angle),(float)Math2.ldirY(dist,angle));
	}
	
	public final float x, y;
	
	public Vector2f() {
		this(0,0);
	}
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof Vector2f)) return false;
		Vector2f v = (Vector2f)o;
		return x == v.x && y == v.y;
	}
	public String toString() {
		return "[Vector2f: "+x+" | "+y+"]";
	}
	
	public Vector2i toInt() {
		return new Vector2i((int)x,(int)y);
	}
	public Vector2d toDouble() {
		return new Vector2d(x,y);
	}
	
	public Vector2f justX() {return new Vector2f(x,0);}
	public Vector2f justY() {return new Vector2f(0,y);}
	
	public Vector2f negate() {return new Vector2f(-x,-y);}
	public Vector2f abs() {return new Vector2f(x >= 0 ? x : -x,y >= 0 ? y : -y);}
	
	public Vector2f add(Vector2f v) {return add(v.x,v.y);}
	public Vector2f add(float x, float y) {return new Vector2f(this.x+x,this.y+y);}
	public Vector2f sub(float x, float y) {return add(-x,-y);}
	public Vector2f sub(Vector2f v) {return sub(v.x,v.y);}
	
	public Vector2f scale(float scale) {return scale(scale,scale);}
	public Vector2f scale(float scaleH, float scaleV) {return new Vector2f(x*scaleH,y*scaleV);}
	public Vector2f div(float scale) {return div(scale,scale);}
	public Vector2f div(float scaleH, float scaleV) {return scale(1/scaleH,1/scaleV);}
	
	public float lengthSquared() {
		return (float)(Math.pow(x,2)+Math.pow(y,2));
	}
	public float length() {
		return (float)Math.sqrt(lengthSquared());
	}
	public float distanceSquared(Vector2f v) {
		return (float)(Math.pow(v.x-x,2)+Math.pow(v.y-y,2));
	}
	public float distance(Vector2f v) {
		return (float)Math.sqrt(distanceSquared(v));
	}
	public double direction(Vector2f v) {
		return Math.toDegrees(Math.atan2(y-v.y,v.x-x));
	}
}