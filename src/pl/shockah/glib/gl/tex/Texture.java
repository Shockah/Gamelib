package pl.shockah.glib.gl.tex;

import static org.lwjgl.opengl.GL11.*;
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
	public static Texture load(File file) throws FileNotFoundException, IOException {
		String[] spl = file.getName().split("\\.");
		return load(new FileInputStream(file),spl[spl.length-1].toUpperCase());
	}
	public static Texture load(String internalPath) throws IOException {
		String[] spl = internalPath.split("\\.");
		return load(Texture.class.getClassLoader().getResourceAsStream(internalPath),spl[spl.length-1].toUpperCase());
	}
	public static Texture load(InputStream is, String format) throws IOException {
		TextureLoader tl = TextureLoader.getTextureLoader(format);
		if (tl == null) throw new RuntimeException("Unsupported format: "+format+".");
		return tl.load(is);
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