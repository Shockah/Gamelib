package pl.shockah.glib.al;

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

public abstract class AudioLoader {
	private static final List<AudioLoader> loaders = new LinkedList<>();
	
	static {
		loaders.add(new WAVAudioLoader());
		loaders.add(new AIFFAudioLoader());
		/*try {
			Class.forName("org.apache.batik.transcoder.image.PNGTranscoder");
			loaders.add(new SVGTextureLoader());
		} catch (Exception e) {}*/
	}
	
	public static AudioLoader audioLoader(String format) {
		for (AudioLoader al : loaders) if (al.supported(format)) return al;
		return null;
	}
	public static List<AudioLoader> all() {
		return Collections.unmodifiableList(loaders);
	}
	
	public static void setOptionGlobal(String option, Object value) {
		for (AudioLoader al : loaders) al.setOption(option,value);
	}
	public static void clearOptionsGlobal() {
		for (AudioLoader al : loaders) al.clearOptions();
	}
	
	private final List<String> formats;
	protected final Map<String,Object> settings = new HashMap<>();
	
	public AudioLoader(String... formats) {
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
	
	public abstract Audio load(InputStream is) throws IOException;
	
	@Target(ElementType.FIELD) @Retention(RetentionPolicy.RUNTIME) public static @interface IntOptions {
		public IntOption[] value();
	}
	@Target(ElementType.FIELD) @Retention(RetentionPolicy.RUNTIME) public static @interface IntOption {
		public String option();
		public int value();
	}
}