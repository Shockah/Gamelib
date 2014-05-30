package pl.shockah.glib.logic.component;

public class ComponentSystemByClass extends ComponentSystem {
	protected final Class<? extends Component> cls;
	
	public ComponentSystemByClass(Class<? extends Component> cls) {
		this.cls = cls;
	}
	
	public boolean matches(Entity entity) {
		return entity.hasComponent(cls);
	}
}