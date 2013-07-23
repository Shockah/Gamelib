package pl.shockah.glib.animfx;

public interface ITimeline<T> {
	public T getState(Animation anim);
	public T getState(double time);
	public double getMaxTime();
}