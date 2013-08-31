package pl.shockah.glib.state.transitions;

import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;
import pl.shockah.glib.state.State;

public class TransitionThroughColor extends Transition {
	protected final Color color;
	protected final float baseSpeed;
	protected float alpha, incr;
	
	public TransitionThroughColor(Color color) {
		this(color,.05f);
	}
	public TransitionThroughColor(Color color, float baseSpeed) {
		this.color = color;
		this.baseSpeed = baseSpeed;
	}
	
	public void init(boolean in) {
		alpha = in ? 1 : 0;
		incr = (in ? -1 : 1)*baseSpeed;
	}
	
	public boolean update() {
		alpha += incr;
		return (incr < 0 && alpha <= 0) || (incr > 0 && alpha >= 1);
	}

	public void render(Graphics g) {
		g.setColor(color.setAlpha(alpha));
		g.draw(new Rectangle(new Vector2d(),State.get().getDisplaySize().toDouble()));
	}
}