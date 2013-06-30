package pl.shockah.glib.room;

import java.util.LinkedList;
import java.util.List;
import pl.shockah.glib.Gamelib;
import pl.shockah.glib.geom.vector.Vector2i;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.room.transitions.Transition;

public class Room {
	protected static Room current;
	protected static TransitionState transitionState;
	
	public static Room get() {
		return current;
	}
	public static void change(Room room) {change(room,null,null);}
	public static void change(Room room, Transition transition) {change(room,transition,transition);}
	public static void change(Room room, Transition out, Transition in) {
		if (out == null && in == null) {
			current = room;
			transitionState = null;
			return;
		}
		if (out == null) {
			current = room;
			current.create();
			transitionState = new TransitionState(room,out,in,true);
			in.init(true);
			return;
		}
		transitionState = new TransitionState(room,out,in,false);
		out.init(true);
	}
	
	public void updateTransition() {
		if (transitionState == null) return;
		if (transitionState.update()) {
			if (transitionState.in) {
				transitionState = null;
			} else {
				transitionState.in = true;
				current = transitionState.room;
				current.create();
				transitionState.tIn.init(true);
			}
		}
	}
	public void renderTransition(Graphics g) {
		if (transitionState == null) return;
		transitionState.render(g);
	}
	
	public boolean shouldTransitionUpdate() {
		if (transitionState == null) return true;
		return transitionState.shouldUpdate();
	}
	public boolean shouldTransitionRender(Graphics g) {
		if (transitionState == null) return true;
		return transitionState.shouldRender(g);
	}
	
	protected List<View> views = new LinkedList<>();
	protected int fps = 60;
	
	public final Vector2i getDisplaySize() {
		int w = 0, h = 0, xx, yy;
		for (View view : views) {
			xx = view.viewPortPos.x+view.viewPortSize.x;
			yy = view.viewPortPos.y+view.viewPortSize.y;
			if (xx > w) w = xx;
			if (yy > h) h = yy;
		}
		return new Vector2i(w,h);
	}
	
	public final int getFPS() {
		if (fps < 1) fps = 60;
		return fps;
	}
	
	public final void setup() {
		onSetup();
		if (views.isEmpty()) views.add(new View());
		Gamelib.setDisplayMode(getDisplaySize());
	}
	protected void onSetup() {}
	
	public final void create() {
		onCreate();
	}
	protected void onCreate() {}
}