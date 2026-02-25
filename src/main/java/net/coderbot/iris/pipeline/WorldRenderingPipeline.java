package net.coderbot.iris.pipeline;

import net.coderbot.iris.features.FeatureFlags;
import net.coderbot.iris.gbuffer_overrides.matching.SpecialCondition;

import java.util.OptionalInt;

public interface WorldRenderingPipeline {
    OptionalInt getForcedShadowRenderDistanceChunksForDisplay();

	WorldRenderingPhase getPhase();

	void setOverridePhase(WorldRenderingPhase phase);
	void setPhase(WorldRenderingPhase phase);

    void setSpecialCondition(SpecialCondition special);

    void onBindTexture(int id);

    void destroy();

    boolean shouldDisableDirectionalShading();

    boolean hasFeature(FeatureFlags flag);
}
