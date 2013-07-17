package pl.shockah.glib.gl.tex;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;
import pl.shockah.BinBuffer;
import pl.shockah.BinBufferInputStream;
import pl.shockah.BinBufferOutputStream;

public class SVGTextureLoader extends TextureLoader {
	public SVGTextureLoader() {
		super("SVG");
	}
	
	public Texture load(InputStream is) throws IOException {
		SAXSVGDocumentFactory df = new SAXSVGDocumentFactory(XMLResourceDescriptor.getXMLParserClassName());
		Document doc = df.createSVGDocument(null,is);
		
		TranscoderInput ti = new TranscoderInput(doc);
		BinBuffer binb = new BinBuffer();
		TranscoderOutput to = new TranscoderOutput(new BinBufferOutputStream(binb));
		
		PNGTranscoder trans = new PNGTranscoder();
		if (settings.containsKey("width")) trans.addTranscodingHint(PNGTranscoder.KEY_WIDTH,new Float((int)settings.get("width")));
		if (settings.containsKey("height")) trans.addTranscodingHint(PNGTranscoder.KEY_HEIGHT,new Float((int)settings.get("height")));
		
		try {
			trans.transcode(ti,to);
		} catch (TranscoderException e) {throw new IOException(e);}
		
		binb.setPos(0);
		return Texture.load(new BinBufferInputStream(binb),"PNG");
	}
	
	@Target(ElementType.FIELD) @Retention(RetentionPolicy.RUNTIME) public static @interface Options {
		public int width() default -1;
		public int height() default -1;
	}
}