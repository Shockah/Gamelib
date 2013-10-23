package pl.shockah.glib.particle;

import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.Graphics;

public class Particle {
	public final ParticleSystem ps;
	public final ParticleType pt;
	public Vector2d pos = new Vector2d(), vel = new Vector2d(), size = new Vector2d();
	public double rotation = 0;
	public boolean dead = false;
	
	protected Particle(ParticleSystem ps, ParticleType pt) {
		this.ps = ps;
		this.pt = pt;
	}
	
	public void update() {
		pt.onUpdate(this);
	}
	public void render(Graphics g) {
		pt.onRender(this,g);
	}
}