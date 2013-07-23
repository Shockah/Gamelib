package pl.shockah.glib.animfx;

import java.util.List;
import pl.shockah.SortedLinkedList;

@SuppressWarnings("unchecked") public class Timeline<T> implements ITimeline<T> {
	protected final Interpolate method;
	protected final List<Fx<T>> fxs = new SortedLinkedList<>();
	protected double maxTime = 0;
	
	public Timeline() {this(Interpolate.Smoothstep);}
	public Timeline(Interpolate method) {
		this.method = method;
	}
	
	public void add(int step, double time) {
		add(new Fx<>((T)new Integer(step),time));
	}
	public void add(float step, double time) {
		add(new Fx<>((T)new Float(step),time));
	}
	public void add(double step, double time) {
		add(new Fx<>((T)new Double(step),time));
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
		if (fxs.size() == 1) return fxs.get(0).getState(null,0,method);
		
		Fx<T> prev = null;
		for (Fx<T> fx : fxs) {
			if (fx.time >= time) return prev == null ? fx.step : prev.getState(fx,(time-prev.time)/(fx.time-prev.time),method);
			prev = fx;
		}
		return prev.getState(null,0,method);
	}
	
	public double getMaxTime() {return maxTime;}
}