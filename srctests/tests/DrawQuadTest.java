package tests;

import static org.lwjgl.opengl.GL11.*;
import pl.shockah.glib.Gamelib;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;
import pl.shockah.glib.logic.standard.EntityRenderable;
import pl.shockah.glib.logic.standard.Game;
import pl.shockah.glib.room.Room;

public class DrawQuadTest extends Room {
	public static void main(String[] args) {
		DrawQuadTest test = new DrawQuadTest();
		Gamelib.make(new Game()).start(test,test.getClass().getName());
	}
	
	protected void onSetup() {
		fps = 10;
	}
	
	protected void onCreate() {
		new EntityRenderable(){
			protected void onRender(Graphics g) {
				g.setColor(Color.Gold);
				
				glBegin(GL_QUADS);
				glVertex2f(100,100);
				glVertex2f(100+200,100);
				glVertex2f(100+200,100+200);
				glVertex2f(100,100+200);
				glEnd();
			}
		}.create();
	}
}