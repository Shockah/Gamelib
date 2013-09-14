package pl.shockah.glib.gl;

import static org.lwjgl.opengl.GL11.*;
import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.lwjgl.BufferUtils;
import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.geom.Shape;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.geom.vector.Vector2f;
import pl.shockah.glib.geom.vector.Vector2i;
import pl.shockah.glib.gl.color.Color;
import pl.shockah.glib.gl.tex.ITextureSupplier;
import pl.shockah.glib.gl.tex.Image;
import pl.shockah.glib.state.State;

public class Graphics {
	private static BlendMode defaultBlendMode = BlendMode.Normal;
	private static boolean init = false;
	private static Color color = null;
	private static DoubleBuffer tmpTransformedClip = BufferUtils.createDoubleBuffer(4);
	protected static Graphics lastGraphics = null;
	
	protected static void init() {
		if (init) return;
		init = true;
		if (color == null) setColor(Color.White);
	}
	
	public static void setColor(Color color) {
		if (Graphics.color != null && !Graphics.color.equals(color)) color.unbind();
		Graphics.color = color;
		color.bind();
	}
	
	public static void setDefaultBlendMode(BlendMode bm) {
		defaultBlendMode = bm;
	}
	public static BlendMode getDefaultBlendMode() {
		return defaultBlendMode;
	}
	
	protected Graphics redirect = null;
	protected List<Transformation> transformations = new ArrayList<>();
	protected List<Rectangle> clipStack = new LinkedList<>();
	protected List<Rectangle> transformedClipStack = new LinkedList<>();
	protected boolean absolute = false;
	
	public final void preDraw() {
		init();
		if (redirect != null) {
			redirect.preDraw();
			return;
		}
		if (lastGraphics != null && lastGraphics != this) lastGraphics.onUnbind();
		onBind();
		
		if (lastGraphics != this) {
			GL.popMatrixOnce();
			GL.pushMatrixOnce();
			if (!absolute) applyTransformations();
			applyClip(clipStack.isEmpty() ? null : clipStack.get(clipStack.size()-1));
			applyTransformedClip(transformedClipStack.isEmpty() ? null : transformedClipStack.get(transformedClipStack.size()-1));
		}
		lastGraphics = this;
	}
	protected void onBind() {
		GL.unbindSurface();
	}
	protected void onUnbind() {}
	
	protected void applyTransformations() {
		if (lastGraphics != this) return;
		for (Transformation t : transformations) t.apply();
	}
	protected void unapplyTransformations() {
		if (lastGraphics != this) return;
		GL.popMatrixOnce();
		GL.pushMatrixOnce();
	}
	
	public void translate(Vector2d v) {
		translate(v.x,v.y);
	}
	public void translate(double x, double y) {
		if (redirect != null) {
			redirect.translate(x,y);
			return;
		}
		Transformation t = new TransformationTranslate(new Vector2d(x,y));
		transformations.add(t);
		if (lastGraphics == this) t.apply();
	}
	
	public void scale(Vector2d v) {
		scale(v.x,v.y);
	}
	public void scale(double x, double y) {
		if (redirect != null) {
			redirect.scale(x,y);
			return;
		}
		Transformation t = new TransformationScale(new Vector2d(x,y));
		transformations.add(t);
		if (lastGraphics == this) t.apply();
	}
	
	public int popTransformation() {
		if (transformations.isEmpty()) return 0;
		transformations.get(transformations.size()-1).unapply();
		transformations.remove(transformations.size()-1);
		return transformations.size();
	}
	public void clearTransformations() {
		unapplyTransformations();
		transformations.clear();
	}
	
	public void pushClip(Rectangle rect) {
		if (redirect != null) {
			redirect.pushClip(rect);
			return;
		}
		if (((rect == null) ^ clipStack.isEmpty()) || !clipStack.get(clipStack.size()-1).equals(rect)) {
			applyClip(rect);
			if (rect == null) {
				if (!clipStack.isEmpty()) clipStack.remove(clipStack.size()-1);
				if (!clipStack.isEmpty()) {
					rect = clipStack.get(clipStack.size()-1);
					applyClip(rect);
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
		if (redirect != null) {
			redirect.clearClip();
			return;
		}
		clipStack.clear();
		if (lastGraphics != this) return;
		glDisable(GL_SCISSOR_TEST);
	}
	protected void applyClip(Rectangle rect) {
		if (redirect != null) {
			redirect.applyClip(rect);
			return;
		}
		if (lastGraphics != this) return;
		if (rect == null) glDisable(GL_SCISSOR_TEST); else {
			glScissor((int)(rect.pos.x),(int)(GL.flipped() ? State.get().getDisplaySize().y-rect.pos.y-rect.size.y : rect.pos.y),(int)rect.size.x,(int)rect.size.y);
			glEnable(GL_SCISSOR_TEST);
		}
	}
	
	public void pushTransformedClip(Rectangle rect) {
		if (redirect != null) {
			redirect.pushTransformedClip(rect);
			return;
		}
		if (((rect == null) ^ transformedClipStack.isEmpty()) || !transformedClipStack.get(transformedClipStack.size()-1).equals(rect)) {
			applyTransformedClip(rect);
			if (rect == null) {
				if (!transformedClipStack.isEmpty()) transformedClipStack.remove(transformedClipStack.size()-1);
				if (!transformedClipStack.isEmpty()) {
					rect = transformedClipStack.get(transformedClipStack.size()-1);
					applyTransformedClip(rect);
				}
			} else transformedClipStack.add(rect.copyMe());
		}
	}
	public void pushTransformedClip(double x, double y, double w, double h) {
		pushTransformedClip(new Rectangle(x,y,w,h));
	}
	public void popTransformedClip() {
		pushTransformedClip(null);
	}
	public void clearTransformedClip() {
		if (redirect != null) {
			redirect.clearTransformedClip();
			return;
		}
		transformedClipStack.clear();
		if (lastGraphics != this) return;
		glDisable(GL_CLIP_PLANE0);
		glDisable(GL_CLIP_PLANE1);
		glDisable(GL_CLIP_PLANE2);
		glDisable(GL_CLIP_PLANE3);
	}
	protected void applyTransformedClip(Rectangle rect) {
		if (redirect != null) {
			redirect.applyTransformedClip(rect);
			return;
		}
		if (lastGraphics != this) return;
		if (rect == null) {
			glDisable(GL_CLIP_PLANE0);
			glDisable(GL_CLIP_PLANE1);
			glDisable(GL_CLIP_PLANE2);
			glDisable(GL_CLIP_PLANE3);
		} else {
			glEnable(GL_CLIP_PLANE0);
			tmpTransformedClip.put(1).put(0).put(0).put(-rect.pos.x).flip();
			glClipPlane(GL_CLIP_PLANE0,tmpTransformedClip);
			
			glEnable(GL_CLIP_PLANE1);
			tmpTransformedClip.put(-1).put(0).put(0).put(rect.pos.x+rect.size.x).flip();
			glClipPlane(GL_CLIP_PLANE1,tmpTransformedClip);
			
			glEnable(GL_CLIP_PLANE2);
			tmpTransformedClip.put(0).put(1).put(0).put(-rect.pos.y).flip();
			glClipPlane(GL_CLIP_PLANE2,tmpTransformedClip);
			
			glEnable(GL_CLIP_PLANE3);
			tmpTransformedClip.put(0).put(-1).put(0).put(rect.pos.y+rect.size.y).flip();
			glClipPlane(GL_CLIP_PLANE3,tmpTransformedClip);
		}
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
		glClear(GL_COLOR_BUFFER_BIT);
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
	
	public void draw(GLList gll) {draw(gll,0,0);}
	public void draw(GLList gll, Vector2d v) {draw(gll,v.x,v.y);}
	public void draw(GLList gll, Vector2f v) {draw(gll,v.x,v.y);}
	public void draw(GLList gll, Vector2i v) {draw(gll,v.x,v.y);}
	public void draw(GLList gll, double x, double y) {
		if (redirect != null) {
			redirect.draw(gll,x,y);
			return;
		}
		glCallList(gll.getID());
	}
	
	public void drawAbsolute() {
		if (!absolute) {
			absolute = true;
			unapplyTransformations();
			applyClip(clipStack.isEmpty() ? null : clipStack.get(clipStack.size()-1));
		}
	}
	public void drawTransformed() {
		if (absolute) {
			absolute = false;
			applyTransformations();
			applyClip(clipStack.isEmpty() ? null : clipStack.get(clipStack.size()-1));
		}
	}
	public void toggleAbsolute() {
		absolute = !absolute;
		if (absolute) unapplyTransformations(); else applyTransformations();
		applyClip(clipStack.isEmpty() ? null : clipStack.get(clipStack.size()-1));
	}
	public boolean drawingAbsolute() {
		return absolute;
	}
	
	public static abstract class Transformation {
		public abstract void apply();
		public abstract void unapply();
	}
	public static class TransformationTranslate extends Transformation {
		protected final Vector2d v;
		public TransformationTranslate(Vector2d v) {
			this.v = v;
		}
		public void apply() {glTranslated(v.x,v.y,0d);}
		public void unapply() {glTranslated(-v.x,-v.y,0d);}
	}
	public static class TransformationScale extends Transformation {
		protected final Vector2d v;
		public TransformationScale(Vector2d v) {
			this.v = v;
		}
		public void apply() {glScaled(v.x,v.y,1d);}
		public void unapply() {glScaled(-v.x,-v.y,1d);}
	}
}