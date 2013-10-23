package pl.shockah.glib.gl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.*;

public abstract class BlendMode {
	private static BlendMode current = null;
	protected static boolean blending = false;
	
	public static final BlendMode
		Off = new BlendMode(){
			public void onApply() {
				if (blending) {
					glDisable(GL_BLEND);
					blending = false;
				}
				GL.colorMask(true,true,true,true);
			}
		},
		Normal = new BlendMode(){
			public void onApply() {
				if (!blending) {
					glEnable(GL_BLEND);
					blending = true;
				}
				GL.colorMask(true,true,true,true);
				glBlendFunc(GL_SRC_ALPHA,GL_ONE_MINUS_SRC_ALPHA);
			}
		},
		Premultiplied = new BlendMode(){
			public void onApply() {
				if (!blending) {
					glEnable(GL_BLEND);
					blending = true;
				}
				GL.colorMask(true,true,true,true);
				glBlendFunc(GL_ONE,GL_ONE_MINUS_SRC_ALPHA);
			}
		},
		Add = new BlendMode(){
			public void onApply() {
				if (!blending) {
					glEnable(GL_BLEND);
					blending = true;
				}
				GL.colorMask(true,true,true,true);
				glBlendFunc(GL_SRC_ALPHA,GL_ONE);
			}
		},
		AddSimple = new BlendMode(){
			public void onApply() {
				if (!blending) {
					glEnable(GL_BLEND);
					blending = true;
				}
				GL.colorMask(true,true,true,true);
				glBlendFunc(GL_ONE,GL_ONE);
			}
		},
		Multiply = new BlendMode(){
			public void onApply() {
				if (!blending) {
					glEnable(GL_BLEND);
					blending = true;
				}
				GL.colorMask(true,true,true,true);
				glBlendFunc(GL_DST_COLOR,GL_ZERO);
			}
		},
		Subtract = new BlendMode(){
			public void onApply() {
				if (!blending) {
					glEnable(GL_BLEND);
					blending = true;
				}
				GL.colorMask(true,true,true,true);
				glBlendFunc(GL_SRC_ALPHA,GL_ONE);
				glBlendEquation(GL_FUNC_REVERSE_SUBTRACT);
			}
			public void onReset() {
				glBlendEquation(GL_FUNC_ADD);
			}
		};
	
	public final void apply() {
		if (current != null) current.onReset();
		if (current == this) return;
		current = this;
		onApply();
	}
	public abstract void onApply();
	public void onReset() {}
}