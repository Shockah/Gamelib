package pl.shockah.glib;

import java.lang.reflect.Field;
import java.util.List;
import pl.shockah.glib.LoadableProcessor;

public interface ILoadableAnnotationHandler {
	public void handle(List<LoadableProcessor.LoadAction<?>> ret, Field fld, Object o);
}