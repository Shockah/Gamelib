package pl.shockah.glib;

import java.util.Arrays;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;
import pl.shockah.glib.gl.GLHelper;
import pl.shockah.glib.logic.Game;
import pl.shockah.glib.room.Room;

public final class Gamelib<G extends Game<G>> {
	@SuppressWarnings("rawtypes") public static Gamelib me = null;
	public static DisplayMode originalDisplayMode;
	
	public static <G extends Game<G>> Gamelib<G> make(Class<G> cls) {
		try {
			return make(cls.newInstance());
		} catch (Exception e) {e.printStackTrace();}
		return null;
	}
	public static <G extends Game<G>> Gamelib<G> make(G inst) {
		Gamelib<G> glib = new Gamelib<>(inst);
		inst.setGamelib(glib);
		return glib;
	}
	
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
	public final G game;
	protected boolean cachedFullscreen = false;
	protected DisplayMode cachedDisplayMode = null;
	protected Room room;
	protected boolean isRunning = false;
	
	private Gamelib(G game) {
		this.game = game;
	}
	
	public void setDisplayMode(int width, int height) {
		setDisplayMode(width,height,cachedFullscreen);
	}
	public void setDisplayMode(int width, int height, boolean fullscreen) {
		if (cachedDisplayMode == null) cachedDisplayMode = originalDisplayMode;
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
		
		tryCreatingDisplay();
		capabilities.lock();
		
		room = firstRoom;
		GLHelper.initDisplay(cachedDisplayMode.getWidth(),cachedDisplayMode.getHeight());
		GLHelper.enterOrtho(cachedDisplayMode.getWidth(),cachedDisplayMode.getHeight());
		room.create();
		
		isRunning = true;
		gameLoop();
		Display.destroy();
	}
	
	private void tryCreatingDisplay() {
		if (tryCreatingDisplay(new PixelFormat(8,8,8,4))) return;
		capabilities.setMultisampleSupport(false);
		
		if (tryCreatingDisplay(new PixelFormat(8,8,8))) return;
		capabilities.setStencilSupport(false);
		
		if (tryCreatingDisplay(new PixelFormat(8,8,0))) return;
		capabilities.setAlphaSupport(false);
		
		if (tryCreatingDisplay(new PixelFormat())) return;
		throw new RuntimeException("Couldn't create display.");
	}
	private boolean tryCreatingDisplay(PixelFormat format) {
		try {
			Display.create(format);
			return true;
		} catch (Exception e) {
			Display.destroy();
			return false;
		}
	}
	
	protected void gameLoop() {
		while (isRunning) {
			game.gameLoop();
			if (Display.isCloseRequested()) isRunning = false;
			Display.update();
			Display.sync(room.getFPS());
		}
	}
	
	public void handle(Throwable t) {
		t.printStackTrace();
	}
}