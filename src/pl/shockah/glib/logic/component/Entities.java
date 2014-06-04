package pl.shockah.glib.logic.component;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import pl.shockah.glib.logic.IFilter;

public final class Entities {
	public static List<Entity> get() {
		return Collections.unmodifiableList(GameComponent.me.entities);
	}
	
	@SuppressWarnings("unchecked") public static <T extends Entity> List<T> type(IFilter<Entity> filter, Class<T> cls) {
		List<T> list = new LinkedList<>();
		for (Entity entity : GameComponent.me.entities) {
			boolean b = true;
			if (b && filter != null) if (!filter.accept(entity)) b = false;
			if (b && cls != null) if (!cls.isAssignableFrom(entity.getClass())) b = false;
			if (b) list.add((T)entity);
		}
		return list;
	}
	public static <T extends Entity> List<T> type(Class<T> cls) {
		return type(null,cls);
	}
	
	@SafeVarargs public static List<Entity> types(IFilter<Entity> filter, Class<? extends Entity>... entityClasses) {
		List<Entity> list = new LinkedList<>();
		for (Entity entity : GameComponent.me.entities) {
			boolean b = true;
			if (b && filter != null) if (!filter.accept(entity)) b = false;
			if (b && entityClasses.length != 0) {
				b = false;
				for (Class<? extends Entity> cls : entityClasses) {
					if (cls.isAssignableFrom(entity.getClass())) {
						b = true;
						break;
					}
				}
			}
			if (b) list.add(entity);
		}
		return list;
	}
	@SafeVarargs public static List<Entity> types(Class<? extends Entity>... entityClasses) {
		return types(null,entityClasses);
	}
}