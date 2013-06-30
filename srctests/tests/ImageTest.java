package tests;

import java.io.IOException;
import pl.shockah.glib.Gamelib;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.Image;
import pl.shockah.glib.gl.tex.Texture;
import pl.shockah.glib.logic.standard.EntityRenderable;
import pl.shockah.glib.logic.standard.GameStandard;
import pl.shockah.glib.state.State;

public class ImageTest extends State {
	public static void main(String[] args) {
		ImageTest test = new ImageTest();
		Gamelib.start(new GameStandard(),test,test.getClass().getName());
	}
	
	Image image1, image2;
	
	protected void onSetup() {
		fps = 60;
	}
	
	protected void onCreate() {
		try {
			image1 = new Image(Texture.load("assets/imagepng1.png"));
			image1.rotation.center();
			
			image2 = new Image(Texture.load("assets/imagejpg1.jpg"));
			image2.rotation.center();
		} catch (IOException e) {e.printStackTrace();}
		
		new EntityRenderable(){
			protected void onTick() {
				image1.rotation.angle -= 2;
				image2.rotation.angle += 1;
			}
			protected void onRender(Graphics g) {
				g.draw(image1,50,50);
				g.draw(image2,384,192);
			}
		}.create();
	}
}