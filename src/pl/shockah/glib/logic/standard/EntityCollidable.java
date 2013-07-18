package pl.shockah.glib.logic.standard;

import java.util.List;
import pl.shockah.glib.geom.Shape;
import pl.shockah.glib.geom.vector.Vector2d;

public class EntityCollidable extends EntityRenderable {
	protected Shape shape = null;
	
	public void updateShapePos() {
		if (shape == null) return;
		shape.translateTo(pos);
	}
	
	public boolean collides(EntityCollidable e) {
		return collides(e,true);
	}
	private boolean collides(EntityCollidable e, boolean update) {
		if (e == null) return false;
		if (shape == null || e.shape == null) return false;
		if (update) updateShapePos();
		e.updateShapePos();
		return shape.collides(e.shape);
	}
	public boolean collidesAt(EntityCollidable e, Vector2d v) {
		return collidesAt(e,v.x,v.y);
	}
	public boolean collidesAt(EntityCollidable e, double x, double y) {
		if (shape == null) return false;
		Vector2d diff = shape.translateTo(x,y);
		boolean b = collides(e,false);
		updateShapePos();
		shape.translate(diff);
		return b;
	}
	
	public boolean collides(List<EntityCollidable> list) {
		return collides(list,true);
	}
	public boolean collides(List<EntityCollidable> list, boolean update) {
		if (shape == null) return false;
		if (list == null || list.isEmpty()) return false;
		for (EntityCollidable e : list) if (collides(e,update)) return true;
		return false;
	}
	public boolean collidesAt(List<EntityCollidable> list, Vector2d v) {
		return collidesAt(list,v.x,v.y);
	}
	public boolean collidesAt(List<EntityCollidable> list, double x, double y) {
		if (shape == null) return false;
		updateShapePos();
		Vector2d diff = shape.translateTo(x,y);
		boolean b = collides(list,false);
		shape.translate(diff);
		return b;
	}
}