package pl.shockah.glib;

import java.util.Comparator;
import org.lwjgl.opengl.DisplayMode;

public final class BestDisplayModeComparator implements Comparator<DisplayMode> {
	private final int width, height;
	
	public BestDisplayModeComparator(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public int compare(DisplayMode dm1, DisplayMode dm2) {
		boolean b1, b2;
		
		b1 = dm1.getWidth() == width && dm1.getHeight() == height;
		b2 = dm2.getWidth() == width && dm2.getHeight() == height;
		if (b1 ^ b2) return b1 ? 1 : -1;
		
		b1 = dm1.getBitsPerPixel() == Gamelib.originalDisplayMode.getBitsPerPixel();
		b2 = dm2.getBitsPerPixel() == Gamelib.originalDisplayMode.getBitsPerPixel();
		if (b1 ^ b2) return b1 ? 1 : -1;
		if (dm1.getBitsPerPixel() != dm2.getBitsPerPixel()) return dm1.getBitsPerPixel() < dm2.getBitsPerPixel() ? 1 : -1;
		
		b1 = dm1.getFrequency() == Gamelib.originalDisplayMode.getFrequency();
		b2 = dm2.getFrequency() == Gamelib.originalDisplayMode.getFrequency();
		if (b1 ^ b2) return b1 ? 1 : -1;
		if (dm1.getFrequency() != dm2.getFrequency()) return dm1.getFrequency() < dm2.getFrequency() ? 1 : -1;
		
		return 0;
	}
}