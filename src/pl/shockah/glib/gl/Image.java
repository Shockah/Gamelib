package pl.shockah.glib.gl;

import pl.shockah.glib.geom.vector.Vector2f;
import pl.shockah.glib.gl.tex.Texture;

public class Image {
	protected final Texture tex;
	public Vector2f centerOfRotation = new Vector2f();
	public float rotation = 0;
	
	public Image(Texture tex) {
		this.tex = tex;
	}
	
	public int getWidth() {
		return tex.getWidth();
	}
	public int getHeight() {
		return tex.getHeight();
	}
}