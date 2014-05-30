package pl.shockah.glib.logic.component;

import pl.shockah.glib.geom.vector.Vector2d;

public class CPosition extends Component {
	public Vector2d pos = new Vector2d();
	
	public CPosition(Entity entity) {
		super(entity);
	}
	public CPosition(Entity entity, Vector2d pos) {
		this(entity,pos.x,pos.y);
	}
	public CPosition(Entity entity, double x, double y) {
		super(entity);
		pos.set(x,y);
	}
}