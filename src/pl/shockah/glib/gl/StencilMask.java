package pl.shockah.glib.gl;

import java.util.Arrays;

public class StencilMask {
	public final Graphics g;
	public final Stencil stencil;
	protected Phase phase = Phase.Pre;
	
	public StencilMask(Graphics g, Stencil stencil) {
		this.g = g;
		this.stencil = stencil;
	}
	
	public void proceed() {
		if (phase == Phase.Post) return;
		phase = Phase.values()[Arrays.asList(Phase.values()).indexOf(phase)+1];
		
		switch (phase) {
			case Stencil: stencil.phaseStencil(g); break;
			case Test: stencil.phaseTest(g); break;
			case Post: stencil.phasePost(g); break;
			default: break;
		}
	}
	
	protected enum Phase {
		Pre(), Stencil(), Test(), Post();
	}
}