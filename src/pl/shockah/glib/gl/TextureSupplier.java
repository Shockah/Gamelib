package pl.shockah.glib.gl;

import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.gl.tex.Texture;

public abstract class TextureSupplier {
	private final Texture tex;
	
	public TextureSupplier(Texture tex) {
		this.tex = tex;
	}
	
	public Texture getTexture() {
		return tex;
	}
	
	public int getWidth() {
		return tex.getWidth();
	}
	public int getHeight() {
		return tex.getHeight();
	}
	
	public Rectangle getTextureRect() {
		return new Rectangle(0,0,getWidth(),getHeight());
	}
	
	protected void preDraw(Graphics g) {}
	protected void postDraw(Graphics g) {}
}