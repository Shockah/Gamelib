package tests;

import pl.shockah.glib.Gamelib;
import pl.shockah.glib.geom.Circle;
import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.Surface;
import pl.shockah.glib.gl.color.Color;
import pl.shockah.glib.logic.standard.EntityRenderable;
import pl.shockah.glib.logic.standard.GameStandard;
import pl.shockah.glib.state.State;

public class SurfaceTest extends State {
	public static void main(String[] args) {
		State test = new SurfaceTest();
		Gamelib.start(new GameStandard(),test,test.getClass().getName());
	}
	
	protected void onSetup() {
		fps = 60;
	}
	
	protected void onCreate() {
		final Surface surface = Surface.create(64,64);
		Graphics g = surface.graphics();
		g.clear();
		Graphics.setColor(Color.BlueViolet);
		g.draw(new Circle(32,32,16));
		
		new EntityRenderable(){
			protected void onRender(Graphics g) {
				Graphics.setColor(Color.Red);
				g.draw(new Rectangle(0,0,State.get().getDisplaySize().toDouble()));
				g.draw(surface.image);
			}
		}.create();
	}
}