package net.coderbot.iris;

import lombok.Getter;
import net.coderbot.iris.config.IrisConfig;

import java.util.HashMap;
import java.util.Map;

public class Iris {

    /**
     * The user-facing name of the mod. Moved into a constant to facilitate easy branding changes (for forks). You'll still need to change this separately in
     * mixin plugin classes & the language files.
     */
    public static final String MODNAME = "AngelicaShaders";

    public static final IrisLogging logger = new IrisLogging(MODNAME);

    @Getter
    private static final int shaderPackLoadId = 0;

    @Getter
    private static IrisConfig irisConfig;

    @Getter
    private static final Map<String, String> shaderPackOptionQueue = new HashMap<>();

    @Getter
    private static boolean fallback;

    public static Iris INSTANCE = new Iris();


}
