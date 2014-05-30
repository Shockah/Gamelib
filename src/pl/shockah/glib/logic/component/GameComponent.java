package pl.shockah.glib.logic.component;

import java.util.LinkedList;
import java.util.List;
import pl.shockah.glib.Gamelib;
import pl.shockah.glib.logic.Game;
import pl.shockah.glib.state.State;

public class GameComponent extends Game {
	@SuppressWarnings("hiding") public static GameComponent me = null;
	
	protected final IModifyComponentSystems imcs;
	protected final List<Entity>
		entities = new LinkedList<>(),
		entitiesAdd = new LinkedList<>(),
		entitiesRemove = new LinkedList<>();
	protected final List<ComponentSystem> systems = new LinkedList<>();
	
	public GameComponent(State initialState) {this(initialState, null);}
	public GameComponent(State initialState, IModifyComponentSystems imcs) {
		super(initialState);
		me = this;
		this.imcs = imcs;
	}
	
	public void setup() {
		List<ComponentSystem> list = new LinkedList<>();
		if (Gamelib.modules().graphics()) list.add(new CRenderSystem());
		if (imcs != null) imcs.modifyComponentSystems(list);
		for (ComponentSystem system : list) system.create();
	}
	
	public void reset() {
		entities.clear();
		entitiesAdd.clear();
		entitiesRemove.clear();
		for (ComponentSystem system : systems) system.reset();
	}
	
	public void gameLoop() {
		State state = State.get();
		
		state.updateTransition();
		if (State.get() == null) return;
		state.preUpdate();
		if (state.shouldTransitionUpdate()) {
			entities.addAll(entitiesAdd);
			for (ComponentSystem system : systems) for (Entity entity : entitiesAdd) system.addCacheOnMatch(entity);
			entities.removeAll(entitiesRemove);
			for (ComponentSystem system : systems) for (Entity entity : entitiesRemove) system.removeCache(entity);
			entitiesAdd.clear();
			entitiesRemove.clear();
			for (ComponentSystem system : systems) system.update();
		}
	}
}