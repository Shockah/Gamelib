package pl.shockah.glib.gl.font;

import pl.shockah.glib.geom.vector.Vector2d;

public enum ETextAlign {
	TopLeft(0,0), TopCenter(-.5d,0), TopRight(-1,0),
	MiddleLeft(0,-.5d), MiddleCenter(-.5d,-.5d), MiddleRight(-1,-.5d),
	BottomLeft(0,-1), BottomCenter(-.5d,-1), BottomRight(-1,-1);
	
	private final double multX, multY;
	
	private ETextAlign(double multX, double multY) {
		this.multX = multX;
		this.multY = multY;
	}
	
	public Vector2d getScale() {
		return new Vector2d(multX,multY);
	}
}