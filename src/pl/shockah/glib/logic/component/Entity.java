package pl.shockah.glib.logic.component;

import java.util.LinkedList;
import java.util.List;
import pl.shockah.glib.logic.GameObject;

public class Entity extends GameObject {
	protected final List<Component> components = new LinkedList<>();
	
	public final void create() {
		super.create();
		GameComponent.me.entitiesAdd.add(this);
		onCreate();
	}
	protected void onCreate() {}
	
	public final void destroy() {
		onDestroy();
		GameComponent.me.entitiesRemove.add(this);
		super.destroy();
	}
	protected void onDestroy() {}
}