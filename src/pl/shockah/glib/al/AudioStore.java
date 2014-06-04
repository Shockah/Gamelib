package pl.shockah.glib.al;

import java.util.LinkedList;
import java.util.List;

public class AudioStore {
	protected static AudioSource musicSource = null;
	protected static AudioInst musicAudioi = null;
	protected static final List<AudioSource> sources = new LinkedList<>();
	
	public static AudioInst music() {
		if (musicSource == null) music(null);
		return musicAudioi;
	}
	public static AudioInst music(Audio audio) {
		if (musicSource == null) musicSource = new AudioSource();
		if (!musicSource.stopped()) musicSource.stop();
		musicSource.reset(audio);
		musicAudioi = new AudioInst(audio,musicSource);
		return musicAudioi;
	}
	
	public static AudioInst sound(Audio audio) {
		for (AudioSource source : sources) {
			if (source.stopped()) return new AudioInst(audio,source);
		}
		
		AudioSource source = new AudioSource();
		sources.add(source);
		return new AudioInst(audio,source);
	}
	
	public static void findNewSource(AudioInst audioi) {
		for (AudioSource source : sources) {
			if (source.stopped()) {
				source.pairAudioInst(audioi);
				return;
			}
		}
		
		AudioSource source = new AudioSource();
		sources.add(source);
		source.pairAudioInst(audioi);
	}
}