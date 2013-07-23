package pl.shockah.glib.animfx;

public abstract class Interpolate {
	public static final Interpolate
		Linear = new Interpolate(){
			protected double interpolateBase(double d1, double d2, double d) {
				return d1+(d2-d1)*d;
			}
		},
		Smoothstep = new Interpolate(){
			protected double interpolateBase(double d1, double d2, double d) {
				double ss = Math.pow(d,2)*(3d-2d*d);
				return d1+(d2-d1)*ss;
			}
		},
		Smoothstep2 = new Interpolate(){
			protected double interpolateBase(double d1, double d2, double d) {
				double ss = Math.pow(d,2)*(3d-2d*d);
				ss = Math.pow(ss,2);
				return d1+(d2-d1)*ss;
			}
		},
		Smoothstep3 = new Interpolate(){
			protected double interpolateBase(double d1, double d2, double d) {
				double ss = Math.pow(d,2)*(3d-2d*d);
				ss = Math.pow(ss,3);
				return d1+(d2-d1)*ss;
			}
		};
	
	public int interpolate(int i1, int i2, double d) {return (int)interpolateBase(i1,i2,d);}
	public float interpolate(float f1, float f2, double d) {return (float)interpolateBase(f1,f2,d);}
	public double interpolate(double d1, double d2, double d) {return interpolateBase(d1,d2,d);}
	
	protected abstract double interpolateBase(double d1, double d2, double d);
}