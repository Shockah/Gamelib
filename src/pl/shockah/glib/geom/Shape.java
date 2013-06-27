package pl.shockah.glib.geom;

public abstract class Shape {
	public abstract Rectangle getBoundingBox();
	
	public boolean collides(Shape shape) {
		return collides(shape,false);
	}
	protected boolean collides(Shape shape, boolean secondTry) {
		throw new UnsupportedOperationException();
	}
}