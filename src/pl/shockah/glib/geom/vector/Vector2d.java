package pl.shockah.glib.geom.vector;

import pl.shockah.glib.Math2;
import pl.shockah.glib.animfx.IInterpolatable;
import pl.shockah.glib.animfx.Interpolate;

public class Vector2d implements IInterpolatable<Vector2d> {
	public static Vector2d make(double dist, double angle) {
		return new Vector2d(Math2.ldirX(dist,angle),Math2.ldirY(dist,angle));
	}
	
	public double x, y;
	
	public Vector2d() {
		this(0,0);
	}
	public Vector2d(Vector2d v) {
		this(v.x,v.y);
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
	
	public Vector2d copyMe() {
		return new Vector2d(this);
	}
	public Vector2i toInt() {
		return new Vector2i((int)x,(int)y);
	}
	public Vector2f toFloat() {
		return new Vector2f((float)x,(float)y);
	}
	
	public Vector2d set(Vector2d v) {return set(v.x,v.y);}
	public Vector2d set(double x, double y) {
		this.x = x;
		this.y = y;
		return this;
	}
	public Vector2d setX(Vector2d v) {return setX(v.x);}
	public Vector2d setX(double x) {
		this.x = x;
		return this;
	}
	public Vector2d setY(Vector2d v) {return setY(v.y);}
	public Vector2d setY(double y) {
		this.y = y;
		return this;
	}
	
	public Vector2d justX() {y = 0; return this;}
	public Vector2d JustX() {return new Vector2d(x,0);}
	public Vector2d justY() {x = 0; return this;}
	public Vector2d JustY() {return new Vector2d(0,y);}
	
	public Vector2d negate() {x = -x; y = -y; return this;}
	public Vector2d Negate() {return new Vector2d(-x,-y);}
	public Vector2d abs() {if (x < 0) x = -x; if (y < 0) y = -y; return this;}
	public Vector2d Abs() {return new Vector2d(x >= 0 ? x : -x,y >= 0 ? y : -y);}
	
	public Vector2d add(Vector2d v) {return add(v.x,v.y);}
	public Vector2d Add(Vector2d v) {return Add(v.x,v.y);}
	public Vector2d add(double x, double y) {this.x += x; this.y += y; return this;}
	public Vector2d Add(double x, double y) {return new Vector2d(this.x+x,this.y+y);}
	public Vector2d sub(double x, double y) {return add(-x,-y);}
	public Vector2d Sub(double x, double y) {return Add(-x,-y);}
	public Vector2d sub(Vector2d v) {return sub(v.x,v.y);}
	public Vector2d Sub(Vector2d v) {return Sub(v.x,v.y);}
	
	public Vector2d scale(double scale) {return scale(scale,scale);}
	public Vector2d Scale(double scale) {return Scale(scale,scale);}
	public Vector2d scale(double scaleH, double scaleV) {x *= scaleH; y *= scaleV; return this;}
	public Vector2d Scale(double scaleH, double scaleV) {return new Vector2d(x*scaleH,y*scaleV);}
	public Vector2d scale(Vector2d v) {return scale(v.x,v.y);}
	public Vector2d Scale(Vector2d v) {return Scale(v.x,v.y);}
	public Vector2d div(double scale) {return div(scale,scale);}
	public Vector2d Div(double scale) {return Div(scale,scale);}
	public Vector2d div(double scaleH, double scaleV) {return scale(1/scaleH,1/scaleV);}
	public Vector2d Div(double scaleH, double scaleV) {return Scale(1/scaleH,1/scaleV);}
	public Vector2d div(Vector2d v) {return div(v.x,v.y);}
	public Vector2d Div(Vector2d v) {return Div(v.x,v.y);}
	
	public Vector2d Vector(Vector2d v) {
		return new Vector2d(v.x-x,v.y-y);
	}
	
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
	public double direction() {
		return new Vector2d().direction(this);
	}
	public double direction(Vector2d v) {
		return Math.toDegrees(Math.atan2(y-v.y,v.x-x));
	}
	
	public Vector2d interpolate(Vector2d v, double d, Interpolate method) {
		return new Vector2d(method.interpolate(x,v.x,d),method.interpolate(y,v.y,d));
	}
}