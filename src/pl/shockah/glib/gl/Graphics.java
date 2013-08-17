package pl.shockah.glib.gl;

import static org.lwjgl.opengl.GL11.*;
import java.util.LinkedList;
import java.util.List;
import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.geom.Shape;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.geom.vector.Vector2f;
import pl.shockah.glib.geom.vector.Vector2i;
import pl.shockah.glib.gl.color.Color;
import pl.shockah.glib.state.State;

public class Graphics {
	private static boolean init = false;
	private static Color color = null;
	private static List<Rectangle> clipStack = new LinkedList<>();
	
	public void init() {
		if (init) return;
		init = true;
		
		if (color == null) setColor(Color.White);
	}
	
	public void setColor(Color color) {
		if (Graphics.color != null && !Graphics.color.equals(color)) color.unbind();
		Graphics.color = color;
		color.bind();
	}
	
	public void pushClip(Rectangle rect) {
		if (((rect == null) ^ clipStack.isEmpty()) || !clipStack.get(clipStack.size()-1).equals(rect)) {
			if (rect != null) {
				glScissor((int)rect.pos.x,(int)(State.get().getDisplaySize().y-rect.pos.y-1-rect.size.y),(int)rect.size.x,(int)rect.size.y);
				glEnable(GL_SCISSOR_TEST);
			} else glDisable(GL_SCISSOR_TEST);
			
			if (rect == null) {
				if (!clipStack.isEmpty()) clipStack.remove(clipStack.size()-1);
				if (!clipStack.isEmpty()) {
					rect = clipStack.get(clipStack.size()-1);
					glScissor((int)rect.pos.x,(int)(State.get().getDisplaySize().y-rect.pos.y-1-rect.size.y),(int)rect.size.x,(int)rect.size.y);
					glEnable(GL_SCISSOR_TEST);
				}
			} else clipStack.add(rect.copyMe());
		}
	}
	public void pushClip(double x, double y, double w, double h) {
		pushClip(new Rectangle(x,y,w,h));
	}
	public void popClip() {
		pushClip(null);
	}
	public void clearClip() {
		clipStack.clear();
		glDisable(GL_SCISSOR_TEST);
	}
	
	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public void draw(Shape shape) {
		shape.draw(this);
	}
	public void draw(Shape shape, boolean filled) {
		shape.draw(this,filled);
	}
	
	public void draw(ITextureSupplier ts) {draw(ts,0,0);}
	public void draw(ITextureSupplier ts, Vector2d v) {draw(ts,v.x,v.y);}
	public void draw(ITextureSupplier ts, Vector2f v) {draw(ts,v.x,v.y);}
	public void draw(ITextureSupplier ts, Vector2i v) {draw(ts,v.x,v.y);}
	public void draw(ITextureSupplier ts, double x, double y) {
		ts.drawTexture(this,x,y);
	}
	
	public void draw(Image image, double rotation) {draw(image,0,0,rotation);}
	public void draw(Image image, Vector2d v, double rotation) {draw(image,v.x,v.y,rotation);}
	public void draw(Image image, Vector2f v, double rotation) {draw(image,v.x,v.y,rotation);}
	public void draw(Image image, Vector2i v, double rotation) {draw(image,v.x,v.y,rotation);}
	public void draw(Image image, double x, double y, double rotation) {
		double rot = image.rotation.angle;
		image.rotation.angle = rotation;
		image.drawTexture(this,x,y);
		image.rotation.angle = rot;
	}
}