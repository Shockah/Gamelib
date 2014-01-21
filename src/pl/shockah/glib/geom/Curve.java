package pl.shockah.glib.geom;

import static org.lwjgl.opengl.GL11.*;
import java.util.ArrayList;
import java.util.List;
import pl.shockah.glib.animfx.IInterpolatable;
import pl.shockah.glib.animfx.Interpolate;
import pl.shockah.glib.geom.vector.Vector2;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.GL;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;

public class Curve extends Shape implements IInterpolatable<Curve> {
	public static Vector2d calculateBezierPoint(double d, Vector2... vs) {
		if (vs.length == 0) throw new IllegalArgumentException();
		if (vs.length == 1) return vs[0].toDouble();
		
		Vector2[] vs2 = new Vector2[vs.length-1];
		for (int i = 0; i < vs2.length; i++) vs2[i] = vs[i].toDouble().interpolate(vs[i+1].toDouble(),d);
		return calculateBezierPoint(d,vs2);
	}
	
	public Vector2d pos1, pos2;
	protected List<Vector2d> controlPoints = new ArrayList<>();
	
	public Curve(double x1, double y1, double x2, double y2) {
		pos1 = new Vector2d(x1,y1);
		pos2 = new Vector2d(x2,y2);
	}
	public Curve(Vector2 pos1, Vector2 pos2) {
		this.pos1 = pos1.ToDouble();
		this.pos2 = pos2.ToDouble();
	}
	public Curve(Curve curve) {
		pos1 = curve.pos1.copyMe();
		pos2 = curve.pos2.copyMe();
	}
	
	public boolean equals(Object other) {
		if (!(other instanceof Curve)) return false;
		Curve c = (Curve)other;
		return pos1.equals(c.pos1) && pos2.equals(c.pos2);
	}
	public String toString() {
		return String.format("[Curve: %s -> %s]",pos1,pos2);
	}
	
	public Shape copy() {
		return copyMe();
	}
	public Curve copyMe() {
		return new Curve(this);
	}
	
	public void addControlPoint(Vector2 v) {
		addControlPoint(v.Xd(),v.Yd());
	}
	public void addControlPoint(double x, double y) {
		controlPoints.add(new Vector2d(x,y));
	}
	public Vector2d removeControlPoint(int index) {
		return controlPoints.remove(index);
	}
	public Vector2d getControlPoint(int index) {
		return controlPoints.get(index);
	}
	public Vector2d[] getControlPoints() {
		return controlPoints.toArray(new Vector2d[0]);
	}
	public int getControlPointCount() {
		return controlPoints.size();
	}
	
	public Rectangle getBoundingBox() {
		double xmin = Math.min(pos1.x,pos2.x), xmax = Math.max(pos1.x,pos2.x);
		double ymin = Math.min(pos1.y,pos2.y), ymax = Math.max(pos1.y,pos2.y);
		return new Rectangle(xmin,ymin,xmax-xmin,ymax-ymin);
	}
	
	public Vector2d translate(double x, double y) {
		pos1.add(x,y);
		pos2.add(x,y);
		for (Vector2d point : controlPoints) point.add(x,y);
		return new Vector2d(x,y);
	}
	public Vector2d translateTo(double x, double y) {
		Vector2d v = new Vector2d(x-pos1.x,y-pos1.y);
		pos1.add(v);
		pos2.add(v);
		for (Vector2d point : controlPoints) point.add(v);
		return v;
	}
	
	public void draw(Graphics g) {draw(g,false);}
	public void draw(Graphics g, boolean filled) {
		draw(g,filled,Math.max((int)(getLength()/3),2));
	}
	public void draw(Graphics g, int precision) {draw(g,false,precision);}
	public void draw(Graphics g, boolean filled, int precision) {
		g.preDraw();
		GL.unbindTexture();
		
		if (filled) {
			throw new UnsupportedOperationException();
		} else {
			Vector2d[] points = getAllPoints();
			glBegin(GL_LINE_STRIP);
				for (int i = 0; i < precision; i++) GL.vertex2d(calculateBezierPoint(1d*i/(precision-1),points));
			glEnd();
		}
	}
	
	public void drawMulticolor(Graphics g, Color cPoint1, Color cPoint2) {
		drawMulticolor(g,cPoint1,cPoint2,Math.max((int)(getLength()/3),2));
	}
	public void drawMulticolor(Graphics g, Color cPoint1, Color cPoint2, int precision) {
		g.preDraw();
		GL.unbindTexture();
		
		Vector2d[] points = getAllPoints();
		glBegin(GL_LINE_STRIP);
			for (int i = 0; i < precision; i++) {
				double d = 1d*i/(precision-1);
				GL.bind(cPoint1.interpolate(cPoint2,d));
				GL.vertex2d(calculateBezierPoint(d,points));
			}
		glEnd();
	}
	
	public Vector2d[] getAllPoints() {
		Vector2d[] ret = new Vector2d[controlPoints.size()+2];
		ret[0] = pos1;
		for (int i = 0; i < controlPoints.size(); i++) ret[i+1] = controlPoints.get(i);
		ret[ret.length-1] = pos2;
		return ret;
	}
	public double getLength() {
		double d = 0d;
		Vector2d[] points = getAllPoints();
		for (int i = 0; i < points.length-1; i++) d += points[i].distance(points[i+1]);
		return d;
	}
	
	public Curve interpolate(Curve c, double d) {
		return interpolate(c,d,Interpolate.Linear);
	}
	public Curve interpolate(Curve c, double d, Interpolate method) {
		if (controlPoints.size() != c.controlPoints.size()) throw new IllegalArgumentException();
		Curve ret = new Curve(pos1.interpolate(c.pos1,d,method),pos2.interpolate(c.pos2,d,method));
		for (int i = 0; i < controlPoints.size(); i++) ret.addControlPoint(controlPoints.get(i).interpolate(c.controlPoints.get(i),d,method));
		return ret;
	}
}