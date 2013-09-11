package pl.shockah.glib.gl.color;

import static org.lwjgl.opengl.GL11.*;
import pl.shockah.glib.Math2;
import pl.shockah.glib.animfx.IInterpolatable;
import pl.shockah.glib.animfx.Interpolate;

public abstract class Color implements IInterpolatable<Color> {
	public static final Color
		Black = new Colorb(0),
		TransparentBlack = new Colorb(0,0),
		DimGray = new Colorb(105),
		Gray = new Colorb(128),
		DarkGray = new Colorb(169),
		Silver = new Colorb(192),
		LightGray = new Colorb(211),
		Gainsboro = new Colorb(220),
		WhiteSmoke = new Colorb(245),
		TransparentWhite = new Colorb(255,0),
		White = new Colorb(255),
		
		Maroon = new Colorb(128,0,0),
		DarkRed = new Colorb(139,0,0),
		Red = new Colorb(255,0,0),
		Firebrick = new Colorb(178,34,34),
		Brown = new Colorb(165,42,42),
		SaddleBrown = new Colorb(139,69,19),
		Sienna = new Colorb(160,82,45),
		OrangeRed = new Colorb(255,69,0),
		IndianRed = new Colorb(205,92,92),
		Chocolate = new Colorb(210,105,30),
		Tomato = new Colorb(255,99,71),
		Peru = new Colorb(205,133,63),
		RosyBrown = new Colorb(188,143,143),
		Coral = new Colorb(255,127,80),
		LightCoral = new Colorb(240,128,128),
		Salmon = new Colorb(250,128,114),
		DarkSalmon = new Colorb(233,150,122),
		SandyBrown = new Colorb(244,164,96),
		LightSalmon = new Colorb(255,160,122),
		PeachPuff = new Colorb(255,218,185),
		MistyRose = new Colorb(255,228,225),
		SeaShell = new Colorb(255,245,238),
		Snow = new Colorb(255,250,250),
		
		DarkGoldenrod = new Colorb(184,134,11),
		DarkOrange = new Colorb(255,140,0),
		Goldenrod = new Colorb(218,165,32),
		Orange = new Colorb(255,165,0),
		DarkKhaki = new Colorb(189,183,107),
		Tan = new Colorb(210,180,140),
		BurlyWood = new Colorb(222,184,135),
		Gold = new Colorb(255,215,0),
		Khaki = new Colorb(240,230,140),
		Wheat = new Colorb(245,222,179),
		NavajoWhite = new Colorb(255,222,173),
		PaleGoldenrod = new Colorb(238,232,170),
		Moccasin = new Colorb(255,228,181),
		Bisque = new Colorb(255,228,196),
		AntiqueWhite = new Colorb(250,235,215),
		BlanchedAlmond = new Colorb(255,235,205),
		PapayaWhip = new Colorb(255,239,213),
		Linen = new Colorb(250,240,230),
		OldLace = new Colorb(253,245,230),
		LemonChiffon = new Colorb(255,250,205),
		Cornsilk = new Colorb(255,248,220),
		FloralWhite = new Colorb(255,250,240),
		
		DarkOliveGreen = new Colorb(85,107,47),
		Olive = new Colorb(128,128,0),
		OliveDrab = new Colorb(107,142,35),
		YellowGreen = new Colorb(154,205,50),
		GreenYellow = new Colorb(173,255,47),
		Yellow = new Colorb(255,255,0),
		Beige = new Colorb(245,245,220),
		LightGoldenrodYellow = new Colorb(250,250,210),
		LightYellow = new Colorb(255,255,224),
		Ivory = new Colorb(255,255,240),
		
		DarkSeaGreen = new Colorb(143,188,139),
		LawnGreen = new Colorb(124,252,0),
		Chartreuse = new Colorb(127,255,0),
		
		DarkGreen = new Colorb(0,100,0),
		Green = new Colorb(0,128,0),
		ForestGreen = new Colorb(34,139,34),
		SeaGreen = new Colorb(46,139,87),
		MediumSeaGreen = new Colorb(60,179,113),
		LimeGreen = new Colorb(50,205,50),
		Lime = new Colorb(0,255,0),
		SpringGreen = new Colorb(0,255,127),
		LightGreen = new Colorb(144,238,144),
		PaleGreen = new Colorb(152,251,152),
		Honeydew = new Colorb(240,255,240),
		
		LightSeaGreen = new Colorb(32,178,170),
		MediumSpringGreen = new Colorb(0,250,154),
		MediumTurquoise = new Colorb(72,209,204),
		MediumAquamarine = new Colorb(102,205,170),
		Turquiose = new Colorb(64,224,208),
		Aquamarine = new Colorb(127,255,212),
		MintCream = new Colorb(245,255,250),
		
		DarkSlateGray = new Colorb(47,79,79),
		Teal = new Colorb(0,128,128),
		DarkCyan = new Colorb(0,139,139),
		SteelBlue = new Colorb(70,130,180),
		DodgerBlue = new Colorb(30,144,255),
		CadetBlue = new Colorb(95,158,160),
		DeepSkyBlue = new Colorb(0,191,255),
		DarkTurquoise = new Colorb(0,206,209),
		Aqua = new Colorb(0,255,255),
		Cyan = new Colorb(0,255,255),
		SkyBlue = new Colorb(135,206,235),
		LightSkyBlue = new Colorb(135,206,250),
		LightBlue = new Colorb(173,216,230),
		PowderBlue = new Colorb(176,224,230),
		PaleTurquoise = new Colorb(175,238,238),
		LightCyan = new Colorb(224,255,255),
		AliceBlue = new Colorb(240,248,255),
		Azure = new Colorb(240,255,255),
		
		RoyalBlue = new Colorb(65,105,225),
		SlateGray = new Colorb(112,128,144),
		LightSlateGray = new Colorb(119,136,153),
		CornflowerBlue = new Colorb(100,149,237),
		LightSteelBlue = new Colorb(176,196,222),
		
		Navy = new Colorb(0,0,128),
		DarkBlue = new Colorb(0,0,139),
		MediumBlue = new Colorb(0,0,205),
		Blue = new Colorb(0,0,255),
		MidnightBlue = new Colorb(25,25,112),
		DarkSlateBlue = new Colorb(72,61,139),
		SlateBlue = new Colorb(106,90,205),
		MediumSlateBlue = new Colorb(123,104,238),
		MediumPurple = new Colorb(147,112,219),
		Lavender = new Colorb(230,230,250),
		GhostWhite = new Colorb(248,248,255),
		
		Indigo = new Colorb(75,0,130),
		DarkViolet = new Colorb(148,0,211),
		BlueViolet = new Colorb(138,43,226),
		DarkOrchid = new Colorb(153,50,204),
		MediumOrchid = new Colorb(186,85,211),
		
		Purple = new Colorb(128,0,128),
		DarkMagenta = new Colorb(139,0,139),
		MediumVioletRed = new Colorb(199,21,133),
		DeepPink = new Colorb(255,20,147),
		Fuchsia = new Colorb(255,0,255),
		Magenta = new Colorb(255,0,255),
		Orchid = new Colorb(218,112,214),
		Violet = new Colorb(238,130,238),
		Plum = new Colorb(221,160,221),
		Thistle = new Colorb(216,191,216),
		
		Crimson = new Colorb(220,20,60),
		PaleVioletRed = new Colorb(219,112,147),
		HotPink = new Colorb(255,105,180),
		LightPink = new Colorb(225,182,193),
		Pink = new Colorb(255,192,203),
		LavenderBlush = new Colorb(255,240,245);
	
	public static Color fromHSB(float H, float S, float B) {
		java.awt.Color awtc = java.awt.Color.getHSBColor(H,S,B);
		return new Colorb(awtc.getRed(),awtc.getGreen(),awtc.getBlue());
	}
	public static Color fromPackedValue(int value) {
		return new Colorb((value & 0x00FF0000) >> 16,(value & 0x0000FF00) >> 8,(value & 0x000000FF),(value & 0xFF000000) >> 24);
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
	
	public Color copyMe() {
		return new ColorbMutable(R(),G(),B(),A());
	}
	public void copy(Color c) {
		throw new UnsupportedOperationException();
	}
	
	public void bind() {
		glColor4f(Rf(),Gf(),Bf(),Af());
	}
	public void unbind() {}
	
	public Color alpha(float alpha) {
		return new Colorb(Rf(),Gf(),Bf(),Math2.limit(Af()*alpha,0f,1f));
	}
	public Color setAlpha(float alpha) {
		return new Colorb(Rf(),Gf(),Bf(),Math2.limit(alpha,0f,1f));
	}
	public Color lerp(Color c2, float ratio) {
		if (ratio < 0 || ratio > 1) throw new IllegalArgumentException("Ratio should have a value of 0-1.");
		float R = Rf()-((Rf()-c2.Rf())*ratio);
		float G = Gf()-((Gf()-c2.Gf())*ratio);
		float B = Bf()-((Bf()-c2.Bf())*ratio);
		float A = Af()-((Af()-c2.Af())*ratio);
		return new Colorb(R,G,B,A);
	}
	public Color inverse() {
		return new Colorb(255-R(),255-G(),255-B(),A());
	}
	
	public ColorbMutable toColorbMutable() {
		if (getClass() == ColorbMutable.class) return (ColorbMutable)this;
		return new ColorbMutable(R(),G(),B(),A());
	}
	public Colorb toColorb() {
		if (getClass() == Colorb.class) return (Colorb)this;
		return new Colorb(R(),G(),B(),A());
	}
	public ColorfMutable toColorfMutable() {
		if (getClass() == ColorfMutable.class) return (ColorfMutable)this;
		return new ColorfMutable(Rf(),Gf(),Bf(),Af());
	}
	public Colorf toColorf() {
		if (getClass() == Colorf.class) return (Colorf)this;
		return new Colorf(Rf(),Gf(),Bf(),Af());
	}
	
	public Color interpolate(Color c, double d, Interpolate method) {
		return new ColorbMutable(method.interpolate(Rf(),c.Rf(),d),method.interpolate(Gf(),c.Gf(),d),method.interpolate(Bf(),c.Bf(),d),method.interpolate(Af(),c.Af(),d));
	}
}