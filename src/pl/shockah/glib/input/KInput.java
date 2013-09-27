package pl.shockah.glib.input;

import org.lwjgl.input.Keyboard;

public class KInput {
	public static final int ANYKEY = 256;
	
	public static KTextInput text = new KTextInput();
	protected static boolean[]
		keyPressedOld = new boolean[257],
		keyPressed = new boolean[257],
		keyReleasedOld = new boolean[257],
		keyReleased = new boolean[257],
		keyDown = new boolean[257],
		keyDownOld = new boolean[257];
	protected static char[] keyCharacter = new char[256];
	
	public static void update() {
		for (int i = 0; i < keyPressed.length; i++) {
			keyPressedOld[i] = keyPressed[i];
			keyReleasedOld[i] = keyReleased[i];
			keyDownOld[i] = keyDown[i];
			
			keyPressed[i] = false;
			keyReleased[i] = false;
		}
		
		while (Keyboard.next()) {
			boolean pressed = Keyboard.getEventKeyState();
			int eventKey = Keyboard.getEventKey();
			
			boolean[] ar = pressed ? keyPressed : keyReleased;
			ar[eventKey] = true;
			keyCharacter[Keyboard.getEventKey()] = Keyboard.getEventCharacter();
			ar[ANYKEY] = true;
			keyDown[eventKey] = pressed;
			
			text.handle(Keyboard.getEventKeyState(),Keyboard.getEventKey());
		}
		text.handleAll();
	}
	
	public static boolean isPressed(int key) {
		return keyPressed[key];
	}
	public static boolean isReleased(int key) {
		return keyReleased[key];
	}
	public static boolean isDown(int key) {
		return keyDown[key];
	}
}