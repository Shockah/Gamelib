package pl.shockah.glib.state;

import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.state.transitions.Transition;

public final class TransitionState {
	public final State state;
	public final Transition tOut, tIn;
	public ETransition trans = ETransition.Out;
	
	public TransitionState(State state, Transition tOut, Transition tIn) {
		this.state = state;
		this.tOut = tOut;
		this.tIn = tIn;
	}
	
	public Transition getCurrent() {
		switch (trans) {
			case Out: return tOut;
			case In: return tIn;
			default: return null;
		}
	}
	public void init() {
		Transition current = getCurrent();
		if (current == null) return;
		current.init(trans == ETransition.In);
	}
	
	public boolean shouldUpdate() {
		Transition current = getCurrent();
		if (current == null) return true;
		return current.shouldUpdate();
	}
	public boolean shouldRender(Graphics g) {
		Transition current = getCurrent();
		if (current == null) return true;
		return current.shouldRender(g);
	}
	
	public boolean update() {
		Transition current = getCurrent();
		if (current == null || current.update()) {
			switch (trans) {
				case Out: {
					trans = ETransition.In;
					State.set(state);
					if (tIn == null) {
						trans = ETransition.None;
						return true;
					} else init();
				} break;
				case In: {
					trans = ETransition.None;
					return true;
				}
				default: return true;
			}
		}
		return false;
	}
	public void render(Graphics g) {
		Transition current = getCurrent();
		if (current == null) return;
		current.render(g);
	}
	public void preRender(Graphics g) {
		Transition current = getCurrent();
		if (current == null) return;
		current.preRender(g);
	}
}