package pl.shockah.glib;

import java.util.List;
import org.lwjgl.Sys;
import pl.shockah.Util;
import pl.shockah.glib.state.State;

public class AssetLoader {
	protected final List<LoadableProcessor.LoadAction<?>> toLoad;
	protected int loaded = 0;
	protected double currentStatus = 0d;
	
	public AssetLoader() {
		this(Util.getCallingClass(AssetLoader.class));
	}
	public AssetLoader(Class<?> cls) {
		this(LoadableProcessor.process(cls));
	}
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
	public boolean finished() {
		return getLoadedCount() == getTotalCount();
	}
	
	public void update() {
		update(1000/State.get().getFPS());
	}
	public void update(int maxms) {
		long time = getTime();
		while (!toLoad.isEmpty()) {
			if (toLoad.get(0).load(this)) {
				toLoad.remove(0);
				loaded++;
				currentStatus = 0d;
			}
			if (getTime()-time >= maxms) break;
		}
	}
	public void setCurrentStatus(double d) {
		currentStatus = d;
	}
	
	public double getStatus() {
		double d = 1d*getLoadedCount()/getTotalCount();
		d += currentStatus/getTotalCount();
		return d;
	}
	
	private long getTime() {
		return Sys.getTime()*1000/Sys.getTimerResolution();
	}
}