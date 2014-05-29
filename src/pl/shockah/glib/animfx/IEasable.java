package pl.shockah.glib.animfx;

public interface IEasable<T> {
	public T ease(T t, double d);
	public T ease(T t, double d, Ease method);
	public T copyMe();
}