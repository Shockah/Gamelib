package pl.shockah.glib.gl.tex;

import static org.lwjgl.opengl.GL11.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import pl.shockah.glib.geom.vector.Vector2;
import pl.shockah.glib.geom.vector.Vector2i;
import pl.shockah.glib.gl.GL;

public class Texture {
	public static Texture load(Path path) throws FileNotFoundException, IOException {
		return load(path.toFile());
	}
	public static Texture load(Path path, String format) throws FileNotFoundException, IOException {
		return load(path.toFile(),format);
	}
	public static Texture load(File file) throws FileNotFoundException, IOException {
		String[] spl = file.getName().split("\\.");
		return load(file,spl[spl.length-1].toUpperCase());
	}
	public static Texture load(File file, String format) throws FileNotFoundException, IOException {
		return load(new BufferedInputStream(new FileInputStream(file)),format);
	}
	public static Texture load(String internalPath) throws IOException {
		String[] spl = internalPath.split("\\.");
		return load(internalPath,spl[spl.length-1].toUpperCase());
	}
	public static Texture load(String internalPath, String format) throws IOException {
		return load(new BufferedInputStream(Texture.class.getClassLoader().getResourceAsStream(internalPath)),format);
	}
	public static Texture load(InputStream is, String format) throws IOException {
		if (format == null) {
			for (TextureLoader tl : TextureLoader.all()) try {
				Texture tex = tl.load(is);
				if (tex != null) return tex;
			} catch (Exception e) {}
			throw new UnsupportedOperationException("Unsupported image format.");
		} else {
			TextureLoader tl = TextureLoader.textureLoader(format);
			if (tl == null) throw new UnsupportedOperationException("Unsupported image format: "+format+".");
			return tl.load(is);
		}
	}
	
	public static Vector2i fold(Vector2 v) {
		return fold(v.Xi(),v.Yi());
	}
	public static Vector2i fold(int x, int y) {
		int xx = 2, yy = 2;
		while (x > xx) xx <<= 1;
		while (y > yy) yy <<= 1;
		return new Vector2i(xx,yy);
	}
	
	public final int id;
	private final int width, height, widthFold, heightFold;
	private boolean disposed = false;
	
	public Texture(int texId, int width, int height) {
		this.id = texId;
		this.width = width;
		this.height = height;
		Vector2i fold = fold(width,height);
		widthFold = fold.x;
		heightFold = fold.y;
	}
	
	public boolean equals(Object other) {
		if (!(other instanceof Texture)) return false;
		Texture tex = (Texture)other;
		return tex.id == id;
	}
	
	public Vector2i size() {
		return new Vector2i(width(),height());
	}
	public int width() {
		if (disposed) throw new IllegalStateException("Texture already disposed");
		return width;
	}
	public int height() {
		if (disposed) throw new IllegalStateException("Texture already disposed");
		return height;
	}
	public Vector2i sizeFold() {
		return new Vector2i(widthFold(),heightFold());
	}
	public int widthFold() {
		if (disposed) throw new IllegalStateException("Texture already disposed");
		return widthFold;
	}
	public int heightFold() {
		if (disposed) throw new IllegalStateException("Texture already disposed");
		return heightFold;
	}
	
	public void setResizeFilter(EResizeFilter resizeFilter) {
		if (disposed) throw new IllegalStateException("Texture already disposed");
		GL.bind(this);
		resizeFilter.set();
	}
	public void setEdgeFilter(EEdgeFilter edgeFilter) {
		if (disposed) throw new IllegalStateException("Texture already disposed");
		GL.bind(this);
		edgeFilter.set();
	}
	
	protected void finalize() {dispose();}
	public boolean disposed() {return disposed;}
	public void dispose() {
		glDeleteTextures(id);
		disposed = true;
	}
}