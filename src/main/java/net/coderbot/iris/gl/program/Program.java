package net.coderbot.iris.gl.program;

import net.coderbot.iris.gl.GlResource;
import org.lwjgl.opengl.GL20;

public final class Program extends GlResource {

    Program(int program, ProgramUniforms uniforms, ProgramSamplers samplers, ProgramImages images) {
		super(program);

    }

    @Override
    public void destroyInternal() {
		GL20.glDeleteProgram(getGlId());
	}

	/**
	 * @return the OpenGL ID of this program.
	 * @deprecated this should be encapsulated eventually
	 */
	@Deprecated
	public int getProgramId() {
		return getGlId();
	}

}
