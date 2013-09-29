//TODO: make the Gamelib API viable for such things, instead of using GL directly

package tests.random;

import org.lwjgl.opengl.GL11;
import pl.shockah.glib.Gamelib;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.GL;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;
import pl.shockah.glib.logic.standard.EntityRenderable;
import pl.shockah.glib.logic.standard.GameStandard;
import pl.shockah.glib.state.State;

public class SpiralTheodorus extends State {
	public static void main(String[] args) {
		SpiralTheodorus test = new SpiralTheodorus();
		Gamelib.start(new GameStandard(),test,test.getClass().getName());
	}
	
	protected void onCreate() {
		new EntityRenderable(){
			double state = 0d, spd = .02d;
			
			protected void onRender(Graphics g) {
				g.setColor(Color.Gray);
				state += spd;
				
				final double len = 30;
				Vector2d v0 = new Vector2d(400,300), v = v0.copyMe(), v1 = new Vector2d();
				
				GL11.glBegin(GL11.GL_TRIANGLE_FAN);
					GL.bind(Color.Black); GL11.glVertex2d(v.x,v.y);
					v.add(len,0);
					GL.bind(Color.Red); GL11.glVertex2d(v.x,v.y);
					
					double stepsLeft = state;
					while (stepsLeft > 0) {
						double d = stepsLeft >= 1 ? 1 : stepsLeft;
						stepsLeft -= d;
						
						v1.set(v);
						v.add(Vector2d.make(len*d,v0.direction(v)+90));
						GL.bind(Color.fromHSB((float)((v0.direction(v1))/360f),1f,1f)); GL11.glVertex2d(v.x,v.y);
						GL.bind(Color.fromHSB((float)((v0.direction(v))/360f),1f,1f)); GL11.glVertex2d(v.x,v.y);
					}
				GL11.glEnd();
			}
		}.create();
	}
}