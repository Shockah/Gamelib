package pl.shockah.glib.geom.polygon;

import static org.lwjgl.opengl.GL11.*;
import java.util.ArrayList;
import java.util.List;
import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.geom.Shape;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.Graphics;

public class Polygon extends Shape {
	protected List<Vector2d> points = new ArrayList<>();
	
	public Rectangle getBoundingBox() {
		double x1 = 0, y1 = 0, x2 = 0, y2 = 0;
		
		for (int i = 0; i < points.size(); i++) {
			Vector2d point = points.get(i);
			if (i == 0 || point.x < x1) x1 = point.x;
			if (i == 0 || point.y < y1) y1 = point.y;
			if (i == 0 || point.x > x2) x2 = point.x;
			if (i == 0 || point.y > y2) y2 = point.y;
		}
		return new Rectangle(x1,y1,x2-x1,y2-y1);
	}
	
	public Polygon addPoint(double x, double y) {
		return addPoint(new Vector2d(x,y));
	}
	public Polygon addPoint(Vector2d point) {
		points.add(point);
		return this;
	}
	public Vector2d removePoint(int index) {
		return points.remove(index);
	}
	public Vector2d getPoint(int index) {
		return points.get(index);
	}
	public Vector2d[] getPoints() {
		return points.toArray(new Vector2d[0]);
	}
	public int getPointCount() {
		return points.size();
	}
	
	public ITriangulator getTriangulator() {
		return new NeatTriangulator();
	}
	
	public void draw(Graphics g, boolean filled, double x, double y) {
		g.init();
		
		if (filled) {
			glTranslated(x,y,0);
			
			ITriangulator tris = getTriangulator();
			for (Vector2d v : getPoints()) tris.addPolyPoint(new Vector2d(v.x+x,v.y+y));
			tris.triangulate();
			
			glBegin(GL_TRIANGLES);
			for (int i = 0; i < tris.getTriangleCount(); i++) {
				for (int p = 0; p<3; p++) {
					Vector2d v = tris.getTrianglePoint(i,p);
					glVertex2d(v.x,v.y);
				}
			}
			glEnd();
			
			glTranslatef((float)-x,(float)-y,0);
		} else {
			throw new UnsupportedOperationException();
		}
	}
	
	public static class NoHoles extends Polygon {
		public ITriangulator getTriangulator() {
			return new BasicTriangulator();
		}
	}
}