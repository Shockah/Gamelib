package pl.shockah.glib.geom.vector;

import pl.shockah.glib.Math2;

public class Vector2f implements IVector {
	public static Vector2f make(float dist, float angle) {
		return new Vector2f((float)Math2.ldirX(dist,angle),(float)Math2.ldirY(dist,angle));
	}
	
	public float x, y;
	
	public Vector2f() {
		this(0,0);
	}
	public Vector2f(Vector2f v) {
		this(v.x,v.y);
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
	
	public Vector2f justX() {y = 0; return this;}
	public Vector2f JustX() {return new Vector2f(x,0);}
	public Vector2f justY() {x = 0; return this;}
	public Vector2f JustY() {return new Vector2f(0,y);}
	
	public Vector2f negate() {x = -x; y = -y; return this;}
	public Vector2f Negate() {return new Vector2f(-x,-y);}
	public Vector2f abs() {if (x < 0) x = -x; if (y < 0) y = -y; return this;}
	public Vector2f Abs() {return new Vector2f(x >= 0 ? x : -x,y >= 0 ? y : -y);}
	
	public Vector2f add(Vector2f v) {return add(v.x,v.y);}
	public Vector2f Add(Vector2f v) {return Add(v.x,v.y);}
	public Vector2f add(float x, float y) {this.x += x; this.y += y; return this;}
	public Vector2f Add(float x, float y) {return new Vector2f(this.x+x,this.y+y);}
	public Vector2f sub(float x, float y) {return add(-x,-y);}
	public Vector2f Sub(float x, float y) {return Add(-x,-y);}
	public Vector2f sub(Vector2f v) {return sub(v.x,v.y);}
	public Vector2f Sub(Vector2f v) {return Sub(v.x,v.y);}
	
	public Vector2f scale(float scale) {return scale(scale,scale);}
	public Vector2f Scale(float scale) {return Scale(scale,scale);}
	public Vector2f scale(float scaleH, float scaleV) {x *= scaleH; y *= scaleV; return this;}
	public Vector2f Scale(float scaleH, float scaleV) {return new Vector2f(x*scaleH,y*scaleV);}
	public Vector2f div(float scale) {return div(scale,scale);}
	public Vector2f Div(float scale) {return Div(scale,scale);}
	public Vector2f div(float scaleH, float scaleV) {return scale(1/scaleH,1/scaleV);}
	public Vector2f Div(float scaleH, float scaleV) {return Scale(1/scaleH,1/scaleV);}
	
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