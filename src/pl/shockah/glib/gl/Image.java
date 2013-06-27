package pl.shockah.glib.gl;

import static org.lwjgl.opengl.GL11.*;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.tex.Texture;

public class Image extends TextureSupplier {
	public Rotation rotation = new Rotation();
	
	public Image(Texture tex) {
		super(tex);
	}
	
	protected void preDraw(Graphics g) {
		if (rotation.angle != 0) {
			while (rotation.angle >= 360) rotation.angle -= 360;
			while (rotation.angle < 0) rotation.angle += 360;
			
			glTranslated(rotation.center.x,rotation.center.y,0);
			glRotated(-rotation.angle,0f,0f,1f);
			glTranslated(-rotation.center.x,-rotation.center.y,0);
		}
	}
	protected void postDraw(Graphics g) {
		if (rotation.angle != 0) {
			glTranslated(rotation.center.x,rotation.center.y,0);
			glRotated(rotation.angle,0f,0f,1f);
			glTranslated(-rotation.center.x,-rotation.center.y,0);
		}
	}
	
	public class Rotation {
		public Vector2d center = new Vector2d();
		public double angle = 0;
		
		public void center() {
			center = getSize().toDouble().div(2);
		}
	}
}