package pl.shockah.glib;

import java.util.List;
import org.lwjgl.Sys;
import pl.shockah.glib.state.State;

public class AssetLoader {
	protected final List<LoadableProcessor.LoadAction<?>> toLoad;
	protected int loaded = 0;
	
	public AssetLoader(List<LoadableProcessor.LoadAction<?>> toLoad) {
		this.toLoad = toLoad;
	}
	
	public int getLoadedCount() {
		return loaded;
	}
	public int getToLoadCount() {
		return toLoad.size();
	}
	public int getTotalCount() {
		return loaded+toLoad.size();
	}
	
	public void update() {
		update(1000/State.get().getFPS());
	}
	public void update(int maxms) {
		long time = getTime();
		while (!toLoad.isEmpty()) {
			toLoad.remove(0).load();
			loaded++;
			if (getTime()-time >= maxms) break;
		}
	}
	
	private long getTime() {
		return Sys.getTime()*1000/Sys.getTimerResolution();
	}
}