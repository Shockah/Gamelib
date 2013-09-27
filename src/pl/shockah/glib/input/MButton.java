package pl.shockah.glib.input;

public class MButton {
	protected final int[] checkFor;
	
	public MButton(int... checkFor) {
		this.checkFor = checkFor;
	}
	
	public boolean pressed() {
		for (int i = 0; i < checkFor.length; i++) if (MInput.isPressed(checkFor[i])) return true;
		return false;
	}
	public boolean released() {
		for (int i = 0; i < checkFor.length; i++) if (MInput.isReleased(checkFor[i])) return true;
		return false;
	}
	public boolean down() {
		for (int i = 0; i < checkFor.length; i++) if (MInput.isDown(checkFor[i])) return true;
		return false;
	}
}