package pl.shockah.glib.gl;

import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.geom.vector.Vector2i;
import pl.shockah.glib.gl.tex.Texture;

public interface ITextureSupplier {
	public Texture getTexture();
	
	public Vector2i getTextureSize();
	public int getTextureWidth();
	public int getTextureHeight();
	public Rectangle getTextureRect();
	
	public void drawTexture(Graphics g);
	public void drawTexture(Graphics g, Vector2d v);
	public void drawTexture(Graphics g, double x, double y);
}