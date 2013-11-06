package pl.shockah.glib.logic.standard;

import pl.shockah.glib.Gamelib;
import pl.shockah.glib.gl.Graphics;

public abstract class Renderable implements Comparable<Renderable> {
	protected final EntityRenderable parent;
	private double depth = 0;
	
	public Renderable() {
		this(null);
	}
	public Renderable(double depth) {
		this(null,depth);
	}
	public Renderable(EntityRenderable er) {
		this(er,er.baseDepth);
	}
	public Renderable(EntityRenderable er, double depth) {
		parent = er;
		this.depth = depth;
	}
	
	public final int compareTo(Renderable r) {
		if (depth == r.depth) return 0;
		return depth > r.depth ? -1 : 1;
	}
	
	public final EntityRenderable getParent() {
		return parent;
	}
	
	public final void create() {
		if (!Gamelib.modules().graphics()) return;
		if (parent != null) parent.renderables.add(this);
		GameStandard.me.renderableAdd.add(this);
		onCreate();
	}
	protected void onCreate() {}
	
	public final void destroy() {
		if (!Gamelib.modules().graphics()) return;
		onDestroy();
		GameStandard.me.renderableRemove.add(this);
		if (parent != null && !parent.isListUsed()) parent.renderables.remove(this);
	}
	protected void onDestroy() {}
	
	public final double getDepth() {
		return depth;
	}
	public final void setDepth(double depth) {
		if (!Gamelib.modules().graphics()) return;
		this.depth = depth;
		if (GameStandard.me.renderable.contains(this)) {
			GameStandard.me.renderableRemove.add(this);
			GameStandard.me.renderableAdd.add(this);
		}
	}
	
	public final void render(Graphics g) {
		onRender(g);
	}
	protected void onRender(Graphics g) {}
}