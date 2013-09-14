package pl.shockah.glib;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import pl.shockah.BinBuffer;
import pl.shockah.BinBufferInputStream;
import pl.shockah.FieldObj;
import pl.shockah.FileLine;
import pl.shockah.Pair;
import pl.shockah.glib.gl.Shader;
import pl.shockah.glib.gl.font.Font;
import pl.shockah.glib.gl.font.TrueTypeFont;
import pl.shockah.glib.gl.tex.Atlas;
import pl.shockah.glib.gl.tex.Image;
import pl.shockah.glib.gl.tex.SVGTextureLoader;
import pl.shockah.glib.gl.tex.SpriteSheet;
import pl.shockah.glib.gl.tex.Texture;
import pl.shockah.glib.gl.tex.TextureLoader;
import pl.shockah.json.JSONObject;
import pl.shockah.json.JSONParser;

public final class LoadableProcessor {
	public static List<LoadAction<?>> process(Class<?> cls) {
		return process(cls,null,null);
	}
	public static List<LoadAction<?>> process(Class<?> cls, ILoadableAnnotationHandler handler) {
		return process(cls,null,handler);
	}
	public static List<LoadAction<?>> process(Object o) {
		return process(o.getClass(),o,null);
	}
	public static List<LoadAction<?>> process(Object o, ILoadableAnnotationHandler handler) {
		return process(o.getClass(),o,handler);
	}
	private static List<LoadAction<?>> process(Class<?> cls, Object o, ILoadableAnnotationHandler handler) {
		List<LoadAction<?>> ret = new LinkedList<>();
		for (Method mtd : cls.getMethods()) {
			Font.Loadables loadables = mtd.getAnnotation(Font.Loadables.class);
			if (loadables != null) for (Font.Loadable loadable : loadables.value()) ret.add(new FontLoadAction(null,loadable));
			
			Font.Loadable loadable = mtd.getAnnotation(Font.Loadable.class);
			if (loadable != null) ret.add(new FontLoadAction(null,loadable));
		}
		for (Field fld : cls.getFields()) {
			TextureLoader.IntOptions optints = fld.getAnnotation(TextureLoader.IntOptions.class);
			if (optints == null) {
				final TextureLoader.IntOption optint = fld.getAnnotation(TextureLoader.IntOption.class);
				if (optint != null) {
					optints = new TextureLoader.IntOptions() {
						public Class<? extends Annotation> annotationType() {return TextureLoader.IntOptions.class;}
						public TextureLoader.IntOption[] value() {return new TextureLoader.IntOption[]{optint};}
					};
				} else {
					final SVGTextureLoader.Options svgopt = fld.getAnnotation(SVGTextureLoader.Options.class);
					if (svgopt != null) {
						optints = new TextureLoader.IntOptions() {
							public Class<? extends Annotation> annotationType() {return TextureLoader.IntOptions.class;}
							public TextureLoader.IntOption[] value() {
								List<TextureLoader.IntOption> list = new LinkedList<>();
								if (svgopt.width() > 0) list.add(new TextureLoader.IntOption(){
									public Class<? extends Annotation> annotationType() {return TextureLoader.IntOption.class;}
									public String option() {return "width";}
									public int value() {return svgopt.width();}
								});
								if (svgopt.height() > 0) list.add(new TextureLoader.IntOption(){
									public Class<? extends Annotation> annotationType() {return TextureLoader.IntOption.class;}
									public String option() {return "height";}
									public int value() {return svgopt.height();}
								});
								return list.toArray(new TextureLoader.IntOption[0]);
							}
						};
					}
				}
			}
			
			Image.Loadable imageLoadable = fld.getAnnotation(Image.Loadable.class);
			if (imageLoadable != null) ret.add(new ImageLoadAction(new FieldObj(fld,o),imageLoadable,optints));
			
			SpriteSheet.Loadable sheetLoadable = fld.getAnnotation(SpriteSheet.Loadable.class);
			if (sheetLoadable != null) ret.add(new SpriteSheetLoadAction(new FieldObj(fld,o),sheetLoadable,optints));
			SpriteSheet.ZIPLoadable zipSSLoadable = fld.getAnnotation(SpriteSheet.ZIPLoadable.class);
			if (zipSSLoadable != null) ret.add(new ZIPSpriteSheetLoadAction(new FieldObj(fld,o),zipSSLoadable,optints));
			
			Shader.Loadable shaderLoadable = fld.getAnnotation(Shader.Loadable.class);
			if (shaderLoadable != null) ret.add(new ShaderLoadAction(new FieldObj(fld,o),shaderLoadable));
			
			Atlas.Loadable atlasLoadable = fld.getAnnotation(Atlas.Loadable.class);
			if (atlasLoadable != null) ret.add(new AtlasLoadAction(new FieldObj(fld,o),atlasLoadable,optints));
			
			TrueTypeFont.Loadable ttfLoadable = fld.getAnnotation(TrueTypeFont.Loadable.class);
			if (ttfLoadable != null) ret.add(new TrueTypeFontLoadAction(new FieldObj(fld,o),ttfLoadable));
			
			if (handler != null) handler.handle(ret,fld,o);
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
		
		protected String handlePath(String path) {
			path = path.replace("<field.name>",field.getField().getName());
			return path;
		}
		
		public abstract boolean load(AssetLoader al);
	}
	public static class FontLoadAction extends LoadAction<Font.Loadable> {
		public FontLoadAction(FieldObj field, Font.Loadable loadable) {
			super(field,loadable);
		}
		
		public boolean load(AssetLoader al) {
			try {
				switch (loadable.type()) {
					case File: Font.registerNew(new File(loadable.path())); break;
					case Internal: Font.registerNew(loadable.path()); break;
				}
			} catch (Exception e) {e.printStackTrace();}
			return true;
		}
	}
	public static class ImageLoadAction extends LoadAction<Image.Loadable> {
		protected final TextureLoader.IntOptions optints;
		
		public ImageLoadAction(FieldObj field, Image.Loadable loadable, TextureLoader.IntOptions optints) {
			super(field,loadable);
			this.optints = optints;
		}
		
		public boolean load(AssetLoader al) {
			try {
				TextureLoader.clearOptionsGlobal();
				if (optints != null) for (TextureLoader.IntOption optint : optints.value()) TextureLoader.setOptionGlobal(optint.option(),optint.value());
				if (loadable.toPremultiplied()) TextureLoader.setOptionGlobal("toPremultiplied",true);
				
				Texture tex = null;
				String path = handlePath(loadable.path());
				switch (loadable.type()) {
					case File: tex = Texture.load(new File(path)); break;
					case Internal: tex = Texture.load(path); break;
				}
				field.set(new Image(tex));
				TextureLoader.clearOptionsGlobal();
			} catch (Exception e) {e.printStackTrace();}
			return true;
		}
	}
	public static class SpriteSheetLoadAction extends LoadAction<SpriteSheet.Loadable> {
		protected final TextureLoader.IntOptions optints;
		
		public SpriteSheetLoadAction(FieldObj field, SpriteSheet.Loadable loadable, TextureLoader.IntOptions optints) {
			super(field,loadable);
			this.optints = optints;
		}
		
		public boolean load(AssetLoader al) {
			try {
				TextureLoader.clearOptionsGlobal();
				if (optints != null) for (TextureLoader.IntOption optint : optints.value()) TextureLoader.setOptionGlobal(optint.option(),optint.value());
				if (loadable.toPremultiplied()) TextureLoader.setOptionGlobal("toPremultiplied",true);
				
				int gridX = loadable.gridX(), gridY = loadable.gridY(), grid = loadable.grid();
				if (gridX == -1 && gridY != -1) gridX = gridY;
				if (gridX != -1 && gridY == -1) gridY = gridX;
				if (gridX == -1 && gridY == -1 && grid != -1) {gridX = grid; gridY = grid;}
				if (gridX == -1 || gridY == -1) {
					int framesX = loadable.framesX(), framesY = loadable.framesY();
					if (framesX != -1 || framesY != -1) {
						gridX = -framesX;
						gridY = -framesY;
					} else throw new IllegalArgumentException("Grid/frames have to be specified ("+field.getObject()+")");
				}
				
				Texture tex = null;
				String path = handlePath(loadable.path());
				switch (loadable.type()) {
					case File: tex = Texture.load(new File(path)); break;
					case Internal: tex = Texture.load(path); break;
				}
				field.set(new SpriteSheet(tex,gridX,gridY,loadable.spacingX(),loadable.spacingY()));
				TextureLoader.clearOptionsGlobal();
			} catch (Exception e) {e.printStackTrace();}
			return true;
		}
	}
	public static class TrueTypeFontLoadAction extends LoadAction<TrueTypeFont.Loadable> {
		public TrueTypeFontLoadAction(FieldObj field, TrueTypeFont.Loadable loadable) {
			super(field,loadable);
		}
		
		public boolean load(AssetLoader al) {
			try {
				field.set(new TrueTypeFont(loadable.name(),loadable.size(),loadable.bold(),loadable.italic(),loadable.antiAlias(),loadable.additionalChars()));
			} catch (Exception e) {e.printStackTrace();}
			return true;
		}
	}
	public static class ZIPSpriteSheetLoadAction extends LoadAction<SpriteSheet.ZIPLoadable> {
		protected final TextureLoader.IntOptions optints;
		protected boolean loaded = false;
		protected int count = 0, total = 0;
		protected List<Pair<String,BinBuffer>> buffers = new LinkedList<>();
		protected List<Pair<Integer,Image>> images = new LinkedList<>();
		
		public ZIPSpriteSheetLoadAction(FieldObj field, SpriteSheet.ZIPLoadable loadable, TextureLoader.IntOptions optints) {
			super(field,loadable);
			this.optints = optints;
		}
		
		public boolean load(AssetLoader al) {
			try {
				if (!loaded) {
					ZipInputStream zis = null;
					String path = handlePath(loadable.path());
					switch (loadable.type()) {
						case File: zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(path))); break;
						case Internal: zis = new ZipInputStream(new BufferedInputStream(Texture.class.getClassLoader().getResourceAsStream(path))); break;
					}
					final ZipInputStream zis2 = zis;
					
					while (true) {
						ZipEntry ze = zis.getNextEntry();
						if (ze == null) break;
						
						BinBuffer binb = new BinBuffer();
						int b;
						while ((b = zis2.read()) != -1) binb.writeByte(b);
						binb.setPos(0);
						buffers.add(new Pair<>(ze.getName(),binb));
						total++;
						
						loaded = true;
					}
					zis.close();
					return false;
				} else {
					if (!buffers.isEmpty()) {
						al.setCurrentStatus(.3d+.7d/total*count);
						Pair<String,BinBuffer> pair = buffers.remove(0);
						
						TextureLoader.clearOptionsGlobal();
						if (optints != null) for (TextureLoader.IntOption optint : optints.value()) TextureLoader.setOptionGlobal(optint.option(),optint.value());
						if (loadable.toPremultiplied()) TextureLoader.setOptionGlobal("toPremultiplied",true);
						
						String[] spl = pair.get1().split("\\.");
						final BinBufferInputStream binbis = new BinBufferInputStream(pair.get2());
						images.add(new Pair<>(Integer.parseInt(spl[0]),new Image(Texture.load(binbis,spl[spl.length-1].toUpperCase()))));
						
						count++;
					}
					if (buffers.isEmpty()) {
						Image[][] ar = new Image[images.size()][1];
						for (Pair<Integer,Image> pair : images) ar[pair.get1()][0] = pair.get2();
						field.set(new SpriteSheet(ar));
						TextureLoader.clearOptionsGlobal();
						return true;
					}
					return false;
				}
			} catch (Exception e) {e.printStackTrace();}
			return true;
		}
	}
	public static class ShaderLoadAction extends LoadAction<Shader.Loadable> {
		public ShaderLoadAction(FieldObj field, Shader.Loadable loadable) {
			super(field,loadable);
		}
		
		public boolean load(AssetLoader al) {
			try {
				Shader sdr = null;
				String path = handlePath(loadable.path());
				switch (loadable.type()) {
					case File: sdr = Shader.createFromFile(new File(path)); break;
					case Internal: sdr = Shader.createFromPath(path); break;
				}
				if (sdr != null) sdr.setMixTexturing(loadable.mixTexturing());
				field.set(sdr);
			} catch (Exception e) {e.printStackTrace();}
			return true;
		}
	}
	public static class AtlasLoadAction extends LoadAction<Atlas.Loadable> {
		protected final TextureLoader.IntOptions optints;
		
		public AtlasLoadAction(FieldObj field, Atlas.Loadable loadable, TextureLoader.IntOptions optints) {
			super(field,loadable);
			this.optints = optints;
		}
		
		public boolean load(AssetLoader al) {
			try {
				TextureLoader.clearOptionsGlobal();
				if (optints != null) for (TextureLoader.IntOption optint : optints.value()) TextureLoader.setOptionGlobal(optint.option(),optint.value());
				if (loadable.toPremultiplied()) TextureLoader.setOptionGlobal("toPremultiplied",true);
				
				Texture tex = null;
				JSONObject j = null;
				String path = handlePath(loadable.path());
				switch (loadable.type()) {
					case File: {
						File f = new File(path);
						String ext = f.getName().substring(f.getName().lastIndexOf('.'));
						tex = Texture.load(f);
						j = new JSONParser().parseObject(FileLine.readString(new File(f.getParentFile(),f.getName().substring(0,f.getName().length()-ext.length())+".json")));
					} break;
					case Internal: {
						String ext = path.substring(path.lastIndexOf('.'));
						tex = Texture.load(path);
						j = new JSONParser().parseObject(FileLine.readString(getClass().getClassLoader().getResourceAsStream(path.substring(0,path.length()-ext.length())+".json")));
					} break;
				}
				Atlas a = new Atlas(tex);
				a.fillFromJSON(j);
				
				field.set(a);
				TextureLoader.clearOptionsGlobal();
			} catch (Exception e) {e.printStackTrace();}
			return true;
		}
	}
	
	public static enum AssetType {
		Internal(), File();
	}
}