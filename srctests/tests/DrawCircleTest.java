package tests;

import pl.shockah.glib.Gamelib;
import pl.shockah.glib.geom.Circle;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;
import pl.shockah.glib.logic.standard.EntityRenderable;
import pl.shockah.glib.logic.standard.GameStandard;
import pl.shockah.glib.state.State;

public class DrawCircleTest extends State {
	public static void main(String[] args) {
		DrawCircleTest test = new DrawCircleTest();
		Gamelib.start(new GameStandard(),test,test.getClass().getName());
	}
	
	protected void onSetup() {
		fps = 10;
	}
	
	protected void onCreate() {
		new EntityRenderable(){
			protected void onRender(Graphics g) {
				g.setColor(Color.Aqua);
				g.draw(new Circle(150,150,50));
			}
		}.create();
	}
}