package pl.shockah.glib;

public class Debug {
	public static Debug current = new Debug(), last = new Debug();
	
	public static void advance() {
		last = current;
		current = new Debug();
	}
	
	public int bindTexture = 0, bindSurface = 0, bindShader = 0;
}