package pl.shockah.glib.gl.tex;

import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.geom.vector.Vector2;
import pl.shockah.glib.geom.vector.Vector2i;
import pl.shockah.glib.gl.Graphics;

public interface ITextureSupplier {
	public Texture texture();
	public boolean disposed();
	public void dispose();
	
	public Vector2i textureSize();
	public int textureWidth();
	public int textureHeight();
	public Rectangle textureRect();
	
	public void drawTexture(Graphics g);
	public void drawTexture(Graphics g, Vector2 v);
	public void drawTexture(Graphics g, double x, double y);
}