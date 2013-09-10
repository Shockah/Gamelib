package tests;

import java.io.IOException;
import pl.shockah.glib.Gamelib;
import pl.shockah.glib.geom.Circle;
import pl.shockah.glib.geom.vector.Vector2i;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.Stencil;
import pl.shockah.glib.gl.StencilMask;
import pl.shockah.glib.gl.tex.Image;
import pl.shockah.glib.gl.tex.Texture;
import pl.shockah.glib.logic.standard.EntityRenderable;
import pl.shockah.glib.logic.standard.GameStandard;
import pl.shockah.glib.state.State;

public class StencilTest extends State {
	public static void main(String[] args) {
		State test = new StencilTest();
		Gamelib.start(new GameStandard(),test,test.getClass().getName());
	}
	
	Image image;
	
	protected void onSetup() {
		fps = 60;
	}
	
	protected void onCreate() {
		try {
			image = new Image(Texture.load("assets/imagejpg1.jpg"));
			image.center();
		} catch (IOException e) {e.printStackTrace();}
		
		new EntityRenderable(){
			protected void onUpdate() {
				image.rotation.angle += 1;
			}
			protected void onRender(Graphics g) {
				Vector2i size = State.get().getDisplaySize();
				
				StencilMask sm = new StencilMask(g,Stencil.Drop).proceed();
				g.draw(new Circle(size.toDouble().div(2).add(-48,0),48));
				g.draw(new Circle(size.toDouble().div(2).add(48,0),48));
				g.draw(new Circle(size.toDouble().div(2).add(0,-48),48));
				g.draw(new Circle(size.toDouble().div(2).add(0,48),48));
				
				sm.proceed();
				g.draw(image,size.Div(2));
				sm.proceed();
			}
		}.create();
	}
}