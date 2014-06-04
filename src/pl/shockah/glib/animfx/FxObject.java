package pl.shockah.glib.animfx;

@SuppressWarnings("unchecked") public class FxObject<T extends IEasable<T>> extends Fx<T> {
	public FxObject(IEasable<T> step, double time) {
		super((T)step,time);
	}
	public FxObject(IEasable<T> step, double time, Ease method) {
		super((T)step,time,method);
	}
	
	public T state(Fx<?> fx, double d, Ease method) {
		if (fx == null) fx = this;
		return step.ease(((Fx<T>)fx).step,d,method);
	}
}