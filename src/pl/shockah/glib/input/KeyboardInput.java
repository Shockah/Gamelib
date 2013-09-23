package pl.shockah.glib.input;

import org.lwjgl.input.Keyboard;

public class KeyboardInput {
	public static final int ANYKEY = 256;
	
	public KeyboardTextInput text = new KeyboardTextInput(this);
	protected boolean[]
		keyPressedOld = new boolean[257],
		keyPressed = new boolean[257],
		keyReleasedOld = new boolean[257],
		keyReleased = new boolean[257];
	protected char[] keyCharacter = new char[256];
	
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
			keyCharacter[Keyboard.getEventKey()] = Keyboard.getEventCharacter();
			ar[ANYKEY] = true;
			
			text.handle(Keyboard.getEventKeyState(),Keyboard.getEventKey());
		}
		text.handleAll();
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