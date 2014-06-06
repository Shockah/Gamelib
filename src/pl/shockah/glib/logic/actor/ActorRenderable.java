package pl.shockah.glib.logic.actor;

import java.util.List;
import pl.shockah.SortedLinkedList;
import pl.shockah.glib.Gamelib;
import pl.shockah.glib.geom.vector.Vector2;
import pl.shockah.glib.gl.Graphics;

public abstract class ActorRenderable extends Actor {
	protected final List<Renderable> renderables = new SortedLinkedList<>();
	private boolean listUsed = false;
	protected double baseDepth = 0d;
	
	public ActorRenderable() {}
	public ActorRenderable(double depth) {
		baseDepth = depth;
	}
	
	public final void create(Vector2 pos) {
		super.create(pos);
		createRenderables();
	}
	public final void destroy() {
		if (Gamelib.modules().graphics()) {
			listUsed = true;
			for (Renderable r : renderables) r.destroy();
			listUsed = false;
			renderables.clear();
		}
		super.destroy();
	}
	
	protected final boolean listUsed() {
		return listUsed;
	}
	
	public final ActorRenderable setDepth(double depth) {
		if (!Gamelib.modules().graphics()) return this;
		baseDepth = depth;
		for (Renderable r : renderables) if (r.usingBaseDepth) r.setDepth(depth);
		return this;
	}
	
	public final void render(Graphics g) {
		onRender(g);
	}
	protected void onRender(Graphics g) {}
	
	private final void createRenderables() {
		if (!Gamelib.modules().graphics()) return;
		onCreateRenderables();
	}
	protected void onCreateRenderables() {
		new RenderableDefault(this).create();
	}
}