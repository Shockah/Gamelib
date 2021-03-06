package pl.shockah.glib.state;

import pl.shockah.glib.geom.vector.Vector2;
import pl.shockah.glib.geom.vector.Vector2i;

public final class View {
	public Vector2i portPos, portSize, pos, size;
	
	public View() {
		this(new Vector2i(),new Vector2i(800,600),new Vector2i(),new Vector2i(800,600));
	}
	public View(int w, int h) {
		this(new Vector2i(),new Vector2i(w,h),new Vector2i(),new Vector2i(w,h));
	}
	public View(Vector2 size) {
		this(new Vector2i(),size,new Vector2i(),size);
	}
	public View(State state) {
		this(new Vector2i(),state.preferredSize(),new Vector2i(),state.preferredSize());
	}
	public View(Vector2 portPos, Vector2 portSize, Vector2 pos, Vector2 size) {
		this.portPos = portPos.toInt();
		this.portSize = portSize.toInt();
		this.pos = pos.toInt();
		this.size = size.toInt();
	}
}