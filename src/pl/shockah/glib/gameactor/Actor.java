package pl.shockah.glib.gameactor;

import java.util.LinkedList;
import java.util.List;
import pl.shockah.SortedLinkedList;
import pl.shockah.glib.Game;
import pl.shockah.glib.gl.Graphics;

public class Actor implements Comparable<Actor> {
	protected Actor parent = null;
	private double depth = 0d;
	protected final List<Actor>
		actors = new SortedLinkedList<>(),
		toAdd = new LinkedList<>(),
		toRemove = new LinkedList<>();
	
	public double getDepth() {
		return depth;
	}
	public void setDepth(double depth) {
		if (parent == null)
			return;
		
		parent.toAdd.remove(this);
		parent.toRemove.remove(this);
		this.depth = depth;
		parent.toAdd.add(this);
		parent.toRemove.add(this);
	}
	
	public void render(Game game, Graphics g) {
		onRender(game, g);
		
		actors.addAll(toAdd);
		actors.removeAll(toRemove);
		toAdd.clear();
		toRemove.clear();
		
		for (Actor actor : actors)
			actor.render(game, g);
	}
	public void onRender(Game game, Graphics g) {}
	
	public int compareTo(Actor o) {
		return depth == o.depth ? 0 : (depth < o.depth ? -1 : 1);
	}
}