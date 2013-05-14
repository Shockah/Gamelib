package pl.shockah.glib.geom;

public class Vector2i {
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
	
	public Vector2i add(Vector2i v) {return add(v.x,v.y);}
	public Vector2i add(int x, int y) {
		return new Vector2i(this.x+x,this.y+y);
	}
	public Vector2i sub(Vector2i v) {return sub(v.x,v.y);}
	public Vector2i sub(int x, int y) {return add(-x,-y);}
	
	public Vector2i scale(float scale) {return scale(scale,scale);}
	public Vector2i scale(float scaleH, float scaleV) {
		return new Vector2i((int)(x*scaleH),(int)(y*scaleV));
	}
}