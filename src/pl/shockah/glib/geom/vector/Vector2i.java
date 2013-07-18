package pl.shockah.glib.geom.vector;

import pl.shockah.glib.Math2;

public class Vector2i implements IVector {
	public static Vector2i make(int dist, int angle) {
		return new Vector2i((int)Math2.ldirX(dist,angle),(int)Math2.ldirY(dist,angle));
	}
	
	public int x, y;
	
	public Vector2i() {
		this(0,0);
	}
	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof Vector2i)) return false;
		Vector2i v = (Vector2i)o;
		return x == v.x && y == v.y;
	}
	public String toString() {
		return "[Vector2i: "+x+" | "+y+"]";
	}
	
	public Vector2f toFloat() {
		return new Vector2f(x,y);
	}
	public Vector2d toDouble() {
		return new Vector2d(x,y);
	}
	
	public Vector2i justX() {y = 0; return this;}
	public Vector2i JustX() {return new Vector2i(x,0);}
	public Vector2i justY() {x = 0; return this;}
	public Vector2i JustY() {return new Vector2i(0,y);}
	
	public Vector2i negate() {x = -x; y = -y; return this;}
	public Vector2i Negate() {return new Vector2i(-x,-y);}
	public Vector2i abs() {if (x < 0) x = -x; if (y < 0) y = -y; return this;}
	public Vector2i Abs() {return new Vector2i(x >= 0 ? x : -x,y >= 0 ? y : -y);}
	
	public Vector2i add(Vector2i v) {return add(v.x,v.y);}
	public Vector2i Add(Vector2i v) {return Add(v.x,v.y);}
	public Vector2i add(int x, int y) {this.x += x; this.y += y; return this;}
	public Vector2i Add(int x, int y) {return new Vector2i(this.x+x,this.y+y);}
	public Vector2i sub(int x, int y) {return add(-x,-y);}
	public Vector2i Sub(int x, int y) {return Add(-x,-y);}
	public Vector2i sub(Vector2i v) {return sub(v.x,v.y);}
	public Vector2i Sub(Vector2i v) {return Sub(v.x,v.y);}
	
	public Vector2i scale(int scale) {return scale(scale,scale);}
	public Vector2i Scale(int scale) {return Scale(scale,scale);}
	public Vector2i scale(int scaleH, int scaleV) {x *= scaleH; y *= scaleV; return this;}
	public Vector2i Scale(int scaleH, int scaleV) {return new Vector2i(x*scaleH,y*scaleV);}
	public Vector2i div(int scale) {return div(scale,scale);}
	public Vector2i Div(int scale) {return Div(scale,scale);}
	public Vector2i div(int scaleH, int scaleV) {return scale(1/scaleH,1/scaleV);}
	public Vector2i Div(int scaleH, int scaleV) {return Scale(1/scaleH,1/scaleV);}
	
	public int lengthSquared() {
		return (int)(Math.pow(x,2)+Math.pow(y,2));
	}
	public int length() {
		return (int)Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
	}
	public int distanceSquared(Vector2i v) {
		return (int)(Math.pow(v.x-x,2)+Math.pow(v.y-y,2));
	}
	public int distance(Vector2i v) {
		return (int)Math.sqrt(Math.pow(v.x-x,2)+Math.pow(v.y-y,2));
	}
	public double direction(Vector2i v) {
		return Math.toDegrees(Math.atan2(y-v.y,v.x-x));
	}
}