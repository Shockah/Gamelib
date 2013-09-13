package pl.shockah.glib.state.transitions;

import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;
import pl.shockah.glib.state.State;

public class TransitionThroughColor extends Transition {
	protected final Color color;
	protected final float baseSpeed;
	protected final boolean runUpdate;
	protected float alpha, incr;
	
	public TransitionThroughColor() {
		this(Color.Black,.05f,false);
	}
	public TransitionThroughColor(Color color) {
		this(color,.05f,false);
	}
	public TransitionThroughColor(boolean runUpdate) {
		this(Color.Black,.05f,runUpdate);
	}
	public TransitionThroughColor(Color color, boolean runUpdate) {
		this(color,.05f,runUpdate);
	}
	public TransitionThroughColor(float baseSpeed) {
		this(Color.Black,baseSpeed,false);
	}
	public TransitionThroughColor(Color color, float baseSpeed) {
		this(color,baseSpeed,false);
	}
	public TransitionThroughColor(float baseSpeed, boolean runUpdate) {
		this(Color.Black,baseSpeed,runUpdate);
	}
	public TransitionThroughColor(Color color, float baseSpeed, boolean runUpdate) {
		this.color = color;
		this.baseSpeed = baseSpeed;
		this.runUpdate = runUpdate;
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
		Graphics.setColor(color.setAlpha(alpha));
		g.toggleAbsolute();
		g.draw(new Rectangle(new Vector2d(),State.get().getDisplaySize().toDouble()));
		g.toggleAbsolute();
	}
	
	public boolean shouldUpdate() {return runUpdate;}
}