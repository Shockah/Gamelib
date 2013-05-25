package pl.shockah.glib.logic.component;

import java.util.LinkedList;
import java.util.List;

public class Entity {
	private final List<Component> components = new LinkedList<Component>();
	
	protected final void addComponent(Component cmp) {
		if (components.contains(cmp)) throw new RuntimeException("Entity "+this+" already contains component "+cmp+".");
	}
	protected final void removeComponent(Component cmp) {
		components.remove(cmp);
	}
	protected final Component getComponent(Class<? extends Component> cls) {
		for (Component cmp : components) if (cmp.getClass().isAssignableFrom(cls)) return cmp;
		return null;
	}
	@SafeVarargs protected final List<Component> getComponents(Class<? extends Component>... clss) {
		List<Component> ret = new LinkedList<Component>();
		for (Class<? extends Component> cls : clss) for (Component cmp : components) if (cmp.getClass().isAssignableFrom(cls)) ret.add(cmp);
		return ret;
	}
}