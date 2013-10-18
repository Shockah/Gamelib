package pl.shockah.glib.al;

import static org.lwjgl.openal.AL10.*;
import java.io.IOException;
import java.io.InputStream;

public class AIFFAudioLoader extends AudioLoader {
	public AIFFAudioLoader() {
		super("AIFF","AIF");
	}
	
	public Audio load(InputStream is) throws IOException {
		AiffData data = AiffData.create(is);
		if (data == null) throw new IOException();
		
		Audio audio = new Audio(alGenBuffers());
		alBufferData(audio.getID(),data.format,data.data,data.samplerate);
		return audio;
	}
}