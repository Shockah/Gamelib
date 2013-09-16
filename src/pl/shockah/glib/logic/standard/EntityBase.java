package pl.shockah.glib.logic.standard;

import pl.shockah.glib.geom.vector.Vector2d;

public abstract class EntityBase {
	public Vector2d pos = new Vector2d();
	private boolean firstTick = true;
	
	EntityBase() {}
	
	public final void create() {
		pos = null;
		create(new Vector2d());
	}
	public final void create(double x, double y) {
		pos = null;
		create(new Vector2d(x,y));
	}
	public void create(Vector2d pos) {
		if (pos == null) this.pos = pos;
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
		firstUpdate();
		onUpdate();
	}
	public final void firstUpdate() {
		if (!firstTick) return;
		firstTick = false;
		onFirstUpdate();
	}
	protected void onFirstUpdate() {}
	protected void onUpdate() {}
}