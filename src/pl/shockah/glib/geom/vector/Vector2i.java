package pl.shockah.glib.geom.vector;

import pl.shockah.glib.Math2;
import pl.shockah.glib.animfx.IInterpolatable;
import pl.shockah.glib.animfx.Interpolate;

public class Vector2i implements IInterpolatable<Vector2i> {
	public static Vector2i make(int dist, int angle) {
		return new Vector2i((int)Math2.ldirX(dist,angle),(int)Math2.ldirY(dist,angle));
	}
	
	public int x, y;
	
	public Vector2i() {
		this(0,0);
	}
	public Vector2i(Vector2i v) {
		this(v.x,v.y);
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
	
	public Vector2i copy() {
		return new Vector2i(this);
	}
	public Vector2f toFloat() {
		return new Vector2f(x,y);
	}
	public Vector2d toDouble() {
		return new Vector2d(x,y);
	}
	
	public Vector2i set(Vector2i v) {return set(v.x,v.y);}
	public Vector2i set(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}
	public Vector2i setX(Vector2i v) {return setX(v.x);}
	public Vector2i setX(int x) {
		this.x = x;
		return this;
	}
	public Vector2i setY(Vector2i v) {return setY(v.y);}
	public Vector2i setY(int y) {
		this.y = y;
		return this;
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
	public Vector2i scale(Vector2i v) {return scale(v.x,v.y);}
	public Vector2i Scale(Vector2i v) {return Scale(v.x,v.y);}
	public Vector2i div(int scale) {return div(scale,scale);}
	public Vector2i Div(int scale) {return Div(scale,scale);}
	public Vector2i div(int scaleH, int scaleV) {return scale(1/scaleH,1/scaleV);}
	public Vector2i Div(int scaleH, int scaleV) {return Scale(1/scaleH,1/scaleV);}
	public Vector2i div(Vector2i v) {return div(v.x,v.y);}
	public Vector2i Div(Vector2i v) {return Div(v.x,v.y);}
	
	public Vector2i Vector(Vector2i v) {
		return new Vector2i(v.x-x,v.y-y);
	}
	
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
	public double direction() {
		return new Vector2i().direction(this);
	}
	public double direction(Vector2i v) {
		return Math.toDegrees(Math.atan2(y-v.y,v.x-x));
	}
	
	public Vector2i interpolate(Vector2i v, double d, Interpolate method) {
		return new Vector2i(method.interpolate(x,v.x,d),method.interpolate(y,v.y,d));
	}
}