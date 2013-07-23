package pl.shockah.glib.animfx;

import java.util.List;
import pl.shockah.SortedLinkedList;

public class Timeline<T extends IInterpolatable<T>> {
	protected final Interpolate method;
	protected final List<Fx<T>> fxs = new SortedLinkedList<>();
	protected double maxTime = 0;
	
	public Timeline() {this(Interpolate.Smoothstep);}
	public Timeline(Interpolate method) {
		this.method = method;
	}
	
	public void add(T step, double time) {
		add(new Fx<>(step,time));
	}
	public void add(Fx<T> fx) {
		fxs.add(fx);
		if (maxTime < fx.time) maxTime = fx.time;
	}
	
	public T getState(Animation anim) {
		return getState(anim.getTime());
	}
	public T getState(double time) {
		if (fxs.isEmpty()) throw new RuntimeException("Blank timeline");
		if (fxs.size() == 1) return fxs.get(0).step.copy();
		
		Fx<T> prev = null;
		for (Fx<T> fx : fxs) {
			if (fx.time >= time) return prev == null ? fx.step : getState(prev.step,fx.step,(time-prev.time)/(fx.time-prev.time));
			prev = fx;
		}
		return prev.step.copy();
	}
	public T getState(T step1, T step2, double d) {
		return step1.interpolate(step2,d,method);
	}
	
	public double getMaxTime() {return maxTime;}
}