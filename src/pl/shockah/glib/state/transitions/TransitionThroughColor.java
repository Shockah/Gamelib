package pl.shockah.glib.state.transitions;

import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;
import pl.shockah.glib.state.State;

public class TransitionThroughColor extends Transition {
	protected final Color color;
	protected final double baseSpeed;
	protected double alpha, incr;
	
	public TransitionThroughColor(Color color) {
		this(color,.05d);
	}
	public TransitionThroughColor(Color color, double baseSpeed) {
		this.color = color;
		this.baseSpeed = baseSpeed;
	}
	
	public void init(boolean in) {
		alpha = in ? 1 : 0;
		incr = (in ? -1 : 1)*baseSpeed;
	}
	
	public boolean update() {
		alpha += incr;
		return alpha <= 0 || alpha >= 0;
	}

	public void render(Graphics g) {
		g.setColor(color);
		g.draw(new Rectangle(new Vector2d(),State.get().getDisplaySize().toDouble()));
	}
}