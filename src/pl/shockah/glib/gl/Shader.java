package pl.shockah.glib.gl;

import static org.lwjgl.opengl.GL11.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import pl.shockah.FileIO;
import pl.shockah.func.Action1;
import pl.shockah.glib.LoadableProcessor;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.geom.vector.Vector2f;
import pl.shockah.glib.geom.vector.Vector2i;

public class Shader {
	public static Shader createFromFile(File path) throws IOException {return createFromFile(path,false,true);}
	public static Shader createFromFile(File path, boolean mixTexturing, boolean embedConsts) throws IOException {
		String vertex = FileIO.readWholeString(new File(path.getParentFile(),path.getName()+".vert"));
		String fragment = FileIO.readWholeString(new File(path.getParentFile(),path.getName()+".frag"));
		return create(vertex,fragment,mixTexturing,embedConsts);
	}
	public static Shader createFromPath(String internalPath) throws IOException {return createFromPath(internalPath,false,true);}
	public static Shader createFromPath(String internalPath, boolean mixTexturing, boolean embedConsts) throws IOException {
		String vertex = FileIO.readWholeString(new BufferedInputStream(Shader.class.getClassLoader().getResourceAsStream(internalPath+".vert")));
		String fragment = FileIO.readWholeString(new BufferedInputStream(Shader.class.getClassLoader().getResourceAsStream(internalPath+".frag")));
		return create(vertex,fragment,mixTexturing,embedConsts);
	}
	public static Shader create(String vertex, String fragment) throws IOException {return create(vertex,fragment,false,true);}
	public static Shader create(String vertex, String fragment, boolean mixTexturing, boolean embedConsts) throws IOException {
		if (embedConsts) {
			StringBuilder sb = new StringBuilder();
			sb.append("uniform sampler2D tex;\n");
			if (mixTexturing) sb.append("uniform float mixTexturing;\n");
			sb.append("\n");
			fragment = sb.toString()+fragment;
		}
		
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
	private boolean mixTexturing = false;
	
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
	
	private int uniformLocation(String name) {
		Shader old = GL.boundShader();
		if (!equals(old)) GL.bind(this);
		
		Integer loc = cacheUniforms.get(name);
		if (loc == null) {
			loc = ARBShaderObjects.glGetUniformLocationARB(sdrId,name);
			cacheUniforms.put(name,loc);
		}
		
		if (!equals(old)) GL.bind(old);
		return loc;
	}
	
	public void setUniform(String name, double d) {setUniform(name,(float)d);}
	public void setUniform(String name, float f) {
		Shader old = GL.boundShader();
		if (!equals(old)) GL.bind(this);
		ARBShaderObjects.glUniform1fARB(uniformLocation(name),f);
		if (!equals(old)) GL.bind(old);
	}
	public void setUniform(String name, int i) {
		Shader old = GL.boundShader();
		if (!equals(old)) GL.bind(this);
		ARBShaderObjects.glUniform1iARB(uniformLocation(name),i);
		if (!equals(old)) GL.bind(old);
	}
	
	public void setUniform(String name, Vector2d v) {setUniform(name,v.toFloat());}
	public void setUniform(String name, double d1, double d2) {setUniform(name,(float)d1,(float)d2);}
	public void setUniform(String name, Vector2f v) {setUniform(name,v.x,v.y);}
	public void setUniform(String name, float f1, float f2) {
		Shader old = GL.boundShader();
		if (!equals(old)) GL.bind(this);
		ARBShaderObjects.glUniform2fARB(uniformLocation(name),f1,f2);
		if (!equals(old)) GL.bind(old);
	}
	public void setUniform(String name, Vector2i v) {setUniform(name,v.x,v.y);}
	public void setUniform(String name, int i1, int i2) {
		Shader old = GL.boundShader();
		if (!equals(old)) GL.bind(this);
		ARBShaderObjects.glUniform2iARB(uniformLocation(name),i1,i2);
		if (!equals(old)) GL.bind(old);
	}
	
	public void doWith(Action1<Shader> func) {
		Shader old = GL.boundShader();
		if (!equals(old)) GL.bind(this);
		func.f(this);
		if (!equals(old)) GL.bind(old);
	}
	
	public void setMixTexturing(boolean b) {mixTexturing = b;}
	public boolean mixTexturing() {return mixTexturing;}
	
	public void handleTexturing(boolean texturing) {
		if (!mixTexturing) return;
		setUniform("mixTexturing",texturing ? 1f : 0f);
		setUniform("tex",0);
	}
	
	@Target(ElementType.FIELD) @Retention(RetentionPolicy.RUNTIME) public static @interface Loadable {
		public String path() default "assets/images/<field.name>";
		public LoadableProcessor.AssetType type() default LoadableProcessor.AssetType.Internal;
		public boolean mixTexturing() default false;
		public boolean embedConsts() default true;
	}
}