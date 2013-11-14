package tests;

import java.io.IOException;
import pl.shockah.glib.Gamelib;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.tex.Image;
import pl.shockah.glib.gl.tex.Texture;
import pl.shockah.glib.gl.tex.TextureLoader;
import pl.shockah.glib.logic.EntityRenderable;
import pl.shockah.glib.state.State;

public class ImageSVGTest extends State {
	public static void main(String[] args) {
		State test = new ImageSVGTest();
		Gamelib.start(test,test.getClass().getName(),new Gamelib.Modules(false));
	}
	
	Image image;
	
	protected void onSetup() {
		fps = 60;
	}
	
	protected void onCreate() {
		try {
			TextureLoader.getTextureLoader("SVG").setOption("width",384);
			image = new Image(Texture.load("assets/imagesvg1.svg"));
		} catch (IOException e) {e.printStackTrace();}
		
		new EntityRenderable(){
			protected void onRender(Graphics g) {
				g.draw(image);
			}
		}.create();
	}
}