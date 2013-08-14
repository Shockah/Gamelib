package pl.shockah.glib.gl;

import static org.lwjgl.opengl.GL11.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import pl.shockah.glib.LoadableProcessor;
import pl.shockah.glib.geom.Rectangle;
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
			
			glTranslated(rotation.center.x*scale.x,rotation.center.y*scale.y,0);
			glRotated(-rotation.angle,0f,0f,1f);
			glTranslated(-rotation.center.x*scale.x,-rotation.center.y*scale.y,0);
		}
	}
	protected void postDraw(Graphics g) {
		if (rotation.angle != 0) {
			glTranslated(rotation.center.x*scale.x,rotation.center.y*scale.y,0);
			glRotated(rotation.angle,0f,0f,1f);
			glTranslated(-rotation.center.x*scale.x,-rotation.center.y*scale.y,0);
		}
	}
	
	public Image part(int x, int y, int w, int h) {
		return new Image2(getTexture(),x,y,w,h);
	}
	public Image part(Rectangle rect) {
		return part((int)rect.pos.x,(int)rect.pos.y,(int)rect.size.x,(int)rect.size.y);
	}
	
	public void center() {
		offset = getSize().toDouble().div(2);
		rotation.center();
	}
	
	public class Rotation {
		public Vector2d center = new Vector2d();
		public double angle = 0;
		
		public void center() {
			center = getTextureSize().toDouble().div(2);
		}
	}
	
	protected static class Image2 extends Image {
		protected final int x, y, w, h;
		
		public Image2(Texture tex, int x, int y, int w, int h) {
			super(tex);
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
		}
		
		public Rectangle getTextureRect() {
			return new Rectangle(x,y,w,h);
		}
	}
	
	@Target(ElementType.FIELD) @Retention(RetentionPolicy.RUNTIME) public static @interface Loadable {
		public String path() default "assets/images/<field.name>.png";
		public LoadableProcessor.AssetType type() default LoadableProcessor.AssetType.Internal;
	}
}