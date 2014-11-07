package pl.shockah.glib;

public final class Gamelib {
	public static <T extends Game> T start(Class<T> cls) {
		try {
			T game = cls.newInstance();
			return game;
		} catch (Exception e) {}
		return null;
	}
	
	private Gamelib() {}
}