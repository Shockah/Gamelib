package pl.shockah.glib;

public final class Math2 {
	public static double root(double value, double degree) {
		return Math.pow(value,1d/degree);
	}
	
	public static double ldirX(double dist, double angle) {
		return -Math.cos(Math.toRadians(angle+180))*dist;
	}
	public static double ldirY(double dist, double angle) {
		return Math.sin(Math.toRadians(angle+180))*dist;
	}
	
	public static double frac(double value) {
		double sign = Math.signum(value);
		value = Math.abs(value);
		return (value-Math.floor(value))*sign;
	}
	
	public static int minI(int... values) {
		int min = values[0];
		for (int i = 1; i < values.length; i++) if (values[i] < min) min = values[i];
		return min;
	}
	public static float minF(float... values) {
		float min = values[0];
		for (int i = 1; i < values.length; i++) if (values[i] < min) min = values[i];
		return min;
	}
	public static double minD(double... values) {
		double min = values[0];
		for (int i = 1; i < values.length; i++) if (values[i] < min) min = values[i];
		return min;
	}
	
	public static int maxI(int... values) {
		int max = values[0];
		for (int i = 1; i < values.length; i++) if (values[i] > max) max = values[i];
		return max;
	}
	public static float maxF(float... values) {
		float max = values[0];
		for (int i = 1; i < values.length; i++) if (values[i] > max) max = values[i];
		return max;
	}
	public static double maxD(double... values) {
		double max = values[0];
		for (int i = 1; i < values.length; i++) if (values[i] > max) max = values[i];
		return max;
	}
	
	public static int limitI(int value, int min, int max) {
		return Math.min(Math.max(value,min),max);
	}
	public static int limitI(int value, int limit) {
		limit = Math.abs(limit);
		return Math.min(Math.max(value,-limit),limit);
	}
	public static float limitF(float value, float min, float max) {
		return Math.min(Math.max(value,min),max);
	}
	public static float limitF(float value, float limit) {
		limit = Math.abs(limit);
		return Math.min(Math.max(value,-limit),limit);
	}
	public static double limitD(double value, double min, double max) {
		return Math.min(Math.max(value,min),max);
	}
	public static double limitD(double value, double limit) {
		limit = Math.abs(limit);
		return Math.min(Math.max(value,-limit),limit);
	}
}