package pl.shockah.glib.gl;

import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import pl.shockah.glib.geom.vector.Vector2f;
import pl.shockah.glib.gl.tex.Texture;

public class Image extends TextureSupplier {
	public Vector2f centerOfRotation = new Vector2f();
	public float rotation = 0;
	
	public Image(Texture tex) {
		super(tex);
	}
	
	protected void preDraw(Graphics g) {
		if (rotation != 0) {
			glTranslatef(centerOfRotation.x,centerOfRotation.y,0);
			glRotatef(rotation,0f,0f,1f);
			glTranslatef(-centerOfRotation.x,-centerOfRotation.y,0);
		}
	}
	protected void postDraw(Graphics g) {
		if (rotation != 0) {
			glTranslatef(centerOfRotation.x,centerOfRotation.y,0);
			glRotatef(-rotation,0f,0f,1f);
			glTranslatef(-centerOfRotation.x,-centerOfRotation.y,0);
		}
	}
}