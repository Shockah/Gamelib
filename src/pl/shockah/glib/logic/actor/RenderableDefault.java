package pl.shockah.glib.logic.actor;

import pl.shockah.glib.gl.Graphics;

public class RenderableDefault extends Renderable {
	public RenderableDefault(ActorRenderable er) {
		super(er);
	}
	
	protected void onRender(Graphics g) {
		parent.render(g);
	}
}