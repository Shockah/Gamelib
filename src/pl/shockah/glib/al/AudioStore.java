package pl.shockah.glib.al;

import static org.lwjgl.openal.AL10.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AudioStore {
	private static final int musicSource;
	private static AudioInst music = null;
	protected static Map<Audio,List<AudioInst>> sounds = new HashMap<>();
	
	static {
		musicSource = alGenSources();
	}
	
	public static void clearMusic() {
		if (music != null) {
			music.stop();
			music = null;
		}
	}
	public static AudioInst music(Audio audio) {
		clearMusic();
		music = new AudioInst(audio);
		music.id = musicSource;
		music.dirty = false;
		return music;
	}
	public static AudioInst music() {
		return music;
	}
	
	public static AudioInst sound(Audio audio) {
		List<AudioInst> list = sounds.get(audio);
		if (list == null) {
			list = new LinkedList<>();
			sounds.put(audio,list);
		}
		for (AudioInst ai : list) if (!ai.isPlaying()) return ai;
		AudioInst ai = new AudioInst(audio);
		list.add(ai);
		return ai;
	}
}