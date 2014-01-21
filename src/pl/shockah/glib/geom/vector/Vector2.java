package pl.shockah.glib.geom.vector;

public abstract class Vector2 {
	public abstract double Xd();
	public abstract float Xf();
	public abstract int Xi();
	
	public abstract double Yd();
	public abstract float Yf();
	public abstract int Yi();
	
	public Vector2d toDouble() {return ToDouble();}
	public Vector2f toFloat() {return ToFloat();}
	public Vector2i toInt() {return ToInt();}
	public abstract Vector2d ToDouble();
	public abstract Vector2f ToFloat();
	public abstract Vector2i ToInt();
	
	public double dotProduct(Vector2 v) {
		return Xd()*v.Xd()+Yd()*v.Yd();
	}
	public double direction() {
		return new Vector2d().direction(this);
	}
	public double direction(Vector2 v) {
		return Math.toDegrees(Math.atan2(Yd()-v.Yd(),v.Xd()-Xd()));
	}
	public abstract double deltaAngle(Vector2 v);
	public abstract double deltaAngle(double angle);
}