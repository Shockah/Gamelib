package tests;

import java.io.IOException;
import pl.shockah.glib.Gamelib;
import pl.shockah.glib.gl.GL;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.Shader;
import pl.shockah.glib.gl.tex.Image;
import pl.shockah.glib.gl.tex.Texture;
import pl.shockah.glib.logic.EntityRenderable;
import pl.shockah.glib.state.State;

public class ShaderTest extends State {
	public static void main(String[] args) {
		State test = new ShaderTest();
		Gamelib.start(test,test.getClass().getName(),new Gamelib.Modules(false));
	}
	
	Image image;
	Shader shader;
	
	protected void onSetup() {
		fps = 60;
	}
	
	protected void onCreate() {
		try {
			image = new Image(Texture.load("assets/imagejpg1.jpg"));
			shader = Shader.createFromPath("assets/shader1");
		} catch (IOException e) {e.printStackTrace();}
		
		new EntityRenderable(){
			protected void onRender(Graphics g) {
				GL.bind(shader);
				shader.setUniform("texture1",0);
				g.draw(image,50,50);
				GL.unbindShader();
			}
		}.create();
	}
}