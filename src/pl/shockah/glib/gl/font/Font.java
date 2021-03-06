package pl.shockah.glib.gl.font;

import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.file.Path;
import pl.shockah.glib.LoadableProcessor;
import pl.shockah.glib.geom.vector.Vector2;
import pl.shockah.glib.gl.Graphics;

public abstract class Font {
	public static java.awt.Font registerNew(Path path) throws FileNotFoundException, FontFormatException, IOException {
		return registerNew(new FileInputStream(path.toFile()));
	}
	public static java.awt.Font registerNew(File file) throws FileNotFoundException, FontFormatException, IOException {
		return registerNew(new FileInputStream(file));
	}
	public static java.awt.Font registerNew(String internalPath) throws FontFormatException, IOException {
		return registerNew(new BufferedInputStream(Font.class.getClassLoader().getResourceAsStream(internalPath)));
	}
	public static java.awt.Font registerNew(InputStream is) throws FontFormatException, IOException {
		java.awt.Font font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,is);
		GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
		return font;
	}
	
	public abstract int width(String text);
	public abstract int height();
	
	public abstract void draw(Graphics g, Vector2 v, CharSequence text);
	public abstract void draw(Graphics g, double x, double y, CharSequence text);
	
	@Target(ElementType.METHOD) @Retention(RetentionPolicy.RUNTIME) public static @interface Loadables {
		public Loadable[] value();
	}
	@Target(ElementType.METHOD) @Retention(RetentionPolicy.RUNTIME) public static @interface Loadable {
		public String path();
		public LoadableProcessor.AssetType type() default LoadableProcessor.AssetType.Internal;
	}
}