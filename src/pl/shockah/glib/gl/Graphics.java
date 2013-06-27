package pl.shockah.glib.gl;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2d;
import static org.lwjgl.opengl.GL11.glVertex2f;
import pl.shockah.glib.geom.Circle;
import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.geom.Shape;
import pl.shockah.glib.geom.polygon.ITriangulator;
import pl.shockah.glib.geom.polygon.Polygon;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.color.Color;

public class Graphics {
	private static boolean init = false;
	private static Color color = null;
	
	protected void init() {
		if (init) return;
		init = true;
		
		if (color == null) setColor(Color.White);
	}
	
	public void setColor(Color color) {
		if (Graphics.color != null && !Graphics.color.equals(color)) color.unbindMe();
		Graphics.color = color;
		color.bindMe();
	}
	
	public void draw(Shape shape) {draw(shape,0,0);}
	public void draw(Shape shape, boolean filled) {draw(shape,filled,0,0);}
	public void draw(Shape shape, Vector2d v) {draw(shape,v.x,v.y);}
	public void draw(Shape shape, double x, double y) {draw(shape,true,0,0);}
	public void draw(Shape shape, boolean filled, Vector2d v) {draw(shape,filled,v.x,v.y);}
	public void draw(Shape shape, boolean filled, double x, double y) {
		init();
		glTranslated(x,y,0);
		
		if (shape instanceof Rectangle) {
			Rectangle rect = (Rectangle)shape;
			if (filled) {
				glBegin(GL_QUADS);
				glVertex2d(rect.pos.x+x,rect.pos.y+y);
				glVertex2d(rect.pos.x+rect.size.x+x,rect.pos.y+y);
				glVertex2d(rect.pos.x+rect.size.x+x,rect.pos.y+rect.size.y+y);
				glVertex2d(rect.pos.x+x,rect.pos.y+rect.size.y+y);
				glEnd();
			} else {
				throw new UnsupportedOperationException();
			}
		} else if (shape instanceof Circle) {
			Circle circle = (Circle)shape;
			draw(circle.asPolygon(),filled,x,y);
		} else if (shape instanceof Polygon) {
			Polygon polygon = (Polygon)shape;
			if (filled) {
				ITriangulator tris = polygon.getTriangulator();
				for (Vector2d v : polygon.getPoints()) tris.addPolyPoint(new Vector2d(v.x+x,v.y+y));
				tris.triangulate();
				
				glBegin(GL_TRIANGLES);
				for (int i = 0; i < tris.getTriangleCount(); i++) {
					for (int p = 0; p<3; p++) {
						Vector2d v = tris.getTrianglePoint(i,p);
						glVertex2d(v.x,v.y);
					}
				}
				glEnd();
			} else {
				throw new UnsupportedOperationException();
			}
		}
		
		glTranslatef((float)-x,(float)-y,0);
	}
	
	public void draw(TextureSupplier ts) {draw(ts,0,0);}
	public void draw(TextureSupplier ts, Vector2d v) {draw(ts,v.x,v.y);}
	public void draw(TextureSupplier ts, double x, double y) {
		init();
		ts.getTexture().bindMe();
		glTranslated(x,y,0);
		
		ts.preDraw(this);
		glBegin(GL_QUADS);
		Rectangle texRect = ts.getTextureRect();
		internalDrawImage(0,0,(int)texRect.size.x,(int)texRect.size.y,(int)texRect.pos.x,(int)texRect.pos.y,(int)texRect.size.x,(int)texRect.size.y);
		glEnd();
		ts.postDraw(this);
		
		glTranslated(-x,-y,0);
		ts.getTexture().unbindMe();
	}
	
	private void internalDrawImage(float x, float y, float w, float h, float tx, float ty, float tw, float th) {
		glTexCoord2f(tx,ty);
		glVertex2f(x,y);
		glTexCoord2f(tx,ty+th);
		glVertex2f(x,y+h);
		glTexCoord2f(tx+tw,ty+th);
		glVertex2f(x+w,y+h);
		glTexCoord2f(tx+tw,ty);
		glVertex2f(x+w,y);
	}
}