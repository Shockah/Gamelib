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
		glTexCoord2f(0,0);
		glVertex2f(0,0);
		glTexCoord2f(0,image.tex.getHeight());
		glVertex2f(0,image.tex.getHeight());
		glTexCoord2f(image.tex.getWidth(),image.tex.getHeight());
		glVertex2f(image.tex.getWidth(),image.tex.getHeight());
		glTexCoord2f(image.tex.getWidth(),0);
		glVertex2f(image.tex.getWidth(),0);
		glEnd();
		
		if (image.rotation != 0) {
			glTranslatef(image.centerOfRotation.x,image.centerOfRotation.y,0);
			glRotatef(-image.rotation,0f,0f,1f);
			glTranslatef(-image.centerOfRotation.x,-image.centerOfRotation.y,0);
		}
		
		glTranslatef(-x,-y,0);
		image.tex.unbindMe();
	}
}