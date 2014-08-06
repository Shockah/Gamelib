package pl.shockah.glib.geom.polygon;

import static org.lwjgl.opengl.GL11.*;
import java.util.ArrayList;
import java.util.List;
import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.geom.Shape;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.GL;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;

public class TriangleFan extends Shape implements IPolygonable {
	protected List<Point> points = new ArrayList<>();
	
	public Shape copy() {
		return copyMe();
	}
	public TriangleFan copyMe() {
		TriangleFan tf = new TriangleFan();
		for (Point p : points) tf.addPoint(p.pos,p.color);
		return tf;
	}
	
	public Rectangle boundingBox() {
		double x1 = 0, y1 = 0, x2 = 0, y2 = 0;
		
		for (int i = 0; i < points.size(); i++) {
			Vector2d point = points.get(i).pos;
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
		for (Point p : points) {
			p.pos.x += x;
			p.pos.y += y;
		}
		return new Vector2d(x,y);
	}
	public Vector2d translateTo(double x, double y) {
		Rectangle bb = boundingBox();
		return translate(x-bb.pos.x,y-bb.pos.y);
	}
	
	public TriangleFan addPoint(Vector2d v) {
		return addPoint(v,null);
	}
	public TriangleFan addPoint(Vector2d v, Color color) {
		return addPoint(v.x,v.y,color);
	}
	public TriangleFan addPoint(double x, double y) {
		return addPoint(x,y,null);
	}
	public TriangleFan addPoint(double x, double y, Color color) {
		points.add(new Point(new Vector2d(x,y),color));
		return this;
	}
	
	public Point removePoint(int index) {
		return points.remove(index);
	}
	public Point point(int index) {
		return points.get(index);
	}
	public Point[] points() {
		return points.toArray(new Point[0]);
	}
	public int pointCount() {
		return points.size();
	}
	
	public void draw(Graphics g, boolean filled) {
		g.preDraw();
		GL.unbindTexture();
		
		if (filled) {
			glBegin(GL_TRIANGLE_FAN);
				for (Point p : points) {
					GL.bind(p.color == null ? g.getColor() : p.color);
					glVertex2d(p.pos.x,p.pos.y);
				}
			glEnd();
		} else {
			throw new UnsupportedOperationException();
		}
	}
	
	public Polygon asPolygon() {
		Polygon p = new Polygon();
		for (int i = 1; i < points.size(); i++) p.addPoint(points.get(i).pos);
		return p;
	}
	
	public static class Point {
		public final Vector2d pos;
		public final Color color;
		
		public Point(Vector2d pos, Color color) {
			this.pos = pos;
			this.color = color;
		}
	}
}