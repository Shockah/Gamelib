package pl.shockah.glib.logic.standard;

import pl.shockah.glib.geom.vector.IVector2;

public abstract class Entity extends EntityBase {
	public final void create(IVector2 pos) {
		super.create(pos);
	}
	
	public final void destroy() {
		super.destroy();
	}
}