package tests;

import pl.shockah.glib.Gamelib;
import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;
import pl.shockah.glib.input.InputKeyboard;
import pl.shockah.glib.logic.standard.EntityRenderable;
import pl.shockah.glib.logic.standard.GameStandard;
import pl.shockah.glib.room.Room;

public class KeyboardInputTest extends Room {
	public static void main(String[] args) {
		KeyboardInputTest test = new KeyboardInputTest();
		Gamelib.start(new GameStandard(),test,test.getClass().getName());
	}
	
	protected void onCreate() {
		new EntityRenderable(){
			protected void onTick() {
				InputKeyboard kb = Gamelib.keyboard;
				double xx = ((kb.isDown(InputKeyboard.ARROW_RIGHT) ? 1 : 0)-(kb.isDown(InputKeyboard.ARROW_LEFT) ? 1 : 0))*4;
				double yy = ((kb.isDown(InputKeyboard.ARROW_DOWN) ? 1 : 0)-(kb.isDown(InputKeyboard.ARROW_UP) ? 1 : 0))*4;
				pos = pos.add(xx,yy);
			}
			
			protected void onRender(Graphics g) {
				g.setColor(Color.GreenYellow);
				g.draw(new Rectangle(pos.x,pos.y,48,48));
			}
		}.create();
	}
}