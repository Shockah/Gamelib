package pl.shockah.glib.geom;

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
	
	public Vector2f add(Vector2f v) {return add(v.x,v.y);}
	public Vector2f add(float x, float y) {
		return new Vector2f(this.x+x,this.y+y);
	}
	public Vector2f sub(Vector2f v) {return sub(v.x,v.y);}
	public Vector2f sub(float x, float y) {return add(-x,-y);}
	
	public Vector2f scale(float scale) {return scale(scale,scale);}
	public Vector2f scale(float scaleH, float scaleV) {
		return new Vector2f(x*scaleH,y*scaleV);
	}
}