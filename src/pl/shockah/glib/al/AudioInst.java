package pl.shockah.glib.al;

import static org.lwjgl.openal.AL10.*;

public class AudioInst {
	public final Audio audio;
	public int id = -1;
	protected float gain = 1f, pitch = 1f;
	protected boolean dirty = true;
	
	protected AudioInst(Audio audio) {
		this.audio = audio;
	}
	
	public void play() {play(false);}
	public void play(boolean loop) {
		findSlot();
		stop();
		
		alSourcef(id,AL_BUFFER,audio.getID());
		alSourcef(id,AL_GAIN,gain);
		alSourcef(id,AL_PITCH,pitch);
		alSourcef(id,AL_LOOPING,loop ? 1 : 0);
		alSourcePlay(id);
	}
	public void pause() {
		if (isPlaying()) alSourcePause(id);
	}
	/*public void resume() {
		if (isPaused()) alSourcePlay(id);
	}
	public void togglePause() {
		if (isPaused()) resume(); else pause();
	}*/
	public void stop() {
		if (isStopped()) return;
		alSourceStop(id);
	}
	
	public boolean isPlaying() {
		findSlot();
		return alGetSourcei(id,AL_SOURCE_STATE) == AL_PLAYING;
	}
	/*public boolean isPaused() {
		findSlot();
		return alGetSourcei(id,4112) == 4115;
	}*/
	public boolean isStopped() {
		return isStopped(true);
	}
	protected boolean isStopped(boolean findSlot) {
		if (findSlot) findSlot();
		int state = alGetSourcei(id,AL_SOURCE_STATE);
		return state != AL_PLAYING/* && state != 4115*/;
	}
	
	public AudioInst setGain(float gain) {
		this.gain = gain;
		if (!isStopped()) alSourcef(id,AL_GAIN,gain);
		return this;
	}
	public float getGain() {
		return gain;
	}
	
	public AudioInst setPitch(float pitch) {
		this.pitch = pitch;
		if (!isStopped()) alSourcef(id,AL_PITCH,pitch);
		return this;
	}
	public float getPitch() {
		return pitch;
	}
	
	protected void findSlot() {
		if (!dirty) return;
		for (AudioInst ai : AudioStore.sounds.get(audio)) {
			if (ai.dirty) continue;
			if (ai.isStopped(false)) ai.dirty = true;
			id = ai.id;
			dirty = false;
			return;
		}
		id = alGenSources();
		dirty = false;
	}
	
	public void finalize() {
		if (!dirty) alDeleteSources(id);
	}
}