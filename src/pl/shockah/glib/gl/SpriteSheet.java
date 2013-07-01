package pl.shockah.glib.gl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.gl.tex.Texture;

public class SpriteSheet extends TextureSupplier {
	protected final Image[][] grid;
	protected final int gridX, gridY, spacingX, spacingY;
	
	public SpriteSheet(Texture tex, int grid) {
		this(tex,grid,grid);
	}
	public SpriteSheet(Texture tex, int gridX, int gridY) {
		this(tex,gridX,gridY,0);
	}
	public SpriteSheet(Texture tex, int gridX, int gridY, int spacing) {
		this(tex,gridX,gridY,spacing,spacing);
	}
	public SpriteSheet(Texture tex, int gridX, int gridY, int spacingX, int spacingY) {
		super(tex);
		this.gridX = gridX;
		this.gridY = gridY;
		this.spacingX = spacingX;
		this.spacingY = spacingY;
		
		int w = tex.getWidth(), h = tex.getHeight();
		grid = new Image[w/gridX][h/gridY];
		
		for (int y = 0; y < h; y += gridY+spacingY) for (int x = 0; x < w; x += gridX+spacingX) {
			grid[x][y] = new Image2(tex,x,y);
		}
	}
	
	public int getColumns() {return grid.length;}
	public int getRows() {return grid[0].length;}
	
	//region Java-OO
	public Image[] get(int x) {
		return grid[x];
	}
	//endregion
	
	public Image getImage(int x, int y) {
		return grid[x][y];
	}
	
	protected class Image2 extends Image {
		protected final int x, y;
		
		public Image2(Texture tex, int x, int y) {
			super(tex);
			this.x = x;
			this.y = y;
		}
		
		public Rectangle getTextureRect() {
			return new Rectangle(x*(gridX+spacingX),y*(gridY+spacingY),gridX,gridY);
		}
	}
	
	@Target(ElementType.FIELD) @Retention(RetentionPolicy.RUNTIME) public static @interface Loadable {
		public String path();
		public Type type() default Type.Internal;
		public int gridX() default -1;
		public int gridY() default -1;
		
		public static enum Type {
			Internal(), File();
		}
	}
}