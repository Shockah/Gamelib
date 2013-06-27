package pl.shockah.glib.geom.vector;

public class Vector2i implements IVector {
	public final int x, y;
	
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
	
	public Vector2i justX() {
		return new Vector2i(x,0);
	}
	public Vector2i justY() {
		return new Vector2i(0,y);
	}
	
	//region Java-OO
	public Vector2i negate() {return new Vector2i(-x,-y);}
	public Vector2i add(Vector2i v) {return add(v.x,v.y);}
	public Vector2i subtract(Vector2i v) {return subtract(v.x,v.y);}
	public Vector2i multiply(float scale) {return multiply(scale,scale);}
	public Vector2i divide(int scale) {return divide(scale,scale);}
	//endregion
	
	public Vector2i abs() {
		return new Vector2i(x >= 0 ? x : -x,y >= 0 ? y : -y);
	}
	public Vector2i add(int x, int y) {
		return new Vector2i(this.x+x,this.y+y);
	}
	public Vector2i subtract(int x, int y) {
		return add(-x,-y);
	}
	public Vector2i multiply(float scaleH, float scaleV) {
		return new Vector2i((int)(x*scaleH),(int)(y*scaleV));
	}
	public Vector2i divide(int scaleH, int scaleV) {
		return new Vector2i(x/scaleH,y/scaleV);
	}
	
	public int lengthSquared() {
		return (int)(Math.pow(x,2)+Math.pow(y,2));
	}
	public int length() {
		return (int)Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
	}
	public int distanceSquared(Vector2d v) {
		return (int)(Math.pow(v.x-x,2)+Math.pow(v.y-y,2));
	}
	public int distance(Vector2d v) {
		return (int)Math.sqrt(Math.pow(v.x-x,2)+Math.pow(v.y-y,2));
	}
}