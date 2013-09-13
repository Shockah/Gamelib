package pl.shockah.glib.gl;

import pl.shockah.glib.gl.color.Color;

public class GraphicsSurface extends Graphics {
	private final Surface sur;
	
	public GraphicsSurface(Surface sur) {
		this.sur = sur;
	}
	
	protected void onBind() {
		GL.bind(sur);
	}
	
	public void clear() {
		clear(Color.TransparentBlack);
	}
}