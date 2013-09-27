package pl.shockah.glib.input;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;

public class KTextInput {
	protected StringBuilder sb = new StringBuilder();
	protected int lastKey = -1;
	protected long repeat = -1;
	
	public String toString() {
		return sb.toString();
	}
	
	public void handle(boolean pressed, int kbKey) {
		char kbChr = KInput.keyCharacter[kbKey];
		
		if (pressed) {
			repeat = append(kbKey,kbChr) ? System.currentTimeMillis()+500 : -1;
		} else repeat = -1;
		
		lastKey = kbKey;
	}
	public void handleAll() {
		if (repeat == -1) return;
		long millis = System.currentTimeMillis();
		
		if (repeat-millis <= 0) {
			repeat = append(lastKey,KInput.keyCharacter[lastKey]) ? millis+50 : -1;
		}
	}
	
	public boolean append(int key, char chr) {
		if (key == Keyboard.KEY_BACK) {
			if (sb.length() == 0) return false;
			sb.deleteCharAt(sb.length()-1);
			return true;
		}
		if (key == Keyboard.KEY_V && KInput.isDown(Keyboard.KEY_LCONTROL)) {
			String clip = Sys.getClipboard();
			if (clip != null) sb.append(clip);
			return clip != null;
		}
		
		if (chr >= 32) {
			sb.append(chr);
			return true;
		}
		return false;
	}
	
	public void clear() {
		sb = new StringBuilder();
	}
}