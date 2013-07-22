package pl.shockah.json;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class JSONObject {
	protected Map<String,Object> map = Collections.synchronizedMap(new LinkedHashMap<String,Object>());
	
	public boolean contains(String key) {
		return map.containsKey(key);
	}
	public List<String> keys() {
		return new LinkedList<>(map.keySet());
	}
	public List<Map.Entry<String,Object>> entries() {
		return new LinkedList<>(map.entrySet());
	}
	
	public Object get(String key) {
		return map.get(key);
	}
	public boolean isNull(String key) {
		return contains(key) && map.get(key) == null;
	}
	public boolean isEmpty() {
		return map.isEmpty();
	}
	public int size() {
		return map.size();
	}
	
	public String getString(String key) {
		Object o = get(key);
		if (o instanceof String) return (String)o;
		throw new IllegalArgumentException("Key '"+key+"' doesn't hold a string.");
	}
	public boolean getBoolean(String key) {
		Object o = get(key);
		if (o instanceof Boolean) return (Boolean)o;
		throw new IllegalArgumentException("Key '"+key+"' doesn't hold a boolean.");
	}
	public Number getNumber(String key) {
		Object o = get(key);
		if (o instanceof Number) return (Number)o;
		throw new IllegalArgumentException("Key '"+key+"' doesn't hold a number.");
	}
	public int getInt(String key) {return getNumber(key).intValue();}
	public long getLong(String key) {return getNumber(key).longValue();}
	public float getFloat(String key) {return getNumber(key).floatValue();}
	public double getDouble(String key) {return getNumber(key).doubleValue();}
	
	public JSONObject getJSONObject(String key) {
		Object o = get(key);
		if (o instanceof JSONObject) return (JSONObject)o;
		throw new IllegalArgumentException("Key '"+key+"' doesn't hold a JSONObject.");
	}
	public JSONList<?> getJSONList(String key) {
		Object o = get(key);
		if (o instanceof JSONList<?>) return (JSONList<?>)o;
		throw new IllegalArgumentException("Key '"+key+"' doesn't hold a JSONList.");
	}
	
	public void put(String key, Object o) {map.put(key,o);}
	public void put(String key, String s) {map.put(key,s);}
	public void put(String key, boolean b) {map.put(key,b);}
	public void put(String key, Number n) {map.put(key,n);}
	public void put(String key, JSONObject j) {map.put(key,j);}
	public void put(String key, JSONList<?> j) {map.put(key,j);}
	
	public Object remove(String key) {
		return map.remove(key);
	}
}