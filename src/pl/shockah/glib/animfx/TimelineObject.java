package pl.shockah.glib.animfx;

public class TimelineObject<T extends IInterpolatable<T>> extends Timeline<T,FxObject<T>> {
	public TimelineObject() {super();}
	public TimelineObject(Interpolate method) {super(method);}
	
	public void add(T step, double time) {
		add(new FxObject<>(step,time));
	}
	public void add(T step, double time, Interpolate method) {
		add(new FxObject<>(step,time,method));
	}
	public void add(FxObject<T> fx) {
		fxs.add(fx);
		if (maxTime < fx.time) maxTime = fx.time;
	}
}