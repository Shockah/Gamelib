package pl.shockah.glib.gl.tex;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class TextureLoader {
	private static final List<TextureLoader> loaders = new LinkedList<>();
	
	static {
		loaders.add(new ImageIOTextureLoader("PNG","GIF","JPG","JPEG","BMP","WBMP"));
		try {
			Class.forName("org.apache.batik.transcoder.image.PNGTranscoder");
			loaders.add(new SVGTextureLoader());
		} catch (Exception e) {}
	}
	
	public static TextureLoader textureLoader(String format) {
		for (TextureLoader tl : loaders) if (tl.supported(format)) return tl;
		return null;
	}
	public static List<TextureLoader> all() {
		return Collections.unmodifiableList(loaders);
	}
	
	public static void setOptionGlobal(String option, Object value) {
		for (TextureLoader tl : loaders) tl.setOption(option,value);
	}
	public static void clearOptionsGlobal() {
		for (TextureLoader tl : loaders) tl.clearOptions();
	}
	
	private final List<String> formats;
	protected final Map<String,Object> settings = new HashMap<>();
	
	public TextureLoader(String... formats) {
		this.formats = new LinkedList<>(Arrays.asList(formats));
	}
	
	public boolean supported(String format) {
		return formats.contains(format);
	}
	
	public void setOption(String option, Object value) {
		if (value == null) {
			if (settings.containsKey(option)) settings.remove(option);
		} else {
			settings.put(option,value);
		}
	}
	public void clearOptions() {
		settings.clear();
	}
	
	public abstract Texture load(InputStream is) throws IOException;
	
	@Target(ElementType.FIELD) @Retention(RetentionPolicy.RUNTIME) public static @interface IntOptions {
		public IntOption[] value();
	}
	@Target(ElementType.FIELD) @Retention(RetentionPolicy.RUNTIME) public static @interface IntOption {
		public String option();
		public int value();
	}
}