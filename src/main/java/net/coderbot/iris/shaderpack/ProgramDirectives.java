package net.coderbot.iris.shaderpack;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.coderbot.iris.gl.blending.AlphaTestOverride;
import net.coderbot.iris.gl.blending.BlendModeOverride;
import net.coderbot.iris.gl.blending.BufferBlendInformation;
import net.coderbot.iris.gl.framebuffer.ViewportData;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProgramDirectives {
	private static final ImmutableList<String> LEGACY_RENDER_TARGETS = PackRenderTargetDirectives.LEGACY_RENDER_TARGETS;

	private final int[] drawBuffers;
	private boolean unknownDrawBuffers;
	private final ViewportData viewportScale;
	@Nullable
	private final AlphaTestOverride alphaTestOverride;

	private final Optional<BlendModeOverride> blendModeOverride;
	private final List<BufferBlendInformation> bufferBlendInformations;
	private final ImmutableSet<Integer> mipmappedBuffers;
	private final ImmutableMap<Integer, Boolean> explicitFlips;

	private ProgramDirectives(int[] drawBuffers, ViewportData viewportScale, @Nullable AlphaTestOverride alphaTestOverride,
							  Optional<BlendModeOverride> blendModeOverride, List<BufferBlendInformation> bufferBlendInformations, ImmutableSet<Integer> mipmappedBuffers,
							  ImmutableMap<Integer, Boolean> explicitFlips) {
		this.drawBuffers = drawBuffers;
		this.viewportScale = viewportScale;
		this.alphaTestOverride = alphaTestOverride;
		this.blendModeOverride = blendModeOverride;
		this.bufferBlendInformations = bufferBlendInformations;
		this.mipmappedBuffers = mipmappedBuffers;
		this.explicitFlips = explicitFlips;
		this.unknownDrawBuffers = false;
	}

    public ProgramDirectives withOverriddenDrawBuffers(int[] drawBuffersOverride) {
		return new ProgramDirectives(drawBuffersOverride, viewportScale, alphaTestOverride, blendModeOverride, bufferBlendInformations,
			mipmappedBuffers, explicitFlips);
	}

	private static Optional<CommentDirective> findDrawbuffersDirective(Optional<String> stageSource) {
		return stageSource.flatMap(fragment -> CommentDirectiveParser.findDirective(fragment, CommentDirective.Type.DRAWBUFFERS));
	}

	private static Optional<CommentDirective> findRendertargetsDirective(Optional<String> stageSource) {
		return stageSource.flatMap(fragment -> CommentDirectiveParser.findDirective(fragment, CommentDirective.Type.RENDERTARGETS));
	}

	private static int[] parseDigits(char[] directiveChars) {
		int[] buffers = new int[directiveChars.length];
		int index = 0;

		for (char buffer : directiveChars) {
			buffers[index++] = Character.digit(buffer, 10);
		}

		return buffers;
	}

	private static int[] parseDigitList(String digitListString) {
		return Arrays.stream(digitListString.split(","))
				.mapToInt(Integer::parseInt)
				.toArray();
	}

	private static Optional<CommentDirective> getAppliedDirective(Optional<CommentDirective> optionalDrawbuffersDirective, Optional<CommentDirective> optionalRendertargetsDirective) {
		if (optionalDrawbuffersDirective.isPresent() && optionalRendertargetsDirective.isPresent()) {
			if (optionalDrawbuffersDirective.get().getLocation() > optionalRendertargetsDirective.get().getLocation()) {
				return optionalDrawbuffersDirective;
			} else {
				return optionalRendertargetsDirective;
			}
		} else if (optionalDrawbuffersDirective.isPresent()) {
			return optionalDrawbuffersDirective;
		} else if (optionalRendertargetsDirective.isPresent()) {
			return optionalRendertargetsDirective;
		} else {
			return Optional.empty();
		}
	}

	public int[] getDrawBuffers() {
		return drawBuffers;
	}

	public boolean hasUnknownDrawBuffers() {
		return unknownDrawBuffers;
	}

	public ViewportData getViewportScale() {
		return viewportScale;
	}

	public Optional<AlphaTestOverride> getAlphaTestOverride() {
		return Optional.ofNullable(alphaTestOverride);
	}

	public Optional<BlendModeOverride> getBlendModeOverride() {
		return blendModeOverride;
	}

	public List<BufferBlendInformation> getBufferBlendOverrides() {
		return bufferBlendInformations;
	}

	public ImmutableSet<Integer> getMipmappedBuffers() {
		return mipmappedBuffers;
	}

	public ImmutableMap<Integer, Boolean> getExplicitFlips() {
		return explicitFlips;
	}
}
