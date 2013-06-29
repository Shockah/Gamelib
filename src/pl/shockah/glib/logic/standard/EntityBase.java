package pl.shockah.glib.logic.standard;

import pl.shockah.glib.geom.vector.Vector2d;

abstract class EntityBase {
	protected Vector2d pos;
	private boolean firstTick;
	
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
	
	public final void tick() {
		if (firstTick) {
			firstTick = false;
			onFirstTick();
		}
		onTick();
	}
	protected void onFirstTick() {}
	protected void onTick() {}
}