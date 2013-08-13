package pl.shockah.glib.animfx;

public class TimelineFloat extends Timeline<Float,Fx<Float>> {
	public TimelineFloat() {super();}
	public TimelineFloat(Interpolate method) {super(method);}
	
	public void add(float step, double time) {
		add(new Fx<>(step,time));
	}
	public void add(float step, double time, Interpolate method) {
		add(new Fx<>(step,time,method));
	}
	public void add(Fx<Float> fx) {
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