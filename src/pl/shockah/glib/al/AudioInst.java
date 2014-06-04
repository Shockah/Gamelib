package pl.shockah.glib.al;

public class AudioInst {
	public final Audio audio;
	protected AudioSource source = null;
	
	protected AudioInst(Audio audio) {
		this.audio = audio;
	}
	protected AudioInst(Audio audio, AudioSource source) {
		this.audio = audio;
		source.pairAudioInst(this);
	}
	
	public boolean readyCheck() {
		if (audio.disposed()) return false;
		if (source == null || source.disposed()) AudioStore.findNewSource(this);
		return true;
	}
	
	public float gain() {
		if (!readyCheck()) throw new ALAudioException();
		return source.gain();
	}
	public AudioInst gain(float gain) {
		if (!readyCheck()) throw new ALAudioException();
		source.gain(gain);
		return this;
	}
	
	public float pitch() {
		if (!readyCheck()) throw new ALAudioException();
		return source.pitch();
	}
	public AudioInst pitch(float pitch) {
		if (!readyCheck()) throw new ALAudioException();
		source.pitch(pitch);
		return this;
	}
	
	public boolean looping() {
		if (!readyCheck()) throw new ALAudioException();
		return source.looping();
	}
	public AudioInst looping(boolean looping) {
		if (!readyCheck()) throw new ALAudioException();
		source.looping(looping);
		return this;
	}
	
	public void play() {
		if (!readyCheck()) throw new ALAudioException();
		source.play();
	}
	public void pause() {
		if (!readyCheck()) throw new ALAudioException();
		source.pause();
	}
	public void resume() {
		if (!readyCheck()) throw new ALAudioException();
		source.resume();
	}
	public void stop() {
		if (!readyCheck()) throw new ALAudioException();
		source.stop();
	}
	
	public boolean isPlaying() {
		if (!readyCheck()) throw new ALAudioException();
		return source.isPlaying();
	}
	public boolean isPaused() {
		if (!readyCheck()) throw new ALAudioException();
		return source.isPaused();
	}
	public boolean isStopped() {
		if (!readyCheck()) throw new ALAudioException();
		return source.isStopped();
	}
}