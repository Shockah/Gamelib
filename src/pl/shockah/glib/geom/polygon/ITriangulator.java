package pl.shockah.glib.geom.polygon;

import pl.shockah.glib.geom.vector.Vector2d;

/*
 * code taken from Slick2D - http://slick.ninjacave.com/
 */
public interface ITriangulator {
	public int triangleCount();
	public Vector2d trianglePoint(int tri, int i);
	public void addPolyPoint(Vector2d point);
	public boolean triangulate();
}