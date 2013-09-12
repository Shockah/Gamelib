package pl.shockah.glib.gl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.*;

public abstract class BlendMode {
	private static BlendMode current = null;
	
	public static final BlendMode
		Normal = new BlendMode(){
			public void onApply() {
				glEnable(GL_BLEND);
				glColorMask(true,true,true,true);
				glBlendFunc(GL_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA);
			}
		},
		Premultiplied = new BlendMode(){
			public void onApply() {
				glEnable(GL_BLEND);
				glColorMask(true,true,true,true);
				glBlendFunc(GL_ONE,GL_ONE_MINUS_SRC_ALPHA);
			}
		},
		Add = new BlendMode(){
			public void onApply() {
				glEnable(GL_BLEND);
				glColorMask(true,true,true,true);
				glBlendFunc(GL_SRC_ALPHA,GL_ONE);
			}
		},
		Subtract = new BlendMode(){
			public void onApply() {
				glEnable(GL_BLEND);
				glColorMask(true,true,true,true);
				glBlendFunc(GL_SRC_ALPHA,GL_ONE);
				glBlendEquation(GL_FUNC_SUBTRACT);
				glBlendEquation(GL_FUNC_REVERSE_SUBTRACT);
			}
			public void onReset() {
				glBlendEquation(GL_FUNC_ADD);
			}
		};
	
	public final void apply() {
		if (current != null) current.onReset();
		current = this;
		onApply();
	}
	public abstract void onApply();
	public void onReset() {}
}