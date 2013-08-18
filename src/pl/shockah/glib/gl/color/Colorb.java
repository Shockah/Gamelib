package pl.shockah.glib.gl.color;

public final class Colorb extends Color {
	private final byte[] comp = new byte[4];
	
	public Colorb(int v) {this(v,255);}
	public Colorb(int v, int a) {this(v,v,v,a);}
	public Colorb(int r, int g, int b) {this(r,g,b,255);}
	public Colorb(int r, int g, int b, int a) {
		comp[0] = (byte)r;
		comp[1] = (byte)g;
		comp[2] = (byte)b;
		comp[3] = (byte)a;
	}
	public Colorb(float v) {this((int)(v*255f));}
	public Colorb(float v, float a) {this((int)(v*255f),(int)(v*255f));}
	public Colorb(float r, float g, float b) {this((int)(r*255f),(int)(g*255f),(int)(b*255f));}
	public Colorb(float r, float g, float b, float a) {this((int)(r*255f),(int)(g*255f),(int)(b*255f),(int)(a*255f));}
	
	public int R() {return comp[0] & 0xFF;}
	public int G() {return comp[1] & 0xFF;}
	public int B() {return comp[2] & 0xFF;}
	public int A() {return comp[3] & 0xFF;}
	
	public float Rf() {return R()/255f;}
	public float Gf() {return G()/255f;}
	public float Bf() {return B()/255f;}
	public float Af() {return A()/255f;}
}