package pl.shockah.glib.geom.vector;

public interface IVector2 {
	public double Xd();
	public float Xf();
	public int Xi();
	
	public double Yd();
	public float Yf();
	public int Yi();
	
	public Vector2d toDouble();
	public Vector2f toFloat();
	public Vector2i toInt();
	
	public double direction();
	public double direction(IVector2 v);
	public double deltaAngle(IVector2 v);
	public double deltaAngle(double angle);
}