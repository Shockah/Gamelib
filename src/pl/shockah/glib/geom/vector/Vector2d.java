package pl.shockah.glib.geom.vector;

import pl.shockah.Math2;
import pl.shockah.glib.animfx.IEasable;
import pl.shockah.glib.animfx.Ease;

public class Vector2d extends Vector2 implements IEasable<Vector2d> {
	public static Vector2d make(double dist, double angle) {
		return new Vector2d(Math2.ldirX(dist,angle),Math2.ldirY(dist,angle));
	}
	
	public double x, y;
	
	public Vector2d() {
		this(0,0);
	}
	public Vector2d(Vector2 v) {
		this(v.Xd(),v.Yd());
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
		return "[Vector2d: "+x+", "+y+"]";
	}
	
	public double Xd() {return x;}
	public float Xf() {return (float)x;}
	public int Xi() {return (int)x;}
	
	public double Yd() {return y;}
	public float Yf() {return (float)y;}
	public int Yi() {return (int)y;}
	
	public Vector2d copyMe() {return new Vector2d(this);}
	public Vector2i ToInt() {return new Vector2i((int)x,(int)y);}
	public Vector2f ToFloat() {return new Vector2f((float)x,(float)y);}
	public Vector2d ToDouble() {return copyMe();}
	public Vector2d toDouble() {return this;}
	
	public Vector2d set(Vector2 v) {return set(v.Xd(),v.Yd());}
	public Vector2d set(double value) {return set(value,value);}
	public Vector2d set(double x, double y) {
		this.x = x;
		this.y = y;
		return this;
	}
	public Vector2d setX(Vector2 v) {return setX(v.Xd());}
	public Vector2d setX(double x) {
		this.x = x;
		return this;
	}
	public Vector2d setY(Vector2 v) {return setY(v.Yd());}
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
	
	public Vector2d add(Vector2 v) {return add(v.Xd(),v.Yd());}
	public Vector2d Add(Vector2 v) {return Add(v.Xd(),v.Yd());}
	public Vector2d add(double x, double y) {this.x += x; this.y += y; return this;}
	public Vector2d Add(double x, double y) {return new Vector2d(this.x+x,this.y+y);}
	public Vector2d sub(double x, double y) {return add(-x,-y);}
	public Vector2d Sub(double x, double y) {return Add(-x,-y);}
	public Vector2d sub(Vector2 v) {return sub(v.Xd(),v.Yd());}
	public Vector2d Sub(Vector2 v) {return Sub(v.Xd(),v.Yd());}
	
	public Vector2d scale(double scale) {return scale(scale,scale);}
	public Vector2d Scale(double scale) {return Scale(scale,scale);}
	public Vector2d scale(double scaleH, double scaleV) {x *= scaleH; y *= scaleV; return this;}
	public Vector2d Scale(double scaleH, double scaleV) {return new Vector2d(x*scaleH,y*scaleV);}
	public Vector2d scale(Vector2 v) {return scale(v.Xd(),v.Yd());}
	public Vector2d Scale(Vector2 v) {return Scale(v.Xd(),v.Yd());}
	public Vector2d div(double scale) {return div(scale,scale);}
	public Vector2d Div(double scale) {return Div(scale,scale);}
	public Vector2d div(double scaleH, double scaleV) {return scale(1/scaleH,1/scaleV);}
	public Vector2d Div(double scaleH, double scaleV) {return Scale(1/scaleH,1/scaleV);}
	public Vector2d div(Vector2 v) {return div(v.Xd(),v.Yd());}
	public Vector2d Div(Vector2 v) {return Div(v.Xd(),v.Yd());}
	
	public Vector2d floor() {x = Math.floor(x); y = Math.floor(y); return this;}
	public Vector2d Floor() {return copyMe().floor();}
	public Vector2d round() {x = Math.round(x); y = Math.round(y); return this;}
	public Vector2d Round() {return copyMe().round();}
	public Vector2d ceil() {x = Math.ceil(x); y = Math.ceil(y); return this;}
	public Vector2d Ceil() {return copyMe().ceil();}
	
	public Vector2d Vector(Vector2 v) {
		return new Vector2d(v.Xd()-x,v.Yd()-y);
	}
	public Vector2d rotate(double angle) {
		return set(Rotate(angle));
	}
	public Vector2d Rotate(double angle) {
		return Vector2d.make(length(),direction()+angle);
	}
	
	public double lengthSquared() {
		return Math.pow(x,2)+Math.pow(y,2);
	}
	public double length() {
		return Math.sqrt(lengthSquared());
	}
	public double distanceSquared(Vector2 v) {
		return Math.pow(v.Xd()-x,2)+Math.pow(v.Yd()-y,2);
	}
	public double distance(Vector2 v) {
		return Math.sqrt(distanceSquared(v));
	}
	public double deltaAngle(Vector2 v) {return deltaAngle(v.direction());}
	public double deltaAngle(double angle) {
		return Math2.deltaAngle(direction(),angle);
	}
	
	public Vector2d projectOnto(Vector2 v) {
		Vector2d vd = v.ToDouble();
		return scale(vd).div(vd.scale(vd));
	}
	public Vector2d ProjectOnto(Vector2 v) {
		Vector2d vd = v.ToDouble();
		return Scale(vd).div(vd.scale(vd));
	}
	
	public Vector2d ease(Vector2d v, double d) {
		return ease(v,d,Ease.Linear);
	}
	public Vector2d ease(Vector2d v, double d, Ease method) {
		return new Vector2d(method.ease(x,v.x,d),method.ease(y,v.y,d));
	}
	
	/*
	 * for use with Xtend
	 */
	public Vector2d operator_plus() {return new Vector2d(this);}
	public Vector2d operator_plus(final Vector2 v) {return Add(v);}
	public Vector2d operator_minus() {return Negate();}
	public Vector2d operator_minus(final Vector2 v) {return Sub(v);}
	public Vector2d operator_multiply(final Vector2 v) {return Scale(v);}
	public Vector2d operator_divide(final Vector2 v) {return Div(v);}
}