package pl.shockah.glib.logic.standard;

import pl.shockah.glib.geom.vector.Vector2d;

public abstract class Entity extends EntityBase {
	public final void create(Vector2d pos) {
		super.create(pos);
	}
	
	public final void destroy() {
		super.destroy();
	}
}