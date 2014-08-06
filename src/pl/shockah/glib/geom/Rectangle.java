package pl.shockah.glib.geom;

import static org.lwjgl.opengl.GL11.*;
import pl.shockah.glib.animfx.IEasable;
import pl.shockah.glib.animfx.Ease;
import pl.shockah.glib.geom.polygon.IPolygonable;
import pl.shockah.glib.geom.polygon.Polygon;
import pl.shockah.glib.geom.vector.Vector2;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.GL;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;

public class Rectangle extends Shape implements IPolygonable,IEasable<Rectangle> {
	public Vector2d pos, size;
	
	public Rectangle(double w, double h) {
		this(new Vector2d(),w,h);
	}
	public Rectangle(Vector2 size) {
		this(new Vector2d(),size);
	}
	public Rectangle(double x, double y, double w, double h) {
		this(new Vector2d(x,y),new Vector2d(w,h));
	}
	public Rectangle(double x, double y, Vector2 size) {
		this(new Vector2d(x,y),size);
	}
	public Rectangle(Vector2 pos, double w, double h) {
		this(pos,new Vector2d(w,h));
	}
	public Rectangle(Rectangle rect) {
		this(rect.pos,rect.size);
	}
	public Rectangle(Vector2 pos, Vector2 size) {
		this.pos = pos.ToDouble();
		this.size = size.ToDouble();
	}
	
	public boolean equals(Object other) {
		if (!(other instanceof Rectangle)) return false;
		Rectangle r = (Rectangle)other;
		return pos.equals(r.pos) && size.equals(r.size);
	}
	public String toString() {
		return String.format("[Rectangle: pos %s, size %s]",pos,size);
	}
	
	public Shape copy() {
		return copyMe();
	}
	public Rectangle copyMe() {
		return new Rectangle(this);
	}
	
	public Rectangle boundingBox() {
		return new Rectangle(this);
	}
	public Vector2d center() {
		return pos.Add(size.Scale(.5d));
	}
	
	public Vector2d translate(double x, double y) {
		pos.add(x,y);
		return new Vector2d(x,y);
	}
	public Vector2d translateTo(double x, double y) {
		Vector2d v = new Vector2d(x-pos.x,y-pos.y);
		pos.x = x;
		pos.y = y;
		return v;
	}
	
	protected boolean collides(Shape shape, boolean secondTry) {
		if (shape instanceof Rectangle) {
			Rectangle rect = (Rectangle)shape;
			Vector2d v = pos.Add(size.Div(2)).sub(rect.pos.Add(rect.size.Div(2))).abs().sub(size.Div(2).add(rect.size.Div(2)));
			return v.x < 0 && v.y < 0;
		} else if (shape instanceof Circle) {
			Circle circle = (Circle)shape;
			return circle.pos.Sub(circle.pos.Sub(pos.Add(size.Div(2))).sub(size.Div(2))).lengthSquared()-Math.pow(circle.radius,2) < 0;
		}
		return super.collides(shape);
	}
	
	public Polygon asPolygon() {
		return new Polygon()
			.addPoint(pos)
			.addPoint(pos.add(size.justX()))
			.addPoint(pos.add(size))
			.addPoint(pos.add(size.justY()));
	}
	
	public void draw(Graphics g, boolean filled) {
		g.preDraw();
		GL.unbindTexture();
		
		if (filled) {
			glBegin(GL_QUADS);
				glVertex2d(pos.x,pos.y);
				glVertex2d(pos.x+size.x,pos.y);
				glVertex2d(pos.x+size.x,pos.y+size.y);
				glVertex2d(pos.x,pos.y+size.y);
			glEnd();
		} else {
			glBegin(GL_LINE_LOOP);
				glVertex2d(pos.x+.5f,pos.y+.5f);
				glVertex2d(pos.x+size.x-1+.5f,pos.y+.5f);
				glVertex2d(pos.x+size.x-1+.5f,pos.y+size.y-1+.5f);
				glVertex2d(pos.x+.5f,pos.y+size.y-1+.5f);
			glEnd();
		}
	}
	
	public void drawMulticolor(Graphics g, boolean filled, Color cTopLeft, Color cTopRight, Color cBottomLeft, Color cBottomRight) {
		g.preDraw();
		GL.unbindTexture();
		
		if (filled) {
			glBegin(GL_QUADS);
				GL.bind(cTopLeft); glVertex2d(pos.x,pos.y);
				GL.bind(cTopRight); glVertex2d(pos.x+size.x,pos.y);
				GL.bind(cBottomRight); glVertex2d(pos.x+size.x,pos.y+size.y);
				GL.bind(cBottomLeft); glVertex2d(pos.x,pos.y+size.y);
			glEnd();
		} else {
			glBegin(GL_LINE_LOOP);
				GL.bind(cTopLeft); glVertex2d(pos.x+.5f,pos.y+.5f);
				GL.bind(cTopRight); glVertex2d(pos.x+size.x-1+.5f,pos.y+.5f);
				GL.bind(cBottomRight); glVertex2d(pos.x+size.x-1+.5f,pos.y+size.y-1+.5f);
				GL.bind(cBottomLeft); glVertex2d(pos.x+.5f,pos.y+size.y-1+.5f);
			glEnd();
		}
	}
	
	public Rectangle ease(Rectangle r, double d) {
		return ease(r,d,Ease.Linear);
	}
	public Rectangle ease(Rectangle r, double d, Ease method) {
		return new Rectangle(pos.ease(r.pos,d,method),size.ease(r.size,d,method));
	}
}