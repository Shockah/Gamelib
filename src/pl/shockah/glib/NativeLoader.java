package pl.shockah.glib;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarFile;
import org.lwjgl.LWJGLUtil;

/**
 * Solves the long standing issue of managing the native linking libraries by
 * the developer by auto-magically loading them into the Classpath at runtime.
 * This class absolutely <b>must</b> be called before any of the native library
 * code is expected, or a {@link java.lang.UnsatisfiedLinkError}s will not be
 * prevented. The loader can be disabled by setting the property designated by
 * {@link #DISABLE_AUTO_LOADING_PROPERTY} to the String value "true".
 * 
 * @author Joshua Mabrey Jul 21, 2012
 */
public class NativeLoader {
	public static final String DISABLE_AUTO_LOADING_PROPERTY = "org.newdawn.slick.NativeLoader.load";
	private static final String LINUX_NATIVE_JAR = "natives-linux.jar";
	private static final String MAC_NATIVE_JAR = "natives-mac.jar";
	private static final String WINDOWS_NATIVE_JAR = "natives-windows.jar";
	private static final String LINUX_NATIVES_DEFAULT_PACKAGE = LINUX_NATIVE_JAR.substring(0, LINUX_NATIVE_JAR.indexOf(".jar"));
	private static final String MAC_NATIVES_DEFAULT_PACKAGE = MAC_NATIVE_JAR.substring(0, MAC_NATIVE_JAR.indexOf(".jar"));
	private static final String WINDOWS_NATIVES_DEFAULT_PACKAGE = WINDOWS_NATIVE_JAR.substring(0, WINDOWS_NATIVE_JAR.indexOf(".jar"));
	private static final String LIBRARY_SYSTEM_PROPERTY = "java.library.path";
	private static final String LWJGL_LIBRARY_PROPERTY = "org.lwjgl.librarypath";
	private static final String JINPUT_LIBRARY_PROPERTY = "net.java.games.input.librarypath";
	private static final String[] LINUX_LIBRARIES = {
		"libjinput-linux.so",
		"libjinput-linux64.so",
		"liblwjgl.so",
		"liblwjgl64.so",
		"libopenal.so",
		"libopenal64.so"
	};
	private static final String[] MAC_LIBRARIES = {
		"libjinput-osx.jnilib",
		"liblwjgl.jnilib",
		"libopenal.dylib"
	};
	private static final String[] WINDOWS_LIBRARIES = {
		"jinput-dx8.dll",
		"jinput-dx8_64.dll",
		"jinput-raw.dll",
		"jinput-raw_64.dll",
		"lwjgl.dll",
		"lwjgl64.dll",
		"OpenAL32.dll",
		"OpenAL64.dll"
	};
	private static boolean alreadyLoaded = false;
	private static File outputDirectoryHandle = null;
	private static final int TEMP_DIR_CREATE_ATTEMPTS = 10000;
	
	public static final void load() {
		if (librariesAreLoadable()) {
			int OS = LWJGLUtil.getPlatform();
			try {
				NativeLoader.loadLibraries(OS);
				alreadyLoaded = true;
			} catch (Exception e) {e.printStackTrace();}
		}
	}
	
	public static final boolean destroy() {
		if (alreadyLoaded && outputDirectoryHandle != null) {
			boolean successFlag = true;
			File[] children = outputDirectoryHandle.listFiles();
			for (int iter = 0; iter < children.length; iter++) {
				File child = children[iter];

				try {
					child.delete();
				} catch (Exception e) {
					successFlag = false;
					e.printStackTrace();
				}

			}

			try {
				outputDirectoryHandle.delete();
			} catch (Exception e) {
				successFlag = false;
				e.printStackTrace();
			}

			alreadyLoaded = false;
			outputDirectoryHandle = null;

			return successFlag;
		}

		return false;
	}
	
	private static final boolean librariesAreLoadable() {
		if (alreadyLoaded) {
			return false;
		}

		if (System.getProperty(DISABLE_AUTO_LOADING_PROPERTY, "false").equalsIgnoreCase("true")) {
			return false;
		}

		boolean lwjglAlreadyAvailable = false;

		try {
			lwjglAlreadyAvailable = testLibraryAvailable("lwjgl", "lwjgl64");
		} catch (SecurityException e) {
			return false;
		}

		if (lwjglAlreadyAvailable) {
			return false;
		} else {
			return true;
		}
	}
	
	private static final boolean testLibraryAvailable(String libName,
			String libName64) throws SecurityException {
		try {
			System.loadLibrary(libName);
			return true;
		} catch (UnsatisfiedLinkError e) {
			if (libName64 != null) {
				return testLibraryAvailable(libName64, null);
			}
			return false;
		} catch (SecurityException e1) {
			throw new SecurityException("Unable to load libraries because of a SecurityManager", e1);
		}
	}
	
	@SuppressWarnings("resource") private static final void loadLibraries(int platform) throws IOException {
		outputDirectoryHandle = createTempDir();

		String[] libraries;
		String prefix;
		JarFile nativeResourceJar = null;

		switch (platform) {
			case LWJGLUtil.PLATFORM_LINUX:
				libraries = LINUX_LIBRARIES;
				prefix = LINUX_NATIVES_DEFAULT_PACKAGE + "/";
				nativeResourceJar = new JarFile(new File(LINUX_NATIVE_JAR));
				break;
			case LWJGLUtil.PLATFORM_MACOSX:
				libraries = MAC_LIBRARIES;
				prefix = MAC_NATIVES_DEFAULT_PACKAGE + "/";
				nativeResourceJar = new JarFile(new File(MAC_NATIVE_JAR));
				break;
			case LWJGLUtil.PLATFORM_WINDOWS:
				libraries = WINDOWS_LIBRARIES;
				prefix = WINDOWS_NATIVES_DEFAULT_PACKAGE + "/";
				nativeResourceJar = new JarFile(new File(WINDOWS_NATIVE_JAR));
				break;
			default:
				throw new IllegalStateException("Encountered an unknown platform while loading native libraries");
		}

		for (int iter = 0; iter < libraries.length; iter++) {
			String fileName = libraries[iter];
			InputStream resourceStream = null;
			try {
				resourceStream = nativeResourceJar.getInputStream(nativeResourceJar.getJarEntry(prefix + fileName));
				writeResourceToFile(resourceStream, new File(outputDirectoryHandle, fileName));
			} finally {
				if (resourceStream != null) {
					resourceStream.close();
				}
			}
		}

		String outputDir = outputDirectoryHandle.getAbsolutePath();
		System.setProperty(LIBRARY_SYSTEM_PROPERTY, outputDir + File.pathSeparator + System.getProperty(LIBRARY_SYSTEM_PROPERTY));
		System.setProperty(LWJGL_LIBRARY_PROPERTY, outputDir);
		System.setProperty(JINPUT_LIBRARY_PROPERTY, outputDir);
	}
	
	private static final File createTempDir() {
		File baseDir = new File(System.getProperty("java.io.tmpdir"));
		String baseName = "slick-natives-" + System.nanoTime() + "-";

		for (int counter = 0; counter < TEMP_DIR_CREATE_ATTEMPTS; counter++) {
			File tempDir = new File(baseDir, baseName + counter);
			if (tempDir.mkdir()) {
				return tempDir;
			}
		}

		throw new IllegalStateException("Unable to create temporary directory");
	}
	
	private static final void writeResourceToFile(InputStream resource, File outputFile) throws IOException {
		if (resource == null) {
			throw new IOException("Class resource is invalid and null.");
		}

		if (outputFile == null) {
			throw new IOException("File out handle is invalid and null.");
		}

		if (outputFile.isDirectory()) {
			throw new IOException(
					"Cannot write native library resource to file because file is a directory.");
		}

		if (!outputFile.exists()) {
			outputFile.createNewFile();
		}
		
		byte[] readBuffer = new byte[1024];
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(outputFile);

			while (true) {
				int bytesRead = resource.read(readBuffer);
				if (bytesRead == -1) {
					break;
				}
				fos.write(readBuffer, 0, bytesRead);
			}

		} finally {
			if (fos != null) {
				fos.flush();
				fos.close();
			}
		}
	}
}