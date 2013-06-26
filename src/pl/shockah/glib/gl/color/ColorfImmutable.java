package pl.shockah.glib.gl.color;

import static org.lwjgl.opengl.GL11.*;

public final class ColorfImmutable extends Color {
	public final float r, g, b, a;
	
	public ColorfImmutable(int v) {this(v,255);}
	public ColorfImmutable(int v, int a) {this(v,v,v,a);}
	public ColorfImmutable(int r, int g, int b) {this(r,g,b,255);}
	public ColorfImmutable(int r, int g, int b, int a) {this(r/255f,g/255f,b/255f,a/255f);}
	public ColorfImmutable(float v) {this(v,1f);}
	public ColorfImmutable(float v, float a) {this(v,v,v,a);}
	public ColorfImmutable(float r, float g, float b) {this(r,g,b,1f);}
	public ColorfImmutable(float r, float g, float b, float a) {
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
	
	public ColorfImmutable copy(Color color) {
		return new ColorfImmutable(color.Rf(),color.Gf(),color.Bf(),color.Af());
	}
	
	public ColorfImmutable alpha(float alpha) {
		return alpha(this,alpha).toColorfImmutable();
	}
	public ColorfImmutable lerp(Color color, float ratio) {
		return lerp(this,color,ratio).toColorfImmutable();
	}
	
	public void bindMe() {
		glColor4f(Rf(),Gf(),Bf(),Af());
	}
}