package pl.shockah.glib.logic;

import pl.shockah.glib.geom.vector.Vector2;
import pl.shockah.glib.geom.vector.Vector2d;

public abstract class Entity {
	public Vector2d pos = new Vector2d();
	private boolean firstTick = true;
	
	protected Entity() {}
	
	public final void create() {create(null);}
	public final void create(double x, double y) {create(new Vector2d(x,y));}
	public void create(Vector2 pos) {
		if (pos != null) this.pos.set(pos);
		firstTick = true;
		Game.me.entitiesAdd.add(this);
		onCreate();
	}
	protected void onCreate() {}
	
	public void destroy() {
		onDestroy();
		Game.me.entitiesRemove.add(this);
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