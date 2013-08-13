package pl.shockah.glib.animfx;

public class TimelineDouble extends Timeline<Double,Fx<Double>> {
	public TimelineDouble() {super();}
	public TimelineDouble(Interpolate method) {super(method);}
	
	public void add(double step, double time) {
		add(new Fx<>(step,time));
	}
	public void add(double step, double time, Interpolate method) {
		add(new Fx<>(step,time,method));
	}
	public void add(Fx<Double> fx) {
		fxs.add(fx);
		if (maxTime < fx.time) maxTime = fx.time;
	}
	
	public void copyFirst(double time) {
		add(new Fx<>(fxs.get(0).step,time));
	}
	public void copyLast(double time) {
		add(new Fx<>(fxs.get(fxs.size()-1).step,time));
	}
	public void copyFirst(double time, Interpolate method) {
		add(new Fx<>(fxs.get(0).step,time,method));
	}
	public void copyLast(double time, Interpolate method) {
		add(new Fx<>(fxs.get(fxs.size()-1).step,time,method));
	}
}