package pl.shockah.glib.animfx;

import java.util.List;
import pl.shockah.SortedLinkedList;

public abstract class Timeline<T,F extends Fx<T>> {
	protected final List<F> fxs = new SortedLinkedList<>();
	protected final Ease method;
	protected double maxTime = 0;
	
	Timeline() {this(Ease.Smoothstep.P1);}
	Timeline(Ease method) {
		this.method = method;
	}
	
	public T getState(double time) {
		return getState(time,false);
	}
	public T getState(double time, boolean looped) {
		if (fxs.isEmpty()) throw new IllegalStateException("Blank timeline");
		if (fxs.size() == 1) return fxs.get(0).getState(null,0,fxs.get(0).getMethod(this));
		
		if (looped && time < fxs.get(0).time) return fxs.get(fxs.size()-1).getState(fxs.get(0),(getMaxTime()-time)/(getMaxTime()-maxTime+fxs.get(0).time),fxs.get(fxs.size()-1).getMethod(this));
		Fx<T> prev = null;
		for (Fx<T> fx : fxs) {
			if (fx.time >= time) return prev == null ? fx.step : prev.getState(fx,(time-prev.time)/(fx.time-prev.time),prev.getMethod(this));
			prev = fx;
		}
		if (looped && time > maxTime) return prev.getState(fxs.get(0),(getMaxTime()-time)/(getMaxTime()-maxTime+fxs.get(0).time),prev.getMethod(this));
		return prev.getState(null,0,prev.getMethod(this));
	}
	public T getState(Animation anim) {
		if (fxs.isEmpty()) throw new IllegalStateException("Blank timeline");
		if (fxs.size() == 1) return fxs.get(0).getState(null,0,fxs.get(0).getMethod(this));
		
		if (anim.isLooped() && anim.getTime() < fxs.get(0).time) return fxs.get(fxs.size()-1).getState(fxs.get(0),(anim.getMaxTime()-anim.getTime())/(anim.getMaxTime()-maxTime+fxs.get(0).time),fxs.get(fxs.size()-1).getMethod(this));
		Fx<T> prev = null;
		for (Fx<T> fx : fxs) {
			if (fx.time >= anim.getTime()) return prev == null ? fx.step : prev.getState(fx,(anim.getTime()-prev.time)/(fx.time-prev.time),prev.getMethod(this));
			prev = fx;
		}
		if (anim.isLooped() && anim.getTime() > maxTime) return prev.getState(fxs.get(0),(anim.getMaxTime()-anim.getTime())/(anim.getMaxTime()-maxTime+fxs.get(0).time),prev.getMethod(this));
		return prev.getState(null,0,prev.getMethod(this));
	}
	
	public abstract void copyFirst(double time);
	public abstract void copyLast(double time);
	public abstract void copyFirst(double time, Ease method);
	public abstract void copyLast(double time, Ease method);
	
	public double getMaxTime() {
		return maxTime;
	}
}