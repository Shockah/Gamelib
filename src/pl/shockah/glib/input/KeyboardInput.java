package pl.shockah.glib.input;

import org.lwjgl.input.Keyboard;

public class KeyboardInput {
	public static final int
		ARROW_LEFT = 203,
		ARROW_RIGHT = 205,
		ARROW_UP = 200,
		ARROW_DOWN = 208,
		
		ANYKEY = 256;
	
	protected boolean[]
		keyPressedOld = new boolean[257],
		keyPressed = new boolean[257],
		keyReleasedOld = new boolean[257],
		keyReleased = new boolean[257];
	
	public void update() {
		for (int i = 0; i < keyPressed.length; i++) {
			keyPressedOld[i] = keyPressed[i];
			keyReleasedOld[i] = keyReleased[i];
			
			keyPressed[i] = false;
			keyReleased[i] = false;
		}
		
		while (Keyboard.next()) {
			boolean[] ar = Keyboard.getEventKeyState() ? keyPressed : keyReleased;
			ar[Keyboard.getEventKey()] = true;
			ar[ANYKEY] = true;
		}
	}
	
	public boolean isPressed(int key) {
		return keyPressed[key];
	}
	public boolean isReleased(int key) {
		return keyReleased[key];
	}
	public boolean isDown(int key) {
		return keyPressed[key] || Keyboard.isKeyDown(key);
	}
}