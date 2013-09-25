package pl.shockah.glib.geom.vector;

import pl.shockah.Math2;
import pl.shockah.glib.animfx.IInterpolatable;
import pl.shockah.glib.animfx.Interpolate;

public class Vector2d implements IInterpolatable<Vector2d>,IVector2 {
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
		return "[Vector2d: "+x+", "+y+"]";
	}
	
	public double Xd() {return x;}
	public float Xf() {return (float)x;}
	public int Xi() {return (int)x;}
	
	public double Yd() {return y;}
	public float Yf() {return (float)y;}
	public int Yi() {return (int)y;}
	
	public Vector2d copyMe() {
		return new Vector2d(this);
	}
	public Vector2i toInt() {
		return new Vector2i((int)x,(int)y);
	}
	public Vector2f toFloat() {
		return new Vector2f((float)x,(float)y);
	}
	public Vector2d toDouble() {
		return copyMe();
	}
	
	public Vector2d set(IVector2 v) {return set(v.Xd(),v.Yd());}
	public Vector2d set(double x, double y) {
		this.x = x;
		this.y = y;
		return this;
	}
	public Vector2d setX(IVector2 v) {return setX(v.Xd());}
	public Vector2d setX(double x) {
		this.x = x;
		return this;
	}
	public Vector2d setY(IVector2 v) {return setY(v.Yd());}
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
	
	public Vector2d add(IVector2 v) {return add(v.Xd(),v.Yd());}
	public Vector2d Add(IVector2 v) {return Add(v.Xd(),v.Yd());}
	public Vector2d add(double x, double y) {this.x += x; this.y += y; return this;}
	public Vector2d Add(double x, double y) {return new Vector2d(this.x+x,this.y+y);}
	public Vector2d sub(double x, double y) {return add(-x,-y);}
	public Vector2d Sub(double x, double y) {return Add(-x,-y);}
	public Vector2d sub(IVector2 v) {return sub(v.Xd(),v.Yd());}
	public Vector2d Sub(IVector2 v) {return Sub(v.Xd(),v.Yd());}
	
	public Vector2d scale(double scale) {return scale(scale,scale);}
	public Vector2d Scale(double scale) {return Scale(scale,scale);}
	public Vector2d scale(double scaleH, double scaleV) {x *= scaleH; y *= scaleV; return this;}
	public Vector2d Scale(double scaleH, double scaleV) {return new Vector2d(x*scaleH,y*scaleV);}
	public Vector2d scale(IVector2 v) {return scale(v.Xd(),v.Yd());}
	public Vector2d Scale(IVector2 v) {return Scale(v.Xd(),v.Yd());}
	public Vector2d div(double scale) {return div(scale,scale);}
	public Vector2d Div(double scale) {return Div(scale,scale);}
	public Vector2d div(double scaleH, double scaleV) {return scale(1/scaleH,1/scaleV);}
	public Vector2d Div(double scaleH, double scaleV) {return Scale(1/scaleH,1/scaleV);}
	public Vector2d div(IVector2 v) {return div(v.Xd(),v.Yd());}
	public Vector2d Div(IVector2 v) {return Div(v.Xd(),v.Yd());}
	
	public Vector2d Vector(IVector2 v) {
		return new Vector2d(v.Xd()-x,v.Yd()-y);
	}
	
	public double lengthSquared() {
		return Math.pow(x,2)+Math.pow(y,2);
	}
	public double length() {
		return Math.sqrt(lengthSquared());
	}
	public double distanceSquared(IVector2 v) {
		return Math.pow(v.Xd()-x,2)+Math.pow(v.Yd()-y,2);
	}
	public double distance(IVector2 v) {
		return Math.sqrt(distanceSquared(v));
	}
	public double direction() {
		return new Vector2d().direction(this);
	}
	public double direction(IVector2 v) {
		return Math.toDegrees(Math.atan2(y-v.Yd(),v.Xd()-x));
	}
	public double deltaAngle(IVector2 v) {return deltaAngle(v.direction());}
	public double deltaAngle(double angle) {
		double a = direction();
		while (angle <= -180) angle += 360;
		while (angle > 180) angle -= 360;
		while (a <= -180) a += 360;
		while (a > 180) a -= 360;
		
		double r = angle-a;
		return r+((r>180) ? -360 : (r<-180) ? 360 : 0);
	}
	
	public Vector2d interpolate(Vector2d v, double d, Interpolate method) {
		return new Vector2d(method.interpolate(x,v.x,d),method.interpolate(y,v.y,d));
	}
}