package pl.shockah.glib.logic.standard;

import pl.shockah.glib.geom.vector.Vector2d;

public abstract class EntityBase {
	public Vector2d pos;
	private boolean firstTick;
	
	EntityBase() {}
	
	public final void create() {create(new Vector2d());}
	public final void create(double x, double y) {create(new Vector2d(x,y));}
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