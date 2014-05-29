package pl.shockah.glib.logic.actor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import pl.shockah.glib.logic.IFilter;

public final class Actors {
	public static List<Actor> get() {
		return Collections.unmodifiableList(GameActor.me.actors);
	}
	public static List<Renderable> getRenderable() {
		return Collections.unmodifiableList(GameActor.me.renderable);
	}
	
	@SuppressWarnings("unchecked") public static <T extends Actor> List<T> getType(IFilter<Actor> filter, Class<T> cls) {
		List<T> list = new LinkedList<>();
		for (Actor actor : GameActor.me.actors) {
			boolean b = true;
			if (b && filter != null) if (!filter.accept(actor)) b = false;
			if (b && cls != null) if (!cls.isAssignableFrom(actor.getClass())) b = false;
			if (b) list.add((T)actor);
		}
		return list;
	}
	public static <T extends Actor> List<T> getType(Class<T> cls) {
		return getType(null,cls);
	}
	
	@SafeVarargs public static List<Actor> getTypes(IFilter<Actor> filter, Class<? extends Actor>... actorClasses) {
		List<Actor> list = new LinkedList<>();
		for (Actor actor : GameActor.me.actors) {
			boolean b = true;
			if (b && filter != null) if (!filter.accept(actor)) b = false;
			if (b && actorClasses.length != 0) {
				b = false;
				for (Class<? extends Actor> cls : actorClasses) {
					if (cls.isAssignableFrom(actor.getClass())) {
						b = true;
						break;
					}
				}
			}
			if (b) list.add(actor);
		}
		return list;
	}
	@SafeVarargs public static List<Actor> getTypes(Class<? extends Actor>... actorClasses) {
		return getTypes(null,actorClasses);
	}
}