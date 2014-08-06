package pl.shockah.glib;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.Sys;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;
import pl.shockah.glib.geom.vector.Vector2;
import pl.shockah.glib.geom.vector.Vector2i;
import pl.shockah.glib.gl.GL;
import pl.shockah.glib.input.KInput;
import pl.shockah.glib.input.MInput;
import pl.shockah.glib.logic.Game;
import pl.shockah.glib.logic.actor.GameActor;
import pl.shockah.glib.state.State;

public final class Gamelib {
	public static final class Modules {
		private boolean graphics, sound;
		private boolean locked = false;
		
		public Modules() {this(true,true);}
		public Modules(boolean sound) {this(true,sound);}
		public Modules(boolean graphics, boolean sound) {
			this.graphics = graphics;
			this.sound = sound;
		}
		
		public String toString() {
			return "[Gamelib.Modules: "+(graphics ? "" : "no ")+"graphics, "+(sound ? "" : "no ")+"sound]";
		}
		
		public boolean graphics() {return graphics;}
		public boolean sound() {return sound;}
		
		public void setGraphicsSupport(boolean b) {
			if (locked) throw new IllegalStateException("This object is locked, it can't be modified.");
			graphics = b;
		}
		public void setSoundSupport(boolean b) {
			if (locked) throw new IllegalStateException("This object is locked, it can't be modified.");
			sound = b;
		}
		
		public void lock() {
			locked = true;
		}
	}
	
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
	
	protected static Modules modules;
	public static final Capabilities capabilities = new Capabilities();
	private static AWTWindow windowAWT = null;
	public static Game game;
	public static DisplayMode originalDisplayMode, cachedDisplayMode = null;
	protected static boolean cachedFullscreen = false, isRunning = false;
	protected static double lastFrame = 0d, lastDelta = 0d;
	private static boolean displayChanged = false;
	
	public static Modules modules() {return modules;}
	
	public static void setDisplayMode(Vector2 v) {
		setDisplayMode(v.Xi(),v.Yi());
	}
	public static void setDisplayMode(int width, int height) {
		setDisplayMode(width,height,cachedFullscreen);
	}
	public static void setDisplayMode(Vector2i v, boolean fullscreen) {
		setDisplayMode(v.x,v.y,fullscreen);
	}
	public static void setDisplayMode(int width, int height, boolean fullscreen) {
		if (!modules.graphics) throw new IllegalStateException("Graphics are disabled");
		
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
			windowAWT.updateSize(cachedDisplayMode);
			Display.setDisplayMode(cachedDisplayMode);
			Display.setFullscreen(fullscreen);
			displayChanged = true;
		} catch (Exception e) {e.printStackTrace();}
	}
	
	private static void findAndSetupNatives() {
		File dir = null;
		try {
			List<File> check = new LinkedList<>();
			check.add(new File("").getAbsoluteFile());
			String lookFor = lookForFile();
			
			while (!check.isEmpty()) {
				File f = check.remove(0);
				if (f.isDirectory()) {
					for (File f2 : f.listFiles()) {
						if (f2.getName().matches("^\\.+.*$")) continue;
						check.add(f2);
					}
				} else {
					if (f.getName().equals(lookFor)) {
						dir = f.getParentFile();
						break;
					}
				}
			}
		} catch (Exception e) {}
		if (dir != null) System.setProperty("org.lwjgl.librarypath",dir.getAbsolutePath());
	}
	private static String lookForFile() {
		switch (LWJGLUtil.getPlatform()) {
			case LWJGLUtil.PLATFORM_WINDOWS: return "lwjgl.dll";
			case LWJGLUtil.PLATFORM_LINUX: return "liblwjgl.so";
			case LWJGLUtil.PLATFORM_MACOSX: return "liblwjgl.jnilib";
		}
		throw new IllegalStateException("Couldn't detect operating system type.");
	}
	
	public static void start(State state) {start(state,"Gamelib",new Modules());}
	public static void start(State state, Modules modules) {start(state,"Gamelib",modules);}
	public static void start(State state, String windowTitle) {start(state,windowTitle,new Modules());}
	public static void start(State state, String windowTitle, Modules modules) {start(new GameActor(state),windowTitle,modules);}
	public static void start(Game game) {start(game,"Gamelib",new Modules());}
	public static void start(Game game, Modules modules) {start(game,"Gamelib",modules);}
	public static void start(Game game, String windowTitle) {start(game,windowTitle,new Modules());}
	public static void start(Game game, String windowTitle, Modules modules) {
		Gamelib.game = game;
		Gamelib.modules = modules;
		findAndSetupNatives();
		
		if (modules.graphics()) {
			System.setProperty("org.lwjgl.input.Mouse.allowNegativeMouseCoords","true");
			originalDisplayMode = Display.getDesktopDisplayMode();
		}
		
		if (windowTitle == null) windowTitle = "";
		if (modules.graphics()) windowAWT = new AWTWindow(windowTitle);
		
		game.setupInitialState();
		
		if (modules.graphics()) {
			windowAWT.setLocationRelativeTo(null);
			windowAWT.setVisible(true);
			windowAWT.setup();
			tryCreatingDisplay();
			//if (!GLContext.getCapabilities().GL_EXT_framebuffer_object) capabilities.setFBOSupport(false);
		}
		capabilities.lock();
		
		game.setInitialState();
		State.get().create();
		
		if (modules.sound()) {
			try {
				AL.create();
			} catch (Exception e) {
				modules.setSoundSupport(false);
			}
		}
		
		modules.lock();
		isRunning = true;
		Display.setTitle(windowTitle);
		game.setup();
		gameLoop();
		if (modules.sound()) AL.destroy();
		if (modules.graphics()) Display.destroy();
		if (windowAWT != null) windowAWT.dispose();
	}
	public static void stop() {
		isRunning = false;
	}
	public static boolean isRunning() {
		return isRunning;
	}
	
	public static void resetGL() {
		if (modules.graphics()) {
			GL.initDisplay(cachedDisplayMode.getWidth(),cachedDisplayMode.getHeight());
			GL.enterOrtho(cachedDisplayMode.getWidth(),cachedDisplayMode.getHeight());
			GL.setup();
		}
	}
	
	public static boolean closeRequested() {
		return windowAWT.closeRequested || Display.isCloseRequested();
	}
	
	public static void maximize() {
		windowAWT.maximize();
	}
	public static void unmaximize() {
		windowAWT.unmaximize();
	}
	public static boolean maximized() {
		return windowAWT.maximized();
	}
	
	private static void tryCreatingDisplay() {
		if (tryCreatingDisplay(new PixelFormat(8,8,8,4))) return;
		capabilities.setMultisampleSupport(false);
		
		if (tryCreatingDisplay(new PixelFormat(8,8,8))) return;
		capabilities.setStencilSupport(false);
		
		if (tryCreatingDisplay(new PixelFormat(8,8,0))) return;
		capabilities.setAlphaSupport(false);
		
		if (tryCreatingDisplay(new PixelFormat())) return;
		modules.setGraphicsSupport(false);
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
		if (State.get() != null) Display.sync(State.get().fps());
		advanceFrame();
		while (isRunning) {
			KInput.update();
			if (modules.graphics()) MInput.update();
			game.gameLoop();
			if (State.get() != null) advanceFrame(State.get().fps());
		}
	}
	public static void advanceFrame() {
		advanceFrame(0);
	}
	public static void advanceFrame(int fps) {
		if (modules.graphics()) GL.unbind();
		Debug.advance();
		updateDelta();
		if (closeRequested()) isRunning = false;
		if (modules.graphics() && isRunning) {
			Display.update();
			if (displayChanged) {
				State state = State.get();
				if (state != null) state.displayChange(new Vector2i(Display.getWidth(),Display.getHeight()),Display.isFullscreen());
				resetGL();
				displayChanged = false;
			}
		}
		if (fps > 0) Display.sync(fps);
	}
	
	protected static void displayChange(Vector2 v) {
		displayChanged = true;
		cachedDisplayMode = new DisplayMode(v.Xi(),v.Yi());
	}
	
	public static void handle(Throwable t) {
		t.printStackTrace();
	}
	
	public static double getDoubleTime() {
		return 1d*Sys.getTime()/Sys.getTimerResolution();
	}
	public static double getDelta() {
		return lastDelta;
	}
	protected static double updateDelta() {
		double time = Gamelib.getDoubleTime();
	    double delta = time-lastFrame;
	    lastFrame = time;
	    return lastDelta = delta;
	}
}