package pl.shockah.glib.state.transitions;

import pl.shockah.glib.gl.Graphics;

public abstract class Transition {
	public abstract void init(boolean in);
	public abstract boolean update();
	public abstract void render(Graphics g);
	
	public boolean shouldUpdate() {return false;}
	public boolean shouldRender(Graphics g) {return true;}
}