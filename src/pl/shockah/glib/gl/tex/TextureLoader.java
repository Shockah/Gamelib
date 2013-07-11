package pl.shockah.glib.gl.tex;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class TextureLoader {
	private static final List<TextureLoader> loaders = new LinkedList<>();
	
	static {
		try {
			Class.forName("de.matthiasmann.twl.utils.PNGDecoder");
			loaders.add(new PNGTextureLoader());
		} catch (Exception e) {
			loaders.add(new ImageIOTextureLoader("PNG"));
		}
		loaders.add(new ImageIOTextureLoader("GIF","JPG","JPEG","BMP","WBMP"));
		try {
			Class.forName("org.apache.batik.transcoder.image.PNGTranscoder");
			loaders.add(new SVGTextureLoader());
		} catch (Exception e) {}
	}
	
	public static TextureLoader getTextureLoader(String format) {
		for (TextureLoader tl : loaders) if (tl.isSupported(format)) return tl;
		return null;
	}
	public static List<TextureLoader> getAll() {
		return Collections.unmodifiableList(loaders);
	}
	
	private final List<String> formats;
	protected final Map<String,Object> settings = new HashMap<>();
	
	public TextureLoader(String... formats) {
		this.formats = new LinkedList<>(Arrays.asList(formats));
	}
	
	public boolean isSupported(String format) {
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
}