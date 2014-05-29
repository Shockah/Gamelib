package pl.shockah.glib.logic.component;

import pl.shockah.glib.gl.Graphics;

public class CRenderable extends Component {
	public CRenderable(Entity entity) {
		super(entity);
	}
	
	public final void render(Graphics g) {
		onRender(g);
	}
	protected void onRender(Graphics g) {}
}