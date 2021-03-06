package pl.shockah.glib.gl.tex;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import pl.shockah.glib.LoadableProcessor;

public class SpriteSheet extends TextureSupplier {
	protected final Image[][] grid;
	protected final int gridX, gridY, spacingX, spacingY;
	
	public SpriteSheet(Image[][] grid) {
		super(null);
		this.grid = grid;
		gridX = gridY = spacingX = spacingY = -1;
		for (int x = 0; x < grid.length; x++) for (int y = 0; y < grid[x].length; y++) {
			grid[x][y].offset = offset;
		}
	}
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
		this.spacingX = spacingX;
		this.spacingY = spacingY;
		
		int w = tex.width(), h = tex.height();
		if (gridX < 0 || gridY < 0) {
			gridX = gridX < 0 ? w/(-gridX) : w;
			gridY = gridY < 0 ? h/(-gridY) : h;
		}
		this.gridX = gridX;
		this.gridY = gridY;
		grid = new Image[w/gridX][h/gridY];
		
		Image img = new Image(tex);
		for (int y = 0, yy = 0; y < h; y += gridY+spacingY, yy++) for (int x = 0, xx = 0; x < w; x += gridX+spacingX, xx++) {
			grid[xx][yy] = img.part(x,y,gridX,gridY);
			grid[xx][yy].offset = offset;
		}
	}
	
	public int columns() {return grid.length;}
	public int rows() {return grid[0].length;}
	public int count() {return columns()*rows();}
	
	public Image image(int x) {
		return image(x%grid.length,x/grid.length);
	}
	public Image image(int x, int y) {
		return grid[x][y];
	}
	
	@Target(ElementType.FIELD) @Retention(RetentionPolicy.RUNTIME) public static @interface Loadable {
		public String path() default "assets/spritesheets/<field.name>.png";
		public LoadableProcessor.AssetType type() default LoadableProcessor.AssetType.Internal;
		public int grid() default -1;
		public int gridX() default -1;
		public int gridY() default -1;
		public int framesX() default -1;
		public int framesY() default -1;
		public int spacingX() default 0;
		public int spacingY() default 0;
		public boolean toPremultiplied() default false;
	}
	@Target(ElementType.FIELD) @Retention(RetentionPolicy.RUNTIME) public static @interface ZIPLoadable {
		public String path() default "assets/images/<field.name>.zip";
		public LoadableProcessor.AssetType type() default LoadableProcessor.AssetType.Internal;
		public boolean toPremultiplied() default false;
	}
}