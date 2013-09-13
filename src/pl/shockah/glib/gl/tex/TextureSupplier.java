package pl.shockah.glib.gl.tex;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex2d;
import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.geom.vector.Vector2f;
import pl.shockah.glib.geom.vector.Vector2i;
import pl.shockah.glib.gl.GL;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;

public abstract class TextureSupplier implements ITextureSupplier {
	private final Texture tex;
	public Vector2d offset = new Vector2d();
	public Vector2d scale = new Vector2d(1d,1d);
	
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
	public int getTextureWidthFold() {
		return tex.getWidthFold();
	}
	public int getTextureHeightFold() {
		return tex.getHeightFold();
	}
	public Rectangle getTextureRect() {
		return new Rectangle(0,0,getTextureWidth(),getTextureHeight());
	}
	
	public Vector2i getSize() {return getTextureSize();}
	public double getWidth() {return getTextureRect().size.x*scale.x;}
	public double getHeight() {return getTextureRect().size.y*scale.y;}
	
	public void drawTexture(Graphics g) {drawTexture(g,0,0);}
	public void drawTexture(Graphics g, Vector2d v) {drawTexture(g,v.x,v.y);}
	public void drawTexture(Graphics g, Vector2f v) {drawTexture(g,v.x,v.y);}
	public void drawTexture(Graphics g, Vector2i v) {drawTexture(g,v.x,v.y);}
	public void drawTexture(Graphics g, double x, double y) {
		if (disposed()) throw new IllegalStateException("Texture already disposed");
		g.preDraw();
		
		GL.bind(getTexture());
		if (offset.x != 0 || offset.y != 0) glTranslated(-offset.x*scale.x,-offset.y*scale.y,0);
		glTranslated(x,y,0);
		
		preDraw(g);
		glBegin(GL_QUADS);
		Rectangle texRect = getTextureRect();
		internalDrawImage(0,0,texRect.size.x*scale.x,texRect.size.y*scale.y,texRect.pos.x/getTextureWidthFold(),texRect.pos.y/getTextureHeightFold(),texRect.size.x/getTextureWidthFold(),texRect.size.y/getTextureHeightFold());
		glEnd();
		postDraw(g);
		
		glTranslated(-x,-y,0);
		if (offset.x != 0 || offset.y != 0) glTranslated(offset.x*scale.x,offset.y*scale.y,0);
	}
	
	public void drawTextureMulticolor(Graphics g, Color cTopLeft, Color cTopRight, Color cBottomLeft, Color cBottomRight) {drawTextureMulticolor(g,0,0,cTopLeft,cTopRight,cBottomLeft,cBottomRight);}
	public void drawTextureMulticolor(Graphics g, Vector2d v, Color cTopLeft, Color cTopRight, Color cBottomLeft, Color cBottomRight) {drawTextureMulticolor(g,v.x,v.y,cTopLeft,cTopRight,cBottomLeft,cBottomRight);}
	public void drawTextureMulticolor(Graphics g, Vector2f v, Color cTopLeft, Color cTopRight, Color cBottomLeft, Color cBottomRight) {drawTextureMulticolor(g,v.x,v.y,cTopLeft,cTopRight,cBottomLeft,cBottomRight);}
	public void drawTextureMulticolor(Graphics g, Vector2i v, Color cTopLeft, Color cTopRight, Color cBottomLeft, Color cBottomRight) {drawTextureMulticolor(g,v.x,v.y,cTopLeft,cTopRight,cBottomLeft,cBottomRight);}
	public void drawTextureMulticolor(Graphics g, double x, double y, Color cTopLeft, Color cTopRight, Color cBottomLeft, Color cBottomRight) {
		if (disposed()) throw new IllegalStateException("Texture already disposed");
		g.preDraw();
		
		GL.bind(getTexture());
		if (offset.x != 0 || offset.y != 0) glTranslated(-offset.x*scale.x,-offset.y*scale.y,0);
		glTranslated(x,y,0);
		
		preDraw(g);
		glBegin(GL_QUADS);
		Rectangle texRect = getTextureRect();
		internalDrawImageMulticolor(0,0,texRect.size.x*scale.x,texRect.size.y*scale.y,texRect.pos.x/getTextureWidthFold(),texRect.pos.y/getTextureHeightFold(),texRect.size.x/getTextureWidthFold(),texRect.size.y/getTextureHeightFold(),cTopLeft,cTopRight,cBottomLeft,cBottomRight);
		glEnd();
		postDraw(g);
		
		glTranslated(-x,-y,0);
		if (offset.x != 0 || offset.y != 0) glTranslated(offset.x*scale.x,offset.y*scale.y,0);
	}
	
	private void internalDrawImage(double x, double y, double w, double h, double tx, double ty, double tw, double th) {
		glTexCoord2d(tx,ty); glVertex2d(x,y);
		glTexCoord2d(tx,ty+th); glVertex2d(x,y+h);
		glTexCoord2d(tx+tw,ty+th); glVertex2d(x+w,y+h);
		glTexCoord2d(tx+tw,ty); glVertex2d(x+w,y);
	}
	private void internalDrawImageMulticolor(double x, double y, double w, double h, double tx, double ty, double tw, double th, Color cTopLeft, Color cTopRight, Color cBottomLeft, Color cBottomRight) {
		GL.color4f(cTopLeft); glTexCoord2d(tx,ty); glVertex2d(x,y);
		GL.color4f(cBottomLeft); glTexCoord2d(tx,ty+th); glVertex2d(x,y+h);
		GL.color4f(cBottomRight); glTexCoord2d(tx+tw,ty+th); glVertex2d(x+w,y+h);
		GL.color4f(cTopRight); glTexCoord2d(tx+tw,ty); glVertex2d(x+w,y);
	}
	
	protected void preDraw(Graphics g) {}
	protected void postDraw(Graphics g) {}
	
	public boolean disposed() {
		return tex.disposed();
	}
	public void dispose() {
		tex.dispose();
	}
}