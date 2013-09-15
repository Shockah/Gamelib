package pl.shockah.glib.gl.font;

import java.util.LinkedList;
import java.util.List;
import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.geom.vector.Vector2f;
import pl.shockah.glib.geom.vector.Vector2i;
import pl.shockah.glib.gl.Graphics;

public final class TextHelper {
	public static CharSequence prepare(CharSequence text) {
		return text.toString().replace("\t","    ");
	}
	
	public static CharSequence wordwrap(Font font, CharSequence text, double maxWidth) {
		List<String> ret = new LinkedList<>();
		String[] ss = prepare(text).toString().split("\\r?\\n");
		for (String s : ss) {
			s = s.trim();
			while (true) {
				if (s.isEmpty()) break;
				String s2 = s;
				if (font.getWidth(s) > maxWidth) {
					String[] words = s.split("\\S+"), nwords = s.split("\\s+");
					
					for (int i = words.length; i >= 0; i--) {
						String sret = implodeWords(words,nwords,i);
						if (font.getWidth(sret) <= maxWidth) {
							s = s.substring(sret.length());
							ret.add(sret);
							break;
						}
					}
				} else {
					ret.add(s);
					break;
				}
				if (s2.equals(s)) {
					ret.add(s);
					break;
				}
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for (String s : ret) {
			if (sb.length() != 0) sb.append("\n");
			sb.append(s);
		}
		return sb;
	}
	private static String implodeWords(String[] words, String[] nwords, int n) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append(words[i]);
			if (i != n-1) sb.append(nwords[i]);
		}
		return sb.toString();
	}
	
	public static void draw(Graphics g, Font font, Vector2d v, CharSequence text) {draw(g,font,v.x,v.y,text);}
	public static void draw(Graphics g, Font font, Vector2f v, CharSequence text) {draw(g,font,v.x,v.y,text);}
	public static void draw(Graphics g, Font font, Vector2i v, CharSequence text) {draw(g,font,v.x,v.y,text);}
	public static void draw(Graphics g, Font font, Vector2d v, CharSequence text, ETextAlign align) {draw(g,font,v.x,v.y,text,align);}
	public static void draw(Graphics g, Font font, Vector2f v, CharSequence text, ETextAlign align) {draw(g,font,v.x,v.y,text,align);}
	public static void draw(Graphics g, Font font, Vector2i v, CharSequence text, ETextAlign align) {draw(g,font,v.x,v.y,text,align);}
	public static void draw(Graphics g, Font font, Vector2d v, CharSequence text, Vector2d alignScale) {draw(g,font,v.x,v.y,text,alignScale);}
	public static void draw(Graphics g, Font font, Vector2f v, CharSequence text, Vector2d alignScale) {draw(g,font,v.x,v.y,text,alignScale);}
	public static void draw(Graphics g, Font font, Vector2i v, CharSequence text, Vector2d alignScale) {draw(g,font,v.x,v.y,text,alignScale);}
	
	public static void drawWrapped(Graphics g, Font font, Vector2d v, CharSequence text, double maxWidth) {draw(g,font,v.x,v.y,wordwrap(font,text,maxWidth));}
	public static void drawWrapped(Graphics g, Font font, Vector2f v, CharSequence text, double maxWidth) {draw(g,font,v.x,v.y,wordwrap(font,text,maxWidth));}
	public static void drawWrapped(Graphics g, Font font, Vector2i v, CharSequence text, double maxWidth) {draw(g,font,v.x,v.y,wordwrap(font,text,maxWidth));}
	public static void drawWrapped(Graphics g, Font font, Vector2d v, CharSequence text, ETextAlign align, double maxWidth) {draw(g,font,v.x,v.y,wordwrap(font,text,maxWidth),align);}
	public static void drawWrapped(Graphics g, Font font, Vector2f v, CharSequence text, ETextAlign align, double maxWidth) {draw(g,font,v.x,v.y,wordwrap(font,text,maxWidth),align);}
	public static void drawWrapped(Graphics g, Font font, Vector2i v, CharSequence text, ETextAlign align, double maxWidth) {draw(g,font,v.x,v.y,wordwrap(font,text,maxWidth),align);}
	public static void drawWrapped(Graphics g, Font font, Vector2d v, CharSequence text, Vector2d alignScale, double maxWidth) {draw(g,font,v.x,v.y,wordwrap(font,text,maxWidth),alignScale);}
	public static void drawWrapped(Graphics g, Font font, Vector2f v, CharSequence text, Vector2d alignScale, double maxWidth) {draw(g,font,v.x,v.y,wordwrap(font,text,maxWidth),alignScale);}
	public static void drawWrapped(Graphics g, Font font, Vector2i v, CharSequence text, Vector2d alignScale, double maxWidth) {draw(g,font,v.x,v.y,wordwrap(font,text,maxWidth),alignScale);}
	
	public static void drawWrapped(Graphics g, Font font, double x, double y, CharSequence text, double maxWidth) {draw(g,font,x,y,wordwrap(font,text,maxWidth),ETextAlign.TopLeft);}
	public static void drawWrapped(Graphics g, Font font, double x, double y, CharSequence text, ETextAlign align, double maxWidth) {draw(g,font,x,y,wordwrap(font,text,maxWidth),align.getScale());}
	public static void drawWrapped(Graphics g, Font font, double x, double y, CharSequence text, Vector2d alignScale, double maxWidth) {draw(g,font,x,y,wordwrap(font,text,maxWidth),alignScale);}
	
	public static void draw(Graphics g, Font font, double x, double y, CharSequence text) {draw(g,font,x,y,text,ETextAlign.TopLeft);}
	public static void draw(Graphics g, Font font, double x, double y, CharSequence text, ETextAlign align) {draw(g,font,x,y,text,align.getScale());}
	public static void draw(Graphics g, Font font, double x, double y, CharSequence text, Vector2d alignScale) {
		String[] ss = prepare(text).toString().split("\\r?\\n");
		
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