package pl.shockah.glib.animfx;

public class TimelineInt extends Timeline<Integer,Fx<Integer>> {
	public TimelineInt() {super();}
	public TimelineInt(Interpolate method) {super(method);}
	
	public void add(int step, double time) {
		add(new Fx<>(step,time));
	}
	public void add(int step, double time, Interpolate method) {
		add(new Fx<>(step,time,method));
	}
	public void add(Fx<Integer> fx) {
		fxs.add(fx);
		if (maxTime < fx.time) maxTime = fx.time;
	}
}