package pl.shockah.glib.animfx;

public class Fx<T> implements Comparable<Fx<T>> {
	public final T step;
	public final double time;
	public final Interpolate method;
	
	public Fx(T step, double time) {
		this(step,time,null);
	}
	public Fx(T step, double time, Interpolate method) {
		this.step = step;
		this.time = time;
		this.method = method;
	}

	public final int compareTo(Fx<T> fx) {
		if (time == fx.time) return 0;
		return time < fx.time ? -1 : 1;
	}
	
	@SuppressWarnings("unchecked") public T getState(Fx<?> fx, double d, Interpolate method) {
		if (fx == null) fx = this;
		if (step instanceof Integer) return (T)new Integer(method.interpolate((Integer)step,(Integer)fx.step,d));
		if (step instanceof Float) return (T)new Float(method.interpolate((Float)step,(Float)fx.step,d));
		if (step instanceof Double) return (T)new Double(method.interpolate((Double)step,(Double)fx.step,d));
		throw new UnsupportedOperationException();
	}
	
	public Interpolate getMethod(Timeline<T,?> timeline) {
		return method == null ? timeline.method : method;
	}
}