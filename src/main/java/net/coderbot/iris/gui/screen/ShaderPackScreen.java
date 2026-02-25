package net.coderbot.iris.gui.screen;

import net.minecraft.client.gui.GuiScreen;

import java.util.HashSet;
import java.util.Set;

public class ShaderPackScreen extends GuiScreen implements HudHideable {
    /**
     * Queue rendering to happen on top of all elements. Useful for tooltips or dialogs.
     */
    public static final Set<Runnable> TOP_LAYER_RENDER_QUEUE = new HashSet<>();
}
