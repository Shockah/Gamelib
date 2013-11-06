package pl.shockah.glib.al;

public class AudioNullInst extends AudioInst {
	protected AudioNullInst() {
		super(null);
	}
	
	public void play(boolean loop) {}
	public void pause() {}
	public void stop() {}
	
	public boolean isPlaying() {return false;}
	protected boolean isStopped(boolean findSlot) {return true;}
	
	public AudioInst setGain(float gain) {return this;}
	public AudioInst setPitch(float pitch) {return this;}
	
	protected void findSlot() {}
	public void finalize() {}
}