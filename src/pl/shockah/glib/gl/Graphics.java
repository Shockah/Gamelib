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
	
	public static void setColor(Color color) {
		if (Graphics.color != null && !Graphics.color.equals(color)) color.unbind();
		Graphics.color = color;
		color.bind();
	}
	
	public static void pushClip(Rectangle rect) {
		if (((rect == null) ^ clipStack.isEmpty()) || !clipStack.get(clipStack.size()-1).equals(rect)) {
			if (rect != null) {
				glScissor((int)rect.pos.x,(int)(GL.flipped() ? State.get().getDisplaySize().y-rect.pos.y-1-rect.size.y : rect.pos.y),(int)rect.size.x,(int)rect.size.y);
				glEnable(GL_SCISSOR_TEST);
			} else glDisable(GL_SCISSOR_TEST);
			
			if (rect == null) {
				if (!clipStack.isEmpty()) clipStack.remove(clipStack.size()-1);
				if (!clipStack.isEmpty()) {
					rect = clipStack.get(clipStack.size()-1);
					glScissor((int)rect.pos.x,(int)(GL.flipped() ? State.get().getDisplaySize().y-rect.pos.y-1-rect.size.y : rect.pos.y),(int)rect.size.x,(int)rect.size.y);
					glEnable(GL_SCISSOR_TEST);
				}
			} else clipStack.add(rect.copyMe());
		}
	}
	public static void pushClip(double x, double y, double w, double h) {
		pushClip(new Rectangle(x,y,w,h));
	}
	public static void popClip() {
		pushClip(null);
	}
	public static void clearClip() {
		clipStack.clear();
		glDisable(GL_SCISSOR_TEST);
	}
	
	protected Graphics redirect = null;
	
	public void init() {
		if (init) return;
		init = true;
		if (color == null) setColor(Color.White);
	}
	
	public void preDraw() {
		if (redirect != null) {
			redirect.preDraw();
			return;
		}
		GL.unbindSurface();
	}
	
	public void clear() {
		if (redirect != null) {
			redirect.clear();
			return;
		}
		clear(Color.Black);
	}
	public void clear(Color color) {
		if (redirect != null) {
			redirect.clear(color);
			return;
		}
		preDraw();
		glClearColor(color.Rf(),color.Gf(),color.Bf(),color.Af());
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
	}
	
	public final void setRedirect(Graphics g) {
		if (g == null) return;
		if (g.equals(redirect)) return;
		if (redirect == null) {
			redirect = g;
		} else redirect.setRedirect(g);
	}
	public final void clearRedirect() {
		if (redirect == null) return;
		if (redirect.redirect == null) {
			redirect = null;
		} else redirect.clearRedirect();
	}
	public final Graphics getRedirect() {
		return redirect;
	}
	
	public void draw(Shape shape) {
		if (redirect != null) {
			redirect.draw(shape);
			return;
		}
		shape.draw(this);
	}
	public void draw(Shape shape, boolean filled) {
		if (redirect != null) {
			redirect.draw(shape,filled);
			return;
		}
		shape.draw(this,filled);
	}
	
	public void draw(ITextureSupplier ts) {draw(ts,0,0);}
	public void draw(ITextureSupplier ts, Vector2d v) {draw(ts,v.x,v.y);}
	public void draw(ITextureSupplier ts, Vector2f v) {draw(ts,v.x,v.y);}
	public void draw(ITextureSupplier ts, Vector2i v) {draw(ts,v.x,v.y);}
	public void draw(ITextureSupplier ts, double x, double y) {
		if (redirect != null) {
			redirect.draw(ts,x,y);
			return;
		}
		ts.drawTexture(this,x,y);
	}
	
	public void draw(Image image, double rotation) {draw(image,0,0,rotation);}
	public void draw(Image image, Vector2d v, double rotation) {draw(image,v.x,v.y,rotation);}
	public void draw(Image image, Vector2f v, double rotation) {draw(image,v.x,v.y,rotation);}
	public void draw(Image image, Vector2i v, double rotation) {draw(image,v.x,v.y,rotation);}
	public void draw(Image image, double x, double y, double rotation) {
		if (redirect != null) {
			redirect.draw(image,x,y,rotation);
			return;
		}
		double rot = image.rotation.angle;
		image.rotation.angle = rotation;
		image.drawTexture(this,x,y);
		image.rotation.angle = rot;
	}
	
	public void draw(Surface surface) {draw(surface,0,0,0);}
	public void draw(Surface surface, double rotation) {draw(surface,0,0,rotation);}
	public void draw(Surface surface, Vector2d v) {draw(surface,v.x,v.y,0);}
	public void draw(Surface surface, Vector2f v) {draw(surface,v.x,v.y,0);}
	public void draw(Surface surface, Vector2i v) {draw(surface,v.x,v.y,0);}
	public void draw(Surface surface, Vector2d v, double rotation) {draw(surface,v.x,v.y,rotation);}
	public void draw(Surface surface, Vector2f v, double rotation) {draw(surface,v.x,v.y,rotation);}
	public void draw(Surface surface, Vector2i v, double rotation) {draw(surface,v.x,v.y,rotation);}
	public void draw(Surface surface, double x, double y) {draw(surface,x,y,0);}
	public void draw(Surface surface, double x, double y, double rotation) {
		if (redirect != null) {
			redirect.draw(surface,x,y,rotation);
			return;
		}
		draw(surface.image(),x,y,rotation);
	}
}