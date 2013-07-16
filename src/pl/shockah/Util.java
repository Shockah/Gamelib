package pl.shockah;

import java.util.Arrays;
import java.util.List;

public final class Util {
	public static Class<?> getCallingClass(Class<?>... ignore) {
		List<Class<?>> list = Arrays.asList(ignore);
		StackTraceElement[] stes = new Throwable().getStackTrace();
		for (int i = 1; i < stes.length; i++) {
			try {
				Class<?> cls = Class.forName(stes[i].getClassName());
				if (!list.contains(cls)) return cls;
			} catch (Exception e) {e.printStackTrace();}
		}
		return null;
	}
}