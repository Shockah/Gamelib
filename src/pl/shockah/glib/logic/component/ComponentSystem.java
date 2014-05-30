package pl.shockah.glib.logic.component;

import java.util.LinkedList;
import java.util.List;

public abstract class ComponentSystem {
	protected final List<Entity> cache = new LinkedList<>();
	
	public void create() {
		GameComponent.me.systems.add(this);
	}
	
	public void reset() {
		cache.clear();
	}
	public void reset(List<Entity> entities) {
		reset();
		for (Entity entity : entities) if (matches(entity)) addCache(entity);
	}
	
	public abstract boolean matches(Entity entity);
	public void addCache(Entity entity) {
		cache.add(entity);
	}
	public void addCacheOnMatch(Entity entity) {
		if (matches(entity)) addCache(entity);
	}
	public void removeCache(Entity entity) {
		if (matches(entity)) cache.remove(entity);
	}
	
	@SuppressWarnings("unchecked") public <T extends Component> List<T> getComponents(Class<T> cls) {
		List<T> list = new LinkedList<>();
		for (Entity entity : cache) for (Component component : entity.components) if (cls.isAssignableFrom(component.getClass())) list.add((T)component);
		return list;
	}
	
	public final void update() {
		onUpdate();
	}
	protected void onUpdate() {}
}