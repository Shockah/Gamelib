package pl.shockah.glib.animfx;

public abstract class Interpolate {
	public static final Interpolate
		Linear = new Interpolate(){
			public int interpolate(int i1, int i2, double d) {return (int)_interpolate(i1,i2,d);}
			public float interpolate(float f1, float f2, double d) {return (float)_interpolate(f1,f2,d);}
			public double interpolate(double d1, double d2, double d) {return _interpolate(d1,d2,d);}
			protected double _interpolate(double d1, double d2, double d) {
				return d1+(d2-d1)*d;
			}
		},
		Smoothstep = new Interpolate(){
			public int interpolate(int i1, int i2, double d) {return (int)_interpolate(i1,i2,d);}
			public float interpolate(float f1, float f2, double d) {return (float)_interpolate(f1,f2,d);}
			public double interpolate(double d1, double d2, double d) {return _interpolate(d1,d2,d);}
			protected double _interpolate(double d1, double d2, double d) {
				return d1+(d2-d1)*(Math.pow(d,2)*(3d-2d*d));
			}
		};
	
	public abstract int interpolate(int i1, int i2, double d);
	public abstract float interpolate(float f1, float f2, double d);
	public abstract double interpolate(double d1, double d2, double d);
}