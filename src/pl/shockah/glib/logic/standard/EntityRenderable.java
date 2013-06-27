package pl.shockah.glib.logic.standard;

import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.Graphics;

public abstract class EntityRenderable extends EntityBase implements Comparable<EntityRenderable> {
	private float depth = 0;
	
	public final int compareTo(EntityRenderable er) {
		if (depth == er.depth) return 0;
		return depth < er.depth ? -1 : 1;
	}
	
	public final void create(Vector2d pos) {
		super.create(pos);
		Game.me.renderableAdd.add(this);
	}
	public final void destroy() {
		Game.me.renderableRemove.add(this);
		super.destroy();
	}
	
	public final float getDepth() {
		return depth;
	}
	public final void setDepth(float depth) {
		this.depth = depth;
		Game.me.renderableRemove.add(this);
		Game.me.renderableAdd.add(this);
	}
	
	public final void render(Graphics g) {
		onRender(g);
	}
	protected void onRender(Graphics g) {}
}