package pl.shockah.glib.al;

import static org.lwjgl.openal.AL10.*;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class WAVAudioLoader extends AudioLoader {
	public static class WaveData {
		public final ByteBuffer data;
		public final int format;
		public final int samplerate;

		private WaveData(ByteBuffer data, int format, int samplerate) {
			this.data = data;
			this.format = format;
			this.samplerate = samplerate;
		}

		public void dispose() {
			data.clear();
		}

		public static WaveData create(URL path) {
			try {
				return create(AudioSystem.getAudioInputStream(new BufferedInputStream(path.openStream())));
			} catch (Exception e) {e.printStackTrace();}
			return null;
		}

		public static WaveData create(String path) {
			return create(WaveData.class.getClassLoader().getResource(path));
		}

		public static WaveData create(InputStream is) {
			try {
				return create(AudioSystem.getAudioInputStream(is));
			} catch (Exception e) {e.printStackTrace();}
			return null;
		}

		public static WaveData create(byte[] buffer) {
			try {
				return create(AudioSystem.getAudioInputStream(new BufferedInputStream(new ByteArrayInputStream(buffer))));
			} catch (Exception e) {e.printStackTrace();}
			return null;
		}

		public static WaveData create(ByteBuffer buffer) {
			try {
				byte[] bytes = null;

				if (buffer.hasArray()) {
					bytes = buffer.array();
				} else {
					bytes = new byte[buffer.capacity()];
					buffer.get(bytes);
				}
				return create(bytes);
			} catch (Exception e) {e.printStackTrace();}
			return null;
		}

		public static WaveData create(AudioInputStream ais) {
			AudioFormat audioformat = ais.getFormat();

			int channels = 0;
			if (audioformat.getChannels() == 1) {
				if (audioformat.getSampleSizeInBits() == 8) channels = 4352;
				else if (audioformat.getSampleSizeInBits() == 16) channels = 4353;
				else throw new RuntimeException("Illegal sample size");
			} else if (audioformat.getChannels() == 2) {
				if (audioformat.getSampleSizeInBits() == 8) channels = 4354;
				else if (audioformat.getSampleSizeInBits() == 16) channels = 4355;
				else throw new RuntimeException("Illegal sample size");
			} else throw new RuntimeException("Only mono or stereo is supported");

			byte[] buf = new byte[audioformat.getChannels()*(int)ais.getFrameLength()*audioformat.getSampleSizeInBits()/8];

			int read = 0;
			int total = 0;
			try {
				while (((read = ais.read(buf,total,buf.length - total)) != -1) && (total < buf.length)) total += read;
			} catch (IOException ioe) {return null;}

			ByteBuffer buffer = convertAudioBytes(buf,audioformat.getSampleSizeInBits() == 16);
			WaveData wavedata = new WaveData(buffer,channels,(int)audioformat.getSampleRate());
			try {
				ais.close();
			} catch (IOException ioe) {}
			return wavedata;
		}

		private static ByteBuffer convertAudioBytes(byte[] audio_bytes, boolean two_bytes_data) {
			ByteBuffer dest = ByteBuffer.allocateDirect(audio_bytes.length);
			dest.order(ByteOrder.nativeOrder());
			ByteBuffer src = ByteBuffer.wrap(audio_bytes);
			src.order(ByteOrder.LITTLE_ENDIAN);
			if (two_bytes_data) {
				ShortBuffer dest_short = dest.asShortBuffer();
				ShortBuffer src_short = src.asShortBuffer();
				while (src_short.hasRemaining()) dest_short.put(src_short.get());
			} else while (src.hasRemaining()) dest.put(src.get());
			dest.rewind();
			return dest;
		}
	}
	
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