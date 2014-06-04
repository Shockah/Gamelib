package pl.shockah.glib.al;

import static org.lwjgl.openal.AL10.*;

public class AudioSource {
	public final int id;
	protected boolean disposed = false, dirty = true;
	protected AudioInst currentInst = null;
	protected Audio cacheAudio;
	protected float cacheGain, cachePitch;
	protected boolean cacheLooping;
	
	protected AudioSource() {
		this(alGenSources());
	}
	protected AudioSource(int id) {
		this.id = id;
	}
	
	public boolean disposed() {
		return disposed;
	}
	public void dispose() {
		pairAudioInst(null);
		alDeleteSources(id);
		AudioStore.sources.remove(this);
		disposed = true;
	}
	protected void finalize() {
		dispose();
	}
	
	public void pairAudioInst(AudioInst audioi) {
		if (currentInst != null) {
			currentInst.source = null;
		}
		currentInst = audioi;
		if (audioi != null) audioi.source = this;
		reset(audioi == null ? null : audioi.audio);
	}
	
	public void reset() {reset(null);}
	public void reset(Audio audio) {
		audio(audio);
		gain(1);
		pitch(1);
		looping(false);
		dirty = false;
	}
	
	public AudioSource audio(Audio audio) {
		if (cacheAudio == audio) return this;
		alSourcef(id,AL_BUFFER,audio == null ? -1 : audio.id);
		cacheAudio = audio;
		return this;
	}
	public Audio audio() {return cacheAudio;}
	
	public AudioSource gain(float gain) {
		if (cacheGain == gain) return this;
		alSourcef(id,AL_GAIN,gain);
		cacheGain = gain;
		return this;
	}
	public float gain() {return cacheGain;}
	
	public AudioSource pitch(float pitch) {
		if (cachePitch == pitch) return this;
		alSourcef(id,AL_PITCH,pitch);
		cachePitch = pitch;
		return this;
	}
	public float pitch() {return cachePitch;}
	
	public AudioSource looping(boolean looping) {
		if (cacheLooping == looping) return this;
		alSourcef(id,AL_LOOPING,looping?1:0);
		cacheLooping = looping;
		return this;
	}
	public boolean looping() {return cacheLooping;}
	
	protected int getAlSourceState() {
		return alGetSourcei(id,AL_SOURCE_STATE);
	}
	
	public void play() {
		if (isPaused()) stop();
		alSourcePlay(id);
	}
	public boolean isPlaying() {
		if (cacheAudio == null) return false;
		return getAlSourceState() == AL_PLAYING;
	}
	
	public void pause() {
		if (cacheAudio == null) return;
		alSourcePause(id);
	}
	public void resume() {
		if (isPaused()) alSourcePlay(id);
	}
	public boolean isPaused() {
		if (cacheAudio == null) return false;
		return getAlSourceState() == AL_PAUSED;
	}
	
	public void stop() {
		if (cacheAudio == null) return;
		alSourceStop(id);
	}
	public boolean isStopped() {
		if (cacheAudio == null) return true;
		int state = getAlSourceState();
		return state != AL_PLAYING && state != AL_PAUSED;
	}
}