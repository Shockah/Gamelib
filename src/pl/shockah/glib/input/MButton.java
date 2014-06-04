package pl.shockah.glib.input;

public class MButton {
	protected final int[] checkFor;
	
	public MButton(int... checkFor) {
		this.checkFor = checkFor;
	}
	
	public boolean pressed() {
		for (int i = 0; i < checkFor.length; i++) if (MInput.pressed(checkFor[i])) return true;
		return false;
	}
	public boolean released() {
		for (int i = 0; i < checkFor.length; i++) if (MInput.released(checkFor[i])) return true;
		return false;
	}
	public boolean down() {
		for (int i = 0; i < checkFor.length; i++) if (MInput.down(checkFor[i])) return true;
		return false;
	}
}