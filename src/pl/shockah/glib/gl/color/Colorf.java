package pl.shockah.glib.gl.color;

public final class Colorf extends Color {
	public float r, g, b, a;
	
	public Colorf(int v) {this(v,255);}
	public Colorf(int v, int a) {this(v,v,v,a);}
	public Colorf(int r, int g, int b) {this(r,g,b,255);}
	public Colorf(int r, int g, int b, int a) {this(r/255f,g/255f,b/255f,a/255f);}
	public Colorf(float v) {this(v,1f);}
	public Colorf(float v, float a) {this(v,v,v,a);}
	public Colorf(float r, float g, float b) {this(r,g,b,1f);}
	public Colorf(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public int R() {return (int)(r*255);}
	public int G() {return (int)(g*255);}
	public int B() {return (int)(b*255);}
	public int A() {return (int)(a*255);}
	
	public float Rf() {return r;}
	public float Gf() {return g;}
	public float Bf() {return b;}
	public float Af() {return a;}
	
	public void copy(Color color) {
		r = color.Rf();
		g = color.Gf();
		b = color.Bf();
		a = color.Af();
	}
}