package pl.shockah.glib;

import org.lwjgl.Sys;

public final class Math2 {
	public static double root(double value, double degree) {
		return Math.pow(value,1d/degree);
	}
	
	public static double ldirX(double dist, double angle) {
		return -Math.cos(Math.toRadians(angle+180d))*dist;
	}
	public static double ldirY(double dist, double angle) {
		return Math.sin(Math.toRadians(angle+180d))*dist;
	}
	
	public static double frac(double value) {
		double sign = Math.signum(value);
		value = Math.abs(value);
		return (value-Math.floor(value))*sign;
	}
	
	public static double min(double... values) {
		double min = values[0];
		for (int i = 1; i < values.length; i++) if (values[i] < min) min = values[i];
		return min;
	}
	public static float min(float... values) {
		float min = values[0];
		for (int i = 1; i < values.length; i++) if (values[i] < min) min = values[i];
		return min;
	}
	public static long min(long... values) {
		long min = values[0];
		for (int i = 1; i < values.length; i++) if (values[i] < min) min = values[i];
		return min;
	}
	public static int min(int... values) {
		int min = values[0];
		for (int i = 1; i < values.length; i++) if (values[i] < min) min = values[i];
		return min;
	}
	
	public static double max(double... values) {
		double max = values[0];
		for (int i = 1; i < values.length; i++) if (values[i] > max) max = values[i];
		return max;
	}
	public static float max(float... values) {
		float max = values[0];
		for (int i = 1; i < values.length; i++) if (values[i] > max) max = values[i];
		return max;
	}
	public static long max(long... values) {
		long max = values[0];
		for (int i = 1; i < values.length; i++) if (values[i] > max) max = values[i];
		return max;
	}
	public static int max(int... values) {
		int max = values[0];
		for (int i = 1; i < values.length; i++) if (values[i] > max) max = values[i];
		return max;
	}
	
	public static double limit(double value, double min, double max) {
		return Math.min(Math.max(value,min),max);
	}
	public static float limit(float value, float min, float max) {
		return Math.min(Math.max(value,min),max);
	}
	public static long limit(long value, long min, long max) {
		return Math.min(Math.max(value,min),max);
	}
	public static int limit(int value, int min, int max) {
		return Math.min(Math.max(value,min),max);
	}
	
	public static double limit(double value, double limit) {
		limit = Math.abs(limit);
		return Math.min(Math.max(value,-limit),limit);
	}
	public static float limit(float value, float limit) {
		limit = Math.abs(limit);
		return Math.min(Math.max(value,-limit),limit);
	}
	public static long limit(long value, long limit) {
		limit = Math.abs(limit);
		return Math.min(Math.max(value,-limit),limit);
	}
	public static int limit(int value, int limit) {
		limit = Math.abs(limit);
		return Math.min(Math.max(value,-limit),limit);
	}
	
	public static double getDoubleTime() {
		return 1d*Sys.getTime()/Sys.getTimerResolution();
	}
}