package tests;

import org.lwjgl.input.Keyboard;
import pl.shockah.glib.Gamelib;
import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;
import pl.shockah.glib.input.KInput;
import pl.shockah.glib.logic.EntityRenderable;
import pl.shockah.glib.state.State;

public class KeyboardInputTest extends State {
	public static void main(String[] args) {
		State test = new KeyboardInputTest();
		Gamelib.start(test,test.getClass().getName(),new Gamelib.Modules(false));
	}
	
	protected void onCreate() {
		new EntityRenderable(){
			Vector2d oldPos;
			
			protected void onUpdate() {
				oldPos = pos;
				double xx = ((KInput.isDown(Keyboard.KEY_RIGHT) ? 1 : 0)-(KInput.isDown(Keyboard.KEY_LEFT) ? 1 : 0))*4;
				double yy = ((KInput.isDown(Keyboard.KEY_DOWN) ? 1 : 0)-(KInput.isDown(Keyboard.KEY_UP) ? 1 : 0))*4;
				pos = pos.add(xx,yy);
			}
			
			protected void onRender(Graphics g) {
				g.setColor(oldPos.equals(pos) ? Color.GreenYellow : Color.GreenYellow.inverse());
				g.draw(new Rectangle(pos.x,pos.y,48,48));
			}
		}.create();
	}
}