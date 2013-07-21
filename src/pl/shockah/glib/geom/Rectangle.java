package pl.shockah.glib.geom;

import static org.lwjgl.opengl.GL11.*;
import pl.shockah.glib.geom.polygon.IPolygonable;
import pl.shockah.glib.geom.polygon.Polygon;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.Graphics;

public class Rectangle extends Shape implements IPolygonable {
	public Vector2d pos, size;
	
	public Rectangle(double x, double y, double w, double h) {
		this(new Vector2d(x,y),new Vector2d(w,h));
	}
	public Rectangle(double x, double y, Vector2d size) {
		this(new Vector2d(x,y),size);
	}
	public Rectangle(Vector2d pos, double w, double h) {
		this(pos,new Vector2d(w,h));
	}
	public Rectangle(Vector2d pos, Vector2d size) {
		this.pos = pos;
		this.size = size;
	}
	public Rectangle(Rectangle rect) {
		pos = new Vector2d(rect.pos);
		size = new Vector2d(rect.size);
	}
	
	public Shape copy() {
		return copyMe();
	}
	public Rectangle copyMe() {
		return new Rectangle(this);
	}
	
	public Rectangle getBoundingBox() {
		return new Rectangle(this);
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
		g.init();
		
		if (filled) {
			glBegin(GL_QUADS);
			glVertex2d(pos.x,pos.y);
			glVertex2d(pos.x+size.x-1,pos.y);
			glVertex2d(pos.x+size.x-1,pos.y+size.y-1);
			glVertex2d(pos.x,pos.y+size.y-1);
			glEnd();
		} else {
			glBegin(GL_LINE_STRIP);
			glVertex2d(pos.x,pos.y);
			glVertex2d(pos.x+size.x-1,pos.y);
			glVertex2d(pos.x+size.x-1,pos.y+size.y-1);
			glVertex2d(pos.x,pos.y+size.y-1);
			glEnd();
		}
	}
}