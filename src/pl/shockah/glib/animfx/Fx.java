package pl.shockah.glib.animfx;

public class Fx<T> implements Comparable<Fx<T>> {
	public final T step;
	public final double time;
	public final Ease method;
	
	public Fx(T step, double time) {
		this(step,time,null);
	}
	public Fx(T step, double time, Ease method) {
		if (time < 0) throw new IllegalArgumentException("Time can't be < 0");
		this.step = step;
		this.time = time;
		this.method = method;
	}

	public final int compareTo(Fx<T> fx) {
		if (time == fx.time) return 0;
		return time < fx.time ? -1 : 1;
	}
	
	@SuppressWarnings("unchecked") public T state(Fx<?> fx, double d, Ease method) {
		if (fx == null) fx = this;
		if (step instanceof Integer) return (T)new Integer(method.ease((Integer)step,(Integer)fx.step,d));
		if (step instanceof Float) return (T)new Float(method.ease((Float)step,(Float)fx.step,d));
		if (step instanceof Double) return (T)new Double(method.ease((Double)step,(Double)fx.step,d));
		throw new UnsupportedOperationException();
	}
	
	public Ease method(Timeline<T,?> timeline) {
		return method == null ? timeline.method : method;
	}
}