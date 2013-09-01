package pl.shockah.glib.gl;

import static org.lwjgl.opengl.GL11.*;

public abstract class Stencil {
	public static final Stencil
		Keep = new Stencil(){
			public void phaseStencil(Graphics g) {
				super.phaseStencil(g);
				glStencilFunc(GL_ALWAYS,1,0xFF);
				glStencilOp(GL_KEEP,GL_REPLACE,GL_REPLACE);
				glStencilMask(0xFF);
			}
			public void phaseTest(Graphics g) {
				super.phaseTest(g);
				glStencilFunc(GL_EQUAL,1,0xFF);
				glStencilOp(GL_REPLACE,GL_REPLACE,GL_REPLACE);
			}
		},
		Drop = new Stencil(){
			public void phaseStencil(Graphics g) {
				super.phaseStencil(g);
				glStencilFunc(GL_ALWAYS,1,0xFF);
				glStencilOp(GL_KEEP,GL_REPLACE,GL_REPLACE);
				glStencilMask(0xFF);
			}
			public void phaseTest(Graphics g) {
				super.phaseTest(g);
				glStencilFunc(GL_NOTEQUAL,1,0xFF);
				glStencilOp(GL_KEEP,GL_KEEP,GL_KEEP);
			}
		};
	
	public void phaseStencil(Graphics g) {
		glColorMask(false,false,false,false);
		glEnable(GL_STENCIL_TEST);
	}
	public void phaseTest(Graphics g) {
		glColorMask(true,true,true,true);
	}
	public void phasePost(Graphics g) {
		glDisable(GL_STENCIL_TEST);
	}
}