package pl.shockah.glib.gl.font;

import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.geom.vector.Vector2f;
import pl.shockah.glib.geom.vector.Vector2i;
import pl.shockah.glib.gl.Graphics;

public final class TextHelper {
	public static void draw(Graphics g, Font font, Vector2d v, CharSequence text) {draw(g,font,v.x,v.y,text);}
	public static void draw(Graphics g, Font font, Vector2f v, CharSequence text) {draw(g,font,v.x,v.y,text);}
	public static void draw(Graphics g, Font font, Vector2i v, CharSequence text) {draw(g,font,v.x,v.y,text);}
	
	public static void draw(Graphics g, Font font, Vector2d v, CharSequence text, ETextAlign align) {draw(g,font,v.x,v.y,text,align);}
	public static void draw(Graphics g, Font font, Vector2f v, CharSequence text, ETextAlign align) {draw(g,font,v.x,v.y,text,align);}
	public static void draw(Graphics g, Font font, Vector2i v, CharSequence text, ETextAlign align) {draw(g,font,v.x,v.y,text,align);}
	
	public static void draw(Graphics g, Font font, Vector2d v, CharSequence text, Vector2d alignScale) {draw(g,font,v.x,v.y,text,alignScale);}
	public static void draw(Graphics g, Font font, Vector2f v, CharSequence text, Vector2d alignScale) {draw(g,font,v.x,v.y,text,alignScale);}
	public static void draw(Graphics g, Font font, Vector2i v, CharSequence text, Vector2d alignScale) {draw(g,font,v.x,v.y,text,alignScale);}
	
	public static void draw(Graphics g, Font font, double x, double y, CharSequence text) {draw(g,font,x,y,text,ETextAlign.TopLeft);}
	public static void draw(Graphics g, Font font, double x, double y, CharSequence text, ETextAlign align) {draw(g,font,x,y,text,align.getScale());}
	public static void draw(Graphics g, Font font, double x, double y, CharSequence text, Vector2d alignScale) {
		String s = text.toString();
		s = s.replace("\t","    ");
		String[] ss = s.split("\\r?\\n");
		
		Rectangle rect = new Rectangle(0,0,0,0);
		double h = font.getHeight();
		for (String s2 : ss) {
			double w = font.getWidth(s2);
			if (w > rect.size.x) rect.size.x = w;
			rect.size.y += h;
		}
		
		rect.pos.y = rect.size.y*alignScale.y;
		draw(g,font,x,y,rect,ss,alignScale.x);
	}
	
	private static void draw(Graphics g, Font font, double x, double y, Rectangle rect, String[] ss, double xAlignScale) {
		for (int i = 0; i < ss.length; i++) font.draw(g,x+font.getWidth(ss[i])*xAlignScale,y+rect.pos.y+rect.size.y/ss.length*i,ss[i]);
	}
}