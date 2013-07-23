package pl.shockah.glib.animfx;

public class Fx<T extends IInterpolatable<T>> implements Comparable<Fx<T>> {
	public final T step;
	public final double time;
	
	public Fx(T step, double time) {
		this.step = step;
		this.time = time;
	}

	public final int compareTo(Fx<T> fx) {
		if (time == fx.time) return 0;
		return time < fx.time ? -1 : 1;
	}
}