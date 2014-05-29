package tests;

import java.io.IOException;
import pl.shockah.glib.Gamelib;
import pl.shockah.glib.animfx.Animation;
import pl.shockah.glib.animfx.Ease;
import pl.shockah.glib.animfx.TimelineDouble;
import pl.shockah.glib.animfx.TimelineObject;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.tex.Image;
import pl.shockah.glib.gl.tex.Texture;
import pl.shockah.glib.logic.EntityRenderable;
import pl.shockah.glib.state.State;

public class AnimationTest extends State {
	public static void main(String[] args) {
		State test = new AnimationTest();
		Gamelib.start(test,test.getClass().getName(),new Gamelib.Modules(false));
	}
	
	Image image;
	
	protected void onSetup() {
		fps = 60;
	}
	
	protected void onCreate() {
		try {
			image = new Image(Texture.load("assets/imagepng1.png"));
			image.rotation.center();
		} catch (IOException e) {e.printStackTrace();}
		
		final Animation anim = new Animation().setLooped(true);
		final TimelineObject<Vector2d> linePos = new TimelineObject<>(Ease.Back.InOut); anim.add(linePos);
		final TimelineObject<Vector2d> linePos2 = new TimelineObject<>(Ease.Bounce.Out); anim.add(linePos2);
		
		final Animation anim2 = new Animation().setLooped(true);
		final TimelineDouble lineY = new TimelineDouble(Ease.Smoothstep.P3); anim2.add(lineY);
		final TimelineDouble lineY2 = new TimelineDouble(Ease.Linear); anim2.add(lineY2);
		
		linePos.add(new Vector2d(0,0),0);
		linePos.add(new Vector2d(300,50),120);
		linePos.add(new Vector2d(0,0),240);
		
		linePos2.add(new Vector2d(0,100),0);
		linePos2.add(new Vector2d(300,150),120);
		linePos2.add(new Vector2d(0,100),240);
		
		lineY.add(250d,0);
		lineY.add(400d,100);
		lineY.add(250d,200);
		
		lineY2.add(250d,0);
		lineY2.add(400d,100);
		lineY2.add(250d,200);
		
		new EntityRenderable(){
			protected void onUpdate() {
				anim.update();
				anim2.update();
			}
			protected void onRender(Graphics g) {
				g.draw(image,linePos.getState(anim));
				g.draw(image,linePos2.getState(anim));
				g.draw(image,0,lineY.getState(anim2));
				g.draw(image,100,lineY2.getState(anim2));
			}
		}.create();
	}
}