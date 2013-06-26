package pl.shockah.glib.gl;

import static org.lwjgl.opengl.GL11.*;
import pl.shockah.glib.gl.color.Color;

public class Graphics {
	public void draw(Image image) {draw(image,0,0);}
	public void draw(Image image, Color color) {draw(image,0,0,color);}
	public void draw(Image image, float x, float y) {draw(image,x,y,Color.White);}
	public void draw(Image image, float x, float y, Color color) {
		if (color == null) color = Color.White;
		color.bindMe();
		
		image.tex.bindMe();
		glTranslatef(x,y,0);
		
		if (image.rotation != 0) {
			glTranslatef(image.centerOfRotation.x,image.centerOfRotation.y,0);
			glRotatef(image.rotation,0f,0f,1f);
			glTranslatef(-image.centerOfRotation.x,-image.centerOfRotation.y,0);
		}
		
		glBegin(GL_QUADS);
		internalDrawImage(0,0,image.getWidth(),image.getHeight(),0,0,image.getWidth(),image.getHeight());
		glEnd();
		
		if (image.rotation != 0) {
			glTranslatef(image.centerOfRotation.x,image.centerOfRotation.y,0);
			glRotatef(-image.rotation,0f,0f,1f);
			glTranslatef(-image.centerOfRotation.x,-image.centerOfRotation.y,0);
		}
		
		glTranslatef(-x,-y,0);
		image.tex.unbindMe();
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