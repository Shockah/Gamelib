package pl.shockah.glib.gl;

import static org.lwjgl.opengl.GL11.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import pl.shockah.FileLine;
import pl.shockah.glib.geom.vector.Vector2f;
import pl.shockah.glib.geom.vector.Vector2i;

public class Shader {
	public static Shader createFromPath(String internalPath) throws IOException {
		String vertex = FileLine.readString(new BufferedInputStream(Shader.class.getClassLoader().getResourceAsStream(internalPath+".vert")));
		String fragment = FileLine.readString(new BufferedInputStream(Shader.class.getClassLoader().getResourceAsStream(internalPath+".frag")));
		return create(vertex,fragment);
	}
	public static Shader create(String vertex, String fragment) throws IOException {
		int shaderVertex = createShader(ARBVertexShader.GL_VERTEX_SHADER_ARB,vertex);
		int shaderFragment = createShader(ARBFragmentShader.GL_FRAGMENT_SHADER_ARB,fragment);
		if (shaderVertex == 0 || shaderFragment == 0) {
			throw new IOException("Couldn't create shader.");
		}
		
		int sdrId = ARBShaderObjects.glCreateProgramObjectARB();
		
		ARBShaderObjects.glAttachObjectARB(sdrId,shaderVertex);
		ARBShaderObjects.glAttachObjectARB(sdrId,shaderFragment);
		
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
	private final Map<String,Integer> cacheUniforms = new HashMap<>();
	
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
	
	private int getUniformLocation(String name) {
		Shader old = GL.boundShader();
		GL.bind(this);
		
		Integer loc = cacheUniforms.get(name);
		if (loc == null) {
			loc = ARBShaderObjects.glGetUniformLocationARB(sdrId,name);
			cacheUniforms.put(name,loc);
		}
		
		GL.bind(old);
		return loc;
	}
	
	public void setUniform(String name, float f) {
		Shader old = GL.boundShader();
		GL.bind(this);
		ARBShaderObjects.glUniform1fARB(getUniformLocation(name),f);
		GL.bind(old);
	}
	public void setUniform(String name, int i) {
		Shader old = GL.boundShader();
		GL.bind(this);
		ARBShaderObjects.glUniform1iARB(getUniformLocation(name),i);
		GL.bind(old);
	}
	
	public void setUniform(String name, Vector2f v) {setUniform(name,v.x,v.y);}
	public void setUniform(String name, float f1, float f2) {
		Shader old = GL.boundShader();
		GL.bind(this);
		ARBShaderObjects.glUniform2fARB(getUniformLocation(name),f1,f2);
		GL.bind(old);
	}
	public void setUniform(String name, Vector2i v) {setUniform(name,v.x,v.y);}
	public void setUniform(String name, int i1, int i2) {
		Shader old = GL.boundShader();
		GL.bind(this);
		ARBShaderObjects.glUniform2iARB(getUniformLocation(name),i1,i2);
		GL.bind(old);
	}
}