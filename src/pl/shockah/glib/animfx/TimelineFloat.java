package pl.shockah.glib.animfx;

public class TimelineFloat extends Timeline<Float,Fx<Float>> {
	public TimelineFloat() {super();}
	public TimelineFloat(Interpolate method) {super(method);}
	
	public void add(float step, double time) {
		add(new Fx<>(step,time));
	}
	public void add(Fx<Float> fx) {
		fxs.add(fx);
		if (maxTime < fx.time) maxTime = fx.time;
	}
}