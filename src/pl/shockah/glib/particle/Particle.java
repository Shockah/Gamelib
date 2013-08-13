package pl.shockah.glib.particle;

import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.Image;

public abstract class Particle {
	protected final Image image;
	protected Vector2d pos = new Vector2d(), vel = new Vector2d(), size;
	protected boolean dead = false;
	
	protected Particle(Image image, Vector2d pos, Vector2d size) {
		this.image = image;
		this.pos = new Vector2d(pos);
		this.size = new Vector2d(size);
	}
	
	public final void update() {
		onUpdate();
	}
	protected void onUpdate() {
		pos.add(vel);
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public final void render(Graphics g) {
		onRender(g);
	}
	protected void onRender(Graphics g) {
		image.scale = size.Div(image.getSize().toDouble()).scale(size);
		g.draw(image,pos);
	}
}