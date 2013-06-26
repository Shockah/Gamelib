package pl.shockah.glib.geom.vector;

public class Vector2d implements IVector {
	public final double x, y;
	
	public Vector2d() {
		this(0,0);
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
	
	public Vector2i toInt() {
		return new Vector2i((int)x,(int)y);
	}
	public Vector2f toFloat() {
		return new Vector2f((float)x,(float)y);
	}
	
	//region Java-OO
	public Vector2d negate() {return new Vector2d(-x,-y);}
	public Vector2d add(Vector2d v) {return add(v.x,v.y);}
	public Vector2d subtract(Vector2d v) {return subtract(v.x,v.y);}
	public Vector2d multiply(double scale) {return multiply(scale,scale);}
	public Vector2d divide(double scale) {return divide(scale,scale);}
	//endregion
	
	public Vector2d abs() {
		return new Vector2d(x >= 0 ? x : -x,y >= 0 ? y : -y);
	}
	public Vector2d add(double x, double y) {
		return new Vector2d(this.x+x,this.y+y);
	}
	public Vector2d subtract(double x, double y) {
		return add(-x,-y);
	}
	public Vector2d multiply(double scaleH, double scaleV) {
		return new Vector2d(x*scaleH,y*scaleV);
	}
	public Vector2d divide(double scaleH, double scaleV) {
		return multiply(1/scaleH,1/scaleV);
	}
	
	public double distanceSquared(Vector2d v) {
		return Math.pow(v.x-x,2)+Math.pow(v.y-y,2);
	}
	public double distance(Vector2d v) {
		return Math.sqrt(distanceSquared(v));
	}
}