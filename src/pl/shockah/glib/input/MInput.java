package pl.shockah.glib.input;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.geom.vector.Vector2i;

public class MInput {
	public static final int
		LEFT = 0,
		RIGHT = 1,
		MIDDLE = 2,
	
		ANYBUTTON = -1;
	
	protected static Vector2i pos, delta;
	protected static boolean onPress = false;
	protected static boolean[] btnPressedOld, btnPressed, btnReleasedOld, btnReleased;
	protected static int mouseScroll = 0;
	
	public static void update() {
		btnPressedOld = new boolean[Mouse.getButtonCount()+1];
		btnPressed = new boolean[Mouse.getButtonCount()+1];
		btnReleasedOld = new boolean[Mouse.getButtonCount()+1];
		btnReleased = new boolean[Mouse.getButtonCount()+1];
		
		for (int i = 0; i < btnPressed.length; i++) {
			btnPressedOld[i] = btnPressed[i];
			btnReleasedOld[i] = btnReleased[i];
			
			btnPressed[i] = false;
			btnReleased[i] = false;
		}
		
		onPress = false;
		pos = new Vector2i(Mouse.getX(),Display.getHeight()-Mouse.getY()-1);
		delta = new Vector2i(Mouse.getDX(),-Mouse.getDY());
		
		while (Mouse.next()) {
			if (Mouse.getEventButton() < 0) continue;
			
			boolean[] ar = Mouse.getEventButtonState() ? btnPressed : btnReleased;
			ar[Mouse.getEventButton()] = true;
			ar[specialButton(ANYBUTTON)] = true;
			
			if (!onPress) {
				pos = new Vector2i(Mouse.getEventX(),Display.getHeight()-Mouse.getEventY()-1);
				if (ar == btnPressed) onPress = true;
			}
		}
		
		mouseScroll = Mouse.getDWheel() / 120;
	}
	
	public static Vector2i pos() {
		return pos.copyMe();
	}
	public static Vector2i deltaPos() {
		return delta.copyMe();
	}
	
	public static int scroll() {
		return mouseScroll;
	}
	
	public static boolean pressed(int btn) {
		return btnPressed[specialButton(btn)];
	}
	public static boolean released(int btn) {
		return btnReleased[specialButton(btn)];
	}
	public static boolean down(int btn) {
		return btnPressed[specialButton(btn)] || Mouse.isButtonDown(specialButton(btn));
	}
	
	protected static int specialButton(int btn) {
		return btn >= 0 ? btn : btnPressed.length+btn;
	}
	
	public static boolean inRectangle(Rectangle rect) {
		return pos.x >= rect.pos.x && pos.y >= rect.pos.y && pos.x < rect.pos.x+rect.size.x && pos.y < rect.pos.y+rect.size.y;
	}
}