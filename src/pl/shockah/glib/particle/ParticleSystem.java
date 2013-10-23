package pl.shockah.glib.particle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import pl.shockah.glib.gl.BlendMode;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.logic.standard.EntityRenderable;

public class ParticleSystem extends EntityRenderable {
	protected static BlendMode currentBlendMode = null;
	
	public static BlendMode getCurrentBlendMode() {
		return currentBlendMode;
	}
	
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
			if (p.dead) particles.remove(i--);
		}
	}
	protected void onRender(Graphics g) {
		blendMode.apply();
		currentBlendMode = blendMode;
		
		for (Particle p : particles) p.render(g);
		
		Graphics.getDefaultBlendMode().apply();
		currentBlendMode = null;
	}
	
	public void add(Particle pt) {
		particles.add(pt);
	}
	
	public boolean isEmpty() {
		return particles.isEmpty();
	}
	public int particleCount() {
		return particles.size();
	}
	public List<Particle> getAll() {
		return Collections.unmodifiableList(particles);
	}
}