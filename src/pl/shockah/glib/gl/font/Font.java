package pl.shockah.glib.gl.font;

import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import pl.shockah.glib.geom.vector.Vector2d;
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
	
	public abstract int getWidth(String text);
	public abstract int getHeight();
	
	public abstract void draw(Graphics g, Vector2d v, CharSequence text);
	public abstract void draw(Graphics g, double x, double y, CharSequence text);
}