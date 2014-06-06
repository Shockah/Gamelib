package pl.shockah.glib.logic.actor;

import java.util.LinkedList;
import java.util.List;
import pl.shockah.SortedLinkedList;
import pl.shockah.glib.Gamelib;
import pl.shockah.glib.gl.GL;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;
import pl.shockah.glib.logic.Game;
import pl.shockah.glib.state.State;

public class GameActor extends Game {
	@SuppressWarnings("hiding") public static GameActor me = null;
	
	protected final List<Actor>
		actors = new LinkedList<>(),
		actorsAdd = new LinkedList<>(),
		actorsRemove = new LinkedList<>();
	protected final List<Renderable>
		renderable = new SortedLinkedList<>(),
		renderableAdd = new SortedLinkedList<>(),
		renderableRemove = new SortedLinkedList<>();
	public boolean autoResetColor = true;
	
	public GameActor(State initialState) {
		super(initialState);
		me = this;
	}
	
	public void setup() {}
	
	public void reset() {
		actors.clear();
		actorsAdd.clear();
		actorsRemove.clear();
		renderable.clear();
		renderableAdd.clear();
		renderableRemove.clear();
	}
	
	public void gameLoop() {
		State state = State.get();
		Graphics g = graphics();
		
		state.updateTransition();
		if (State.get() == null) return;
		state.preUpdate();
		if (state.shouldTransitionUpdate()) {
			actors.addAll(actorsAdd);
			actors.removeAll(actorsRemove);
			actorsAdd.clear();
			actorsRemove.clear();
			for (Actor a : actors) a.update();
		}
		
		if (State.get() == null) return;
		if (Gamelib.modules().graphics()) {
			Graphics.getDefaultBlendMode().apply();
			g.clearClip();
			g.clearTransformedClip();
			g.clearTransformations();
			state.preRender(g);
			state.renderTransitionPre(g);
			if (state.shouldTransitionRender(g)) {
				renderable.removeAll(renderableRemove);
				renderable.addAll(renderableAdd);
				renderableRemove.clear();
				renderableAdd.clear();
				if (autoResetColor) g.setColor(Color.White);
				g.clear();
				for (Renderable r : renderable) {
					if (autoResetColor) g.setColor(Color.White);
					r.render(g);
				}
			}
			if (autoResetColor) g.setColor(Color.White);
			state.preTransitionRender(g);
			if (autoResetColor) g.setColor(Color.White);
			state.renderTransition(g);
			if (autoResetColor) g.setColor(Color.White);
			state.postRender(g);
			GL.loadIdentity();
		}
	}
}