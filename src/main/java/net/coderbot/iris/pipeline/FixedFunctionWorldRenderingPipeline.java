package net.coderbot.iris.pipeline;

import net.coderbot.iris.block_rendering.BlockRenderingSettings;
import net.coderbot.iris.compat.dh.DHCompat;
import net.coderbot.iris.features.FeatureFlags;
import net.coderbot.iris.gbuffer_overrides.matching.InputAvailability;
import net.coderbot.iris.gbuffer_overrides.matching.SpecialCondition;

import java.util.OptionalInt;

public class FixedFunctionWorldRenderingPipeline implements WorldRenderingPipeline {
	public FixedFunctionWorldRenderingPipeline() {
		BlockRenderingSettings.INSTANCE.setDisableDirectionalShading(shouldDisableDirectionalShading());
		BlockRenderingSettings.INSTANCE.setUseSeparateAo(false);
		BlockRenderingSettings.INSTANCE.setAmbientOcclusionLevel(1.0f);
		BlockRenderingSettings.INSTANCE.setUseExtendedVertexFormat(false);
		BlockRenderingSettings.INSTANCE.setBlockTypeIds(null);
	}

    @Override
	public OptionalInt getForcedShadowRenderDistanceChunksForDisplay() {
		return OptionalInt.empty();
	}

	@Override
	public WorldRenderingPhase getPhase() {
		return WorldRenderingPhase.NONE;
	}

	@Override
	public void setOverridePhase(WorldRenderingPhase phase) {

	}

	@Override
	public void setPhase(WorldRenderingPhase phase) {

	}

	@Override
	public void setInputs(InputAvailability availability) {

	}

	@Override
	public void setSpecialCondition(SpecialCondition special) {

	}

    @Override
	public int getCurrentNormalTexture() {
		return 0;
	}

	@Override
	public int getCurrentSpecularTexture() {
		return 0;
	}

	@Override
	public void onBindTexture(int id) {

	}

    @Override
	public void destroy() {
		// stub: nothing to do here
	}

    private static final DHCompat DH_COMPAT = new DHCompat();

	@Override
	public DHCompat getDHCompat() {
		return DH_COMPAT;
	}

    @Override
	public boolean shouldDisableDirectionalShading() {
		return false;
	}

    @Override
	public boolean hasFeature(FeatureFlags flag) {
		return false;
	}

}
