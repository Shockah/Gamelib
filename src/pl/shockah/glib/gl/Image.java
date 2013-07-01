package pl.shockah.glib.gl;

import static org.lwjgl.opengl.GL11.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import pl.shockah.glib.LoadableProcessor;
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
			center = getTextureSize().toDouble().div(2);
		}
	}
	
	@Target(ElementType.FIELD) @Retention(RetentionPolicy.RUNTIME) public static @interface Loadable {
		public String path() default "assets/images/<field.name>.png";
		public LoadableProcessor.AssetType type() default LoadableProcessor.AssetType.Internal;
	}
}