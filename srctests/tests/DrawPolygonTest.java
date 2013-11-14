package tests;

import java.util.Random;
import pl.shockah.glib.Gamelib;
import pl.shockah.glib.geom.polygon.Polygon;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;
import pl.shockah.glib.logic.EntityRenderable;
import pl.shockah.glib.state.State;

public class DrawPolygonTest extends State {
	public static void main(String[] args) {
		State test = new DrawPolygonTest();
		Gamelib.start(test,test.getClass().getName(),new Gamelib.Modules(false));
	}
	
	Polygon p = new Polygon();
	
	protected void onSetup() {
		fps = 10;
		
		Random rand = new Random();
		for (int i = 0; i < 10; i++) {
			Vector2d pos = i == 0 ? new Vector2d(400,300) : p.getPoints()[p.getPointCount()-1];
			p.addPoint(pos.Add((32+rand.nextDouble()*32)*(rand.nextBoolean() ? 1 : -1),(32+rand.nextDouble()*32)*(rand.nextBoolean() ? 1 : -1)));
		}
	}
	
	protected void onCreate() {
		new EntityRenderable(){
			protected void onRender(Graphics g) {
				g.setColor(Color.Crimson);
				g.draw(p);
			}
		}.create();
	}
}