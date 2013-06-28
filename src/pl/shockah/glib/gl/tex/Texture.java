package pl.shockah.glib.gl.tex;

import static org.lwjgl.opengl.GL11.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import pl.shockah.glib.IBoundable;
import pl.shockah.glib.geom.vector.Vector2i;

public class Texture implements IBoundable {
	private static int bound = 0;
	
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
	
	public static void bindNone() {
		if (bound == 0) return;
		glDisable(GL_TEXTURE_2D);
		bound = 0;
	}
	
	public static enum EResizeFilter {
		Nearest(GL_NEAREST), Linear(GL_LINEAR);
		
		private final int gl;
		
		EResizeFilter(int gl) {
			this.gl = gl;
		}
		
		public int getGLConst() {
			return gl;
		}
		public void set() {
			glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,gl);
			glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,gl);
		}
	}
	
	private final int texId;
	private final int width, height;
	
	public Texture(int texId, int width, int height) {
		this.texId = texId;
		this.width = width;
		this.height = height;
	}
	
	public Vector2i getSize() {
		return new Vector2i(getWidth(),getHeight());
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	
	public void setResizeFilter(EResizeFilter resizeFilter) {
		bind();
		resizeFilter.set();
		unbind();
	}
	
	public void bind() {
		if (bound == texId) return;
		glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D,texId);
		bound = texId;
	}
	public void unbind() {
		if (bound != texId) return;
		bindNone();
	}
}