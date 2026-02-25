package net.coderbot.iris.gl.program;

import com.google.common.collect.ImmutableSet;
import net.coderbot.iris.gl.shader.GlShader;
import net.coderbot.iris.gl.shader.ProgramCreator;
import net.coderbot.iris.gl.shader.ShaderType;
import org.jetbrains.annotations.Nullable;

public class ProgramBuilder extends ProgramUniforms.Builder {
	private final int program;
	private final ProgramSamplers.Builder samplers;
	private final ProgramImages.Builder images;

	private ProgramBuilder(String name, int program, ImmutableSet<Integer> reservedTextureUnits) {
		super(name, program);

		this.program = program;
		this.samplers = ProgramSamplers.builder(reservedTextureUnits);
		this.images = ProgramImages.builder();
	}

    public static ProgramBuilder begin(String name, @Nullable String vertexSource, @Nullable String geometrySource,
									   @Nullable String fragmentSource, ImmutableSet<Integer> reservedTextureUnits) {
		return begin(name, vertexSource, geometrySource, null, null, fragmentSource, reservedTextureUnits);
	}

	public static ProgramBuilder begin(String name, @Nullable String vertexSource, @Nullable String geometrySource,
									   @Nullable String tessControlSource, @Nullable String tessEvalSource,
									   @Nullable String fragmentSource, ImmutableSet<Integer> reservedTextureUnits) {
		GlShader vertex = buildShader(ShaderType.VERTEX, name + ".vsh", vertexSource);
		GlShader geometry = geometrySource != null ? buildShader(ShaderType.GEOMETRY, name + ".gsh", geometrySource) : null;
		GlShader tessControl = tessControlSource != null ? buildShader(ShaderType.TESSELATION_CONTROL, name + ".tcs", tessControlSource) : null;
		GlShader tessEval = tessEvalSource != null ? buildShader(ShaderType.TESSELATION_EVAL, name + ".tes", tessEvalSource) : null;
		GlShader fragment = buildShader(ShaderType.FRAGMENT, name + ".fsh", fragmentSource);

		java.util.List<GlShader> shaders = new java.util.ArrayList<>();
		shaders.add(vertex);
		if (geometry != null) shaders.add(geometry);
		if (tessControl != null) shaders.add(tessControl);
		if (tessEval != null) shaders.add(tessEval);
		shaders.add(fragment);

		int programId = ProgramCreator.create(name, shaders.toArray(new GlShader[0]));

		for (GlShader shader : shaders) {
			shader.destroy();
		}

		return new ProgramBuilder(name, programId, reservedTextureUnits);
	}

    public Program build() {
		return new Program(program, super.buildUniforms(), this.samplers.build(), this.images.build());
	}

    private static GlShader buildShader(ShaderType shaderType, String name, @Nullable String source) {
		try {
			return new GlShader(shaderType, name, source);
		} catch (RuntimeException e) {
			throw new RuntimeException("Failed to compile " + shaderType + " shader for program " + name, e);
		}
	}

}
