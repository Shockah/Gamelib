package pl.shockah.glib.geom;

import java.util.ArrayList;
import java.util.List;
import pl.shockah.glib.geom.vector.Vector2d;

public class Polygon extends Shape {
	public List<Vector2d> points = new ArrayList<>();
	
	public Rectangle getBoundingBox() {
		double x1 = 0, y1 = 0, x2 = 0, y2 = 0;
		
		for (int i = 0; i < points.size(); i++) {
			Vector2d point = points[i];
			if (i == 0 || point.x < x1) x1 = point.x;
			if (i == 0 || point.y < y1) y1 = point.y;
			if (i == 0 || point.x > x2) x2 = point.x;
			if (i == 0 || point.y > y2) y2 = point.y;
		}
		return new Rectangle(x1,y1,x2-x1,y2-y1);
	}
	
	//region Java-OO
	public Vector2d get(int index) {
		return points[index];
	}
	//endregion
	
	public Polygon addPoint(Vector2d point) {
		points.add(point);
		return this;
	}
	public Vector2d removePoint(int index) {
		return points.remove(index);
	}
	public Vector2d[] getPoints() {
		return points.toArray(new Vector2d[0]);
	}
}