package tests.random;

import pl.shockah.Math2;
import pl.shockah.glib.Gamelib;
import pl.shockah.glib.animfx.Animation;
import pl.shockah.glib.animfx.TimelineDouble;
import pl.shockah.glib.geom.Circle;
import pl.shockah.glib.geom.vector.Vector2i;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;
import pl.shockah.glib.logic.standard.EntityRenderable;
import pl.shockah.glib.logic.standard.GameStandard;
import pl.shockah.glib.state.State;
import pl.shockah.glib.state.View;

public class Hypnodots extends State {
	public static void main(String[] args) {
		Gamelib.useSound(false);
		Hypnodots test = new Hypnodots();
		Gamelib.start(new GameStandard(test),test.getClass().getName());
	}
	
	protected void onSetup() {
		views.add(new View(new Vector2i(500,500)));
	}
	
	protected void onCreate() {
		new EntityRenderable(){
			protected final Animation anim = new Animation().setLooped();
			protected final TimelineDouble line = new TimelineDouble();
			protected int ticks = 120;
			
			protected void onCreate() {
				anim.add(line);
				
				line.add(0d,0d);
				line.add(1d,60d);
				line.add(0d,120d);
			}
			
			protected void onUpdate() {
				ticks--;
				if (ticks < 0) ticks += 120;
			}
			
			protected void onRender(Graphics g) {
				final int dots = 16;
				for (int i = 0; i < dots; i++) {
					float angle = 360f/dots*i;
					for (int j = 0; j < 8; j++) {
						double val = ticks+10-j+angle;
						while (val >= 120) val -= 120;
						while (val < 0) val += 120;
						
						anim.setTime(val);
						g.setColor(Color.fromHSB(angle/360f,1f,1f).alpha(1f/8*j));
						g.draw(new Circle(250+Math2.ldirX(25+line.getState(anim)*200f,angle),250+Math2.ldirY(25+line.getState(anim)*200f,angle),5).asPolygon(16));
					}
				}
			}
		}.create();
	}
}