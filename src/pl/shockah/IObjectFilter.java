package pl.shockah;

public interface IObjectFilter<T> {
	public boolean matches(T t);
}