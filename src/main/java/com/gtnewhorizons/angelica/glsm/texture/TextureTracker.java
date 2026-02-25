package com.gtnewhorizons.angelica.glsm.texture;

import com.gtnewhorizons.angelica.glsm.GLStateManager;
import com.gtnewhorizons.angelica.glsm.RenderSystem;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.renderer.texture.AbstractTexture;
import org.jetbrains.annotations.Nullable;

public class TextureTracker {
    /**
     * Adapted from Iris for use in GLSM
     */

	public static final TextureTracker INSTANCE = new TextureTracker();

	private final Int2ObjectMap<AbstractTexture> textures = new Int2ObjectOpenHashMap<>();

	private boolean lockBindCallback;

	private TextureTracker() {
	}

    @Nullable
	public AbstractTexture getTexture(int id) {
		return textures.get(id);
	}

	public void onBindTexture(int id) {
		if (lockBindCallback) {
			return;
		}
		if (GLStateManager.getActiveTextureUnit() == 0) {
			lockBindCallback = true;
            // Reset texture state
			RenderSystem.bindTextureToUnit(0, id);
			lockBindCallback = false;
		}
	}

	public void onDeleteTexture(int id) {
		textures.remove(id);
	}
}
