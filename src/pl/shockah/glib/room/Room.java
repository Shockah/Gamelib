package pl.shockah.glib.room;

import java.util.LinkedList;
import java.util.List;
import pl.shockah.glib.Gamelib;

public class Room {
	protected List<View> views = new LinkedList<>();
	protected int fps = 60;
	
	public final int getFPS() {
		if (fps < 1) fps = 60;
		return fps;
	}
	
	public final void setup() {
		onSetup();
		if (views.isEmpty()) views.add(new View());
		
		int w = 0, h = 0, xx, yy;
		for (View view : views) {
			xx = view.viewPortPos.x+view.viewPortSize.x;
			yy = view.viewPortPos.y+view.viewPortSize.y;
			if (xx > w) w = xx;
			if (yy > h) h = yy;
		}
		
		Gamelib.me.setDisplayMode(w,h);
	}
	protected void onSetup() {}
	
	public final void create() {
		onCreate();
	}
	protected void onCreate() {}
}