package pl.shockah.glib.gl;

import java.io.InputStream;

public class Texture {
	public static Texture get(InputStream is) {
		return null;
	}
	
	private final int texId;
	private final int width, height;
	
	public Texture(int texId, int width, int height) {
		this.texId = texId;
		this.width = width;
		this.height = height;
	}
}