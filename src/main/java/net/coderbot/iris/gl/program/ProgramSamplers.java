package net.coderbot.iris.gl.program;

import com.google.common.collect.ImmutableList;
import com.gtnewhorizons.angelica.glsm.GLStateManager;
import com.gtnewhorizons.angelica.glsm.RenderSystem;
import net.coderbot.iris.gl.sampler.SamplerBinding;
import net.coderbot.iris.gl.sampler.SamplerLimits;
import net.coderbot.iris.gl.state.ValueUpdateNotifier;
import org.lwjgl.opengl.GL13;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProgramSamplers {
	private static ProgramSamplers active;
	private final ImmutableList<SamplerBinding> samplerBindings;
	private final ImmutableList<ValueUpdateNotifier> notifiersToReset;
	private List<GlUniform1iCall> initializer;

	private ProgramSamplers(ImmutableList<SamplerBinding> samplerBindings, ImmutableList<ValueUpdateNotifier> notifiersToReset, List<GlUniform1iCall> initializer) {
		this.samplerBindings = samplerBindings;
		this.notifiersToReset = notifiersToReset;
		this.initializer = initializer;
	}

	public void update() {
		if (active != null) {
			active.removeListeners();
		}

		active = this;

		if (initializer != null) {
			for (GlUniform1iCall call : initializer) {
				RenderSystem.uniform1i(call.getLocation(), call.getValue());
			}

			initializer = null;
		}

		// We need to keep the active texture intact, since if we mess it up
		// in the middle of RenderType setup, bad things will happen.
		int activeTexture = GLStateManager.getActiveTextureUnit();

		for (SamplerBinding samplerBinding : samplerBindings) {
			samplerBinding.update();
		}

		GLStateManager.glActiveTexture(GL13.GL_TEXTURE0 + activeTexture);
	}

	public void removeListeners() {
		active = null;

		for (ValueUpdateNotifier notifier : notifiersToReset) {
			notifier.setListener(null);
		}
	}

    public static Builder builder(Set<Integer> reservedTextureUnits) {
		return new Builder(reservedTextureUnits);
	}

    public static final class Builder {
        private final ImmutableList.Builder<SamplerBinding> samplers;
		private final ImmutableList.Builder<ValueUpdateNotifier> notifiersToReset;
		private final List<GlUniform1iCall> calls;

        private Builder(Set<Integer> reservedTextureUnits) {
            this.samplers = ImmutableList.builder();
			this.notifiersToReset = ImmutableList.builder();
			this.calls = new ArrayList<>();

			int maxTextureUnits = SamplerLimits.get().getMaxTextureUnits();

			for (int unit : reservedTextureUnits) {
				if (unit >= maxTextureUnits) {
					throw new IllegalStateException("Cannot mark texture unit " + unit + " as reserved because that " +
							"texture unit isn't available on this system! Only " + maxTextureUnits +
							" texture units are available.");
				}
			}

            int nextUnit = 0;

			while (reservedTextureUnits.contains(nextUnit)) {
				nextUnit += 1;
			}

        }

        public ProgramSamplers build() {
			return new ProgramSamplers(samplers.build(), notifiersToReset.build(), calls);
		}
	}

}
