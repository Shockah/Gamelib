package tests;

import org.lwjgl.input.Keyboard;
import pl.shockah.glib.Gamelib;
import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;
import pl.shockah.glib.input.Key;
import pl.shockah.glib.logic.actor.ActorRenderable;
import pl.shockah.glib.state.State;

public class KeyboardInputTest2 extends State {
	public static void main(String[] args) {
		State test = new KeyboardInputTest2();
		Gamelib.start(test,test.getClass().getName(),new Gamelib.Modules(false));
	}
	
	protected void onCreate() {
		new ActorRenderable(){
			Vector2d oldPos;
			Key
				kL = new Key(Keyboard.KEY_LEFT,Keyboard.KEY_A),
				kR = new Key(Keyboard.KEY_RIGHT,Keyboard.KEY_D),
				kU = new Key(Keyboard.KEY_UP,Keyboard.KEY_W),
				kD = new Key(Keyboard.KEY_DOWN,Keyboard.KEY_S);
			
			protected void onUpdate() {
				oldPos = pos;
				double xx = ((kR.down() ? 1 : 0)-(kL.down() ? 1 : 0))*4;
				double yy = ((kD.down() ? 1 : 0)-(kU.down() ? 1 : 0))*4;
				pos = pos.add(xx,yy);
			}
			
			protected void onRender(Graphics g) {
				g.setColor(oldPos.equals(pos) ? Color.GreenYellow : Color.GreenYellow.inverse());
				g.draw(new Rectangle(pos.x,pos.y,48,48));
			}
		}.create();
	}
}