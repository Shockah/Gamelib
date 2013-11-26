package pl.shockah.glib.geom;

import static org.lwjgl.opengl.GL11.*;
import pl.shockah.glib.geom.vector.Vector2;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.GL;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;

public class Rectangle9 extends Rectangle {
	protected Color cIn, cOut;
	protected Vector2d offTL = new Vector2d(), offBR = new Vector2d();
	
	public Rectangle9(double w, double h) {
		this(new Vector2d(),w,h);
	}
	public Rectangle9(Vector2 size) {
		this(new Vector2d(),size);
	}
	public Rectangle9(double x, double y, double w, double h) {
		this(new Vector2d(x,y),new Vector2d(w,h));
	}
	public Rectangle9(double x, double y, Vector2 size) {
		this(new Vector2d(x,y),size);
	}
	public Rectangle9(Vector2 pos, double w, double h) {
		this(pos,new Vector2d(w,h));
	}
	public Rectangle9(Rectangle rect) {
		this(rect.pos,rect.size);
	}
	public Rectangle9(Vector2 pos, Vector2 size) {
		super(pos,size);
	}
	
	public Rectangle9 setColors(Color inside, Color outside) {
		cIn = inside;
		cOut = outside;
		return this;
	}
	protected void copyColors(Graphics g) {
		Color c = g.getColor();
		if (cIn == null) cIn = c;
		if (cOut == null) cOut = c;
	}
	
	public Rectangle9 setOffsetTopLeft(Vector2 v) {return setOffsetTopLeft(v.Xd(),v.Yd());}
	public Rectangle9 setOffsetTopLeft(double x, double y) {
		offTL.set(x,y);
		return this;
	}
	
	public Rectangle9 setOffsetBottomRight(Vector2 v) {return setOffsetBottomRight(v.Xd(),v.Yd());}
	public Rectangle9 setOffsetBottomRight(double x, double y) {
		offBR.set(x,y);
		return this;
	}
	
	public Rectangle9 setOffset(Vector2 v) {return setOffset(v.Xd(),v.Yd(),v.Xd(),v.Yd());}
	public Rectangle9 setOffset(double x, double y) {return setOffset(x,y,x,y);}
	public Rectangle9 setOffset(Vector2 vTopLeft, Vector2 vBottomRight) {return setOffset(vTopLeft.Xd(),vTopLeft.Yd(),vBottomRight.Xd(),vBottomRight.Yd());}
	public Rectangle9 setOffset(double xTopLeft, double yTopLeft, double xBottomRight, double yBottomRight) {
		setOffsetTopLeft(xTopLeft,yTopLeft);
		setOffsetBottomRight(xBottomRight,yBottomRight);
		return this;
	}
	
	public void draw(Graphics g, boolean filled) {
		g.preDraw();
		GL.unbindTexture();
		copyColors(g);
		
		if (filled) {
			glBegin(GL_TRIANGLES);
				GL.bind(cOut); glVertex2d(pos.x+offTL.x,pos.y);
				GL.bind(cIn); glVertex2d(pos.x+offTL.x,pos.y+offTL.y);
				GL.bind(cOut); glVertex2d(pos.x,pos.y+offTL.y);
				
				GL.bind(cIn); glVertex2d(pos.x+size.x-offBR.x,pos.y+size.y-offBR.y);
				GL.bind(cOut); glVertex2d(pos.x+size.x,pos.y+size.y-offBR.y);
				GL.bind(cOut); glVertex2d(pos.x+size.x-offBR.x,pos.y+size.y);
				
				GL.bind(cOut); glVertex2d(pos.x+size.x-offBR.x,pos.y);
				GL.bind(cOut); glVertex2d(pos.x+size.x,pos.y+offTL.y);
				GL.bind(cIn); glVertex2d(pos.x+size.x-offBR.x,pos.y+offTL.y);
				
				GL.bind(cOut); glVertex2d(pos.x,pos.y+size.y-offBR.y);
				GL.bind(cIn); glVertex2d(pos.x+offTL.x,pos.y+size.y-offBR.y);
				GL.bind(cOut); glVertex2d(pos.x+offTL.x,pos.y+size.y);
			glEnd();
			
			glBegin(GL_QUADS);
				GL.bind(cOut); glVertex2d(pos.x+offTL.x,pos.y);
				GL.bind(cOut); glVertex2d(pos.x+size.x-offBR.x,pos.y);
				GL.bind(cIn); glVertex2d(pos.x+size.x-offBR.x,pos.y+offTL.y);
				GL.bind(cIn); glVertex2d(pos.x+offTL.x,pos.y+offTL.y);
				
				GL.bind(cOut); glVertex2d(pos.x,pos.y+offTL.y);
				GL.bind(cIn); glVertex2d(pos.x+offTL.x,pos.y+offTL.y);
				GL.bind(cIn); glVertex2d(pos.x+offTL.x,pos.y+size.y-offBR.y);
				GL.bind(cOut); glVertex2d(pos.x,pos.y+size.y-offBR.y);
				
				GL.bind(cIn); glVertex2d(pos.x+size.x-offBR.x,pos.y+offTL.y);
				GL.bind(cOut); glVertex2d(pos.x+size.x,pos.y+offTL.y);
				GL.bind(cOut); glVertex2d(pos.x+size.x,pos.y+size.y-offBR.y);
				GL.bind(cIn); glVertex2d(pos.x+size.x-offBR.x,pos.y+size.y-offBR.y);
				
				GL.bind(cIn); glVertex2d(pos.x+offTL.x,pos.y+size.y-offBR.y);
				GL.bind(cIn); glVertex2d(pos.x+size.x-offBR.x,pos.y+size.y-offBR.y);
				GL.bind(cOut); glVertex2d(pos.x+size.x-offBR.x,pos.y+size.y);
				GL.bind(cOut); glVertex2d(pos.x+offTL.x,pos.y+size.y);

				GL.bind(cIn);
				glVertex2d(pos.x+offTL.x,pos.y+offTL.y);
				glVertex2d(pos.x+size.x-offBR.x,pos.y+offTL.y);
				glVertex2d(pos.x+size.x-offBR.x,pos.y+size.y-offBR.y);
				glVertex2d(pos.x+offTL.x,pos.y+size.y-offBR.y);
			glEnd();
		} else {
			throw new UnsupportedOperationException();
		}
	}
}