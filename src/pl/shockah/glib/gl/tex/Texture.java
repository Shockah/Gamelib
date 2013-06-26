package pl.shockah.glib.gl.tex;

import static org.lwjgl.opengl.GL11.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import pl.shockah.glib.IBoundable;

public class Texture implements IBoundable {
	private static int bound = 0;
	
	public static Texture get(Path path) throws FileNotFoundException, IOException {
		return get(path.toFile());
	}
	public static Texture get(File file) throws FileNotFoundException, IOException {
		String[] spl = file.getName().split("\\.");
		return get(new FileInputStream(file),spl[spl.length-1]);
	}
	public static Texture get(InputStream is, String format) throws IOException {
		TextureLoader tl = TextureLoader.getTextureLoader(format);
		if (tl == null) throw new RuntimeException("Unsupported format: "+format+".");
		return tl.load(is);
	}
	
	public static void unbind() {
		if (bound == 0) return;
		glBindTexture(GL_TEXTURE_2D,0);
		bound = 0;
	}
	
	private final int texId;
	private final int width, height;
	
	public Texture(int texId, int width, int height) {
		this.texId = texId;
		this.width = width;
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	
	public void bindMe() {
		if (bound == texId) return;
		glBindTexture(GL_TEXTURE_2D,texId);
		bound = texId;
	}
	public void unbindMe() {
		if (bound != texId) return;
		unbind();
	}
}