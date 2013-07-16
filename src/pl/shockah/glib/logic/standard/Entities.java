package pl.shockah.glib.logic.standard;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class Entities {
	public static List<EntityBase> getAll() {
		return Collections.unmodifiableList(GameStandard.me.entities);
	}
	
	@SuppressWarnings("unchecked") public static <T extends Entity> List<T> get(IEntityFilter filter, Class<T> cls) {
		List<T> list = new LinkedList<>();
		for (EntityBase entity : GameStandard.me.entities) {
			boolean b = true;
			if (b && filter != null) if (!filter.accept(entity)) b = false;
			if (b && cls != null) if (!cls.isAssignableFrom(entity.getClass())) b = false;
			if (b) list.add((T)entity);
		}
		return list;
	}
	public static <T extends Entity> List<T> get(Class<T> cls) {
		return get(null,cls);
	}
	
	@SafeVarargs public static List<EntityBase> get(Class<? extends EntityBase>... entityClasses) {
		return get(null,entityClasses);
	}
	@SafeVarargs public static List<EntityBase> get(IEntityFilter filter, Class<? extends EntityBase>... entityClasses) {
		List<EntityBase> list = new LinkedList<>();
		for (EntityBase entity : GameStandard.me.entities) {
			boolean b = true;
			if (b && filter != null) if (!filter.accept(entity)) b = false;
			if (b && entityClasses.length != 0) {
				b = false;
				for (Class<? extends EntityBase> cls : entityClasses) {
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
}