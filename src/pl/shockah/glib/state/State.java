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
	public static boolean change(State state) {return change(state,null,null);}
	public static boolean change(State state, Transition transition) {return change(state,transition,transition);}
	public static boolean change(State state, Transition out, Transition in) {
		if (transitionState != null) return false;
		if (current == null) {
			current = state;
			return true;
		}
		transitionState = new TransitionState(state,out,in);
		transitionState.init();
		return true;
	}
	
	static void set(State state) {
		current = state;
		if (current != null) {
			Gamelib.game.reset();
			current.create();
		} else Gamelib.stop();
	}
	
	public final void updateTransition() {
		if (transitionState == null) return;
		if (transitionState.update()) transitionState = null;
	}
	public final void renderTransition(Graphics g) {
		if (transitionState == null) return;
		transitionState.render(g);
	}
	public final void renderTransitionPre(Graphics g) {
		if (transitionState == null) return;
		transitionState.preRender(g);
	}
	
	public final boolean shouldTransitionUpdate() {
		if (transitionState == null) return true;
		return transitionState.shouldUpdate();
	}
	public final boolean shouldTransitionRender(Graphics g) {
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
		if (!Gamelib.modules().graphics()) return;
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
	
	public final void preRender(Graphics g) {
		onPreRender(g);
	}
	protected void onPreRender(Graphics g) {}
	
	public final void preTransitionRender(Graphics g) {
		onPreTransitionRender(g);
	}
	protected void onPreTransitionRender(Graphics g) {}
	
	public final void postRender(Graphics g) {
		onPostRender(g);
	}
	protected void onPostRender(Graphics g) {}
}