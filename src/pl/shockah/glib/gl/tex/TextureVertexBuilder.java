package pl.shockah.glib.gl.tex;

import static org.lwjgl.opengl.GL11.*;
import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.geom.vector.Vector2i;
import pl.shockah.glib.gl.GL;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;

public class TextureVertexBuilder extends TextureSupplier {
	public final int splitX, splitY;
	protected final Vector2d[][] vertexPos;
	protected final Vector2d[][] vertexCoord;
	protected final Color[][] vertexColor;
	
	public TextureVertexBuilder(Texture tex, int split) {this(tex,split,split,null);}
	public TextureVertexBuilder(Texture tex, int split, Color color) {this(tex,split,split,color);}
	public TextureVertexBuilder(Texture tex, int splitX, int splitY) {this(tex,splitX,splitY,null);}
	public TextureVertexBuilder(Texture tex, int splitX, int splitY, Color color) {
		super(tex);
		this.splitX = splitX;
		this.splitY = splitY;
		
		vertexPos = new Vector2d[splitX+1][splitY+1];
		vertexCoord = new Vector2d[splitX+1][splitY+1];
		vertexColor = new Color[splitX+1][splitY+1];
		
		Rectangle texRect = getTextureRect();
		for (int x = 0; x < vertexPos.length; x++) for (int y = 0; y < vertexPos[0].length; y++) {
			vertexPos[x][y] = new Vector2d(texRect.size.x*scale.x/(vertexPos.length-1)*x,texRect.size.y*scale.y/(vertexPos[0].length-1)*y);
			vertexCoord[x][y] = new Vector2d(texRect.size.x/getTextureWidthFold()/(vertexPos.length-1)*x,texRect.size.y/getTextureHeightFold()/(vertexPos[0].length-1)*y);
		}
	}
	
	public void reset(boolean pos, boolean coord, boolean color) {
		Rectangle texRect = getTextureRect();
		for (int x = 0; x < vertexPos.length; x++) for (int y = 0; y < vertexPos[0].length; y++) reset(x,y,texRect,pos,coord,color);
	}
	public void reset(int xx, int yy, boolean pos, boolean coord, boolean color) {reset(xx,yy,getTextureRect(),pos,coord,color);}
	private void reset(int xx, int yy, Rectangle texRect, boolean pos, boolean coord, boolean color) {
		if (pos) vertexPos[xx][yy].set(texRect.size.x*scale.x/(vertexPos.length-1)*xx,texRect.size.y*scale.y/(vertexPos[0].length-1)*yy);
		if (coord) vertexCoord[xx][yy].set(texRect.size.x/getTextureWidthFold()/(vertexPos.length-1)*xx,texRect.size.y/getTextureHeightFold()/(vertexPos[0].length-1)*yy);
		if (color) vertexColor[xx][yy] = null;
	}
	
	public Vector2d getVertexPos(int xx, int yy) {
		return vertexPos[xx][yy];
	}
	public Vector2d getVertexTexCoord(int xx, int yy) {
		return vertexCoord[xx][yy];
	}
	public Color getVertexColor(int xx, int yy) {
		return vertexColor[xx][yy];
	}
	public void setVertexColor(int xx, int yy, Color c) {
		vertexColor[xx][yy] = c;
	}
	
	public void drawTexture(Graphics g, double x, double y) {
		if (disposed()) throw new IllegalStateException("Texture already disposed");
		g.preDraw();
		
		GL.bind(getTexture());
		if (offset.x != 0 || offset.y != 0) glTranslated(-offset.x*scale.x,-offset.y*scale.y,0);
		if (x != 0 || y != 0) glTranslated(x,y,0);
		
		preDraw(g);
		glBegin(GL_QUADS);
			for (int xx = 0; xx < vertexPos.length-1; xx++) for (int yy = 0; yy < vertexPos[0].length-1; yy++) {
				oneVertex(xx,yy);
				oneVertex(xx+1,yy);
				oneVertex(xx+1,yy+1);
				oneVertex(xx,yy+1);
			}
		glEnd();
		postDraw(g);
		
		if (x != 0 || y != 0) glTranslated(-x,-y,0);
		if (offset.x != 0 || offset.y != 0) glTranslated(offset.x*scale.x,offset.y*scale.y,0);
	}
	protected void oneVertex(int xx, int yy) {
		if (vertexColor[xx][yy] != null) GL.bind(vertexColor[xx][yy]);
		fixedVertexTexCoord(xx,yy);
		GL.vertex2d(vertexPos[xx][yy]);
	}
	protected void fixedVertexTexCoord(int xx, int yy) {
		double cx = vertexCoord[xx][yy].x, cy = vertexCoord[xx][yy].y;
		while (cx < 0) cx += 1; while (cx >= 1) cx -= 1;
		while (cy < 0) cy += 1; while (cy >= 1) cy -= 1;
		Vector2i size = getTexture().getSize(), sizeFold = getTexture().getSizeFold();
		GL.texCoord2d(1d*size.x/sizeFold.x*cx,1d*size.y/sizeFold.y*cy);
	}
	
	public void drawTextureMulticolor(Graphics g, double x, double y, Color cTopLeft, Color cTopRight, Color cBottomLeft, Color cBottomRight) {
		throw new UnsupportedOperationException();
	}
}