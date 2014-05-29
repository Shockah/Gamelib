package pl.shockah.glib.logic.component;

import java.util.LinkedList;
import java.util.List;
import pl.shockah.glib.logic.GameObject;

public class Entity extends GameObject {
	protected final List<Component> components = new LinkedList<>();
	
	public final void create() {
		super.create();
		GameEntity.me.entitiesAdd.add(this);
		onCreate();
	}
	protected void onCreate() {}
	
	public final void destroy() {
		onDestroy();
		GameEntity.me.entitiesRemove.add(this);
		super.destroy();
	}
	protected void onDestroy() {}
}