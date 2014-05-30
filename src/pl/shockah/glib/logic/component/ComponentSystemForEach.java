package pl.shockah.glib.logic.component;

public abstract class ComponentSystemForEach extends ComponentSystem {
	protected final void onUpdate() {
		for (Entity entity : cache) foreach(entity);
	}
	protected void foreach(Entity entity) {}
}