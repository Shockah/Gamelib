package pl.shockah.glib.input;

import pl.shockah.glib.Gamelib;

public class MButton {
	protected final MouseInput mouse;
	protected final int[] checkFor;
	
	public MButton(int... checkFor) {this(Gamelib.mouse,checkFor);}
	public MButton(MouseInput mouse, int... checkFor) {
		this.mouse = mouse;
		this.checkFor = checkFor;
	}
	
	public boolean pressed() {
		for (int i = 0; i < checkFor.length; i++) if (mouse.isPressed(checkFor[i])) return true;
		return false;
	}
	public boolean released() {
		for (int i = 0; i < checkFor.length; i++) if (mouse.isReleased(checkFor[i])) return true;
		return false;
	}
	public boolean down() {
		for (int i = 0; i < checkFor.length; i++) if (mouse.isDown(checkFor[i])) return true;
		return false;
	}
}