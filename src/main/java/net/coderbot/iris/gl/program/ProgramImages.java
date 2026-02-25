package net.coderbot.iris.gl.program;

import com.gtnewhorizons.angelica.glsm.RenderSystem;

import java.util.ArrayList;
import java.util.List;

public class ProgramImages {
    private List<GlUniform1iCall> initializer;

	private ProgramImages(List<GlUniform1iCall> initializer) {
        this.initializer = initializer;
	}

	public void update() {
		if (initializer != null) {
			for (GlUniform1iCall call : initializer) {
				RenderSystem.uniform1i(call.getLocation(), call.getValue());
			}

			initializer = null;
		}
	}

    public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
        private final List<GlUniform1iCall> calls;

        private Builder() {
            this.calls = new ArrayList<>();
        }

        public ProgramImages build() {
			return new ProgramImages(calls);
		}
	}
}
