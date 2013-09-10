package pl.shockah.glib.gl;

import static org.lwjgl.opengl.GL11.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import pl.shockah.FileLine;

public class Shader {
	public static Shader createFromPath(String internalPath) throws IOException {
		return createFromPaths(internalPath+".vert",internalPath+".frag");
	}
	public static Shader createFromPaths(String internalPathVertex, String internalPathFragment) throws IOException {
		String vertex = internalPathVertex == null ? null : FileLine.readString(new BufferedInputStream(Shader.class.getClassLoader().getResourceAsStream(internalPathVertex)));
		String fragment = internalPathFragment == null ? null : FileLine.readString(new BufferedInputStream(Shader.class.getClassLoader().getResourceAsStream(internalPathFragment)));
		return create(vertex,fragment);
	}
	public static Shader create(String vertex, String fragment) throws IOException {
		int shaderVertex = createShader(ARBVertexShader.GL_VERTEX_SHADER_ARB,vertex);
		int shaderFragment = createShader(ARBFragmentShader.GL_FRAGMENT_SHADER_ARB,fragment);
		if (shaderVertex == 0 && shaderFragment == 0) {
			throw new IOException("Couldn't create shader.");
		}
		
		int sdrId = ARBShaderObjects.glCreateProgramObjectARB();
		if (shaderVertex != 0) ARBShaderObjects.glAttachObjectARB(sdrId,shaderVertex);
		if (shaderFragment != 0) ARBShaderObjects.glAttachObjectARB(sdrId,shaderFragment);
		
		ARBShaderObjects.glLinkProgramARB(sdrId);
		if (ARBShaderObjects.glGetObjectParameteriARB(sdrId,ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL_FALSE) {
			throw new IOException("Couldn't create shader.");
		}
		
		ARBShaderObjects.glValidateProgramARB(sdrId);
		if (ARBShaderObjects.glGetObjectParameteriARB(sdrId,ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL_FALSE) {
			throw new IOException("Couldn't create shader.");
		}
		
		return new Shader(sdrId);
	}
	private static int createShader(int type, String code) throws IOException {
		if (code == null) return 0;
		int id = 0;
		try {
			id = ARBShaderObjects.glCreateShaderObjectARB(type);
			if (id == 0) throw new IOException("Couldn't create shader.");
			
			ARBShaderObjects.glShaderSourceARB(id,code);
			ARBShaderObjects.glCompileShaderARB(id);
			
			if (ARBShaderObjects.glGetObjectParameteriARB(id,ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL_FALSE) {
				throw new IOException("Couldn't create shader.");
			}
			return id;
		} catch (Exception e) {
			if (id != 0) ARBShaderObjects.glDeleteObjectARB(id);
			throw new IOException("Couldn't create shader.",e);
		}
	}
	
	private final int sdrId;
	
	public Shader(int sdrId) {
		this.sdrId = sdrId;
	}
	
	public boolean equals(Object other) {
		if (!(other instanceof Shader)) return false;
		Shader sdr = (Shader)other;
		return sdr.sdrId == sdrId;
	}
	
	public int getID() {
		return sdrId;
	}
}