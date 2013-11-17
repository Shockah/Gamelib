package pl.shockah.glib.logic;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public final class Entities {
	public static List<Entity> get() {
		return Collections.unmodifiableList(Game.me.entities);
	}
	public static List<Renderable> getRenderable() {
		return Collections.unmodifiableList(Game.me.renderable);
	}
	
	@SuppressWarnings("unchecked") public static <T extends Entity> List<T> getType(IEntityFilter filter, Class<T> cls) {
		List<T> list = new LinkedList<>();
		for (Entity entity : Game.me.entities) {
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
	
	@SafeVarargs public static List<Entity> getTypes(IEntityFilter filter, Class<? extends Entity>... entityClasses) {
		List<Entity> list = new LinkedList<>();
		for (Entity entity : Game.me.entities) {
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