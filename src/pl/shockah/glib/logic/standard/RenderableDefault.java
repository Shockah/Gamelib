package pl.shockah.glib.logic.standard;

import pl.shockah.glib.gl.Graphics;

public class RenderableDefault extends Renderable {
	public RenderableDefault(EntityRenderable er) {
		super(er);
	}
	
	protected void onRender(Graphics g) {
		parent.render(g);
	}
}