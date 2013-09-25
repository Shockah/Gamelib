package pl.shockah.glib.geom.polygon;

import pl.shockah.glib.geom.vector.Vector2d;

/*
 * code taken from Slick2D - http://slick.ninjacave.com/
 */
public interface ITriangulator {
	public int getTriangleCount();
	public Vector2d getTrianglePoint(int tri, int i);
	public void addPolyPoint(Vector2d point);
	public void startHole();
	public boolean triangulate();
}