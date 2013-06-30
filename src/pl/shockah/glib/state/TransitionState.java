package pl.shockah.glib.state;

import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.state.transitions.Transition;

public final class TransitionState {
	public final State state;
	public final Transition tOut, tIn;
	public boolean in;
	
	public TransitionState(State state, Transition tOut, Transition tIn, boolean in) {
		this.state = state;
		this.tOut = tOut;
		this.tIn = tIn;
		this.in = in;
	}
	
	public Transition getCurrent() {
		return in ? tIn : tOut;
	}
	public boolean shouldUpdate() {return getCurrent().shouldUpdate();}
	public boolean shouldRender(Graphics g) {return getCurrent().shouldRender(g);}
	
	public boolean update() {
		return getCurrent().update();
	}
	public void render(Graphics g) {
		getCurrent().render(g);
	}
}