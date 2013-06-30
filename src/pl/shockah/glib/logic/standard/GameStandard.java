package pl.shockah.glib.logic.standard;

import static org.lwjgl.opengl.GL11.*;
import java.util.LinkedList;
import java.util.List;
import pl.shockah.SortedLinkedList;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.logic.IGame;
import pl.shockah.glib.state.State;

public class GameStandard implements IGame {
	public static GameStandard me = null;
	private static Graphics g = new Graphics();
	
	public GameStandard() {
		me = this;
	}
	
	protected final List<EntityBase>
		entities = new LinkedList<>(),
		entitiesAdd = new LinkedList<>(),
		entitiesRemove = new LinkedList<>();
	protected final List<EntityRenderable>
		renderable = new SortedLinkedList<>(),
		renderableAdd = new SortedLinkedList<>(),
		renderableRemove = new SortedLinkedList<>();
	
	public void reset() {
		entities.clear();
		entitiesAdd.clear();
		entitiesRemove.clear();
	}
	
	public void gameLoop() {
		State state = State.get();
		
		state.preUpdate();
		state.updateTransition();
		if (state.shouldTransitionUpdate()) {
			entities.addAll(entitiesAdd);
			entities.removeAll(entitiesRemove);
			entitiesAdd.clear();
			entitiesRemove.clear();
			
			for (EntityBase e : entities) e.update();
		}
		
		if (state.shouldTransitionRender(g)) {
			renderable.removeAll(renderableRemove);
			renderable.addAll(renderableAdd);
			renderableRemove.clear();
			renderableAdd.clear();
			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			for (EntityRenderable er : renderable) er.onRender(g);
		}
		state.postRender(g);
		state.renderTransition(g);
	}
}