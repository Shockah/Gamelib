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
	
	public Vector2d add(Vector2d v) {return add(v.x,v.y);}
	public Vector2d add(double x, double y) {
		return new Vector2d(this.x+x,this.y+y);
	}
	public Vector2d sub(Vector2d v) {return sub(v.x,v.y);}
	public Vector2d sub(double x, double y) {return add(-x,-y);}
	
	public Vector2d scale(double scale) {return scale(scale,scale);}
	public Vector2d scale(double scaleH, double scaleV) {
		return new Vector2d(x*scaleH,y*scaleV);
	}
	
	public double distanceSquared(Vector2d v) {
		return Math.pow(v.x-x,2)+Math.pow(v.y-y,2);
	}
	public double distance(Vector2d v) {
		return Math.sqrt(distanceSquared(v));
	}
}