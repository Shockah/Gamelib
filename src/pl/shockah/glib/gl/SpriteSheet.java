package pl.shockah.glib.gl;

import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.gl.tex.Texture;

public class SpriteSheet extends TextureSupplier {
	protected final Image[][] grid;
	protected final int gridX, gridY;
	
	public SpriteSheet(Texture tex, int gridX, int gridY) {
		super(tex);
		this.gridX = gridX;
		this.gridY = gridY;
		
		int w = tex.getWidth(), h = tex.getHeight();
		grid = new Image[w/gridX][h/gridY];
		
		for (int y = 0; y < h; y += gridY) for (int x = 0; x < w; x += gridX) {
			grid[x][y] = new Image2(tex,x,y);
		}
	}
	
	public int getColumns() {return grid.length;}
	public int getRows() {return grid[0].length;}
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
			return new Rectangle(x*gridX,y*gridY,gridX,gridY);
		}
	}
}