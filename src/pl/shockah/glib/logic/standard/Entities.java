package pl.shockah.glib.logic.standard;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class Entities {
	public static List<EntityBase> get() {
		return Collections.unmodifiableList(GameStandard.me.entities);
	}
	public static List<EntityRenderable> getRenderable() {
		return Collections.unmodifiableList(GameStandard.me.renderable);
	}
	
	@SuppressWarnings("unchecked") public static <T extends EntityBase> List<T> getType(IEntityFilter filter, Class<T> cls) {
		List<T> list = new LinkedList<>();
		for (EntityBase entity : GameStandard.me.entities) {
			boolean b = true;
			if (b && filter != null) if (!filter.accept(entity)) b = false;
			if (b && cls != null) if (!cls.isAssignableFrom(entity.getClass())) b = false;
			if (b) list.add((T)entity);
		}
		return list;
	}
	public static <T extends EntityBase> List<T> getType(Class<T> cls) {
		return getType(null,cls);
	}
	
	@SafeVarargs public static List<EntityBase> getTypes(IEntityFilter filter, Class<? extends EntityBase>... entityClasses) {
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
	@SafeVarargs public static List<EntityBase> getTypes(Class<? extends EntityBase>... entityClasses) {
		return getTypes(null,entityClasses);
	}
}