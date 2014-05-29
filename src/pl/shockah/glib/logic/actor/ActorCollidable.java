package pl.shockah.glib.logic.actor;

import java.util.LinkedList;
import java.util.List;
import pl.shockah.glib.geom.Shape;
import pl.shockah.glib.geom.vector.Vector2;
import pl.shockah.glib.geom.vector.Vector2d;

public class ActorCollidable extends ActorRenderable {
	protected Shape shape = null;
	
	public void updateShapePos() {
		if (shape == null) return;
		shape.translateTo(pos);
	}
	
	public boolean collides(Shape shape) {
		return collides(shape,true);
	}
	public boolean collides(Shape shape, boolean update) {
		if (shape == null || this.shape == null) return false;
		if (update) updateShapePos();
		return this.shape.collides(shape);
	}
	
	public boolean collides(ActorCollidable a) {
		return collides(a,true);
	}
	private boolean collides(ActorCollidable a, boolean update) {
		if (a == null) return false;
		if (a == this) return false;
		if (shape == null || a.shape == null) return false;
		if (update) updateShapePos();
		a.updateShapePos();
		return shape.collides(a.shape);
	}
	public boolean collidesAt(ActorCollidable a, Vector2 v) {
		return collidesAt(a,v.Xd(),v.Yd());
	}
	public boolean collidesAt(ActorCollidable a, double x, double y) {
		if (shape == null) return false;
		Vector2d diff = shape.translateTo(x,y);
		boolean b = collides(a,false);
		updateShapePos();
		shape.translate(diff);
		return b;
	}
	
	public boolean collides(List<ActorCollidable> list) {
		return collides(list,true);
	}
	public boolean collides(List<ActorCollidable> list, boolean update) {
		if (shape == null) return false;
		if (list == null || list.isEmpty()) return false;
		for (ActorCollidable a : list) if (collides(a,update)) return true;
		return false;
	}
	public boolean collidesAt(List<ActorCollidable> list, Vector2 v) {
		return collidesAt(list,v.Xd(),v.Yd());
	}
	public boolean collidesAt(List<ActorCollidable> list, double x, double y) {
		if (shape == null) return false;
		updateShapePos();
		Vector2d diff = shape.translateTo(x,y);
		boolean b = collides(list,false);
		shape.translate(diff);
		return b;
	}
	
	public List<ActorCollidable> collidesWith(List<ActorCollidable> list) {
		return collidesWith(list,true);
	}
	public List<ActorCollidable> collidesWith(List<ActorCollidable> list, boolean update) {
		List<ActorCollidable> ret = new LinkedList<>();
		if (shape == null) return ret;
		if (list == null || list.isEmpty()) return ret;
		for (ActorCollidable a : list) if (collides(a,update)) ret.add(a);
		return ret;
	}
	public List<ActorCollidable> collidesAtWith(List<ActorCollidable> list, Vector2 v) {
		return collidesAtWith(list,v.Xd(),v.Yd());
	}
	public List<ActorCollidable> collidesAtWith(List<ActorCollidable> list, double x, double y) {
		if (shape == null) return new LinkedList<>();
		updateShapePos();
		Vector2d diff = shape.translateTo(x,y);
		List<ActorCollidable> ret = collidesWith(list,false);
		shape.translate(diff);
		return ret;
	}
}