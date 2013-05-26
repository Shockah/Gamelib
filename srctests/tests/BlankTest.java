package tests;

import pl.shockah.glib.Gamelib;
import pl.shockah.glib.geom.Vector2f;
import pl.shockah.glib.logic.standard.Game;
import pl.shockah.glib.room.Room;

public class BlankTest extends Room {
	public static void main(String[] args) {
		BlankTest test = new BlankTest();
		Gamelib.make(new Game<Vector2f>()).start(test,test.getClass().getName());
	}
}