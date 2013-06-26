package pl.shockah.glib.gl.color;

public final class ColorbImmutable extends Color {
	private final byte[] comp = new byte[4];
	
	public ColorbImmutable(int v) {this(v,255);}
	public ColorbImmutable(int v, int a) {this(v,v,v,a);}
	public ColorbImmutable(int r, int g, int b) {this(r,g,b,255);}
	public ColorbImmutable(int r, int g, int b, int a) {
		comp[0] = (byte)r;
		comp[1] = (byte)g;
		comp[2] = (byte)b;
		comp[3] = (byte)a;
	}
	public ColorbImmutable(float v) {this((int)(v*255f));}
	public ColorbImmutable(float v, float a) {this((int)(v*255f),(int)(v*255f));}
	public ColorbImmutable(float r, float g, float b) {this((int)(r*255f),(int)(g*255f),(int)(b*255f));}
	public ColorbImmutable(float r, float g, float b, float a) {this((int)(r*255f),(int)(g*255f),(int)(b*255f),(int)(a*255f));}
	
	public int R() {return comp[0] & 0xFF;}
	public int G() {return comp[1] & 0xFF;}
	public int B() {return comp[2] & 0xFF;}
	public int A() {return comp[3] & 0xFF;}
	
	public float Rf() {return R()/255f;}
	public float Gf() {return G()/255f;}
	public float Bf() {return B()/255f;}
	public float Af() {return A()/255f;}
	
	public ColorbImmutable copy(Color color) {
		return new ColorbImmutable(color.R(),color.G(),color.B(),color.A());
	}
	
	public ColorbImmutable alpha(float alpha) {
		return alpha(this,alpha).toColorbImmutable();
	}
	public ColorbImmutable lerp(Color color, float ratio) {
		return lerp(this,color,ratio).toColorbImmutable();
	}
}