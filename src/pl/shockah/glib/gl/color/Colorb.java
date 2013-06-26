package pl.shockah.glib.gl.color;

public final class Colorb extends Color {
	public byte r, g, b, a;
	
	public Colorb(int v) {this(v,255);}
	public Colorb(int v, int a) {this(v,v,v,a);}
	public Colorb(int r, int g, int b) {this(r,g,b,255);}
	public Colorb(int r, int g, int b, int a) {
		this.r = (byte)r;
		this.g = (byte)g;
		this.b = (byte)b;
		this.a = (byte)a;
	}
	public Colorb(float v) {this((int)(v*255f));}
	public Colorb(float v, float a) {this((int)(v*255f),(int)(v*255f));}
	public Colorb(float r, float g, float b) {this((int)(r*255f),(int)(g*255f),(int)(b*255f));}
	public Colorb(float r, float g, float b, float a) {this((int)(r*255f),(int)(g*255f),(int)(b*255f),(int)(a*255f));}
	
	public int R() {return r & 0xFF;}
	public int G() {return g & 0xFF;}
	public int B() {return b & 0xFF;}
	public int A() {return a & 0xFF;}
	
	public float Rf() {return R()/255f;}
	public float Gf() {return G()/255f;}
	public float Bf() {return B()/255f;}
	public float Af() {return A()/255f;}
	
	public void copy(Color color) {
		r = (byte)color.R();
		g = (byte)color.G();
		b = (byte)color.B();
		a = (byte)color.A();
	}
	
	public Colorb alpha(float alpha) {
		copy(alpha(this,alpha));
		return this;
	}
	public Colorb lerp(Color color, float ratio) {
		copy(lerp(this,color,ratio));
		return this;
	}
}