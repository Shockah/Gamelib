package pl.shockah.glib.gl;

public final class Color {
	public static final Color
		black = new Color(0),
		transparentBlack = new Color(0,0),
		dimGray = new Color(105),
		gray = new Color(128),
		darkGray = new Color(169),
		silver = new Color(192),
		lightGray = new Color(211),
		gainsboro = new Color(220),
		whiteSmoke = new Color(245),
		transparentWhite = new Color(255,0),
		white = new Color(255),
		
		maroon = new Color(128,0,0),
		darkRed = new Color(139,0,0),
		red = new Color(255,0,0),
		firebrick = new Color(178,34,34),
		brown = new Color(165,42,42),
		saddleBrown = new Color(139,69,19),
		sienna = new Color(160,82,45),
		orangeRed = new Color(255,69,0),
		indianRed = new Color(205,92,92),
		chocolate = new Color(210,105,30),
		tomato = new Color(255,99,71),
		peru = new Color(205,133,63),
		rosyBrown = new Color(188,143,143),
		coral = new Color(255,127,80),
		lightCoral = new Color(240,128,128),
		salmon = new Color(250,128,114),
		darkSalmon = new Color(233,150,122),
		sandyBrown = new Color(244,164,96),
		lightSalmon = new Color(255,160,122),
		peachPuff = new Color(255,218,185),
		mistyRose = new Color(255,228,225),
		seaShell = new Color(255,245,238),
		snow = new Color(255,250,250);
	
	public static Color fromHSB(float H, float S, float B) {
		java.awt.Color awtc = java.awt.Color.getHSBColor(H,S,B);
		return new Color(awtc.getRed(),awtc.getGreen(),awtc.getBlue());
	}
	
	public static Color lerp(Color c1, Color c2, float ratio) {
		return c1.lerp(c2,ratio);
	}
	
	private final byte[] comp = new byte[4];
	
	public Color(int v) {this(v,255);}
	public Color(int v, int a) {this(v,v,v,a);}
	public Color(int r, int g, int b) {this(r,g,b,255);}
	public Color(int r, int g, int b, int a) {
		comp[0] = (byte)r;
		comp[1] = (byte)g;
		comp[2] = (byte)b;
		comp[3] = (byte)a;
	}
	public Color(float v) {this((int)(v*255f));}
	public Color(float v, float a) {this((int)(v*255f),(int)(v*255f));}
	public Color(float r, float g, float b) {this((int)(r*255f),(int)(g*255f),(int)(b*255f));}
	public Color(float r, float g, float b, float a) {this((int)(r*255f),(int)(g*255f),(int)(b*255f),(int)(a*255f));}
	
	public int R() {return comp[0] & 0xFF;}
	public int G() {return comp[1] & 0xFF;}
	public int B() {return comp[2] & 0xFF;}
	public int A() {return comp[3] & 0xFF;}
	
	public float Rf() {return R()/255f;}
	public float Gf() {return G()/255f;}
	public float Bf() {return B()/255f;}
	public float Af() {return A()/255f;}
	
	public Color alpha(float alpha) {
		return new Color(Rf(),Gf(),Bf(),Af()*alpha);
	}
	
	public Color lerp(Color color, float ratio) {
		if (ratio < 0 || ratio > 1) throw new IllegalArgumentException("Ratio should have a value of 0-1.");
		float R = Rf()-((Rf()-color.Rf())*ratio);
		float G = Gf()-((Gf()-color.Gf())*ratio);
		float B = Bf()-((Bf()-color.Bf())*ratio);
		float A = Af()-((Af()-color.Af())*ratio);
		return new Color(R,G,B,A);
	}
}