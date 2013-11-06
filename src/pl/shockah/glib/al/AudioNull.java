package pl.shockah.glib.al;

public class AudioNull extends Audio {
	public static final AudioNullInst instNull = new AudioNullInst();
	
	public AudioNull() {
		super(-1);
	}
	
	public boolean equals(Object other) {
		return other instanceof AudioNull;
	}
	
	public void dispose() {
		disposed = true;
	}
	
	public AudioInst music() {
		return instNull;
	}
	public AudioInst sound() {
		return instNull;
	}
}