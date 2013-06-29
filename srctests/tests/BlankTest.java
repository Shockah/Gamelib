package tests;

import pl.shockah.glib.Gamelib;
import pl.shockah.glib.logic.standard.GameStandard;
import pl.shockah.glib.room.Room;

public class BlankTest extends Room {
	public static void main(String[] args) {
		BlankTest test = new BlankTest();
		Gamelib.start(new GameStandard(),test,test.getClass().getName());
	}
}