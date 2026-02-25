package net.coderbot.iris.pipeline;

import net.coderbot.iris.compat.dh.DHCompat;
import net.coderbot.iris.features.FeatureFlags;
import net.coderbot.iris.gbuffer_overrides.matching.InputAvailability;
import net.coderbot.iris.gbuffer_overrides.matching.SpecialCondition;

import java.util.OptionalInt;

public interface WorldRenderingPipeline {
    OptionalInt getForcedShadowRenderDistanceChunksForDisplay();

	WorldRenderingPhase getPhase();

	void setOverridePhase(WorldRenderingPhase phase);
	void setPhase(WorldRenderingPhase phase);
	void setInputs(InputAvailability availability);
	void setSpecialCondition(SpecialCondition special);

    int getCurrentNormalTexture();
	int getCurrentSpecularTexture();

	void onBindTexture(int id);

    void destroy();

    DHCompat getDHCompat();

    boolean shouldDisableDirectionalShading();

    boolean hasFeature(FeatureFlags flag);
}
