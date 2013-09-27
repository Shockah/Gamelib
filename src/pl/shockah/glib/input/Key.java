package pl.shockah.glib.input;

public class Key {
	protected final int[] checkFor;
	
	public Key(int... checkFor) {
		this.checkFor = checkFor;
	}
	
	public boolean pressed() {
		for (int i = 0; i < checkFor.length; i++) if (KInput.isPressed(checkFor[i])) return true;
		return false;
	}
	public boolean released() {
		for (int i = 0; i < checkFor.length; i++) if (KInput.isReleased(checkFor[i])) return true;
		return false;
	}
	public boolean down() {
		for (int i = 0; i < checkFor.length; i++) if (KInput.isDown(checkFor[i])) return true;
		return false;
	}
}