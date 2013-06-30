package pl.shockah.glib.room.transitions;

import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;
import pl.shockah.glib.room.Room;

public class TransitionThroughColor extends Transition {
	protected final Color color;
	protected double alpha, incr;
	
	public TransitionThroughColor(Color color) {
		this.color = color;
	}
	
	public void init(boolean in) {
		alpha = in ? 1 : 0;
		incr = (in ? -1 : 1)*.05d;
	}
	
	public boolean update() {
		alpha += incr;
		return alpha <= 0 || alpha >= 0;
	}

	public void render(Graphics g) {
		g.setColor(color);
		g.draw(new Rectangle(new Vector2d(),Room.get().getDisplaySize().toDouble()));
	}
}