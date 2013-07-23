package pl.shockah.glib.animfx;

public interface IInterpolatable<T> {
	public T interpolate(T t, double d, Interpolate method);
	public T copy();
}