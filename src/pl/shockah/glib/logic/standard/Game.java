package pl.shockah.glib.logic.standard;

import java.util.LinkedList;
import java.util.List;
import pl.shockah.SortedLinkedList;

public class Game extends pl.shockah.glib.logic.Game<Game> {
	public static Game me = null;
	
	public Game() {
		me = this;
	}
	
	protected final List<EntityBase>
		entities = new LinkedList<>(),
		entitiesAdd = new LinkedList<>(),
		entitiesRemove = new LinkedList<>();
	protected final List<EntityRenderable>
		renderable = new SortedLinkedList<>(),
		renderableAdd = new SortedLinkedList<>(),
		renderableRemove = new LinkedList<>();
	
	public void reset() {
		entities.clear();
		entitiesAdd.clear();
		entitiesRemove.clear();
	}
	
	public void gameLoop() {
		entities.addAll(entitiesAdd);
		entities.removeAll(entitiesRemove);
		entitiesAdd.clear();
		entitiesRemove.clear();
		for (EntityBase e : entities) e.tick();
		
		renderable.removeAll(renderableRemove);
		renderable.addAll(renderableAdd);
		for (EntityRenderable er : renderable) er.onRender();
	}
}