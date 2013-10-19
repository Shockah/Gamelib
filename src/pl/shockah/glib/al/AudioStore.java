package pl.shockah.glib.al;

import static org.lwjgl.openal.AL10.*;
import java.util.LinkedList;
import java.util.List;

public class AudioStore {
	private static final int musicSource;
	private static AudioInst music = null;
	public static List<AudioInst> sounds = new LinkedList<>();
	
	static {
		musicSource = alGenSources();
	}
	
	public static void clearMusic() {
		if (music != null) {
			music.stop();
			music = null;
		}
	}
	public static AudioInst setMusic(Audio audio) {
		clearMusic();
		music = new AudioInst(audio);
		music.id = musicSource;
		music.dirty = false;
		return music;
	}
	public static AudioInst getMusic() {
		return music;
	}
	
	public static AudioInst sound(Audio audio) {
		for (AudioInst ai : sounds) if (ai.isStopped()) return ai;
		AudioInst ai = new AudioInst(audio);
		sounds.add(ai);
		return ai;
	}
}