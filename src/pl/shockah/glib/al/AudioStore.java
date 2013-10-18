package pl.shockah.glib.al;

import java.util.LinkedList;
import java.util.List;

public class AudioStore {
	private static AudioInst music = null;
	protected static List<AudioInst> sounds = new LinkedList<>();
	
	public static void clearMusic() {
		if (music != null) {
			music.stop();
			music = null;
		}
	}
	public static AudioInst setMusic(Audio audio) {
		clearMusic();
		return music = new AudioInst(audio);
	}
	public static AudioInst getMusic() {
		return music;
	}
	
	public static AudioInst sound(Audio audio) {
		for (AudioInst ai : sounds) {
			if (ai.audio.equals(audio) && ai.isStopped()) return ai;
		}
		AudioInst ai = new AudioInst(audio);
		sounds.add(ai);
		return ai;
	}
}