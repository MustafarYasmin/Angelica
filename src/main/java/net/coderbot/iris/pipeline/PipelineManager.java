package net.coderbot.iris.pipeline;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PipelineManager {

    private final WorldRenderingPipeline pipeline = new FixedFunctionWorldRenderingPipeline();
    @Getter
	private final int versionCounterForSodiumShaderReload = 0;

	public PipelineManager() {
    }

    @Nullable
	public WorldRenderingPipeline getPipelineNullable() {
		return pipeline;
	}

	public Optional<WorldRenderingPipeline> getPipeline() {
		return Optional.of(pipeline);
	}


}
