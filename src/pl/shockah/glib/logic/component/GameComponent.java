package pl.shockah.glib.logic.component;

import java.util.LinkedList;
import java.util.List;
import pl.shockah.glib.Gamelib;
import pl.shockah.glib.logic.Game;
import pl.shockah.glib.state.State;

public class GameComponent extends Game {
	@SuppressWarnings("hiding") public static GameComponent me = null;
	
	protected final List<Entity>
		entities = new LinkedList<>(),
		entitiesAdd = new LinkedList<>(),
		entitiesRemove = new LinkedList<>();
	protected final List<ComponentSystem<?>> systems = new LinkedList<>();
	
	public GameComponent(State initialState) {
		super(initialState);
		me = this;
	}
	
	public ComponentSystem<?> getComponentSystemFor(Component component) {
		for (ComponentSystem<?> system : systems) if (component.getClass().isAssignableFrom(system.cls)) return system;
		return null;
	}
	
	public void setup() {
		if (Gamelib.modules().graphics()) new CRenderSystem().create();
	}
	
	public void reset() {
		entities.clear();
		entitiesAdd.clear();
		entitiesRemove.clear();
		for (ComponentSystem<?> system : systems) system.reset();
	}
	
	public void gameLoop() {
		State state = State.get();
		
		state.updateTransition();
		if (State.get() == null) return;
		state.preUpdate();
		if (state.shouldTransitionUpdate()) {
			entities.addAll(entitiesAdd);
			for (ComponentSystem<?> system : systems) for (Entity e : entitiesAdd) for (Component component : e.components) system.addCacheOnMatch(component);
			entities.removeAll(entitiesRemove);
			for (ComponentSystem<?> system : systems) for (Entity e : entitiesRemove) for (Component component : e.components) system.removeCache(component);
			entitiesAdd.clear();
			entitiesRemove.clear();
			for (ComponentSystem<?> system : systems) system.update();
		}
	}
}