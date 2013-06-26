package pl.shockah.glib.gl.color;

import static org.lwjgl.opengl.GL11.*;
import pl.shockah.glib.IBoundable;

public abstract class Color implements IBoundable {
	public static final Color
		Black = new ColorbImmutable(0),
		TransparentBlack = new ColorbImmutable(0,0),
		DimGray = new ColorbImmutable(105),
		Gray = new ColorbImmutable(128),
		DarkGray = new ColorbImmutable(169),
		Silver = new ColorbImmutable(192),
		LightGray = new ColorbImmutable(211),
		Gainsboro = new ColorbImmutable(220),
		WhiteSmoke = new ColorbImmutable(245),
		TransparentWhite = new ColorbImmutable(255,0),
		White = new ColorbImmutable(255),
		
		Maroon = new ColorbImmutable(128,0,0),
		DarkRed = new ColorbImmutable(139,0,0),
		Red = new ColorbImmutable(255,0,0),
		Firebrick = new ColorbImmutable(178,34,34),
		Brown = new ColorbImmutable(165,42,42),
		SaddleBrown = new ColorbImmutable(139,69,19),
		Sienna = new ColorbImmutable(160,82,45),
		OrangeRed = new ColorbImmutable(255,69,0),
		IndianRed = new ColorbImmutable(205,92,92),
		Chocolate = new ColorbImmutable(210,105,30),
		Tomato = new ColorbImmutable(255,99,71),
		Peru = new ColorbImmutable(205,133,63),
		RosyBrown = new ColorbImmutable(188,143,143),
		Coral = new ColorbImmutable(255,127,80),
		LightCoral = new ColorbImmutable(240,128,128),
		Salmon = new ColorbImmutable(250,128,114),
		DarkSalmon = new ColorbImmutable(233,150,122),
		SandyBrown = new ColorbImmutable(244,164,96),
		LightSalmon = new ColorbImmutable(255,160,122),
		PeachPuff = new ColorbImmutable(255,218,185),
		MistyRose = new ColorbImmutable(255,228,225),
		SeaShell = new ColorbImmutable(255,245,238),
		Snow = new ColorbImmutable(255,250,250),
		
		DarkGoldenrod = new ColorbImmutable(184,134,11),
		DarkOrange = new ColorbImmutable(255,140,0),
		Goldenrod = new ColorbImmutable(218,165,32),
		Orange = new ColorbImmutable(255,165,0),
		DarkKhaki = new ColorbImmutable(189,183,107),
		Tan = new ColorbImmutable(210,180,140),
		BurlyWood = new ColorbImmutable(222,184,135),
		Gold = new ColorbImmutable(255,215,0),
		Khaki = new ColorbImmutable(240,230,140),
		Wheat = new ColorbImmutable(245,222,179),
		NavajoWhite = new ColorbImmutable(255,222,173),
		PaleGoldenrod = new ColorbImmutable(238,232,170),
		Moccasin = new ColorbImmutable(255,228,181),
		Bisque = new ColorbImmutable(255,228,196),
		AntiqueWhite = new ColorbImmutable(250,235,215),
		BlanchedAlmond = new ColorbImmutable(255,235,205),
		PapayaWhip = new ColorbImmutable(255,239,213),
		Linen = new ColorbImmutable(250,240,230),
		OldLace = new ColorbImmutable(253,245,230),
		LemonChiffon = new ColorbImmutable(255,250,205),
		Cornsilk = new ColorbImmutable(255,248,220),
		FloralWhite = new ColorbImmutable(255,250,240),
		
		DarkOliveGreen = new ColorbImmutable(85,107,47),
		Olive = new ColorbImmutable(128,128,0),
		OliveDrab = new ColorbImmutable(107,142,35),
		YellowGreen = new ColorbImmutable(154,205,50),
		GreenYellow = new ColorbImmutable(173,255,47),
		Yellow = new ColorbImmutable(255,255,0),
		Beige = new ColorbImmutable(245,245,220),
		LightGoldenrodYellow = new ColorbImmutable(250,250,210),
		LightYellow = new ColorbImmutable(255,255,224),
		Ivory = new ColorbImmutable(255,255,240),
		
		DarkSeaGreen = new ColorbImmutable(143,188,139),
		LawnGreen = new ColorbImmutable(124,252,0),
		Chartreuse = new ColorbImmutable(127,255,0),
		
		DarkGreen = new ColorbImmutable(0,100,0),
		Green = new ColorbImmutable(0,128,0),
		ForestGreen = new ColorbImmutable(34,139,34),
		SeaGreen = new ColorbImmutable(46,139,87),
		MediumSeaGreen = new ColorbImmutable(60,179,113),
		LimeGreen = new ColorbImmutable(50,205,50),
		Lime = new ColorbImmutable(0,255,0),
		SpringGreen = new ColorbImmutable(0,255,127),
		LightGreen = new ColorbImmutable(144,238,144),
		PaleGreen = new ColorbImmutable(152,251,152),
		Honeydew = new ColorbImmutable(240,255,240),
		
		LightSeaGreen = new ColorbImmutable(32,178,170),
		MediumSpringGreen = new ColorbImmutable(0,250,154),
		MediumTurquoise = new ColorbImmutable(72,209,204),
		MediumAquamarine = new ColorbImmutable(102,205,170),
		Turquiose = new ColorbImmutable(64,224,208),
		Aquamarine = new ColorbImmutable(127,255,212),
		MintCream = new ColorbImmutable(245,255,250),
		
		DarkSlateGray = new ColorbImmutable(47,79,79),
		Teal = new ColorbImmutable(0,128,128),
		DarkCyan = new ColorbImmutable(0,139,139),
		SteelBlue = new ColorbImmutable(70,130,180),
		DodgerBlue = new ColorbImmutable(30,144,255),
		CadetBlue = new ColorbImmutable(95,158,160),
		DeepSkyBlue = new ColorbImmutable(0,191,255),
		DarkTurquoise = new ColorbImmutable(0,206,209),
		Aqua = new ColorbImmutable(0,255,255),
		Cyan = new ColorbImmutable(0,255,255),
		SkyBlue = new ColorbImmutable(135,206,235),
		LightSkyBlue = new ColorbImmutable(135,206,250),
		LightBlue = new ColorbImmutable(173,216,230),
		PowderBlue = new ColorbImmutable(176,224,230),
		PaleTurquoise = new ColorbImmutable(175,238,238),
		LightCyan = new ColorbImmutable(224,255,255),
		AliceBlue = new ColorbImmutable(240,248,255),
		Azure = new ColorbImmutable(240,255,255),
		
		RoyalBlue = new ColorbImmutable(65,105,225),
		SlateGray = new ColorbImmutable(112,128,144),
		LightSlateGray = new ColorbImmutable(119,136,153),
		CornflowerBlue = new ColorbImmutable(100,149,237),
		LightSteelBlue = new ColorbImmutable(176,196,222),
		
		Navy = new ColorbImmutable(0,0,128),
		DarkBlue = new ColorbImmutable(0,0,139),
		MediumBlue = new ColorbImmutable(0,0,205),
		Blue = new ColorbImmutable(0,0,255),
		MidnightBlue = new ColorbImmutable(25,25,112),
		DarkSlateBlue = new ColorbImmutable(72,61,139),
		SlateBlue = new ColorbImmutable(106,90,205),
		MediumSlateBlue = new ColorbImmutable(123,104,238),
		MediumPurple = new ColorbImmutable(147,112,219),
		Lavender = new ColorbImmutable(230,230,250),
		GhostWhite = new ColorbImmutable(248,248,255),
		
		Indigo = new ColorbImmutable(75,0,130),
		DarkViolet = new ColorbImmutable(148,0,211),
		BlueViolet = new ColorbImmutable(138,43,226),
		DarkOrchid = new ColorbImmutable(153,50,204),
		MediumOrchid = new ColorbImmutable(186,85,211),
		
		Purple = new ColorbImmutable(128,0,128),
		DarkMagenta = new ColorbImmutable(139,0,139),
		MediumVioletRed = new ColorbImmutable(199,21,133),
		DeepPink = new ColorbImmutable(255,20,147),
		Fuchsia = new ColorbImmutable(255,0,255),
		Magenta = new ColorbImmutable(255,0,255),
		Orchid = new ColorbImmutable(218,112,214),
		Violet = new ColorbImmutable(238,130,238),
		Plum = new ColorbImmutable(221,160,221),
		Thistle = new ColorbImmutable(216,191,216),
		
		Crimson = new ColorbImmutable(220,20,60),
		PaleVioletRed = new ColorbImmutable(219,112,147),
		HotPink = new ColorbImmutable(255,105,180),
		LightPink = new ColorbImmutable(225,182,193),
		Pink = new ColorbImmutable(255,192,203),
		LavenderBlush = new ColorbImmutable(255,240,245);
	
	public static Color fromHSB(float H, float S, float B) {
		java.awt.Color awtc = java.awt.Color.getHSBColor(H,S,B);
		return new ColorbImmutable(awtc.getRed(),awtc.getGreen(),awtc.getBlue());
	}
	public static Color fromPackedValue(int value) {
		return new ColorbImmutable((value & 0x00FF0000) >> 16,(value & 0x0000FF00) >> 8,(value & 0x000000FF),(value & 0xFF000000) >> 24);
	}
	
	public static Color alpha(Color color, float alpha) {
		return new ColorbImmutable(color.Rf(),color.Gf(),color.Bf(),color.Af()*alpha);
	}
	public static Color lerp(Color c1, Color c2, float ratio) {
		if (ratio < 0 || ratio > 1) throw new IllegalArgumentException("Ratio should have a value of 0-1.");
		float R = c1.Rf()-((c1.Rf()-c2.Rf())*ratio);
		float G = c1.Gf()-((c1.Gf()-c2.Gf())*ratio);
		float B = c1.Bf()-((c1.Bf()-c2.Bf())*ratio);
		float A = c1.Af()-((c1.Af()-c2.Af())*ratio);
		return new ColorbImmutable(R,G,B,A);
	}
	
	public boolean equals(Object other) {
		if (!(other instanceof Color)) return false;
		Color c = (Color)other;
		return R() == c.R() && G() == c.G() && B() == c.B() && A() == c.A();
	}
	
	public abstract int R();
	public abstract int G();
	public abstract int B();
	public abstract int A();
	
	public abstract float Rf();
	public abstract float Gf();
	public abstract float Bf();
	public abstract float Af();
	
	public void bindMe() {
		glColor4b((byte)R(),(byte)G(),(byte)B(),(byte)A());
	}
	public void unbindMe() {}
	
	public Colorb toColorb() {
		if (getClass() == Colorb.class) return (Colorb)this;
		return new Colorb(R(),G(),B(),A());
	}
	public ColorbImmutable toColorbImmutable() {
		if (getClass() == ColorbImmutable.class) return (ColorbImmutable)this;
		return new ColorbImmutable(R(),G(),B(),A());
	}
	public Colorf toColorf() {
		if (getClass() == Colorf.class) return (Colorf)this;
		return new Colorf(Rf(),Gf(),Bf(),Af());
	}
	public ColorfImmutable toColorfImmutable() {
		if (getClass() == ColorfImmutable.class) return (ColorfImmutable)this;
		return new ColorfImmutable(Rf(),Gf(),Bf(),Af());
	}
}