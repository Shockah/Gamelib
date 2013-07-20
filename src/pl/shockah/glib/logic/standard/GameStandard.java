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
		state.preUpdate();
		if (state.shouldTransitionUpdate()) {
			entities.addAll(entitiesAdd);
			entities.removeAll(entitiesRemove);
			entitiesAdd.clear();
			entitiesRemove.clear();
			
			for (EntityBase e : entities) e.update();
		}
		
		glPushMatrix();
		if (state.shouldTransitionRender(g)) {
			renderable.removeAll(renderableRemove);
			renderable.addAll(renderableAdd);
			renderableRemove.clear();
			renderableAdd.clear();
			
			g.clear();
			for (EntityRenderable er : renderable) er.onRender(g);
		}
		state.renderTransition(g);
		state.postRender(g);
		glPopMatrix();
	}
}