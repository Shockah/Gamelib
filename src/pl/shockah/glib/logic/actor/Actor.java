package pl.shockah.glib.logic.actor;

import pl.shockah.glib.geom.vector.Vector2;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.logic.GameObject;

public abstract class Actor extends GameObject {
	public Vector2d pos = new Vector2d();
	private boolean firstTick = true;
	
	protected Actor() {}
	
	public final void create() {create(null);}
	public final void create(double x, double y) {create(new Vector2d(x,y));}
	public void create(Vector2 pos) {
		super.create();
		if (pos != null) this.pos.set(pos);
		firstTick = true;
		GameActor.me.actorsAdd.add(this);
		onCreate();
	}
	protected void onCreate() {}
	
	public void destroy() {
		onDestroy();
		GameActor.me.actorsRemove.add(this);
		super.destroy();
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