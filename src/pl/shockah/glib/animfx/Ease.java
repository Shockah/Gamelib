package pl.shockah.glib.animfx;

public abstract class Ease {
	public static final Ease
		Linear = new Ease(){
			protected double easeBase(double d1, double d2, double d) {
				return d1+(d2-d1)*d;
			}
		};
	
	private static class EaseSmoothstep extends Ease {
		protected final double easeBase(double d1, double d2, double d) {
			return d1+(d2-d1)*smoothstepModifier(d);
		}
		protected double smoothstepModifier(double d) {
			return Math.pow(d,2)*(3d-2d*d);
		}
	}
	
	public static final class Smoothstep {
		public static final Ease
			P1 = new EaseSmoothstep(),
			P2 = new EaseSmoothstep(){
				protected double smoothstepModifier(double d) {
					return Math.pow(super.smoothstepModifier(d),2);
				}
			},
			P3 = new EaseSmoothstep(){
				protected double smoothstepModifier(double d) {
					return Math.pow(super.smoothstepModifier(d),3);
				}
			};
	}
		
	private static abstract class EaseEase extends Ease {
		protected double easeBase(double d1, double d2, double d) {
			return ease(d,d1,d2-d1,1);
		}
		protected abstract double ease(double t, double b, double c, double d);
	}
	
	//original code: http://github.com/jesusgollonet/processing-penner-easing
	public static final class Sine {
		public static final Ease
			In = new EaseEase(){
				protected double ease(double t, double b, double c, double d) {
					return -c * Math.cos(t/d * (Math.PI/2)) + c + b;
				}
			},
			Out = new EaseEase(){
				protected double ease(double t, double b, double c, double d) {
					return c * Math.sin(t/d * (Math.PI/2)) + b;
				}
			},
			InOut = new EaseEase(){
				protected double ease(double t, double b, double c, double d) {
					return -c/2 * (Math.cos(Math.PI*t/d) - 1) + b;
				}
			};
	}
	public static final class Quad {
		public static final Ease
			In = new EaseEase(){
				protected double ease(double t, double b, double c, double d) {
					return c*(t/=d)*t + b;
				}
			},
			Out = new EaseEase(){
				protected double ease(double t, double b, double c, double d) {
					return -c *(t/=d)*(t-2) + b;
				}
			},
			InOut = new EaseEase(){
				protected double ease(double t, double b, double c, double d) {
					if ((t/=d/2) < 1) return c/2*t*t + b;
					return -c/2 * ((--t)*(t-2) - 1) + b;
				}
			};
	}
	public static final class Cubic {
		public static final Ease
			In = new EaseEase(){
				protected double ease(double t, double b, double c, double d) {
					return c*(t/=d)*t*t + b;
				}
			},
			Out = new EaseEase(){
				protected double ease(double t, double b, double c, double d) {
					return c*((t=t/d-1)*t*t + 1) + b;
				}
			},
			InOut = new EaseEase(){
				protected double ease(double t, double b, double c, double d) {
					if ((t/=d/2) < 1) return c/2*t*t*t + b;
					return c/2*((t-=2)*t*t + 2) + b;
				}
			};
	}
	public static final class Quart {
		public static final Ease
			In = new EaseEase(){
				protected double ease(double t, double b, double c, double d) {
					return c*(t/=d)*t*t*t + b;
				}
			},
			Out = new EaseEase(){
				protected double ease(double t, double b, double c, double d) {
					return -c * ((t=t/d-1)*t*t*t - 1) + b;
				}
			},
			InOut = new EaseEase(){
				protected double ease(double t, double b, double c, double d) {
					if ((t/=d/2) < 1) return c/2*t*t*t*t + b;
					return -c/2 * ((t-=2)*t*t*t - 2) + b;
				}
			};
	}
	public static final class Quint {
		public static final Ease
			In = new EaseEase(){
				protected double ease(double t, double b, double c, double d) {
					return c*(t/=d)*t*t*t*t + b;
				}
			},
			Out = new EaseEase(){
				protected double ease(double t, double b, double c, double d) {
					return c*((t=t/d-1)*t*t*t*t + 1) + b;
				}
			},
			InOut = new EaseEase(){
				protected double ease(double t, double b, double c, double d) {
					if ((t/=d/2) < 1) return c/2*t*t*t*t*t + b;
					return c/2*((t-=2)*t*t*t*t + 2) + b;
				}
			};
	}
	public static final class Expo {
		public static final Ease
			In = new EaseEase(){
				protected double ease(double t, double b, double c, double d) {
					return (t==0) ? b : c * Math.pow(2, 10 * (t/d - 1)) + b;
				}
			},
			Out = new EaseEase(){
				protected double ease(double t, double b, double c, double d) {
					return (t==d) ? b+c : c * (-Math.pow(2, -10 * t/d) + 1) + b;	
				}
			},
			InOut = new EaseEase(){
				protected double ease(double t, double b, double c, double d) {
					if (t==0) return b;
					if (t==d) return b+c;
					if ((t/=d/2) < 1) return c/2 * Math.pow(2, 10 * (t - 1)) + b;
					return c/2 * (-Math.pow(2, -10 * --t) + 2) + b;
				}
			};
	}
	public static final class Circ {
		public static final Ease
			In = new EaseEase(){
				protected double ease(double t, double b, double c, double d) {
					return -c * (Math.sqrt(1 - (t/=d)*t) - 1) + b;
				}
			},
			Out = new EaseEase(){
				protected double ease(double t, double b, double c, double d) {
					return c * Math.sqrt(1 - (t=t/d-1)*t) + b;
				}
			},
			InOut = new EaseEase(){
				protected double ease(double t, double b, double c, double d) {
					if ((t/=d/2) < 1) return -c/2 * (Math.sqrt(1 - t*t) - 1) + b;
					return c/2 * (Math.sqrt(1 - (t-=2)*t) + 1) + b;
				}
			};
	}
	public static final class Back {
		protected static final double s = 1.70158d;
		
		public static final Ease
			In = new EaseEase(){
				protected double ease(double t, double b, double c, double d) {
					return c*(t/=d)*t*((s+1)*t - s) + b;
				}
			},
			Out = new EaseEase(){
				protected double ease(double t, double b, double c, double d) {
					return c*((t=t/d-1)*t*((s+1)*t + s) + 1) + b;
				}
			},
			InOut = new EaseEase(){
				protected double ease(double t, double b, double c, double d) {
					double s = Back.s;
					if ((t/=d/2) < 1) return c/2*(t*t*(((s*=(1.525d))+1)*t - s)) + b;
					return c/2*((t-=2)*t*(((s*=(1.525f))+1)*t + s) + 2) + b;
				}
			};
	}
	public static final class Elastic {
		public static final Ease
			In = new EaseEase(){
				protected double ease(double t, double b, double c, double d) {
					if (t==0) return b; if ((t/=d)==1) return b+c;
					double p=d*.3d;
					double a=c;
					double s=p/4;
					return (a*Math.pow(2,-10*t) * Math.sin( (t*d-s)*(2*Math.PI)/p ) + c + b);	
				}
			},
			Out = new EaseEase(){
				protected double ease(double t, double b, double c, double d) {
					if (t==0) return b; if ((t/=d)==1) return b+c;
					double p=d*.3d;
					double a=c;
					double s=p/4;
					return -(a*Math.pow(2,10*(t-=1)) * Math.sin( (t*d-s)*(2*Math.PI)/p )) + b;
				}
			},
			InOut = new EaseEase(){
				protected double ease(double t, double b, double c, double d) {
					if (t==0) return b; if ((t/=d/2)==2) return b+c;
					double p=d*(.3d*1.5d);
					double a=c;
					double s=p/4;
					if (t < 1) return -.5d*(a*Math.pow(2,10*(t-=1)) * Math.sin( (t*d-s)*(2*Math.PI)/p )) + b;
					return a*Math.pow(2,-10*(t-=1)) * Math.sin( (t*d-s)*(2*Math.PI)/p )*.5d + c + b;
				}
			};
	}
	public static final class Bounce {
		public static final Ease
			In = new EaseEase(){
				protected double ease(double t, double b, double c, double d) {
					return c - ((EaseEase)Out).ease (d-t, 0, c, d) + b;
				}
			},
			Out = new EaseEase(){
				protected double ease(double t, double b, double c, double d) {
					if ((t/=d) < (1/2.75d)) {
						return c*(7.5625d*t*t) + b;
					} else if (t < (2/2.75d)) {
						return c*(7.5625d*(t-=(1.5d/2.75d))*t + .75d) + b;
					} else if (t < (2.5/2.75)) {
						return c*(7.5625d*(t-=(2.25d/2.75d))*t + .9375d) + b;
					} else {
						return c*(7.5625d*(t-=(2.625d/2.75d))*t + .984375d) + b;
					}
				}
			},
			InOut = new EaseEase(){
				protected double ease(double t, double b, double c, double d) {
					if (t < d/2) return ((EaseEase)In).ease (t*2, 0, c, d) * .5f + b;
					else return ((EaseEase)Out).ease (t*2-d, 0, c, d) * .5f + c*.5f + b;
				}
			};
	}
	
	public int ease(int i1, int i2, double d) {return (int)easeBase(i1,i2,d);}
	public float ease(float f1, float f2, double d) {return (float)easeBase(f1,f2,d);}
	public double ease(double d1, double d2, double d) {return easeBase(d1,d2,d);}
	
	protected abstract double easeBase(double d1, double d2, double d);
}