package pl.shockah.glib.gl;

import static org.lwjgl.opengl.GL11.*;
import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.gl.color.Color;

public class Graphics {
	private static boolean init = false;
	private static Color color = null;
	
	protected void init() {
		if (init) return;
		init = true;
		
		if (color == null) setColor(Color.White);
	}
	
	public void setColor(Color color) {
		if (Graphics.color != null && !Graphics.color.equals(color)) color.unbindMe();
		Graphics.color = color;
		color.bindMe();
	}
	
	public void draw(Rectangle rect) {draw(rect,0,0);}
	public void draw(Rectangle rect, boolean filled) {draw(rect,filled,0,0);}
	public void draw(Rectangle rect, double x, double y) {draw(rect,true,0,0);}
	public void draw(Rectangle rect, boolean filled, double x, double y) {
		init();
		glTranslated(x,y,0);
		
		if (filled) {
			glBegin(GL_QUADS);
			glVertex2d(rect.pos.x+x,rect.pos.y+y);
			glVertex2d(rect.pos.x+rect.size.x+x,rect.pos.y+y);
			glVertex2d(rect.pos.x+rect.size.x+x,rect.pos.y+rect.size.y+y);
			glVertex2d(rect.pos.x+x,rect.pos.y+rect.size.y+y);
			glEnd();
		} else {
			throw new UnsupportedOperationException();
		}
		
		glTranslatef((float)-x,(float)-y,0);
	}
	
	public void draw(TextureSupplier ts) {draw(ts,0,0);}
	public void draw(TextureSupplier ts, double x, double y) {
		init();
		ts.getTexture().bindMe();
		glTranslated(x,y,0);
		
		ts.preDraw(this);
		glBegin(GL_QUADS);
		Rectangle texRect = ts.getTextureRect();
		internalDrawImage(0,0,(int)texRect.size.x,(int)texRect.size.y,(int)texRect.pos.x,(int)texRect.pos.y,(int)texRect.size.x,(int)texRect.size.y);
		glEnd();
		ts.postDraw(this);
		
		glTranslated(-x,-y,0);
		ts.getTexture().unbindMe();
	}
	
	private void internalDrawImage(float x, float y, float w, float h, float tx, float ty, float tw, float th) {
		glTexCoord2f(tx,ty);
		glVertex2f(x,y);
		glTexCoord2f(tx,ty+th);
		glVertex2f(x,y+h);
		glTexCoord2f(tx+tw,ty+th);
		glVertex2f(x+w,y+h);
		glTexCoord2f(tx+tw,ty);
		glVertex2f(x+w,y);
	}
}