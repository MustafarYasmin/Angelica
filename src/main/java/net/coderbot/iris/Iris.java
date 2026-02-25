package net.coderbot.iris;

import lombok.Getter;
import net.coderbot.iris.config.IrisConfig;
import net.coderbot.iris.pipeline.PipelineManager;
import net.minecraft.launchwrapper.Launch;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Iris {
    public final boolean isDevelopmentEnvironment;

    /**
     * The user-facing name of the mod. Moved into a constant to facilitate easy branding changes (for forks). You'll still need to change this separately in
     * mixin plugin classes & the language files.
     */
    public static final String MODNAME = "AngelicaShaders";

    public static final IrisLogging logger = new IrisLogging(MODNAME);

    // Cached at class load - config must be loaded before Iris. Do not change at runtime.
    public static final boolean enabled = false;

    @Getter
    private static final int shaderPackLoadId = 0;

    private static PipelineManager pipelineManager;
    @Getter
    private static IrisConfig irisConfig;

    @Getter
    private static final Map<String, String> shaderPackOptionQueue = new HashMap<>();

    @Getter
    private static boolean fallback;

    public static Iris INSTANCE = new Iris();

    private Iris() {
        // Guard against null blackboard in test environments
        final Object deobfEnv = Launch.blackboard != null ? Launch.blackboard.get("fml.deobfuscatedEnvironment") : null;
        isDevelopmentEnvironment = deobfEnv != null && (boolean) deobfEnv;
    }


    @NotNull
    public static PipelineManager getPipelineManager() {
        if (pipelineManager == null) {
            pipelineManager = new PipelineManager(null);
        }

        return pipelineManager;
    }

}
