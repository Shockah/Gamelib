package pl.shockah.glib.logic.component;

import pl.shockah.glib.Gamelib;
import pl.shockah.glib.gl.GL;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.state.State;

public class CRenderSystem extends ComponentSystem<CRenderable> {
	public CRenderSystem() {
		super(CRenderable.class);
	}
	
	protected void onUpdate() {
		Graphics g = GameComponent.me.getGraphics();
		State state = State.get();
		
		if (state == null) return;
		if (Gamelib.modules().graphics()) {
			Graphics.getDefaultBlendMode().apply();
			g.clearClip();
			g.clearTransformedClip();
			g.clearTransformations();
			state.preRender(g);
			state.renderTransitionPre(g);
			if (state.shouldTransitionRender(g)) {
				g.clear();
				for (CRenderable component : cache) component.render(g);
			}
			state.preTransitionRender(g);
			state.renderTransition(g);
			state.postRender(g);
			GL.loadIdentity();
		}
	}
}