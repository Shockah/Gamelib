package pl.shockah.glib;

public final class Debug {
	public static Debug last = new Debug(), current = new Debug();
	protected static int fpsCounter = 0;
	protected static double fpsTimer = 0d;
	
	public static void advance() {
		last = current;
		current = new Debug();
	}
	
	public int bindTexture = 0, bindSurface = 0, bindShader = 0, fps = 0;
	public double doubleTime = 0d;
	
	private Debug() {
		doubleTime = Gamelib.getDoubleTime();
		
		fps = last == null ? 0 : last.fps;
		fpsTimer += doubleTime-(last == null ? doubleTime : last.doubleTime);
		fpsCounter++;
		if (fpsTimer >= 1d) {
			fpsTimer -= 1d;
			fps = fpsCounter;
			fpsCounter = 0;
		}
	}
}