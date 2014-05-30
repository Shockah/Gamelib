package pl.shockah.glib.logic.component;

import java.util.LinkedList;
import java.util.List;
import pl.shockah.glib.logic.IFilter;

public final class Components {
	@SuppressWarnings("unchecked") public static <T extends Component> List<T> getType(IFilter<Component> filter, Class<T> cls) {
		List<T> list = new LinkedList<>();
		for (ComponentSystem<?> system : GameComponent.me.systems) {
			if (cls != null && !system.cls.isAssignableFrom(cls)) continue;
			for (Component component : system.cache) {
				boolean b = true;
				if (b && filter != null) if (!filter.accept(component)) b = false;
				if (b) list.add((T)component);
			}
		}
		return list;
	}
	public static <T extends Component> List<T> getType(Class<T> cls) {
		return getType(null,cls);
	}
	
	@SafeVarargs public static List<Component> getTypes(IFilter<Component> filter, Class<? extends Component>... componentClasses) {
		List<Component> list = new LinkedList<>();
		for (ComponentSystem<?> system : GameComponent.me.systems) {
			boolean b = true;
			if (componentClasses.length != 0) {
				for (Class<? extends Component> cls : componentClasses) {
					if (!system.cls.isAssignableFrom(cls)) {
						b = false;
						break;
					}
				}
			}
			for (Component component : system.cache) {
				if (b && filter != null) if (!filter.accept(component)) b = false;
				if (b) list.add(component);
			}
		}
		return list;
	}
	@SafeVarargs public static List<Component> getTypes(Class<? extends Component>... componentClasses) {
		return getTypes(null,componentClasses);
	}
	
	private Components() {}
}