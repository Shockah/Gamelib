package pl.shockah.glib.logic.actor;

import pl.shockah.glib.Gamelib;
import pl.shockah.glib.gl.Graphics;

public abstract class Renderable implements Comparable<Renderable> {
	protected final ActorRenderable parent;
	private double depth = 0;
	public final boolean usingBaseDepth;
	
	public Renderable() {
		parent = null;
		usingBaseDepth = false;
	}
	public Renderable(double depth) {
		parent = null;
		this.depth = depth;
		usingBaseDepth = false;
	}
	public Renderable(ActorRenderable er) {
		parent = er;
		depth = er.baseDepth;
		usingBaseDepth = true;
	}
	public Renderable(ActorRenderable er, double depth) {
		parent = er;
		this.depth = depth;
		usingBaseDepth = false;
	}
	
	public final int compareTo(Renderable r) {
		if (depth == r.depth) return 0;
		return depth > r.depth ? -1 : 1;
	}
	
	public final ActorRenderable getParent() {
		return parent;
	}
	
	public final void create() {
		if (!Gamelib.modules().graphics()) return;
		if (parent != null) parent.renderables.add(this);
		GameActor.me.renderableAdd.add(this);
		onCreate();
	}
	protected void onCreate() {}
	
	public final void destroy() {
		if (!Gamelib.modules().graphics()) return;
		onDestroy();
		GameActor.me.renderableRemove.add(this);
		if (parent != null && !parent.listUsed()) parent.renderables.remove(this);
	}
	protected void onDestroy() {}
	
	public final double depth() {
		return depth;
	}
	public final void setDepth(double depth) {
		if (!Gamelib.modules().graphics()) return;
		this.depth = depth;
		if (GameActor.me.renderable.contains(this)) {
			GameActor.me.renderableRemove.add(this);
			GameActor.me.renderableAdd.add(this);
		}
	}
	
	public final void render(Graphics g) {
		onRender(g);
	}
	protected void onRender(Graphics g) {}
}