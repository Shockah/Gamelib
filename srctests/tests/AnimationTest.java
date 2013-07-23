package tests;

import java.io.IOException;
import pl.shockah.glib.Gamelib;
import pl.shockah.glib.animfx.Animation;
import pl.shockah.glib.animfx.Interpolate;
import pl.shockah.glib.animfx.Timeline;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.Image;
import pl.shockah.glib.gl.tex.Texture;
import pl.shockah.glib.logic.standard.EntityRenderable;
import pl.shockah.glib.logic.standard.GameStandard;
import pl.shockah.glib.state.State;

public class AnimationTest extends State {
	public static void main(String[] args) {
		AnimationTest test = new AnimationTest();
		Gamelib.start(new GameStandard(),test,test.getClass().getName());
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
		final Timeline<Vector2d> linePos = new Timeline<>(); anim.add(linePos);
		final Timeline<Vector2d> linePos2 = new Timeline<>(Interpolate.Linear); anim.add(linePos2);
		
		linePos.add(new Vector2d(0,0),0);
		linePos.add(new Vector2d(300,0),120);
		linePos.add(new Vector2d(0,0),240);
		
		linePos2.add(new Vector2d(0,100),0);
		linePos2.add(new Vector2d(300,100),120);
		linePos2.add(new Vector2d(0,100),240);
		
		new EntityRenderable(){
			protected void onUpdate() {
				anim.update();
			}
			protected void onRender(Graphics g) {
				g.draw(image,linePos2.getState(anim));
				g.draw(image,linePos.getState(anim));
			}
		}.create();
	}
}