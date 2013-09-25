package pl.shockah.glib.logic.standard;

import java.util.List;
import pl.shockah.SortedLinkedList;
import pl.shockah.glib.geom.vector.IVector2;
import pl.shockah.glib.gl.Graphics;

public abstract class EntityRenderable extends EntityBase {
	protected final List<Renderable> renderables = new SortedLinkedList<>();
	private boolean listUsed = false;
	protected double baseDepth = 0d;
	
	public EntityRenderable() {}
	public EntityRenderable(double depth) {
		baseDepth = depth;
	}
	
	public final void create(IVector2 pos) {
		super.create(pos);
		register();
	}
	public final void destroy() {
		listUsed = true;
		for (Renderable r : renderables) r.destroy();
		listUsed = false;
		renderables.clear();
		super.destroy();
	}
	
	protected final boolean isListUsed() {
		return listUsed;
	}
	
	public final void setDepth(double depth) {
		for (Renderable r : renderables) r.setDepth(depth);
	}
	
	public final void render(Graphics g) {
		onRender(g);
	}
	protected void onRender(Graphics g) {}
	
	private final void register() {
		onRegister();
	}
	protected void onRegister() {
		new RenderableDefault(this).create();
	}
}