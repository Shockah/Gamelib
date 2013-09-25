package pl.shockah.glib.gl.tex;

import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.geom.vector.IVector2;
import pl.shockah.glib.geom.vector.Vector2i;
import pl.shockah.glib.gl.Graphics;

public interface ITextureSupplier {
	public Texture getTexture();
	public boolean disposed();
	public void dispose();
	
	public Vector2i getTextureSize();
	public int getTextureWidth();
	public int getTextureHeight();
	public Rectangle getTextureRect();
	
	public void drawTexture(Graphics g);
	public void drawTexture(Graphics g, IVector2 v);
	public void drawTexture(Graphics g, double x, double y);
}