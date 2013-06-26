package pl.shockah.glib.gl;

import static org.lwjgl.opengl.GL11.*;
import pl.shockah.glib.gl.color.Color;

public class Graphics {
	public void draw(TextureSupplier ts) {draw(ts,0,0);}
	public void draw(TextureSupplier ts, Color color) {draw(ts,0,0,color);}
	public void draw(TextureSupplier ts, float x, float y) {draw(ts,x,y,Color.White);}
	public void draw(TextureSupplier ts, float x, float y, Color color) {
		if (color == null) color = Color.White;
		color.bindMe();
		
		ts.getTexture().bindMe();
		glTranslatef(x,y,0);
		
		ts.preDraw(this);
		glBegin(GL_QUADS);
		internalDrawImage(0,0,ts.getWidth(),ts.getHeight(),0,0,ts.getWidth(),ts.getHeight());
		glEnd();
		ts.postDraw(this);
		
		glTranslatef(-x,-y,0);
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