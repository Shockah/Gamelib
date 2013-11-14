package tests;

import pl.shockah.glib.Gamelib;
import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.geom.vector.Vector2i;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;
import pl.shockah.glib.input.MInput;
import pl.shockah.glib.logic.EntityRenderable;
import pl.shockah.glib.state.State;

public class MouseInputTest extends State {
	public static void main(String[] args) {
		State test = new MouseInputTest();
		Gamelib.start(test,test.getClass().getName(),new Gamelib.Modules(false));
	}
	
	protected void onCreate() {
		new EntityRenderable(){
			Vector2i drag = null;
			
			protected void onUpdate() {
				Vector2i mp = MInput.getPos();
				if (MInput.isPressed(MInput.LEFT) && MInput.inRectangle(new Rectangle(pos,new Vector2d(48,48)))) drag = pos.sub(mp.toDouble()).toInt();
				if (MInput.isReleased(MInput.LEFT)) drag = null;
				if (drag != null) pos = mp.add(drag).toDouble();
			}
			
			protected void onRender(Graphics g) {
				g.setColor(drag == null ? Color.DeepPink : Color.DeepPink.inverse());
				g.draw(new Rectangle(pos.x,pos.y,48,48));
			}
		}.create();
	}
}