package pl.shockah.glib.logic;

import pl.shockah.glib.Gamelib;

public abstract class Game<G extends Game<G>> implements IGame {
	private Gamelib<G> glib = null;
	private boolean locked = false;
	
	public Gamelib<G> getGamelib() {
		if (glib == null) throw new RuntimeException("Can't run without a Gamelib specified.");
		return glib;
	}
	
	public void setGamelib(Gamelib<G> glib) {
		if (locked) throw new RuntimeException("This object is locked, it can't be modified.");
		this.glib = glib;
	}
	
	public void lock() {
		locked = true;
	}
}