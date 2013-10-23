package pl.shockah.glib.animfx;

import java.util.ArrayList;
import java.util.List;

public class Animation {
	protected final List<String> tags = new ArrayList<>();
	protected final List<Timeline<?,?>> timelines = new ArrayList<>();
	protected double time = 0, updateSpeed = 1;
	protected boolean looped = false;
	protected int loop = 0;
	
	public void update() {update(updateSpeed);}
	public void update(double by) {
		time += by;
		double maxTime = getMaxTime();
		if (time > maxTime) {
			if (looped) {
				time -= maxTime;
				loop++;
			} else time = maxTime;
		} else if (time < 0) {
			if (looped) {
				time += maxTime;
				loop--;
			} else time = 0;
		}
	}
	public void reset() {
		time = 0;
		loop = 0;
	}
	
	public double getTime() {return time;}
	public void setTime(double time) {this.time = time;}
	
	public void add(Timeline<?,?> timeline) {
		add(null,timeline);
	}
	public void add(String tag, Timeline<?,?> timeline) {
		tags.add(tag);
		timelines.add(timeline);
	}
	public Timeline<?,?> getTimeline(String tag) {
		return timelines.get(tags.indexOf(tag));
	}
	public Object getState(String tag) {
		return getTimeline(tag).getState(this);
	}
	
	public double getMaxTime() {
		double max = 0;
		for (Timeline<?,?> timeline : timelines) if (timeline.getMaxTime() > max) max = timeline.getMaxTime();
		return max;
	}
	public boolean isFinished() {
		if (looped) return false;
		return time == getMaxTime();
	}
	public int getLoopNumber() {
		return loop;
	}
	
	public boolean isLooped() {return looped;}
	public Animation setLooped() {return setLooped(true);}
	public Animation setLooped(boolean looped) {
		this.looped = looped;
		return this;
	}
	
	public double getUpdateSpeed() {return updateSpeed;}
	public Animation resetUpdateSpeed() {return setUpdateSpeed(1d);}
	public Animation setUpdateSpeed(double updateSpeed) {
		this.updateSpeed = updateSpeed;
		return this;
	}
}