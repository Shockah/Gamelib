package tests;

import pl.shockah.glib.Gamelib;
import pl.shockah.glib.animfx.Animation;
import pl.shockah.glib.animfx.TimelineDouble;
import pl.shockah.glib.geom.Circle;
import pl.shockah.glib.geom.Curve;
import pl.shockah.glib.geom.Line;
import pl.shockah.glib.geom.polygon.Polygon;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;
import pl.shockah.glib.logic.EntityRenderable;
import pl.shockah.glib.state.State;

public class CurveTest extends State {
	public static void main(String[] args) {
		State test = new CurveTest();
		Gamelib.start(test,test.getClass().getName(),new Gamelib.Modules(false));
	}
	
	protected void onSetup() {
		fps = 60;
	}
	
	protected void onCreate() {
		final Animation anim1 = new Animation().setLooped(true), anim2 = new Animation().setLooped(true), anim3 = new Animation().setLooped(true);
		final TimelineDouble linePos1 = new TimelineDouble(); anim1.add(linePos1);
		final TimelineDouble linePos2 = new TimelineDouble(); anim2.add(linePos2);
		final TimelineDouble linePos3 = new TimelineDouble(); anim3.add(linePos3);
		
		linePos1.add(-300,0);
		linePos1.add(300,120);
		linePos1.add(-300,240);
		
		linePos2.add(-300,0);
		linePos2.add(300,120*Math.PI);
		linePos2.add(-300,240*Math.PI);
		
		linePos3.add(-300,0);
		linePos3.add(300,197);
		linePos3.add(-300,197*2);
		
		new EntityRenderable(){
			protected void onUpdate() {
				anim1.update();
				anim2.update();
				anim3.update();
			}
			protected void onRender(Graphics g) {
				Curve c;
				Polygon p;
				
				g.setColor(Color.White);
				c = new Curve(50,300,250,300);
				c.addControlPoint(150,300+linePos1.getState(anim1));
				c.draw(g);
				p = new Polygon();
				for (Vector2d point : c.getAllPoints()) {
					new Circle(point,4).draw(g);
					p.addPoint(point);
				}
				g.setColor(Color.White.alpha(1f/3f));
				for (Line l : p.getLines(false)) l.draw(g);
				
				g.setColor(Color.White);
				c = new Curve(450,300,750,300);
				c.addControlPoint(550,300+linePos2.getState(anim2));
				c.addControlPoint(650,300+linePos3.getState(anim3));
				c.draw(g);
				p = new Polygon();
				for (Vector2d point : c.getAllPoints()) {
					new Circle(point,4).draw(g);
					p.addPoint(point);
				}
				g.setColor(Color.White.alpha(1f/3f));
				for (Line l : p.getLines(false)) l.draw(g);
			}
		}.create();
	}
}