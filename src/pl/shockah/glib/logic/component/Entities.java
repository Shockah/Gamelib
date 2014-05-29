package pl.shockah.glib.logic.component;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import pl.shockah.glib.logic.IFilter;

public final class Entities {
	public static List<Entity> get() {
		return Collections.unmodifiableList(GameEntity.me.entities);
	}
	
	@SuppressWarnings("unchecked") public static <T extends Entity> List<T> getType(IFilter<Entity> filter, Class<T> cls) {
		List<T> list = new LinkedList<>();
		for (Entity entity : GameEntity.me.entities) {
			boolean b = true;
			if (b && filter != null) if (!filter.accept(entity)) b = false;
			if (b && cls != null) if (!cls.isAssignableFrom(entity.getClass())) b = false;
			if (b) list.add((T)entity);
		}
		return list;
	}
	public static <T extends Entity> List<T> getType(Class<T> cls) {
		return getType(null,cls);
	}
	
	@SafeVarargs public static List<Entity> getTypes(IFilter<Entity> filter, Class<? extends Entity>... entityClasses) {
		List<Entity> list = new LinkedList<>();
		for (Entity entity : GameEntity.me.entities) {
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
	@SafeVarargs public static List<Entity> getTypes(Class<? extends Entity>... entityClasses) {
		return getTypes(null,entityClasses);
	}
}