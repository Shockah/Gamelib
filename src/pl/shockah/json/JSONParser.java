package pl.shockah.json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import pl.shockah.StrBuffer;

public class JSONParser {
	protected static final List<Character> listWhitespace = new ArrayList<>(Arrays.asList(new Character[]{' ','\r','\n','\t'}));
	
	public JSONObject parseObject(CharSequence cs) {
		StrBuffer strb = new StrBuffer();
		strb.append(cs);
		strb.setPos(0);
		return parseJSONObject(strb);
	}
	public JSONList<?> parseList(CharSequence cs) {
		StrBuffer strb = new StrBuffer();
		strb.append(cs);
		strb.setPos(0);
		return parseJSONList(strb);
	}
	
	protected JSONObject parseJSONObject(StrBuffer strb) {
		skipWhitespace(strb);
		if (strb.readChar() != '{') throw new RuntimeException("Invalid JSON input.");
		
		JSONObject j = new JSONObject();
		while (true) {
			skipWhitespace(strb);
			if (strb.atEnd()) break;
			char c = strb.readChar();
			if (c == '}') {
				strb.seek(-1);
				break;
			} else if (c == ',') {
				skipWhitespace(strb);
			} else {
				strb.seek(-1);
			}
			parseJSONObjectEntry(strb,j);
		}
		
		skipWhitespace(strb);
		if (strb.readChar() != '}') throw new RuntimeException("Invalid JSON input.");
		return j;
	}
	protected void parseJSONObjectEntry(StrBuffer strb, JSONObject j) {
		if (strb.readChar() != '"') throw new RuntimeException("Invalid JSON input.");
		strb.seek(-1);
		String key = parseString(strb);
		
		skipWhitespace(strb);
		if (strb.readChar() != ':') throw new RuntimeException("Invalid JSON input.");
		skipWhitespace(strb);
		
		j.put(key,parseValue(strb));
	}
	
	protected JSONList<?> parseJSONList(StrBuffer strb) {
		skipWhitespace(strb);
		if (strb.readChar() != '[') throw new RuntimeException("Invalid JSON input.");
		
		JSONList<?> j = new JSONList<>();
		while (true) {
			skipWhitespace(strb);
			if (strb.atEnd()) break;
			char c = strb.readChar();
			if (c == ']') {
				strb.seek(-1);
				break;
			} else if (c == ',') {
				skipWhitespace(strb);
			} else {
				strb.seek(-1);
			}
			parseJSONListEntry(strb,j);
		}
		
		skipWhitespace(strb);
		if (strb.readChar() != ']') throw new RuntimeException("Invalid JSON input.");
		return j;
	}
	protected void parseJSONListEntry(StrBuffer strb, JSONList<?> j) {
		skipWhitespace(strb);
		j.put(parseValue(strb));
	}
	
	protected void skipWhitespace(StrBuffer strb) {
		while (!strb.atEnd() && listWhitespace.contains(strb.readChar()));
		strb.seek(-1);
	}
	protected String parseString(StrBuffer strb) {return parseString(strb,true);}
	protected String parseWord(StrBuffer strb) {return parseString(strb,false);}
	protected String parseString(StrBuffer strb, boolean realString) {
		char chrStart = realString ? strb.readChar() : 0;
		StringBuilder sb = new StringBuilder();
		
		char store = 0;
		while (true) {
			char c = strb.readChar();
			if (store != 0) {
				if (c == '\\' || (realString && c == chrStart)) {
					sb.append(c);
				} else switch (c) {
					case 'r': sb.append('\r'); break;
					case 'n': sb.append('\n'); break;
					case 't': sb.append('\t'); break;
				}
				store = 0;
			} else {
				if (c == '\\') {
					store = c;
				} else if (realString ? (c == chrStart) : (c == ',' || c == ']' || c == '}' || listWhitespace.contains(c))) {
					if (c == ',' || c == ']' || c == '}') strb.seek(-1);
					return sb.toString();
				} else {
					sb.append(c);
				}
			}
		}
	}
	protected Object parseValue(StrBuffer strb) {
		Object val = null;
		char c = strb.readChar();
		strb.seek(-1);
		if (c == '"') {
			val = parseString(strb);
		} else if (c == '{') {
			val = parseJSONObject(strb);
		} else if (c == '[') {
			val = parseJSONList(strb);
		} else {
			String word = parseWord(strb);
			
			if (word.equals("true")) {
				val = true;
			} else if (word.equals("false")) {
				val = false;
			} else {
				try {
					val = Integer.parseInt(word);
				} catch (Exception e1) {
					try {
						val = Long.parseLong(word);
					} catch (Exception e2) {
						try {
							val = Float.parseFloat(word);
						} catch (Exception e3) {
							try {
								val = Double.parseDouble(word);
							} catch (Exception e4) {
								val = null;
							}
						}
					}
				}
			}
		}
		
		return val;
	}
}