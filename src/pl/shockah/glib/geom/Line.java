package pl.shockah.glib.geom;

import static org.lwjgl.opengl.GL11.*;
import pl.shockah.glib.animfx.IInterpolatable;
import pl.shockah.glib.animfx.Interpolate;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.GL;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;

public class Line extends Shape implements IInterpolatable<Line> {
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
		g.preDraw();
		GL.unbindTexture();
		
		if (filled) {
			throw new UnsupportedOperationException();
		} else {
			glBegin(GL_LINES);
				glVertex2d(pos1.x,pos1.y);
				glVertex2d(pos2.x,pos2.y);
			glEnd();
		}
	}
	
	public void drawMulticolor(Graphics g, boolean filled, Color cPoint1, Color cPoint2) {
		g.preDraw();
		GL.unbindTexture();
		
		if (filled) {
			throw new UnsupportedOperationException();
		} else {
			glBegin(GL_LINES);
				GL.color4f(cPoint1); glVertex2d(pos1.x,pos1.y);
				GL.color4f(cPoint2); glVertex2d(pos2.x,pos2.y);
			glEnd();
		}
	}
	
	public Line interpolate(Line l, double d, Interpolate method) {
		return new Line(pos1.interpolate(l.pos1,d,method),pos2.interpolate(l.pos2,d,method));
	}
}