package pl.shockah.glib.geom.polygon;

import static org.lwjgl.opengl.GL11.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import pl.shockah.glib.geom.Line;
import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.geom.Shape;
import pl.shockah.glib.geom.vector.Vector2;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.GL;
import pl.shockah.glib.gl.Graphics;

public class Polygon extends Shape {
	protected List<Vector2d> points = new ArrayList<>();
	protected List<Triangle> triangles = new LinkedList<>();
	private boolean dirty = true;
	
	public Shape copy() {
		return copyMe();
	}
	public Polygon copyMe() {
		Polygon p = getClass() == NoHoles.class ? new NoHoles() : new Polygon();
		for (Vector2d v : points) p.addPoint(new Vector2d(v));
		return p;
	}
	
	public Rectangle boundingBox() {
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
	public Vector2d center() {
		return boundingBox().center();
	}
	
	public Vector2d translate(double x, double y) {
		for (Vector2d v : points) {
			v.x += x;
			v.y += y;
		}
		dirty = true;
		return new Vector2d(x,y);
	}
	public Vector2d translateTo(double x, double y) {
		Rectangle bb = boundingBox();
		return translate(x-bb.pos.x,y-bb.pos.y);
	}
	
	public Polygon addPoint(double x, double y) {
		return addPoint(new Vector2d(x,y));
	}
	public Polygon addPoint(Vector2 point) {
		points.add(point.ToDouble());
		dirty = true;
		return this;
	}
	public Vector2d removePoint(int index) {
		dirty = true;
		return points.remove(index);
	}
	public Vector2d point(int index) {
		return points.get(index);
	}
	public Vector2d[] points() {
		return points.toArray(new Vector2d[0]);
	}
	public int pointCount() {
		return points.size();
	}
	public Line[] lines() {
		return lines(true);
	}
	public Line[] lines(boolean closed) {
		Line[] ret = new Line[closed ? points.size() : points.size()-1];
		for (int i = 0; i < (closed ? points.size() : points.size()-1); i++) ret[i] = new Line(points.get(i),points.get(i == points.size()-1 ? 0 : i+1));
		return ret;
	}
	
	public ITriangulator triangulator() {
		return new NeatTriangulator();
	}
	public void updateTriangles() {
		if (!dirty) return;
		
		triangles.clear();
		ITriangulator tris = triangulator();
		for (Vector2d v : points()) tris.addPolyPoint(new Vector2d(v.x,v.y));
		tris.triangulate();
		
		for (int i = 0; i < tris.triangleCount(); i++) {
			triangles.add(new Triangle(tris.trianglePoint(i,0),tris.trianglePoint(i,1),tris.trianglePoint(i,2)));
		}
		dirty = false;
	}
	public List<Triangle> triangles() {
		return Collections.unmodifiableList(triangles);
	}
	
	public void draw(Graphics g, boolean filled) {
		g.preDraw();
		GL.unbindTexture();
		
		if (filled) {
			updateTriangles();
			glBegin(GL_TRIANGLES);
				for (Triangle triangle : triangles) for (Vector2d v : triangle.points()) glVertex2d(v.x,v.y);
			glEnd();
		} else {
			glBegin(GL_LINE_LOOP);
				for (int i = 0; i < points.size(); i++) {
					Vector2d v = points.get(i);
					glVertex2d(v.x+.5f,v.y+.5f);
				}
			glEnd();
		}
	}
	
	public static class NoHoles extends Polygon {
		public ITriangulator triangulator() {
			return new BasicTriangulator();
		}
	}
}