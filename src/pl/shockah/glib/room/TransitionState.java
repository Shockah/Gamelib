package pl.shockah.glib.room;

import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.room.transitions.Transition;

public final class TransitionState {
	public final Room room;
	public final Transition tOut, tIn;
	public boolean in;
	
	public TransitionState(Room room, Transition tOut, Transition tIn, boolean in) {
		this.room = room;
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