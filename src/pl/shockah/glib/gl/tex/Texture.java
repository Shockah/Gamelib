package pl.shockah.glib.gl.tex;

import static org.lwjgl.opengl.GL11.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import pl.shockah.glib.geom.vector.Vector2i;
import pl.shockah.glib.gl.EResizeFilter;
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
			for (TextureLoader tl : TextureLoader.getAll()) try {
				Texture tex = tl.load(is);
				if (tex != null) return tex;
			} catch (Exception e) {}
			throw new UnsupportedOperationException("Unsupported image format.");
		} else {
			TextureLoader tl = TextureLoader.getTextureLoader(format);
			if (tl == null) throw new UnsupportedOperationException("Unsupported image format: "+format+".");
			return tl.load(is);
		}
	}
	
	public static Vector2i get2Fold(Vector2i v) {
		return get2Fold(v.x,v.y);
	}
	public static Vector2i get2Fold(int x, int y) {
		int xx = 2, yy = 2;
		while (x > xx) xx <<= 1;
		while (y > yy) yy <<= 1;
		return new Vector2i(xx,yy);
	}
	
	private final int texId;
	private final int width, height, widthFold, heightFold;
	private boolean disposed = false;
	
	public Texture(int texId, int width, int height) {
		this.texId = texId;
		this.width = width;
		this.height = height;
		Vector2i fold = get2Fold(width,height);
		widthFold = fold.x;
		heightFold = fold.y;
	}
	
	public boolean equals(Object other) {
		if (!(other instanceof Texture)) return false;
		Texture tex = (Texture)other;
		return tex.texId == texId;
	}
	
	public int getID() {
		if (disposed) throw new IllegalStateException("Texture already disposed");
		return texId;
	}
	
	public Vector2i getSize() {
		if (disposed) throw new IllegalStateException("Texture already disposed");
		return new Vector2i(getWidth(),getHeight());
	}
	public int getWidth() {
		if (disposed) throw new IllegalStateException("Texture already disposed");
		return width;
	}
	public int getHeight() {
		if (disposed) throw new IllegalStateException("Texture already disposed");
		return height;
	}
	public int getWidthFold() {
		if (disposed) throw new IllegalStateException("Texture already disposed");
		return widthFold;
	}
	public int getHeightFold() {
		if (disposed) throw new IllegalStateException("Texture already disposed");
		return heightFold;
	}
	
	public void setResizeFilter(EResizeFilter resizeFilter) {
		if (disposed) throw new IllegalStateException("Texture already disposed");
		GL.bind(this);
		resizeFilter.set();
	}
	
	public boolean disposed() {
		return disposed;
	}
	public void dispose() {
		glDeleteTextures(texId);
		disposed = true;
	}
}