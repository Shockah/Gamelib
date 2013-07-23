package pl.shockah.glib.animfx;

@SuppressWarnings("unchecked") public class FxObject<T extends IInterpolatable<T>> extends Fx<T> {
	public FxObject(IInterpolatable<T> step, double time) {
		super((T)step,time);
	}
	
	public T getState(Fx<?> fx, double d, Interpolate method) {
		if (fx == null) fx = this;
		return step.interpolate(((Fx<T>)fx).step,d,method);
	}
}
