package pl.shockah.glib.state;

import pl.shockah.glib.geom.vector.Vector2i;

public final class View {
	public Vector2i portPos, portSize, pos, size;
	
	public View() {
		this(new Vector2i(),new Vector2i(800,600),new Vector2i(),new Vector2i(800,600));
	}
	public View(Vector2i size) {
		this(new Vector2i(),size,new Vector2i(),size);
	}
	public View(Vector2i portPos, Vector2i portSize, Vector2i pos, Vector2i size) {
		this.portPos = portPos;
		this.portSize = portSize;
		this.pos = pos;
		this.size = size;
	}
}