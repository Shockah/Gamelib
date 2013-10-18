package pl.shockah.glib;

import java.util.Arrays;
import org.lwjgl.Sys;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.PixelFormat;
import pl.shockah.glib.geom.vector.Vector2i;
import pl.shockah.glib.gl.GL;
import pl.shockah.glib.input.KInput;
import pl.shockah.glib.input.MInput;
import pl.shockah.glib.logic.IGame;
import pl.shockah.glib.state.State;

public final class Gamelib {
	public static DisplayMode originalDisplayMode;
	
	public static final class Capabilities {
		private boolean multisample = true, alpha = true, stencil = true, fbo = true;
		private boolean locked = false;
		
		public String toString() {
			return "[Gamelib.Capabilities: "+(multisample ? "" : "no ")+"multisampling, "+(alpha ? "" : "no ")+"alpha, "+(stencil ? "" : "no ")+"stencil, "+(fbo ? "" : "no ")+"FBO]";
		}
		
		public boolean multisampling() {return multisample;}
		public boolean alpha() {return alpha;}
		public boolean stencil() {return stencil;}
		public boolean FBO() {return fbo;}
		
		public void setMultisampleSupport(boolean b) {
			if (locked) throw new IllegalStateException("This object is locked, it can't be modified.");
			multisample = b;
		}
		public void setAlphaSupport(boolean b) {
			if (locked) throw new IllegalStateException("This object is locked, it can't be modified.");
			alpha = b;
		}
		public void setStencilSupport(boolean b) {
			if (locked) throw new IllegalStateException("This object is locked, it can't be modified.");
			stencil = b;
		}
		public void setFBOSupport(boolean b) {
			if (locked) throw new IllegalStateException("This object is locked, it can't be modified.");
			fbo = b;
		}
		
		public void lock() {
			locked = true;
		}
	}
	
	public static final Capabilities capabilities = new Capabilities();
	public static IGame game;
	public static DisplayMode cachedDisplayMode = null;
	protected static boolean cachedFullscreen = false;
	protected static boolean isRunning = false;
	
	public static void setDisplayMode(Vector2i v) {
		setDisplayMode(v.x,v.y);
	}
	public static void setDisplayMode(int width, int height) {
		setDisplayMode(width,height,cachedFullscreen);
	}
	public static void setDisplayMode(Vector2i v, boolean fullscreen) {
		setDisplayMode(v.x,v.y,fullscreen);
	}
	public static void setDisplayMode(int width, int height, boolean fullscreen) {
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
	
	public static void start(Class<? extends IGame> cls, State initialState) {start(cls,initialState,"Gamelib");}
	public static void start(Class<? extends IGame> cls, State initialState, String windowTitle) {
		try {
			start(cls.newInstance(),initialState,windowTitle);
		} catch (Exception e) {handle(e);}
	}
	public static void start(IGame game, State initialState) {start(game,initialState,"Gamelib");}
	public static void start(IGame game, State initialState, String windowTitle) {
		System.setProperty("org.lwjgl.input.Mouse.allowNegativeMouseCoords","true");
		Gamelib.game = game;
		originalDisplayMode = Display.getDesktopDisplayMode();
		
		if (initialState == null) throw new IllegalArgumentException("A game can't exist without a State.");
		initialState.setup();
		
		if (windowTitle == null) windowTitle = "";
		Display.setTitle(windowTitle);
		
		tryCreatingDisplay();
		if (!GLContext.getCapabilities().GL_EXT_framebuffer_object) capabilities.setFBOSupport(false);
		capabilities.lock();
		
		State.change(initialState);
		GL.initDisplay(cachedDisplayMode.getWidth(),cachedDisplayMode.getHeight());
		GL.enterOrtho(cachedDisplayMode.getWidth(),cachedDisplayMode.getHeight());
		GL.setup();
		State.get().create();
		
		try {
			AL.create();
		} catch (Exception e) {e.printStackTrace();}
		
		isRunning = true;
		gameLoop();
		AL.destroy();
		Display.destroy();
	}
	public static void stop() {
		isRunning = false;
	}
	
	private static void tryCreatingDisplay() {
		if (tryCreatingDisplay(new PixelFormat(8,8,8,4))) return;
		capabilities.setMultisampleSupport(false);
		
		if (tryCreatingDisplay(new PixelFormat(8,8,8))) return;
		capabilities.setStencilSupport(false);
		
		if (tryCreatingDisplay(new PixelFormat(8,8,0))) return;
		capabilities.setAlphaSupport(false);
		
		if (tryCreatingDisplay(new PixelFormat())) return;
		throw new RuntimeException("Couldn't create display.");
	}
	private static boolean tryCreatingDisplay(PixelFormat format) {
		try {
			Display.create(format);
			return true;
		} catch (Exception e) {
			Display.destroy();
			return false;
		}
	}
	
	protected static void gameLoop() {
		while (isRunning) {
			KInput.update();
			MInput.update();
			game.gameLoop();
			if (State.get() != null) advanceFrame(State.get().getFPS());
		}
	}
	public static void advanceFrame() {
		advanceFrame(0);
	}
	public static void advanceFrame(int fps) {
		GL.unbind();
		Debug.advance();
		if (Display.isCloseRequested()) isRunning = false;
		Display.update();
		if (fps > 0) Display.sync(fps);
	}
	
	public static void handle(Throwable t) {
		t.printStackTrace();
	}
	
	public static double getDoubleTime() {
		return 1d*Sys.getTime()/Sys.getTimerResolution();
	}
}