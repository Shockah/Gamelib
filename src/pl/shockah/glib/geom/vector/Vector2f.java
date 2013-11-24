package pl.shockah.glib.geom.vector;

import pl.shockah.Math2;
import pl.shockah.glib.animfx.IInterpolatable;
import pl.shockah.glib.animfx.Interpolate;

public class Vector2f extends Vector2 implements IInterpolatable<Vector2f> {
	public static Vector2f make(float dist, double angle) {
		return new Vector2f((float)Math2.ldirX(dist,angle),(float)Math2.ldirY(dist,angle));
	}
	
	public float x, y;
	
	public Vector2f() {
		this(0,0);
	}
	public Vector2f(Vector2 v) {
		this(v.Xf(),v.Yf());
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
		return "[Vector2f: "+x+", "+y+"]";
	}
	
	public double Xd() {return x;}
	public float Xf() {return x;}
	public int Xi() {return (int)x;}
	
	public double Yd() {return y;}
	public float Yf() {return y;}
	public int Yi() {return (int)y;}
	
	public Vector2f copyMe() {
		return new Vector2f(this);
	}
	public Vector2i toInt() {
		return new Vector2i((int)x,(int)y);
	}
	public Vector2f toFloat() {
		return copyMe();
	}
	public Vector2d toDouble() {
		return new Vector2d(x,y);
	}
	
	public Vector2f set(Vector2 v) {return set(v.Xf(),v.Yf());}
	public Vector2f set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}
	public Vector2f setX(Vector2 v) {return setX(v.Xf());}
	public Vector2f setX(float x) {
		this.x = x;
		return this;
	}
	public Vector2f setY(Vector2 v) {return setY(v.Yf());}
	public Vector2f setY(float y) {
		this.y = y;
		return this;
	}
	
	public Vector2f justX() {y = 0; return this;}
	public Vector2f JustX() {return new Vector2f(x,0);}
	public Vector2f justY() {x = 0; return this;}
	public Vector2f JustY() {return new Vector2f(0,y);}
	
	public Vector2f negate() {x = -x; y = -y; return this;}
	public Vector2f Negate() {return new Vector2f(-x,-y);}
	public Vector2f abs() {if (x < 0) x = -x; if (y < 0) y = -y; return this;}
	public Vector2f Abs() {return new Vector2f(x >= 0 ? x : -x,y >= 0 ? y : -y);}
	
	public Vector2f add(Vector2 v) {return add(v.Xf(),v.Yf());}
	public Vector2f Add(Vector2 v) {return Add(v.Xf(),v.Yf());}
	public Vector2f add(float x, float y) {this.x += x; this.y += y; return this;}
	public Vector2f Add(float x, float y) {return new Vector2f(this.x+x,this.y+y);}
	public Vector2f sub(float x, float y) {return add(-x,-y);}
	public Vector2f Sub(float x, float y) {return Add(-x,-y);}
	public Vector2f sub(Vector2 v) {return sub(v.Xf(),v.Yf());}
	public Vector2f Sub(Vector2 v) {return Sub(v.Xf(),v.Yf());}
	
	public Vector2f scale(float scale) {return scale(scale,scale);}
	public Vector2f Scale(float scale) {return Scale(scale,scale);}
	public Vector2f scale(float scaleH, float scaleV) {x *= scaleH; y *= scaleV; return this;}
	public Vector2f Scale(float scaleH, float scaleV) {return new Vector2f(x*scaleH,y*scaleV);}
	public Vector2f scale(Vector2 v) {return scale(v.Xf(),v.Yf());}
	public Vector2f Scale(Vector2 v) {return Scale(v.Xf(),v.Yf());}
	public Vector2f div(float scale) {return div(scale,scale);}
	public Vector2f Div(float scale) {return Div(scale,scale);}
	public Vector2f div(float scaleH, float scaleV) {return scale(1/scaleH,1/scaleV);}
	public Vector2f Div(float scaleH, float scaleV) {return Scale(1/scaleH,1/scaleV);}
	public Vector2f div(Vector2 v) {return div(v.Xf(),v.Yf());}
	public Vector2f Div(Vector2 v) {return Div(v.Xf(),v.Yf());}
	
	public Vector2f Vector(Vector2 v) {
		return new Vector2f(v.Xf()-x,v.Yf()-y);
	}
	public Vector2f rotate(double angle) {
		return set(Rotate(angle));
	}
	public Vector2f Rotate(double angle) {
		return Vector2f.make(length(),direction()+angle);
	}
	
	public float lengthSquared() {
		return (float)(Math.pow(x,2)+Math.pow(y,2));
	}
	public float length() {
		return (float)Math.sqrt(lengthSquared());
	}
	public float distanceSquared(Vector2 v) {
		return (float)(Math.pow(v.Xf()-x,2)+Math.pow(v.Yf()-y,2));
	}
	public float distance(Vector2 v) {
		return (float)Math.sqrt(distanceSquared(v));
	}
	public double direction() {
		return new Vector2f().direction(this);
	}
	public double direction(Vector2 v) {
		return Math.toDegrees(Math.atan2(y-v.Yf(),v.Xf()-x));
	}
	public double deltaAngle(Vector2 v) {return deltaAngle(v.direction());}
	public double deltaAngle(double angle) {
		return Math2.deltaAngle(direction(),angle);
	}
	
	public Vector2f interpolate(Vector2f v, double d) {
		return interpolate(v,d,Interpolate.Linear);
	}
	public Vector2f interpolate(Vector2f v, double d, Interpolate method) {
		return new Vector2f(method.interpolate(x,v.x,d),method.interpolate(y,v.y,d));
	}
}