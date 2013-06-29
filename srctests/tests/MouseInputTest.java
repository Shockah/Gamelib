package tests;

import pl.shockah.glib.Gamelib;
import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.geom.vector.Vector2i;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;
import pl.shockah.glib.logic.standard.EntityRenderable;
import pl.shockah.glib.logic.standard.GameStandard;
import pl.shockah.glib.room.Room;

public class MouseInputTest extends Room {
	public static void main(String[] args) {
		MouseInputTest test = new MouseInputTest();
		Gamelib.start(new GameStandard(),test,test.getClass().getName());
	}
	
	protected void onCreate() {
		new EntityRenderable(){
			Vector2i drag = null;
			
			protected void onTick() {
				Vector2i mp = Gamelib.mouse.getPos();
				if (Gamelib.mouse.isPressed(0) && Gamelib.mouse.inRectangle(new Rectangle(pos,new Vector2d(48,48)))) {
					drag = new Vector2i((int)(pos.x-mp.x),(int)(pos.y-mp.y));
				}
				if (Gamelib.mouse.isReleased(0)) drag = null;
				
				if (drag != null) pos = mp.add(drag).toDouble();
			}
			
			protected void onRender(Graphics g) {
				g.setColor(drag == null ? Color.DeepPink : Color.inverse(Color.DeepPink));
				g.draw(new Rectangle(pos.x,pos.y,48,48));
			}
		}.create();
	}
}