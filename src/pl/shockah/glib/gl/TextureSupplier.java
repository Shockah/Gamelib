package pl.shockah.glib.gl;

import static org.lwjgl.opengl.GL11.*;
import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.geom.vector.Vector2i;
import pl.shockah.glib.gl.tex.Texture;

public abstract class TextureSupplier implements ITextureSupplier {
	private final Texture tex;
	
	public TextureSupplier(Texture tex) {
		this.tex = tex;
	}
	
	public Texture getTexture() {
		return tex;
	}
	
	public Vector2i getTextureSize() {
		return tex.getSize();
	}
	public int getTextureWidth() {
		return tex.getWidth();
	}
	public int getTextureHeight() {
		return tex.getHeight();
	}
	public Rectangle getTextureRect() {
		return new Rectangle(0,0,getTextureWidth(),getTextureHeight());
	}
	
	public Vector2i getSize() {return getTextureSize();}
	public int getWidth() {return getTextureWidth();}
	public int getHeight() {return getTextureHeight();}
	
	public void drawTexture(Graphics g) {drawTexture(g,0,0);}
	public void drawTexture(Graphics g, Vector2d v) {drawTexture(g,v.x,v.y);}
	public void drawTexture(Graphics g, double x, double y) {
		g.init();
		getTexture().bind();
		glTranslated(x,y,0);
		
		preDraw(g);
		glBegin(GL_QUADS);
		Rectangle texRect = getTextureRect();
		internalDrawImage(0,0,texRect.size.x,texRect.size.y,texRect.pos.x/getTextureWidth(),texRect.pos.y/getTextureHeight(),texRect.size.x/getTextureWidth(),texRect.size.y/getTextureHeight());
		glEnd();
		postDraw(g);
		
		glTranslated(-x,-y,0);
		getTexture().unbind();
	}
	private void internalDrawImage(double x, double y, double w, double h, double tx, double ty, double tw, double th) {
		glTexCoord2d(tx,ty);
		glVertex2d(x,y);
		glTexCoord2d(tx,ty+th);
		glVertex2d(x,y+h);
		glTexCoord2d(tx+tw,ty+th);
		glVertex2d(x+w,y+h);
		glTexCoord2d(tx+tw,ty);
		glVertex2d(x+w,y);
	}
	
	protected void preDraw(Graphics g) {}
	protected void postDraw(Graphics g) {}
}