package pl.shockah.glib.logic.standard;

import java.util.LinkedList;
import java.util.List;
import pl.shockah.glib.geom.vector.Vector2d;

public abstract class EntityBase {
	public static List<EntityBase> getEntities() {return new LinkedList<>(GameStandard.me.entities);}
	@SafeVarargs public static List<EntityBase> getEntities(Class<? extends EntityBase>... entityClasses) {
		List<EntityBase> list = new LinkedList<>();
		for (EntityBase entity : GameStandard.me.entities) for (Class<? extends EntityBase> cls : entityClasses) if (cls.isAssignableFrom(entity.getClass())) list.add(entity);
		return list;
	}
	
	protected Vector2d pos;
	private boolean firstTick;
	
	EntityBase() {}
	
	public final void create() {create(new Vector2d());}
	public void create(Vector2d pos) {
		this.pos = pos;
		firstTick = true;
		GameStandard.me.entitiesAdd.add(this);
		onCreate();
	}
	protected void onCreate() {}
	
	public void destroy() {
		onDestroy();
		GameStandard.me.entitiesRemove.add(this);
	}
	protected void onDestroy() {}
	
	public final void update() {
		if (firstTick) {
			firstTick = false;
			onFirstUpdate();
		}
		onUpdate();
	}
	protected void onFirstUpdate() {}
	protected void onUpdate() {}
}