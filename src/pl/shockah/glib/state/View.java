package pl.shockah.glib.state;

import pl.shockah.glib.geom.vector.Vector2i;

public final class View {
	public Vector2i
		viewPortPos = new Vector2i(),
		viewPortSize = new Vector2i(800,600),
		viewPos = new Vector2i(),
		viewSize = new Vector2i(800,600);
}