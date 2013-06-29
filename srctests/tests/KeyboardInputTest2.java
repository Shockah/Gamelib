package tests;

import org.lwjgl.input.Keyboard;
import pl.shockah.glib.Gamelib;
import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;
import pl.shockah.glib.input.KeyboardInput;
import pl.shockah.glib.input.Key;
import pl.shockah.glib.logic.standard.EntityRenderable;
import pl.shockah.glib.logic.standard.GameStandard;
import pl.shockah.glib.room.Room;

public class KeyboardInputTest2 extends Room {
	public static void main(String[] args) {
		KeyboardInputTest2 test = new KeyboardInputTest2();
		Gamelib.start(new GameStandard(),test,test.getClass().getName());
	}
	
	protected void onCreate() {
		new EntityRenderable(){
			Key
				kL = new Key(KeyboardInput.ARROW_LEFT,Keyboard.KEY_A),
				kR = new Key(KeyboardInput.ARROW_RIGHT,Keyboard.KEY_D),
				kU = new Key(KeyboardInput.ARROW_UP,Keyboard.KEY_W),
				kD = new Key(KeyboardInput.ARROW_DOWN,Keyboard.KEY_S);
			
			protected void onTick() {
				double xx = ((kR.down() ? 1 : 0)-(kL.down() ? 1 : 0))*4;
				double yy = ((kD.down() ? 1 : 0)-(kU.down() ? 1 : 0))*4;
				pos = pos.add(xx,yy);
			}
			
			protected void onRender(Graphics g) {
				g.setColor(Color.GreenYellow);
				g.draw(new Rectangle(pos.x,pos.y,48,48));
			}
		}.create();
	}
}