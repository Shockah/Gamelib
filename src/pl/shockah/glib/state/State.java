package pl.shockah.glib.state;

import java.util.LinkedList;
import java.util.List;
import pl.shockah.glib.Gamelib;
import pl.shockah.glib.geom.vector.Vector2i;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.state.transitions.Transition;

public class State {
	protected static State current;
	protected static TransitionState transitionState;
	
	public static State get() {
		return current;
	}
	public static void change(State state) {change(state,null,null);}
	public static void change(State state, Transition transition) {change(state,transition,transition);}
	public static void change(State state, Transition out, Transition in) {
		if (transitionState != null) return;
		if (out == null && in == null) {
			current = state;
			transitionState = null;
			return;
		}
		if (out == null) {
			current = state;
			current.create();
			transitionState = new TransitionState(state,out,in,true);
			in.init(true);
			return;
		}
		transitionState = new TransitionState(state,out,in,false);
		out.init(true);
	}
	
	public void updateTransition() {
		if (transitionState == null) return;
		if (transitionState.update()) {
			if (transitionState.in) {
				transitionState = null;
			} else {
				transitionState.in = true;
				current = transitionState.state;
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
			xx = view.portPos.x+view.portSize.x;
			yy = view.portPos.y+view.portSize.y;
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
	
	public final void preUpdate() {
		onPreUpdate();
	}
	protected void onPreUpdate() {}
	
	public final void postRender(Graphics g) {
		onPostRender(g);
	}
	protected void onPostRender(Graphics g) {}
}