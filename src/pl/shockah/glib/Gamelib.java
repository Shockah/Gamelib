package pl.shockah.glib;

import java.util.Arrays;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;
import pl.shockah.glib.room.Room;

public final class Gamelib {
	public static Gamelib me = null;
	public static DisplayMode originalDisplayMode;
	
	public final class Capabilities {
		private boolean multisample = true, alpha = true, stencil = true;
		private boolean locked = false;
		
		public boolean supportsMultisampling() {return multisample;}
		public boolean supportsAlpha() {return alpha;}
		public boolean supportsStencil() {return stencil;}
		
		public void setMultisampleSupport(boolean b) {
			if (locked) throw new RuntimeException("This object is locked, it can't be modified.");
			multisample = b;
		}
		public void setAlphaSupport(boolean b) {
			if (locked) throw new RuntimeException("This object is locked, it can't be modified.");
			alpha = b;
		}
		public void setStencilSupport(boolean b) {
			if (locked) throw new RuntimeException("This object is locked, it can't be modified.");
			stencil = b;
		}
		
		public void lock() {
			locked = true;
		}
	}
	
	public final Capabilities capabilities = new Capabilities();
	protected boolean cachedFullscreen = false;
	protected DisplayMode cachedDisplayMode = null;
	protected Room room;
	
	public void setDisplayMode(int width, int height) {
		setDisplayMode(width,height,cachedFullscreen);
	}
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
	
	public void start(Room firstRoom) {start(firstRoom,"Gamelib");}
	public void start(Room firstRoom, String windowTitle) {
		me = this;
		originalDisplayMode = Display.getDesktopDisplayMode();
		
		if (firstRoom == null) throw new RuntimeException("A game can't exist without a Room.");
		firstRoom.setup();
		
		if (windowTitle == null) windowTitle = "";
		Display.setTitle(windowTitle);
		
		try {
			Display.create(new PixelFormat(8,8,8,4));
		} catch (Exception e1) {
			Display.destroy();
			capabilities.setMultisampleSupport(false);
			try {
				Display.create(new PixelFormat(8,8,8));
			} catch (Exception e2) {
				Display.destroy();
				capabilities.setStencilSupport(false);
				try {
					Display.create(new PixelFormat(8,8,0));
				} catch (Exception e3) {
					Display.destroy();
					capabilities.setAlphaSupport(false);
					try {
						Display.create(new PixelFormat());
					} catch (Exception e4) {
						Display.destroy();
						throw new RuntimeException("Couldn't create display.");
					}
				}
			}
		}
		capabilities.lock();
		
		room = firstRoom;
		GLHelper.initDisplay(cachedDisplayMode.getWidth(),cachedDisplayMode.getHeight());
		GLHelper.enterOrtho(cachedDisplayMode.getWidth(),cachedDisplayMode.getHeight());
	}
	
	protected void gameLoop() {
		
	}
	
	public void handle(Throwable t) {
		t.printStackTrace();
	}
}