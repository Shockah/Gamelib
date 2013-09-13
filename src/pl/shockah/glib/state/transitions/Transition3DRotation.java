package pl.shockah.glib.state.transitions;

import static org.lwjgl.opengl.GL11.*;
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
		System.out.println(""+sX+","+sY);
		return in ? (sX >= 1 && sY >= 1) : (sX <= 0 || sY <= 0);
	}
	
	//TODO make use of Graphics' scaling methods when they are added
	
	public void preRender(Graphics g) {
		Vector2i size = State.get().getDisplaySize();
		g.preDraw();
		glTranslated(size.x*(1-sX)/2,size.y*(1-sY)/2,0d);
		glScaled(sX,sY,0d);
	}
	public void render(Graphics g) {
		Vector2i size = State.get().getDisplaySize();
		g.preDraw();
		glScaled(1/sX,1/sY,0d);
		glTranslated(-size.x*(1-sX)/2,-size.y*(1-sY)/2,0d);
	}
	
	public boolean shouldUpdate() {return runUpdate;}
}