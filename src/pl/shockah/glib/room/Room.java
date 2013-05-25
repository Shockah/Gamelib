package pl.shockah.glib.room;

import java.util.LinkedList;
import java.util.List;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import pl.shockah.glib.Gamelib;

public class Room {
	protected List<View> views = new LinkedList<View>();
	protected int fps = 60;
	
	protected final void setup() {
		onSetup();
		if (views.isEmpty()) views.add(new View());
	}
	protected void onSetup() {
		try {
			int w = 0, h = 0, xx, yy;
			for (View view : views) {
				xx = view.viewPortPos.x+view.viewPortSize.x;
				yy = view.viewPortPos.y+view.viewPortSize.y;
				if (xx > w) w = xx;
				if (yy > h) h = yy;
			}
			
			Display.setDisplayMode(new DisplayMode(w,h));
		} catch (Exception e) {Gamelib.handle(e);}
	}
	
	protected final void create() {
		onCreate();
	}
	protected void onCreate() {}
}