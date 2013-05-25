package pl.shockah.glib;

import org.lwjgl.Sys;

public final class Delta {
	private long lastFrame;
	private double delta;
	
	public Delta() {
		lastFrame = Sys.getTime();
	}
	
	public double getDelta() {
		return delta;
	}
	
	public double update() {
		long time = Sys.getTime();
		int delta = (int)(time-lastFrame);
		lastFrame = time;
		return this.delta = 1d*delta/Sys.getTimerResolution();
	}
}