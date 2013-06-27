package tests;

import java.io.IOException;
import pl.shockah.glib.Gamelib;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.Image;
import pl.shockah.glib.gl.tex.Texture;
import pl.shockah.glib.logic.standard.EntityRenderable;
import pl.shockah.glib.logic.standard.Game;
import pl.shockah.glib.room.Room;

public class ImageTest extends Room {
	public static void main(String[] args) {
		ImageTest test = new ImageTest();
		Gamelib.make(new Game()).start(test,test.getClass().getName());
	}
	
	Image image;
	
	protected void onSetup() {
		fps = 60;
	}
	
	protected void onCreate() {
		try {
			image = new Image(Texture.load("assets/image1.png"));
			image.rotation.center();
		} catch (IOException e) {e.printStackTrace();}
		
		new EntityRenderable(){
			protected void onTick() {
				image.rotation.angle -= 2;
			}
			protected void onRender(Graphics g) {
				g.draw(image,50,50);
			}
		}.create();
	}
}