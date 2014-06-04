package pl.shockah.glib.input;

public class Key {
	protected final int[] checkFor;
	
	public Key(int... checkFor) {
		this.checkFor = checkFor;
	}
	
	public boolean pressed() {
		for (int i = 0; i < checkFor.length; i++) if (KInput.pressed(checkFor[i])) return true;
		return false;
	}
	public boolean released() {
		for (int i = 0; i < checkFor.length; i++) if (KInput.released(checkFor[i])) return true;
		return false;
	}
	public boolean down() {
		for (int i = 0; i < checkFor.length; i++) if (KInput.down(checkFor[i])) return true;
		return false;
	}
}