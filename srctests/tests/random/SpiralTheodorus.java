package tests.random;

import pl.shockah.glib.Gamelib;
import pl.shockah.glib.geom.polygon.TriangleFan;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;
import pl.shockah.glib.logic.EntityRenderable;
import pl.shockah.glib.state.State;

public class SpiralTheodorus extends State {
	public static void main(String[] args) {
		SpiralTheodorus test = new SpiralTheodorus();
		Gamelib.start(test,test.getClass().getName(),new Gamelib.Modules(false));
	}
	
	protected void onCreate() {
		new EntityRenderable(){
			double state = 0d, spd = .02d;
			
			protected void onRender(Graphics g) {
				state += spd;
				
				final double len = 30;
				Vector2d v0 = new Vector2d(400,300), v = v0.copyMe(), v1 = new Vector2d();
				
				TriangleFan tf = new TriangleFan();
				tf.addPoint(v,Color.Black);
				tf.addPoint(v.add(len,0),Color.Red);
					
				double stepsLeft = state;
				while (stepsLeft > 0) {
					double d = stepsLeft >= 1 ? 1 : stepsLeft;
					stepsLeft -= d;
					
					v1.set(v);
					v.add(Vector2d.make(len*d,v0.direction(v)+90));
					tf.addPoint(v,Color.fromHSB((float)((v0.direction(v1))/360f),1f,1f));
					tf.addPoint(v,Color.fromHSB((float)((v0.direction(v))/360f),1f,1f));
				}
				
				g.draw(tf);
			}
		}.create();
	}
}