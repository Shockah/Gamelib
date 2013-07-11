package pl.shockah.glib.gl.tex;

import java.io.IOException;
import java.io.InputStream;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import pl.shockah.BinBuffer;
import pl.shockah.BinBufferInputStream;
import pl.shockah.BinBufferOutputStream;

public class SVGTextureLoader extends TextureLoader {
	public SVGTextureLoader() {
		super("SVG");
	}
	
	public Texture load(InputStream is) throws IOException {
		TranscoderInput ti = new TranscoderInput(is);
		BinBuffer binb = new BinBuffer();
		TranscoderOutput to = new TranscoderOutput(new BinBufferOutputStream(binb));
		
		PNGTranscoder trans = new PNGTranscoder();
		if (settings.containsKey("width")) trans.addTranscodingHint(PNGTranscoder.KEY_WIDTH,settings.get("width"));
		if (settings.containsKey("height")) trans.addTranscodingHint(PNGTranscoder.KEY_HEIGHT,settings.get("height"));
		
		try {
			trans.transcode(ti,to);
		} catch (TranscoderException e) {throw new IOException(e);}
		
		binb.setPos(0);
		return Texture.load(new BinBufferInputStream(binb),"PNG");
	}
}