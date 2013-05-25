package pl.shockah.glib;

import java.util.Arrays;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import pl.shockah.glib.room.Room;

public final class Gamelib {
	public static DisplayMode originalDisplayMode;
	
	public static void start(Room firstRoom) {start(firstRoom,"Gamelib");}
	public static void start(Room firstRoom, String windowTitle) {
		originalDisplayMode = Display.getDesktopDisplayMode();
	}
	
	public static void handle(Throwable t) {
		t.printStackTrace();
	}
	
	protected boolean cachedFullscreen = false;
	protected DisplayMode cachedDisplayMode = null;
	
	public void setDisplayMode(int width, int height, boolean fullscreen) {
		if (width == cachedDisplayMode.getWidth() && height == cachedDisplayMode.getHeight() && fullscreen == cachedFullscreen) return;
		
		if (fullscreen) {
			try {
				DisplayMode[] modes = Display.getAvailableDisplayModes();
				Arrays.sort(modes,0,modes.length-1,new BestDisplayModeComparator(width,height));
				DisplayMode newMode = modes[modes.length-1];
				if (newMode.getWidth() != width || newMode.getHeight() != height) throw new RuntimeException("Failed to find a suitable "+width+"x"+height+" display mode.");
				cachedDisplayMode = newMode;
			} catch (Exception e) {e.printStackTrace();}
		} else cachedDisplayMode = new DisplayMode(width,height);
		
		try {
			Display.setDisplayMode(cachedDisplayMode);
			Display.setFullscreen(fullscreen);
		} catch (Exception e) {e.printStackTrace();}
	}
}