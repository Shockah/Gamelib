package pl.shockah.glib.gl;

public class GraphicsSurface extends Graphics {
	private final Surface sur;
	
	public GraphicsSurface(Surface sur) {
		this.sur = sur;
	}
	
	public void preDraw() {
		GL.bind(sur);
	}
}