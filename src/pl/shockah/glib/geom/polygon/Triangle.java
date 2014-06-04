package pl.shockah.glib.geom.polygon;

import static org.lwjgl.opengl.GL11.*;
import pl.shockah.glib.geom.vector.Vector2;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.GL;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;

public class Triangle extends Polygon {
	public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
		this(new Vector2d(x1,y1),new Vector2d(x2,y2),new Vector2d(x3,y3));
	}
	public Triangle(Vector2 v1, Vector2 v2, Vector2 v3) {
		addPoint(v1);
		addPoint(v2);
		addPoint(v3);
	}
	
	public Polygon addPoint(Vector2d point) {
		if (pointCount() >= 3) throw new IllegalStateException();
		return super.addPoint(point);
	}
	
	public void drawMulticolor(Graphics g, boolean filled, Color c1, Color c2, Color c3) {
		g.preDraw();
		GL.unbindTexture();
		
		if (filled) {
			glBegin(GL_TRIANGLES);
				GL.bind(c1); glVertex2d(points.get(0).x,points.get(0).y);
				GL.bind(c2); glVertex2d(points.get(1).x,points.get(1).y);
				GL.bind(c3); glVertex2d(points.get(2).x,points.get(2).y);
			glEnd();
		} else {
			glBegin(GL_LINE_LOOP);
				GL.bind(c1); glVertex2d(points.get(0).x,points.get(0).y);
				GL.bind(c2); glVertex2d(points.get(1).x,points.get(1).y);
				GL.bind(c3); glVertex2d(points.get(2).x,points.get(2).y);
			glEnd();
		}
	}
}