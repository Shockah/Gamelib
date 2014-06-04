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
	
	public T state(double time) {
		return state(time,false);
	}
	public T state(double time, boolean looped) {
		if (fxs.isEmpty()) throw new IllegalStateException("Blank timeline");
		if (fxs.size() == 1) return fxs.get(0).state(null,0,fxs.get(0).method(this));
		
		if (looped && time < fxs.get(0).time) return fxs.get(fxs.size()-1).state(fxs.get(0),(maxTime()-time)/(maxTime()-maxTime+fxs.get(0).time),fxs.get(fxs.size()-1).method(this));
		Fx<T> prev = null;
		for (Fx<T> fx : fxs) {
			if (fx.time >= time) return prev == null ? fx.step : prev.state(fx,(time-prev.time)/(fx.time-prev.time),prev.method(this));
			prev = fx;
		}
		if (looped && time > maxTime) return prev.state(fxs.get(0),(maxTime()-time)/(maxTime()-maxTime+fxs.get(0).time),prev.method(this));
		return prev.state(null,0,prev.method(this));
	}
	public T state(Animation anim) {
		if (fxs.isEmpty()) throw new IllegalStateException("Blank timeline");
		if (fxs.size() == 1) return fxs.get(0).state(null,0,fxs.get(0).method(this));
		
		if (anim.looped() && anim.time() < fxs.get(0).time) return fxs.get(fxs.size()-1).state(fxs.get(0),(anim.maxTime()-anim.time())/(anim.maxTime()-maxTime+fxs.get(0).time),fxs.get(fxs.size()-1).method(this));
		Fx<T> prev = null;
		for (Fx<T> fx : fxs) {
			if (fx.time >= anim.time()) return prev == null ? fx.step : prev.state(fx,(anim.time()-prev.time)/(fx.time-prev.time),prev.method(this));
			prev = fx;
		}
		if (anim.looped() && anim.time() > maxTime) return prev.state(fxs.get(0),(anim.maxTime()-anim.time())/(anim.maxTime()-maxTime+fxs.get(0).time),prev.method(this));
		return prev.state(null,0,prev.method(this));
	}
	
	public abstract void copyFirst(double time);
	public abstract void copyLast(double time);
	public abstract void copyFirst(double time, Ease method);
	public abstract void copyLast(double time, Ease method);
	
	public double maxTime() {
		return maxTime;
	}
}