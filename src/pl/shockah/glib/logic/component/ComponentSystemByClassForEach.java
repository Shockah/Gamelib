package pl.shockah.glib.logic.component;

public class ComponentSystemByClassForEach extends ComponentSystemByClass {
	public ComponentSystemByClassForEach(Class<? extends Component> cls) {
		super(cls);
	}
	
	protected final void onUpdate() {
		for (Entity entity : cache) foreach(entity);
	}
	protected void foreach(Entity entity) {}
}