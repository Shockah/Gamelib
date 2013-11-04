package pl.shockah.glib.logic;

public interface IGame {
	public void gameLoop();
	public void reset();
	
	public void setupInitialState();
	public void setInitialState();
}