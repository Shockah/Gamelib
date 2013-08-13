package pl.shockah.glib.particle;

import java.util.ArrayList;
import java.util.List;
import pl.shockah.glib.gl.BlendMode;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.logic.standard.EntityRenderable;

public class ParticleSystem extends EntityRenderable {
	protected final BlendMode blendMode;
	protected List<Particle> particles = new ArrayList<>();
	
	public ParticleSystem() {this(BlendMode.Add);}
	public ParticleSystem(BlendMode blendMode) {
		this.blendMode = blendMode;
	}
	
	protected void onUpdate() {
		for (int i = 0; i < particles.size(); i++) {
			Particle p = particles.get(i);
			p.update();
			if (p.isDead()) particles.remove(i--);
		}
	}
	protected void onRender(Graphics g) {
		blendMode.apply();
		for (Particle p : particles) p.render(g);
		BlendMode.Normal.apply();
	}
}