package pl.shockah.glib.gl.tex;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;
import pl.shockah.glib.LoadableProcessor;
import pl.shockah.glib.geom.Rectangle;
import pl.shockah.json.JSONList;
import pl.shockah.json.JSONObject;

public class Atlas extends TextureSupplier {
	protected final Image img;
	protected final Map<Object,Image> map = new HashMap<>();
	
	public Atlas(Texture tex) {
		super(tex);
		img = new Image(tex);
	}
	
	public int count() {return map.size();}
	
	public Image image(Object key) {
		return map.get(key);
	}
	
	public void fillFromJSON(JSONObject j) {
		if (j.size() == 1 && j.contains("atlas") && j.get("atlas") instanceof JSONList) {
			JSONList<JSONObject> list = j.getList("atlas").ofObjects();
			for (int i = 0; i < list.size(); i++) {
				JSONObject j2 = list.get(i);
				put(j2.get("key"),readRectangleFromJSON(j2.getObject("rect")));
			}
		} else {
			for (String key : j.keys()) put(key,readRectangleFromJSON(j.getObject(key)));
		}
	}
	protected Rectangle readRectangleFromJSON(JSONObject j) {
		int x = -1, y = -1, w = 0, h = 0;
		
		if (j.contains("x")) x = j.getInt("x");
		if (j.contains("y")) y = j.getInt("y");
		
		if (j.contains("w")) w = j.getInt("w");
		if (j.contains("width")) w = j.getInt("width");
		if (j.contains("h")) h = j.getInt("h");
		if (j.contains("height")) h = j.getInt("height");
		if (j.contains("size")) w = h = j.getInt("size");
		
		if (x < 0 || y < 0 || w <= 0 || h <= 0) throw new IllegalArgumentException();
		return new Rectangle(x,y,w,h);
	}
	
	public void put(Object key, Rectangle rect) {
		map.put(key,img.part(rect));
	}
	
	@Target(ElementType.FIELD) @Retention(RetentionPolicy.RUNTIME) public static @interface Loadable {
		public String path() default "assets/spritesheets/<field.name>.png";
		public LoadableProcessor.AssetType type() default LoadableProcessor.AssetType.Internal;
		public boolean toPremultiplied() default false;
	}
}