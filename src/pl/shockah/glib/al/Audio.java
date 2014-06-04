package pl.shockah.glib.al;

import static org.lwjgl.openal.AL10.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.file.Path;
import pl.shockah.glib.LoadableProcessor;

public class Audio {
	public static Audio load(Path path) throws FileNotFoundException, IOException {
		return load(path.toFile());
	}
	public static Audio load(Path path, String format) throws FileNotFoundException, IOException {
		return load(path.toFile(),format);
	}
	public static Audio load(File file) throws FileNotFoundException, IOException {
		String[] spl = file.getName().split("\\.");
		return load(file,spl[spl.length-1].toUpperCase());
	}
	public static Audio load(File file, String format) throws FileNotFoundException, IOException {
		return load(new BufferedInputStream(new FileInputStream(file)),format);
	}
	public static Audio load(String internalPath) throws IOException {
		String[] spl = internalPath.split("\\.");
		return load(internalPath,spl[spl.length-1].toUpperCase());
	}
	public static Audio load(String internalPath, String format) throws IOException {
		return load(new BufferedInputStream(Audio.class.getClassLoader().getResourceAsStream(internalPath)),format);
	}
	public static Audio load(InputStream is, String format) throws IOException {
		if (format == null) {
			for (AudioLoader al : AudioLoader.all()) try {
				Audio audio = al.load(is);
				if (audio != null) return audio;
			} catch (Exception e) {}
			throw new UnsupportedOperationException("Unsupported audio format.");
		} else {
			AudioLoader al = AudioLoader.audioLoader(format);
			if (al == null) throw new UnsupportedOperationException("Unsupported audio format: "+format+".");
			return al.load(is);
		}
	}
	
	public final int id;
	protected boolean disposed = false;
	
	public Audio() {
		this(alGenBuffers());
	}
	public Audio(int id) {
		this.id = id;
	}
	
	public boolean disposed() {
		return disposed;
	}
	public void dispose() {
		alDeleteBuffers(id);
		disposed = true;
	}
	protected void finalize() {
		dispose();
	}
	
	public AudioInst music() {
		return AudioStore.music(this);
	}
	public AudioInst sound() {
		return AudioStore.sound(this);
	}
	
	@Target(ElementType.FIELD) @Retention(RetentionPolicy.RUNTIME) public static @interface Loadable {
		public String path() default "assets/sounds/<field.name>.wav";
		public LoadableProcessor.AssetType type() default LoadableProcessor.AssetType.Internal;
	}
}