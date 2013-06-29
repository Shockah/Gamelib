package pl.shockah.glib.input;

import pl.shockah.glib.Gamelib;

public class Key {
	protected final KeyboardInput keyboard;
	protected final int[] checkFor;
	
	public Key(int... checkFor) {this(Gamelib.keyboard,checkFor);}
	public Key(KeyboardInput kb, int... checkFor) {
		keyboard = kb;
		this.checkFor = checkFor;
	}
	
	public boolean pressed() {
		for (int i = 0; i < checkFor.length; i++) if (keyboard.isPressed(checkFor[i])) return true;
		return false;
	}
	public boolean released() {
		for (int i = 0; i < checkFor.length; i++) if (keyboard.isReleased(checkFor[i])) return true;
		return false;
	}
	public boolean down() {
		for (int i = 0; i < checkFor.length; i++) if (keyboard.isDown(checkFor[i])) return true;
		return false;
	}
}