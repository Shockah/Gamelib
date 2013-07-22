package pl.shockah.json;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class JSONList<T> implements Iterable<T> {
	protected List<T> list = Collections.synchronizedList(new ArrayList<T>());
	
	public T get(int index) {
		return list.get(index);
	}
	public boolean isNull(int index) {
		return list.get(index) == null;
	}
	public boolean isEmpty() {
		return list.isEmpty();
	}
	public int size() {
		return list.size();
	}
	
	public boolean holdsStrings() {
		if (isEmpty()) return true;
		for (T t : list) if (!(t instanceof String)) return false;
		return true;
	}
	public boolean holdsBooleans() {
		if (isEmpty()) return true;
		for (T t : list) if (!(t instanceof Boolean)) return false;
		return true;
	}
	public boolean holdsNumbers() {
		if (isEmpty()) return true;
		for (T t : list) if (!(t instanceof Number)) return false;
		return true;
	}
	public boolean holdsInts() {return holdsNumbers();}
	public boolean holdsLongs() {return holdsNumbers();}
	public boolean holdsFloat() {return holdsNumbers();}
	public boolean holdsDouble() {return holdsNumbers();}
	public boolean holdsJSONObjects() {
		if (isEmpty()) return true;
		for (T t : list) if (!(t instanceof JSONObject)) return false;
		return true;
	}
	public boolean holdsJSONLists() {
		if (isEmpty()) return true;
		for (T t : list) if (!(t instanceof JSONList)) return false;
		return true;
	}
	
	public String getString(int index) {
		Object o = get(index);
		if (o instanceof String) return (String)o;
		throw new IllegalArgumentException("Index '"+index+"' doesn't hold a string.");
	}
	public boolean getBoolean(int index) {
		Object o = get(index);
		if (o instanceof Boolean) return (Boolean)o;
		throw new IllegalArgumentException("Index '"+index+"' doesn't hold a boolean.");
	}
	public Number getNumber(int index) {
		Object o = get(index);
		if (o instanceof Number) return (Number)o;
		throw new IllegalArgumentException("Index '"+index+"' doesn't hold a number.");
	}
	public int getInt(int index) {return getNumber(index).intValue();}
	public long getLong(int index) {return getNumber(index).longValue();}
	public float getFloat(int index) {return getNumber(index).floatValue();}
	public double getDouble(int index) {return getNumber(index).doubleValue();}
	
	public JSONObject getJSONObject(int index) {
		Object o = get(index);
		if (o instanceof JSONObject) return (JSONObject)o;
		throw new IllegalArgumentException("Index '"+index+"' doesn't hold a JSONObject.");
	}
	public JSONList<?> getJSONList(int index) {
		Object o = get(index);
		if (o instanceof JSONList<?>) return (JSONList<?>)o;
		throw new IllegalArgumentException("Index '"+index+"' doesn't hold a JSONList.");
	}
	
	public void put(Object o) {put(size(),o);}
	public void put(String s) {put(size(),s);}
	public void put(boolean b) {put(size(),b);}
	public void put(Number n) {put(size(),n);}
	public void put(JSONObject j) {put(size(),j);}
	public void put(JSONList<?> j) {put(size(),j);}
	@SuppressWarnings("unchecked") public void put(int index, Object o) {if (index >= size()) list.add((T)o); else list.add(index,(T)o);}
	@SuppressWarnings("unchecked") public void put(int index, String s) {if (index >= size()) list.add((T)s); else list.add(index,(T)s);}
	@SuppressWarnings("unchecked") public void put(int index, boolean b) {if (index >= size()) list.add((T)(Boolean)b); else list.add(index,(T)(Boolean)b);}
	@SuppressWarnings("unchecked") public void put(int index, Number n) {if (index >= size()) list.add((T)n); else list.add(index,(T)n);}
	@SuppressWarnings("unchecked") public void put(int index, JSONObject j) {if (index >= size()) list.add((T)j); else list.add(index,(T)j);}
	@SuppressWarnings("unchecked") public void put(int index, JSONList<?> j) {if (index >= size()) list.add((T)j); else list.add(index,(T)j);}
	
	public T remove(int index) {
		return list.remove(index);
	}
	
	@SuppressWarnings("unchecked") public JSONList<String> asStringList() {return (JSONList<String>)this;}
	@SuppressWarnings("unchecked") public JSONList<Boolean> asBooleanList() {return (JSONList<Boolean>)this;}
	@SuppressWarnings("unchecked") public JSONList<Integer> asIntList() {return (JSONList<Integer>)this;}
	@SuppressWarnings("unchecked") public JSONList<Long> asLongList() {return (JSONList<Long>)this;}
	@SuppressWarnings("unchecked") public JSONList<Float> asFloatList() {return (JSONList<Float>)this;}
	@SuppressWarnings("unchecked") public JSONList<Double> asDoubleList() {return (JSONList<Double>)this;}
	@SuppressWarnings("unchecked") public JSONList<JSONObject> asJSONObjectList() {return (JSONList<JSONObject>)this;}
	@SuppressWarnings("unchecked") public JSONList<JSONList<?>> asJSONListList() {return (JSONList<JSONList<?>>)this;}
	
	public Iterator<T> iterator() {
		return new LinkedList<>(list).iterator();
	}
}