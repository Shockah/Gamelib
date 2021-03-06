package tests;

import pl.shockah.glib.Gamelib;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.font.Font;
import pl.shockah.glib.gl.font.TrueTypeFont;
import pl.shockah.glib.logic.actor.ActorRenderable;
import pl.shockah.glib.state.State;

public class TrueTypeFontTest extends State {
	public static void main(String[] args) {
		State test = new TrueTypeFontTest();
		Gamelib.start(test,test.getClass().getName(),new Gamelib.Modules(false));
	}
	
	Font font1, font2;
	
	protected void onSetup() {
		fps = 10;
	}
	
	protected void onCreate() {
		try {
			font1 = new TrueTypeFont("Calibri",32);
			
			Font.registerNew("assets/fontttf1.ttf");
			font2 = new TrueTypeFont("Andy Std",24);
			//could use too: font2 = new TrueTypeFont(Font.registerNew("assets/fontttf1.ttf"),24);
		} catch (Exception e) {e.printStackTrace();}
		
		new ActorRenderable(){
			protected void onRender(Graphics g) {
				font1.draw(g,16,16,"Does it work?");
				font2.draw(g,32,64,"It works!");
			}
		}.create();
	}
}