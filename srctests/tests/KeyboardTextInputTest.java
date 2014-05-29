package tests;

import pl.shockah.glib.Gamelib;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.font.Font;
import pl.shockah.glib.gl.font.TrueTypeFont;
import pl.shockah.glib.input.KInput;
import pl.shockah.glib.logic.actor.ActorRenderable;
import pl.shockah.glib.state.State;

public class KeyboardTextInputTest extends State {
	public static void main(String[] args) {
		State test = new KeyboardTextInputTest();
		Gamelib.start(test,test.getClass().getName(),new Gamelib.Modules(false));
	}
	
	Font font;
	
	protected void onCreate() {
		try {
			font = new TrueTypeFont("Calibri",32);
		} catch (Exception e) {e.printStackTrace();}
		
		new ActorRenderable(){
			protected void onRender(Graphics g) {
				font.draw(g,16,16,KInput.text.toString());
			}
		}.create();
	}
}