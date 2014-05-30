package pl.shockah.glib.logic.component;

import java.util.LinkedList;
import java.util.List;

public class ModifyComponentSystemsAdd implements IModifyComponentSystems {
	protected final ComponentSystem[] systems;
	
	public ModifyComponentSystemsAdd(ComponentSystem... systems) {
		this.systems = systems;
	}
	
	public void modifyComponentSystems(List<ComponentSystem> list) {
		List<ComponentSystem> copy = new LinkedList<>(list);
		list.clear();
		for (ComponentSystem system : systems) list.add(system);
		list.addAll(copy);
	}
}