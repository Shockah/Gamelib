package pl.shockah.glib.gl;

import static org.lwjgl.opengl.GL11.*;
import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.geom.vector.Vector2d;
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
	
	public void draw(Graphics g) {draw(g,0,0);}
	public void draw(Graphics g, Vector2d v) {draw(g,v.x,v.y);}
	public void draw(Graphics g, double x, double y) {
		g.init();
		getTexture().bindMe();
		glTranslated(x,y,0);
		
		preDraw(g);
		glBegin(GL_QUADS);
		Rectangle texRect = getTextureRect();
		internalDrawImage(0,0,texRect.size.x,texRect.size.y,texRect.pos.x/getWidth(),texRect.pos.y/getHeight(),texRect.size.x/getWidth(),texRect.size.y/getHeight());
		glEnd();
		postDraw(g);
		
		glTranslated(-x,-y,0);
		getTexture().unbindMe();
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