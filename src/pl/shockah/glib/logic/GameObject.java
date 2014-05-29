package pl.shockah.glib.logic;

public abstract class GameObject {
	private boolean created = false, destroyed = false;
	
	public void create() {
		created = true;
	}
	public void destroy() {
		destroyed = true;
	}
	
	public final boolean created() {return created;}
	public final boolean destroyed() {return destroyed;}
}