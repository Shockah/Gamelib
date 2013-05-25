package pl.shockah.glib.logic.standard;

import java.util.LinkedList;
import java.util.List;
import pl.shockah.glib.geom.IVector;

public class Game<V extends IVector> {
	protected List<Entity<V>>
		entities = new LinkedList<Entity<V>>(),
		entitiesToAdd = new LinkedList<Entity<V>>(),
		entitiesToRemove = new LinkedList<Entity<V>>();
}