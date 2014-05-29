package pl.shockah.glib.logic;

public interface IFilter<T> {
	public boolean accept(T a);
}
