package tests;

import pl.shockah.glib.Gamelib;
import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;
import pl.shockah.glib.logic.standard.EntityRenderable;
import pl.shockah.glib.logic.standard.Game;
import pl.shockah.glib.room.Room;

public class DrawRectTest extends Room {
	public static void main(String[] args) {
		DrawRectTest test = new DrawRectTest();
		Gamelib.make(new Game()).start(test,test.getClass().getName());
	}
	
	protected void onSetup() {
		fps = 10;
	}
	
	protected void onCreate() {
		new EntityRenderable(){
			protected void onRender(Graphics g) {
				g.setColor(Color.Gold);
				g.draw(new Rectangle(100,100,200,100));
			}
		}.create();
	}
}