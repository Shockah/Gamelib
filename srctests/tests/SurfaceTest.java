package tests;

import pl.shockah.glib.Gamelib;
import pl.shockah.glib.geom.Circle;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.Surface;
import pl.shockah.glib.gl.color.Color;
import pl.shockah.glib.logic.actor.ActorRenderable;
import pl.shockah.glib.state.State;

public class SurfaceTest extends State {
	public static void main(String[] args) {
		State test = new SurfaceTest();
		Gamelib.start(test,test.getClass().getName(),new Gamelib.Modules(false));
	}
	
	protected void onSetup() {
		fps = 60;
	}
	
	protected void onCreate() {
		final Surface surface = Surface.create(64,64);
		Graphics g = surface.graphics();
		g.clear();
		g.setColor(Color.White);
		g.draw(new Circle(32,32,24));
		
		new ActorRenderable(){
			protected void onRender(Graphics g) {
				for (int x = 0; x < 12; x++) for (int y = 0; y < 9; y++) {
					float s = y == 4 ? 1f : (y < 4 ? y/4f : 1f);
					float b = y == 4 ? 1f : (y > 4 ? (9-y)/4f : 1f);
					g.setColor(Color.fromHSB(x/12f,s,b));
					g.draw(surface.image,x*64,y*64);
				}
			}
		}.create();
	}
}