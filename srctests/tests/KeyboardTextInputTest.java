package tests;

import pl.shockah.glib.Gamelib;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.font.Font;
import pl.shockah.glib.gl.font.TrueTypeFont;
import pl.shockah.glib.logic.standard.EntityRenderable;
import pl.shockah.glib.logic.standard.GameStandard;
import pl.shockah.glib.room.Room;

public class KeyboardTextInputTest extends Room {
	public static void main(String[] args) {
		KeyboardTextInputTest test = new KeyboardTextInputTest();
		Gamelib.start(new GameStandard(),test,test.getClass().getName());
	}
	
	Font font;
	
	protected void onCreate() {
		try {
			font = new TrueTypeFont("Calibri",32);
		} catch (Exception e) {e.printStackTrace();}
		
		new EntityRenderable(){
			protected void onRender(Graphics g) {
				font.draw(g,16,16,Gamelib.keyboard.text.toString());
			}
		}.create();
	}
}