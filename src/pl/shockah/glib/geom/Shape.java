package pl.shockah.glib.geom;

public class Shape {
	public boolean collides(Shape shape) {
		return collides(shape,false);
	}
	protected boolean collides(Shape shape, boolean secondTry) {
		throw new UnsupportedOperationException();
	}
}