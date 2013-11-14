package pl.shockah.glib.logic;

import pl.shockah.glib.geom.vector.Vector2;

public abstract class Entity extends EntityBase {
	public final void create(Vector2 pos) {
		super.create(pos);
	}
	
	public final void destroy() {
		super.destroy();
	}
}