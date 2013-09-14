package pl.shockah.glib.state.transitions;

import pl.shockah.glib.geom.vector.Vector2i;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.state.State;

public class Transition3DRotation extends Transition {
	protected final double baseSpeedX, baseSpeedY;
	protected final boolean runUpdate;
	protected double sX, sY;
	protected boolean in;
	
	public Transition3DRotation() {
		this(.05d,0f,false);
	}
	public Transition3DRotation(boolean runUpdate) {
		this(.05d,0f,runUpdate);
	}
	public Transition3DRotation(double baseSpeedX, double baseSpeedY) {
		this(baseSpeedX,baseSpeedY,false);
	}
	public Transition3DRotation(double baseSpeedX, double baseSpeedY, boolean runUpdate) {
		this.baseSpeedX = baseSpeedX;
		this.baseSpeedY = baseSpeedY;
		this.runUpdate = runUpdate;
	}
	
	public void init(boolean in) {
		sX = sY = 1d;
		if (in) {
			this.in = false;
			while (!update());
		}
		this.in = in;
	}
	
	public boolean update() {
		sX -= baseSpeedX*(in ? -1 : 1);
		sY -= baseSpeedY*(in ? -1 : 1);
		return in ? (sX >= 1 && sY >= 1) : (sX <= 0 || sY <= 0);
	}
	
	public void preRender(Graphics g) {
		Vector2i size = State.get().getDisplaySize();
		g.translate(size.x*(1-sX)/2,size.y*(1-sY)/2);
		g.scale(sX,sY);
	}
	public void render(Graphics g) {
		Vector2i size = State.get().getDisplaySize();
		g.preDraw();
		g.scale(1/sX,1/sY);
		g.translate(-size.x*(1-sX)/2,-size.y*(1-sY)/2);
	}
	
	public boolean shouldUpdate() {return runUpdate;}
}