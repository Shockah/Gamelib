package tests;

import pl.shockah.glib.Gamelib;
import pl.shockah.glib.room.Room;

public class BlankTest extends Room {
	@SuppressWarnings("unchecked") public static void main(String[] args) {
		BlankTest test = new BlankTest();
		Gamelib.make(pl.shockah.glib.logic.standard.Game.class).start(test,test.getClass().getName());
	}
}