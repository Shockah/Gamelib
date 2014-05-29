package pl.shockah.glib.logic.component;

import java.util.LinkedList;
import java.util.List;

public class ComponentSystem<T extends Component> {
	protected final Class<? extends T> cls;
	protected final List<T> cache = new LinkedList<>();
	
	public ComponentSystem(Class<? extends T> cls) {
		this.cls = cls;
	}
	
	public void create() {
		GameEntity.me.systems.add(this);
	}
	
	public void reset() {
		cache.clear();
	}
	@SuppressWarnings("unchecked") public void reset(List<Entity> entities) {
		reset();
		for (Entity e : entities) for (Component component : e.components) if (matches(component)) cache.add((T)component);
	}
	
	public boolean matches(Component component) {
		return cls.isAssignableFrom(component.getClass());
	}
	public void addCache(T component) {
		cache.add(component);
	}
	@SuppressWarnings("unchecked") public void addCacheOnMatch(Component component) {
		if (matches(component)) addCache((T)component);
	}
	public void removeCache(Component component) {
		if (matches(component)) cache.remove(component);
	}
	
	public final void update() {
		onUpdate();
	}
	protected void onUpdate() {}
}