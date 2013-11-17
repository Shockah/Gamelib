package pl.shockah.glib.logic;

import java.util.LinkedList;
import java.util.List;
import pl.shockah.SortedLinkedList;
import pl.shockah.glib.Gamelib;
import pl.shockah.glib.gl.GL;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.state.State;

public class Game {
	public static Game me = null;
	private static Graphics g = new Graphics();
	
	protected final State initialState;
	
	public Game(State initialState) {
		me = this;
		this.initialState = initialState;
	}
	
	protected final List<Entity>
		entities = new LinkedList<>(),
		entitiesAdd = new LinkedList<>(),
		entitiesRemove = new LinkedList<>();
	protected final List<Renderable>
		renderable = new SortedLinkedList<>(),
		renderableAdd = new SortedLinkedList<>(),
		renderableRemove = new SortedLinkedList<>();
	
	public void reset() {
		entities.clear();
		entitiesAdd.clear();
		entitiesRemove.clear();
		renderable.clear();
		renderableAdd.clear();
		renderableRemove.clear();
	}
	
	public Graphics getGraphics() {
		return g;
	}
	public void gameLoop() {
		State state = State.get();
		
		state.updateTransition();
		if (State.get() == null) return;
		state.preUpdate();
		if (state.shouldTransitionUpdate()) {
			entities.addAll(entitiesAdd);
			entities.removeAll(entitiesRemove);
			entitiesAdd.clear();
			entitiesRemove.clear();
			for (Entity e : entities) e.update();
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
				g.clear();
				for (Renderable r : renderable) r.render(g);
			}
			state.preTransitionRender(g);
			state.renderTransition(g);
			state.postRender(g);
			GL.loadIdentity();
		}
	}
	
	public void setupInitialState() {
		if (initialState == null) throw new IllegalArgumentException("A game can't exist without a State.");
		initialState.setup();
	}
	public void setInitialState() {
		State.change(initialState);
	}
}