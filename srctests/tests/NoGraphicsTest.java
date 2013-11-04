package tests;

import pl.shockah.glib.Gamelib;
import pl.shockah.glib.logic.standard.Entity;
import pl.shockah.glib.logic.standard.GameStandard;
import pl.shockah.glib.state.State;

public class NoGraphicsTest extends State {
	public static void main(String[] args) {
		State test = new NoGraphicsTest();
		Gamelib.start(new GameStandard(test),test.getClass().getName(),new Gamelib.Modules(false,false));
	}
	
	protected void onSetup() {
		fps = 1;
	}
	
	protected void onCreate() {
		new Entity(){
			protected void onUpdate() {
				System.out.println("a");
			}
		}.create();
	}
}