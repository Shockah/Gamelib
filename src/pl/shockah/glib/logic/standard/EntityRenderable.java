package pl.shockah.glib.logic.standard;

import pl.shockah.glib.geom.vector.Vector2d;

public class EntityRenderable extends EntityBase implements Comparable<EntityRenderable> {
	private float depth = 0;
	
	public int compareTo(EntityRenderable er) {
		if (depth == er.depth) return 0;
		return depth < er.depth ? -1 : 1;
	}
	
	public void create(Vector2d pos) {
		super.create(pos);
		Game.me.renderableAdd.add(this);
	}
	public void destroy() {
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
	
	public void render() {
		onRender();
	}
	protected void onRender() {}
}