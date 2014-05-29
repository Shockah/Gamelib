package pl.shockah.glib.animfx;

public class TimelineObject<T extends IEasable<T>> extends Timeline<T,FxObject<T>> {
	public TimelineObject() {super();}
	public TimelineObject(Ease method) {super(method);}
	
	public void add(T step, double time) {
		add(new FxObject<>(step,time));
	}
	public void add(T step, double time, Ease method) {
		add(new FxObject<>(step,time,method));
	}
	public void add(FxObject<T> fx) {
		fxs.add(fx);
		if (maxTime < fx.time) maxTime = fx.time;
	}
	
	public void copyFirst(double time) {
		add(new FxObject<>(fxs.get(0).step,time));
	}
	public void copyLast(double time) {
		add(new FxObject<>(fxs.get(fxs.size()-1).step,time));
	}
	public void copyFirst(double time, Ease method) {
		add(new FxObject<>(fxs.get(0).step,time,method));
	}
	public void copyLast(double time, Ease method) {
		add(new FxObject<>(fxs.get(fxs.size()-1).step,time,method));
	}
}