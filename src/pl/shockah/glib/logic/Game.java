package pl.shockah.glib.logic;

import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.state.State;

public abstract class Game {
	public static Game me = null;
	protected static Graphics g = new Graphics();
	
	protected final State initialState;
	
	public Game(State initialState) {
		me = this;
		this.initialState = initialState;
	}
	
	public Graphics graphics() {
		return g;
	}
	
	public abstract void setup();
	public abstract void reset();
	public abstract void gameLoop();
	
	public void setupInitialState() {
		if (initialState == null) throw new IllegalArgumentException("A game can't exist without a State.");
		initialState.setup();
	}
	public void setInitialState() {
		State.change(initialState);
	}
}