package tests;

import pl.shockah.glib.Gamelib;
import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.geom.vector.Vector2i;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;
import pl.shockah.glib.input.MButton;
import pl.shockah.glib.input.MouseInput;
import pl.shockah.glib.logic.standard.EntityRenderable;
import pl.shockah.glib.logic.standard.GameStandard;
import pl.shockah.glib.state.State;

public class MouseInputTest2 extends State {
	public static void main(String[] args) {
		State test = new MouseInputTest2();
		Gamelib.start(new GameStandard(),test,test.getClass().getName());
	}
	
	protected void onCreate() {
		new EntityRenderable(){
			Vector2i drag = null;
			
			MButton mL = new MButton(MouseInput.LEFT);
			
			protected void onUpdate() {
				Vector2i mp = Gamelib.mouse.getPos();
				if (mL.pressed() && Gamelib.mouse.inRectangle(new Rectangle(pos,new Vector2d(48,48)))) drag = pos.sub(mp.toDouble()).toInt();
				if (mL.released()) drag = null;
				if (drag != null) pos = mp.add(drag).toDouble();
			}
			
			protected void onRender(Graphics g) {
				Graphics.setColor(drag == null ? Color.DeepPink : Color.DeepPink.inverse());
				g.draw(new Rectangle(pos.x,pos.y,48,48));
			}
		}.create();
	}
}