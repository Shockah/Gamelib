package tests.random;

import pl.shockah.Math2;
import pl.shockah.glib.Gamelib;
import pl.shockah.glib.animfx.Animation;
import pl.shockah.glib.animfx.TimelineDouble;
import pl.shockah.glib.geom.Circle;
import pl.shockah.glib.geom.vector.Vector2i;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;
import pl.shockah.glib.logic.actor.ActorRenderable;
import pl.shockah.glib.state.State;
import pl.shockah.glib.state.View;

public class Hypnodots2 extends State {
	public static void main(String[] args) {
		Hypnodots2 test = new Hypnodots2();
		Gamelib.start(test,test.getClass().getName(),new Gamelib.Modules(false));
	}
	
	protected void onSetup() {
		views.add(new View(new Vector2i(500,500)));
	}
	
	protected void onCreate() {
		new ActorRenderable(){
			protected final Animation anim = new Animation().setLooped();
			protected final TimelineDouble line = new TimelineDouble();
			protected int ticks = 360;
			
			protected void onCreate() {
				anim.add(line);
				
				line.add(-1d,0d);
				line.add(1d,180d);
				line.add(-1d,360d);
			}
			
			protected void onUpdate() {
				ticks--;
				if (ticks < 0) ticks += 360;
			}
			
			protected void onRender(Graphics g) {
				final int dots = 16, shadows = 8;
				for (int i = 0; i < dots; i++) {
					float angle = 180f/dots*i;
					for (int j = 0; j < shadows; j++) {
						double val = ticks+10-j+angle;
						while (val >= 360) val -= 360;
						while (val < 0) val += 360;
						
						anim.setTime(val);
						g.setColor(Color.fromHSB(angle/180f,1f,1f).alpha(1f/shadows*j));
						g.draw(new Circle(250+Math2.ldirX(line.state(anim)*225f,angle),250+Math2.ldirY(line.state(anim)*225f,angle),5).asPolygon(16));
					}
				}
			}
		}.create();
	}
}