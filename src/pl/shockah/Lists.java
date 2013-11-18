package pl.shockah;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public final class Lists {
	public static <T> boolean remove(List<T> list, IObjectFilter<T> filter) {
		int oldSize = list.size();
		Iterator<T> it = list.iterator();
		while (it.hasNext()) if (filter.matches(it.next())) it.remove();
		return list.size() != oldSize;
	}
	public static <T> List<T> find(List<T> list, IObjectFilter<T> filter) {
		List<T> ret = new LinkedList<>();
		for (T t : list) if (filter.matches(t)) ret.add(t);
		return ret;
	}
	
	@SuppressWarnings("unchecked") public static <K,V> List<V> sublist(List<K> list, Class<V> cls) {
		List<V> ret = new LinkedList<>();
		for (Object o : list) if (o != null && cls.isAssignableFrom(o.getClass())) ret.add((V)o);
		return ret;
	}
}