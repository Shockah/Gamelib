package pl.shockah.glib.gl;

import pl.shockah.glib.gl.color.Color;

public class Graphics {
	public void draw(Image image) {draw(image,0,0);}
	public void draw(Image image, Color color) {draw(image,0,0,color);}
	public void draw(Image image, float x, float y) {draw(image,x,y,Color.White);}
	public void draw(Image image, float x, float y, Color color) {
		image.tex.bind();
		image.tex.unbind();
	}
}