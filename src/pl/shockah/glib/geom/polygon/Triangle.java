package pl.shockah.glib.geom.polygon;

import pl.shockah.glib.geom.vector.Vector2d;

public class Triangle extends Polygon {
	public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
		this(new Vector2d(x1,y1),new Vector2d(x2,y2),new Vector2d(x3,y3));
	}
	public Triangle(Vector2d v1, Vector2d v2, Vector2d v3) {
		addPoint(v1);
		addPoint(v2);
		addPoint(v3);
	}
	
	public Polygon addPoint(Vector2d point) {
		if (getPointCount() >= 3) throw new UnsupportedOperationException();
		return super.addPoint(point);
	}
}