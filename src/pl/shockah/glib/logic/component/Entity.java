package pl.shockah.glib.logic.component;

import java.util.LinkedList;
import java.util.List;
import pl.shockah.glib.logic.GameObject;

public class Entity extends GameObject {
	protected final List<Component> components = new LinkedList<>();
	
	public final void create() {
		super.create();
		GameComponent.me.entitiesAdd.add(this);
		onCreate();
	}
	protected void onCreate() {}
	
	public final void destroy() {
		onDestroy();
		GameComponent.me.entitiesRemove.add(this);
		super.destroy();
	}
	protected void onDestroy() {}
	
	@SuppressWarnings("unchecked") public final <T extends Component> T component(Class<T> cls) {
		for (Component component : components) if (cls.isAssignableFrom(component.getClass())) return (T)component;
		return null;
	}
	@SuppressWarnings("unchecked") public final <T extends Component> List<T> components(Class<T> cls) {
		List<T> list = new LinkedList<>();
		for (Component component : components) if (cls.isAssignableFrom(component.getClass())) list.add((T)component);
		return list;
	}
	public final boolean hasComponent(Class<? extends Component> cls) {
		return component(cls) != null;
	}
	@SafeVarargs public final boolean hasComponents(Class<? extends Component>... clss) {
		for (Class<? extends Component> cls : clss) if (!hasComponent(cls)) return false;
		return true;
	}
}