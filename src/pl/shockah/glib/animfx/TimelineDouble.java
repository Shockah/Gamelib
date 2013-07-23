package pl.shockah.glib.animfx;

public class TimelineDouble extends Timeline<Double,Fx<Double>> {
	public TimelineDouble() {super();}
	public TimelineDouble(Interpolate method) {super(method);}
	
	public void add(double step, double time) {
		add(new Fx<>(step,time));
	}
	public void add(Fx<Double> fx) {
		fxs.add(fx);
		if (maxTime < fx.time) maxTime = fx.time;
	}
}