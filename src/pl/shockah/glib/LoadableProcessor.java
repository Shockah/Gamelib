package pl.shockah.glib;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import pl.shockah.FieldObj;
import pl.shockah.glib.gl.Image;
import pl.shockah.glib.gl.SpriteSheet;
import pl.shockah.glib.gl.font.Font;
import pl.shockah.glib.gl.font.TrueTypeFont;
import pl.shockah.glib.gl.tex.Texture;

public final class LoadableProcessor {
	public static List<LoadAction<?>> process(Class<?> cls) {
		return process(cls,null);
	}
	public static List<LoadAction<?>> process(Object o) {
		return process(o.getClass(),o);
	}
	private static List<LoadAction<?>> process(Class<?> cls, Object o) {
		List<LoadAction<?>> ret = new LinkedList<>();
		for (Method mtd : cls.getDeclaredMethods()) {
			Font.Loadables loadables = mtd.getAnnotation(Font.Loadables.class);
			if (loadables != null) for (Font.Loadable loadable : loadables.value()) ret.add(new FontLoadAction(null,loadable));
			
			Font.Loadable loadable = mtd.getAnnotation(Font.Loadable.class);
			if (loadable != null) ret.add(new FontLoadAction(null,loadable));
		}
		for (Field fld : cls.getDeclaredFields()) {
			Image.Loadable imageLoadable = fld.getAnnotation(Image.Loadable.class);
			if (imageLoadable != null) ret.add(new ImageLoadAction(new FieldObj(fld,o),imageLoadable));
			
			SpriteSheet.Loadable sheetLoadable = fld.getAnnotation(SpriteSheet.Loadable.class);
			if (sheetLoadable != null) ret.add(new SpriteSheetLoadAction(new FieldObj(fld,o),sheetLoadable));
			
			TrueTypeFont.Loadable ttfLoadable = fld.getAnnotation(TrueTypeFont.Loadable.class);
			if (ttfLoadable != null) ret.add(new TrueTypeFontLoadAction(new FieldObj(fld,o),ttfLoadable));
		}
		return ret;
	}
	
	public static abstract class LoadAction<T extends Annotation> {
		protected final FieldObj field;
		protected final T loadable;
		
		public LoadAction(FieldObj field, T loadable) {
			this.field = field;
			this.loadable = loadable;
		}
		
		public abstract void load();
	}
	public static class FontLoadAction extends LoadAction<Font.Loadable> {
		public FontLoadAction(FieldObj field, Font.Loadable loadable) {
			super(field,loadable);
		}
		
		public void load() {
			try {
				switch (loadable.type()) {
					case File: Font.registerNew(new File(loadable.path())); break;
					case Internal: Font.registerNew(loadable.path()); break;
				}
			} catch (Exception e) {e.printStackTrace();}
		}
	}
	public static class ImageLoadAction extends LoadAction<Image.Loadable> {
		public ImageLoadAction(FieldObj field, Image.Loadable loadable) {
			super(field,loadable);
		}
		
		public void load() {
			try {
				Texture tex = null;
				switch (loadable.type()) {
					case File: tex = Texture.load(new File(loadable.path())); break;
					case Internal: tex = Texture.load(loadable.path()); break;
				}
				field.set(new Image(tex));
			} catch (Exception e) {e.printStackTrace();}
		}
	}
	public static class SpriteSheetLoadAction extends LoadAction<SpriteSheet.Loadable> {
		public SpriteSheetLoadAction(FieldObj field, SpriteSheet.Loadable loadable) {
			super(field,loadable);
		}
		
		public void load() {
			try {
				int gridX = loadable.gridX(), gridY = loadable.gridY(), grid = loadable.grid();
				if (gridX == -1 && gridY != -1) gridX = gridY;
				if (gridX != -1 && gridY == -1) gridY = gridX;
				if (gridX == -1 && gridY == -1 && grid != -1) {gridX = grid; gridY = grid;}
				if (gridX == -1 || gridY == -1) throw new IllegalArgumentException("Grid has to be specified ("+field.getObject()+")");
				
				Texture tex = null;
				switch (loadable.type()) {
					case File: tex = Texture.load(new File(loadable.path())); break;
					case Internal: tex = Texture.load(loadable.path()); break;
				}
				field.set(new SpriteSheet(tex,gridX,gridY,loadable.spacingX(),loadable.spacingY()));
			} catch (Exception e) {e.printStackTrace();}
		}
	}
	public static class TrueTypeFontLoadAction extends LoadAction<TrueTypeFont.Loadable> {
		public TrueTypeFontLoadAction(FieldObj field, TrueTypeFont.Loadable loadable) {
			super(field,loadable);
		}
		
		public void load() {
			try {
				field.set(new TrueTypeFont(loadable.name(),loadable.size(),loadable.bold(),loadable.italic(),loadable.antiAlias(),loadable.additionalChars()));
			} catch (Exception e) {e.printStackTrace();}
		}
	}
	
	public static enum AssetType {
		Internal(), File();
	}
}