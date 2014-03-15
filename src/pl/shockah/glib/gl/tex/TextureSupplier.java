package pl.shockah.glib.gl.tex;

import static org.lwjgl.opengl.GL11.*;
import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.geom.vector.Vector2;
import pl.shockah.glib.geom.vector.Vector2d;
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
	public Vector2i getTextureSizeFold() {
		return tex.getSizeFold();
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
	
	public Vector2i getSize() {return getTextureRect().size.toInt();}
	public double getWidth() {return getTextureRect().size.x;}
	public double getHeight() {return getTextureRect().size.y;}
	public double getWidthScaled() {return getWidth()*scale.x;}
	public double getHeightScaled() {return getHeight()*scale.y;}
	
	public void drawTexture(Graphics g) {drawTexture(g,0,0);}
	public void drawTexture(Graphics g, Vector2 v) {drawTexture(g,v.Xd(),v.Yd());}
	public void drawTexture(Graphics g, double x, double y) {
		if (disposed()) throw new IllegalStateException("Texture already disposed");
		g.preDraw();
		
		GL.bind(getTexture());
		if (offset.x != 0 || offset.y != 0) glTranslated(-offset.x*scale.x,-offset.y*scale.y,0);
		if (x != 0 || y != 0) glTranslated(x,y,0);
		
		preDraw(g);
		glBegin(GL_QUADS);
			Rectangle texRect = getTextureRect();
			internalDrawImage(
					0,0,texRect.size.x*scale.x,texRect.size.y*scale.y,
					texRect.pos.x/getTextureWidthFold(),texRect.pos.y/getTextureHeightFold(),texRect.size.x/getTextureWidthFold(),texRect.size.y/getTextureHeightFold()
			);
		glEnd();
		postDraw(g);
		
		if (x != 0 || y != 0) glTranslated(-x,-y,0);
		if (offset.x != 0 || offset.y != 0) glTranslated(offset.x*scale.x,offset.y*scale.y,0);
	}
	
	public void drawTextureMulticolor(Graphics g, Color cTopLeft, Color cTopRight, Color cBottomLeft, Color cBottomRight) {drawTextureMulticolor(g,0,0,cTopLeft,cTopRight,cBottomLeft,cBottomRight);}
	public void drawTextureMulticolor(Graphics g, Vector2 v, Color cTopLeft, Color cTopRight, Color cBottomLeft, Color cBottomRight) {drawTextureMulticolor(g,v.Xd(),v.Yd(),cTopLeft,cTopRight,cBottomLeft,cBottomRight);}
	public void drawTextureMulticolor(Graphics g, double x, double y, Color cTopLeft, Color cTopRight, Color cBottomLeft, Color cBottomRight) {
		if (disposed()) throw new IllegalStateException("Texture already disposed");
		g.preDraw();
		
		GL.bind(getTexture());
		if (offset.x != 0 || offset.y != 0) glTranslated(-offset.x*scale.x,-offset.y*scale.y,0);
		if (x != 0 || y != 0) glTranslated(x,y,0);
		
		preDraw(g);
		glBegin(GL_QUADS);
			Rectangle texRect = getTextureRect();
			internalDrawImageMulticolor(
					0,0,texRect.size.x*scale.x,texRect.size.y*scale.y,
					texRect.pos.x/getTextureWidthFold(),texRect.pos.y/getTextureHeightFold(),texRect.size.x/getTextureWidthFold(),texRect.size.y/getTextureHeightFold(),
					cTopLeft,cTopRight,cBottomLeft,cBottomRight
			);
		glEnd();
		postDraw(g);
		
		if (x != 0 || y != 0) glTranslated(-x,-y,0);
		if (offset.x != 0 || offset.y != 0) glTranslated(offset.x*scale.x,offset.y*scale.y,0);
	}
	
	protected void internalDrawImage(double x, double y, double w, double h, double tx, double ty, double tw, double th) {
		GL.texCoordAndVertex2d(tx,ty,x,y);
		GL.texCoordAndVertex2d(tx,ty+th,x,y+h);
		GL.texCoordAndVertex2d(tx+tw,ty+th,x+w,y+h);
		GL.texCoordAndVertex2d(tx+tw,ty,x+w,y);
	}
	protected void internalDrawImageMulticolor(double x, double y, double w, double h, double tx, double ty, double tw, double th, Color cTopLeft, Color cTopRight, Color cBottomLeft, Color cBottomRight) {
		GL.colorAndTexCoordAndVertex2d(tx,ty,x,y,cTopLeft);
		GL.colorAndTexCoordAndVertex2d(tx,ty+th,x,y+h,cTopRight);
		GL.colorAndTexCoordAndVertex2d(tx+tw,ty+th,x+w,y+h,cBottomRight);
		GL.colorAndTexCoordAndVertex2d(tx+tw,ty,x+w,y,cBottomLeft);
	}
	
	protected void preDraw(Graphics g) {}
	protected void postDraw(Graphics g) {}
	
	protected void finalize() {dispose();}
	public boolean disposed() {return tex.disposed();}
	public void dispose() {tex.dispose();}
}