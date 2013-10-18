package pl.shockah.glib.al;

import static org.lwjgl.openal.AL10.*;
import java.io.IOException;
import java.io.InputStream;

public class WAVAudioLoader extends AudioLoader {
	public WAVAudioLoader() {
		super("WAV");
	}
	
	public Audio load(InputStream is) throws IOException {
		WaveData data = WaveData.create(is);
		if (data == null) throw new IOException();
		
		Audio audio = new Audio(alGenBuffers());
		alBufferData(audio.getID(),data.format,data.data,data.samplerate);
		return audio;
	}
}