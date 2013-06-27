package pl.shockah.glib.geom.vector;

public class Vector2f implements IVector {
	public final float x, y;
	
	public Vector2f() {
		this(0,0);
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
		return "[Vector2f: "+x+" | "+y+"]";
	}
	
	public Vector2i toInt() {
		return new Vector2i((int)x,(int)y);
	}
	public Vector2d toDouble() {
		return new Vector2d(x,y);
	}
	
	//region Java-OO
	public Vector2f negate() {return new Vector2f(-x,-y);}
	public Vector2f add(Vector2f v) {return add(v.x,v.y);}
	public Vector2f subtract(Vector2f v) {return subtract(v.x,v.y);}
	public Vector2f multiply(float scale) {return multiply(scale,scale);}
	public Vector2f divide(float scale) {return divide(scale,scale);}
	//endregion
	
	public Vector2f abs() {
		return new Vector2f(x >= 0 ? x : -x,y >= 0 ? y : -y);
	}
	public Vector2f add(float x, float y) {
		return new Vector2f(this.x+x,this.y+y);
	}
	public Vector2f subtract(float x, float y) {
		return add(-x,-y);
	}
	public Vector2f multiply(float scaleH, float scaleV) {
		return new Vector2f(x*scaleH,y*scaleV);
	}
	public Vector2f divide(float scaleH, float scaleV) {
		return multiply(1/scaleH,1/scaleV);
	}
	
	public float lengthSquared() {
		return (float)(Math.pow(x,2)+Math.pow(y,2));
	}
	public float length() {
		return (float)Math.sqrt(lengthSquared());
	}
	public float distanceSquared(Vector2d v) {
		return (float)(Math.pow(v.x-x,2)+Math.pow(v.y-y,2));
	}
	public float distance(Vector2d v) {
		return (float)Math.sqrt(distanceSquared(v));
	}
}