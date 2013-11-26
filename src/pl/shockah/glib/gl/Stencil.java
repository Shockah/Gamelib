package pl.shockah.glib.gl;

import static org.lwjgl.opengl.GL11.*;
import pl.shockah.glib.gl.color.Color;

public abstract class Stencil {
	public static final Stencil
		Keep = new Stencil(){
			public void phaseStencil(Graphics g) {
				super.phaseStencil(g);
				glStencilFunc(GL_NEVER,1,0xFF);
				glStencilOp(GL_REPLACE,GL_KEEP,GL_KEEP);
			}
			public void phaseTest(Graphics g) {
				super.phaseTest(g);
				glStencilFunc(GL_EQUAL,1,0xFF);
				glStencilOp(GL_KEEP,GL_REPLACE,GL_REPLACE);
			}
		},
		KeepWithDrawing = new Stencil(){
			public void phaseStencil(Graphics g) {
				g.clear(Color.TransparentBlack,false,true);
				glEnable(GL_STENCIL_TEST);
				glStencilFunc(GL_NEVER,1,0xFF);
				glStencilOp(GL_REPLACE,GL_KEEP,GL_KEEP);
			}
			public void phaseTest(Graphics g) {
				glStencilFunc(GL_EQUAL,1,0xFF);
				glStencilOp(GL_KEEP,GL_REPLACE,GL_REPLACE);
			}
		},
		Drop = new Stencil(){
			public void phaseStencil(Graphics g) {
				super.phaseStencil(g);
				glStencilFunc(GL_NEVER,1,0xFF);
				glStencilOp(GL_REPLACE,GL_KEEP,GL_KEEP);
			}
			public void phaseTest(Graphics g) {
				super.phaseTest(g);
				glStencilFunc(GL_NOTEQUAL,1,0xFF);
				glStencilOp(GL_KEEP,GL_KEEP,GL_REPLACE);
			}
		},
		DropWithDrawing = new Stencil(){
			public void phaseStencil(Graphics g) {
				g.clear(Color.TransparentBlack,false,true);
				glEnable(GL_STENCIL_TEST);
				glStencilFunc(GL_NEVER,1,0xFF);
				glStencilOp(GL_REPLACE,GL_KEEP,GL_KEEP);
			}
			public void phaseTest(Graphics g) {
				glStencilFunc(GL_NOTEQUAL,1,0xFF);
				glStencilOp(GL_KEEP,GL_KEEP,GL_REPLACE);
			}
		};
	
	public void phaseStencil(Graphics g) {
		g.clear(Color.TransparentBlack,false,true);
		GL.colorMask(false,false,false,false);
		glEnable(GL_STENCIL_TEST);
	}
	public void phaseTest(Graphics g) {
		GL.colorMask(true,true,true,true);
	}
	public void phasePost(Graphics g) {
		glDisable(GL_STENCIL_TEST);
	}
}