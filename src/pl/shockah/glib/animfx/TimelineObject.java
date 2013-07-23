package pl.shockah.glib.animfx;

import java.util.List;
import pl.shockah.SortedLinkedList;

public class TimelineObject<T extends IInterpolatable<T>> implements ITimeline<T> {
	protected final Interpolate method;
	protected final List<FxObject<T>> fxs = new SortedLinkedList<>();
	protected double maxTime = 0;
	
	public TimelineObject() {this(Interpolate.Smoothstep);}
	public TimelineObject(Interpolate method) {
		this.method = method;
	}
	
	public void add(T step, double time) {
		add(new FxObject<>(step,time));
	}
	public void add(FxObject<T> fx) {
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