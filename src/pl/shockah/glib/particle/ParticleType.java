package pl.shockah.glib.particle;

import pl.shockah.glib.geom.vector.IVector2;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.tex.Image;

public abstract class ParticleType {
	protected final Image image;
	
	public ParticleType(Image image) {
		this.image = image;
	}
	
	public void spawn(ParticleSystem ps, IVector2 pos, Object... args) {
		ps.particles.add(create(ps,pos,args));
	}
	protected abstract Particle create(ParticleSystem ps, IVector2 pos, Object... args);
	
	protected void onSpawn(Particle p) {}
	protected void onDespawn(Particle p) {}
	protected void onUpdate(Particle p) {
		p.pos.add(p.vel);
	}
	protected void onRender(Particle p, Graphics g) {
		image.rotation.angle = p.rotation;
		image.scale = p.size.Div(image.getSize());
		
		g.draw(image,p.pos);
		
		image.rotation.angle = 0;
		image.scale.set(1d,1d);
	}
}