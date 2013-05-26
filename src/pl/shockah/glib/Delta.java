package pl.shockah.glib;

import org.lwjgl.Sys;

public final class Delta {
	private long lastFrame;
	private long delta;
	private long timeLeft = 0;
	
	public Delta() {
		lastFrame = Sys.getTime();
	}
	
	public long getDelta() {
		return delta;
	}
	public long getTimeLeft() {
		return timeLeft;
	}
	public void subTimeLeft(long sub) {
		timeLeft -= sub;
	}
	public void resetTimeLeft() {
		timeLeft = 0;
	}
	
	public double update() {
		long time = Sys.getTime();
		System.out.println(">>> "+time);
		delta = time-lastFrame;
		timeLeft += delta;
		return delta;
	}
}