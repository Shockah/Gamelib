package tests;

import pl.shockah.glib.Gamelib;
import pl.shockah.glib.state.State;

public class BlankTest extends State {
	public static void main(String[] args) {
		State test = new BlankTest();
		Gamelib.start(test,test.getClass().getName(),new Gamelib.Modules(false));
	}
}