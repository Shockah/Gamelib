package pl.shockah.glib.geom;

import static org.lwjgl.opengl.GL11.*;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.Graphics;

public class Line extends Shape {
	public Vector2d pos1, pos2;
	
	public Line(double x1, double y1, double x2, double y2) {
		pos1 = new Vector2d(x1,y1);
		pos2 = new Vector2d(x2,y2);
	}
	public Line(Vector2d pos1, Vector2d pos2) {
		this.pos1 = pos1;
		this.pos2 = pos2;
	}
	public Line(Line line) {
		pos1 = new Vector2d(line.pos1);
		pos2 = new Vector2d(line.pos2);
	}
	
	public Shape copy() {
		return copyMe();
	}
	public Line copyMe() {
		return new Line(this);
	}
	
	public Rectangle getBoundingBox() {
		double xmin = Math.min(pos1.x,pos2.x), xmax = Math.max(pos1.x,pos2.x);
		double ymin = Math.min(pos1.y,pos2.y), ymax = Math.max(pos1.y,pos2.y);
		return new Rectangle(xmin,ymin,xmax-xmin,ymax-ymin);
	}
	
	public Vector2d translate(double x, double y) {
		pos1.add(x,y);
		pos2.add(x,y);
		return new Vector2d(x,y);
	}
	public Vector2d translateTo(double x, double y) {
		Vector2d v = new Vector2d(x-pos1.x,y-pos1.y);
		pos1.add(v);
		pos2.add(v);
		return v;
	}
	
	public void draw(Graphics g) {draw(g,false);}
	public void draw(Graphics g, boolean filled) {
		g.init();
		
		if (filled) {
			throw new UnsupportedOperationException();
		} else {
			glBegin(GL_LINE_STRIP);
			glVertex2d(pos1.x,pos1.y);
			glVertex2d(pos2.x,pos2.y);
			glEnd();
		}
	}
}