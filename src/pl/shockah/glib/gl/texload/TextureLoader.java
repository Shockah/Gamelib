package pl.shockah.glib.gl.texload;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import pl.shockah.glib.gl.Texture;

public abstract class TextureLoader {
	private static final List<TextureLoader> loaders = new LinkedList<>();
	
	static {
		try {
			Class.forName("de.matthiasmann.twl.utils.PNGDecoder");
			loaders.add(new PNGTextureLoader());
		} catch (Exception e) {}
	}
	
	public static TextureLoader getTextureLoader(String format) {
		for (TextureLoader tl : loaders) if (tl.isSupported(format)) return tl;
		return null;
	}
	
	private final List<String> formats;
	
	public TextureLoader(String... formats) {
		this.formats = new LinkedList<>(Arrays.asList(formats));
	}
	
	public boolean isSupported(String format) {
		return formats.contains(format);
	}
	
	public abstract Texture load(InputStream is) throws IOException;
}