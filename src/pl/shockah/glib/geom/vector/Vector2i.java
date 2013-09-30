package pl.shockah.glib.geom.vector;

import pl.shockah.Math2;
import pl.shockah.glib.animfx.IInterpolatable;
import pl.shockah.glib.animfx.Interpolate;

public class Vector2i implements IInterpolatable<Vector2i>,IVector2 {
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
		return "[Vector2i: "+x+", "+y+"]";
	}
	
	public double Xd() {return x;}
	public float Xf() {return x;}
	public int Xi() {return x;}
	
	public double Yd() {return y;}
	public float Yf() {return y;}
	public int Yi() {return y;}
	
	public Vector2i copyMe() {
		return new Vector2i(this);
	}
	public Vector2i toInt() {
		return copyMe();
	}
	public Vector2f toFloat() {
		return new Vector2f(x,y);
	}
	public Vector2d toDouble() {
		return new Vector2d(x,y);
	}
	
	public Vector2i set(IVector2 v) {return set(v.Xi(),v.Yi());}
	public Vector2i set(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}
	public Vector2i setX(IVector2 v) {return setX(v.Xi());}
	public Vector2i setX(int x) {
		this.x = x;
		return this;
	}
	public Vector2i setY(IVector2 v) {return setY(v.Yi());}
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
	
	public Vector2i add(IVector2 v) {return add(v.Xi(),v.Yi());}
	public Vector2i Add(IVector2 v) {return Add(v.Xi(),v.Yi());}
	public Vector2i add(int x, int y) {this.x += x; this.y += y; return this;}
	public Vector2i Add(int x, int y) {return new Vector2i(this.x+x,this.y+y);}
	public Vector2i sub(int x, int y) {return add(-x,-y);}
	public Vector2i Sub(int x, int y) {return Add(-x,-y);}
	public Vector2i sub(IVector2 v) {return sub(v.Xi(),v.Yi());}
	public Vector2i Sub(IVector2 v) {return Sub(v.Xi(),v.Yi());}
	
	public Vector2i scale(int scale) {return scale(scale,scale);}
	public Vector2i Scale(int scale) {return Scale(scale,scale);}
	public Vector2i scale(int scaleH, int scaleV) {x *= scaleH; y *= scaleV; return this;}
	public Vector2i Scale(int scaleH, int scaleV) {return new Vector2i(x*scaleH,y*scaleV);}
	public Vector2i scale(IVector2 v) {return scale(v.Xi(),v.Yi());}
	public Vector2i Scale(IVector2 v) {return Scale(v.Xi(),v.Yi());}
	public Vector2i div(int scale) {return div(scale,scale);}
	public Vector2i Div(int scale) {return Div(scale,scale);}
	public Vector2i div(int scaleH, int scaleV) {x /= scaleH; y /= scaleV; return this;}
	public Vector2i Div(int scaleH, int scaleV) {return new Vector2i(x/scaleH,y/scaleV);}
	public Vector2i div(IVector2 v) {return div(v.Xi(),v.Yi());}
	public Vector2i Div(IVector2 v) {return Div(v.Xi(),v.Yi());}
	
	public Vector2i Vector(IVector2 v) {
		return new Vector2i(v.Xi()-x,v.Yi()-y);
	}
	
	public int lengthSquared() {
		return (int)(Math.pow(x,2)+Math.pow(y,2));
	}
	public int length() {
		return (int)Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
	}
	public int distanceSquared(IVector2 v) {
		return (int)(Math.pow(v.Xi()-x,2)+Math.pow(v.Yi()-y,2));
	}
	public int distance(IVector2 v) {
		return (int)Math.sqrt(Math.pow(v.Xi()-x,2)+Math.pow(v.Yi()-y,2));
	}
	public double direction() {
		return new Vector2i().direction(this);
	}
	public double direction(IVector2 v) {
		return Math.toDegrees(Math.atan2(y-v.Yi(),v.Xi()-x));
	}
	public double deltaAngle(IVector2 v) {return deltaAngle(v.direction());}
	public double deltaAngle(double angle) {
		return Math2.deltaAngle(direction(),angle);
	}
	
	public Vector2i interpolate(Vector2i v, double d, Interpolate method) {
		return new Vector2i(method.interpolate(x,v.x,d),method.interpolate(y,v.y,d));
	}
}