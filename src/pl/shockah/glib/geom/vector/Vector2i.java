package pl.shockah.glib.geom.vector;

import pl.shockah.Math2;
import pl.shockah.glib.animfx.IEasable;
import pl.shockah.glib.animfx.Ease;

public class Vector2i extends Vector2 implements IEasable<Vector2i> {
	public static Vector2i make(int dist, double angle) {
		return new Vector2i((int)Math2.ldirX(dist,angle),(int)Math2.ldirY(dist,angle));
	}
	
	public int x, y;
	
	public Vector2i() {
		this(0,0);
	}
	public Vector2i(Vector2 v) {
		this(v.Xi(),v.Yi());
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
		return "[Vector2i: "+x+", "+y+"]";
	}
	
	public double Xd() {return x;}
	public float Xf() {return x;}
	public int Xi() {return x;}
	
	public double Yd() {return y;}
	public float Yf() {return y;}
	public int Yi() {return y;}
	
	public Vector2i copyMe() {return new Vector2i(this);}
	public Vector2i ToInt() {return copyMe();}
	public Vector2f ToFloat() {return new Vector2f(x,y);}
	public Vector2d ToDouble() {return new Vector2d(x,y);}
	public Vector2i toInt() {return this;}
	
	public Vector2i set(Vector2 v) {return set(v.Xi(),v.Yi());}
	public Vector2i set(int value) {return set(value,value);}
	public Vector2i set(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}
	public Vector2i setX(Vector2 v) {return setX(v.Xi());}
	public Vector2i setX(int x) {
		this.x = x;
		return this;
	}
	public Vector2i setY(Vector2 v) {return setY(v.Yi());}
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
	
	public Vector2i add(Vector2 v) {return add(v.Xi(),v.Yi());}
	public Vector2i Add(Vector2 v) {return Add(v.Xi(),v.Yi());}
	public Vector2i add(int x, int y) {this.x += x; this.y += y; return this;}
	public Vector2i Add(int x, int y) {return new Vector2i(this.x+x,this.y+y);}
	public Vector2i sub(int x, int y) {return add(-x,-y);}
	public Vector2i Sub(int x, int y) {return Add(-x,-y);}
	public Vector2i sub(Vector2 v) {return sub(v.Xi(),v.Yi());}
	public Vector2i Sub(Vector2 v) {return Sub(v.Xi(),v.Yi());}
	
	public Vector2i scale(int scale) {return scale(scale,scale);}
	public Vector2i Scale(int scale) {return Scale(scale,scale);}
	public Vector2i scale(int scaleH, int scaleV) {x *= scaleH; y *= scaleV; return this;}
	public Vector2i Scale(int scaleH, int scaleV) {return new Vector2i(x*scaleH,y*scaleV);}
	public Vector2i scale(Vector2 v) {return scale(v.Xi(),v.Yi());}
	public Vector2i Scale(Vector2 v) {return Scale(v.Xi(),v.Yi());}
	public Vector2i div(int scale) {return div(scale,scale);}
	public Vector2i Div(int scale) {return Div(scale,scale);}
	public Vector2i div(int scaleH, int scaleV) {x /= scaleH; y /= scaleV; return this;}
	public Vector2i Div(int scaleH, int scaleV) {return new Vector2i(x/scaleH,y/scaleV);}
	public Vector2i div(Vector2 v) {return div(v.Xi(),v.Yi());}
	public Vector2i Div(Vector2 v) {return Div(v.Xi(),v.Yi());}
	
	public Vector2i floor() {x = (int)Math.floor(x); y = (int)Math.floor(y); return this;}
	public Vector2i Floor() {return copyMe().floor();}
	public Vector2i round() {x = Math.round(x); y = Math.round(y); return this;}
	public Vector2i Round() {return copyMe().round();}
	public Vector2i ceil() {x = (int)Math.ceil(x); y = (int)Math.ceil(y); return this;}
	public Vector2i Ceil() {return copyMe().ceil();}
	
	public Vector2i Vector(Vector2 v) {
		return new Vector2i(v.Xi()-x,v.Yi()-y);
	}
	public Vector2i rotate(double angle) {
		return set(Rotate(angle));
	}
	public Vector2i Rotate(double angle) {
		return Vector2i.make(length(),direction()+angle);
	}
	
	public int lengthSquared() {
		return (int)(Math.pow(x,2)+Math.pow(y,2));
	}
	public int length() {
		return (int)Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
	}
	public int distanceSquared(Vector2 v) {
		return (int)(Math.pow(v.Xi()-x,2)+Math.pow(v.Yi()-y,2));
	}
	public int distance(Vector2 v) {
		return (int)Math.sqrt(Math.pow(v.Xi()-x,2)+Math.pow(v.Yi()-y,2));
	}
	public double deltaAngle(Vector2 v) {return deltaAngle(v.direction());}
	public double deltaAngle(double angle) {
		return Math2.deltaAngle(direction(),angle);
	}
	
	public Vector2i ease(Vector2i v, double d) {
		return ease(v,d,Ease.Linear);
	}
	public Vector2i ease(Vector2i v, double d, Ease method) {
		return new Vector2i(method.ease(x,v.x,d),method.ease(y,v.y,d));
	}
	
	public Vector2i projectOnto(Vector2 v) {
		Vector2i vi = v.ToInt();
		return scale(vi).div(vi.scale(vi));
	}
	public Vector2i ProjectOnto(Vector2 v) {
		Vector2i vi = v.ToInt();
		return Scale(vi).div(vi.scale(vi));
	}
	
	/*
	 * for use with Xtend
	 */
	public Vector2i operator_plus() {return new Vector2i(this);}
	public Vector2i operator_plus(final Vector2 v) {return Add(v);}
	public Vector2i operator_minus() {return Negate();}
	public Vector2i operator_minus(final Vector2 v) {return Sub(v);}
	public Vector2i operator_multiply(final Vector2 v) {return Scale(v);}
	public Vector2i operator_divide(final Vector2 v) {return Div(v);}
}