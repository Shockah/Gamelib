package tests;

import pl.shockah.glib.Gamelib;
import pl.shockah.glib.logic.standard.GameStandard;
import pl.shockah.glib.state.State;

public class BlankTest extends State {
	public static void main(String[] args) {
		BlankTest test = new BlankTest();
		Gamelib.start(new GameStandard(),test,test.getClass().getName());
	}
}