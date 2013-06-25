package pl.shockah.glib.gl.texload;

import java.io.IOException;
import java.io.InputStream;
import pl.shockah.glib.gl.Texture;

public abstract class TextureLoader {
	public abstract Texture load(InputStream is) throws IOException;
}