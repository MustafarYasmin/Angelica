package net.irisshaders.iris.api.v0;

import net.coderbot.iris.apiimpl.IrisApiV0Impl;

/**
 * The entry point to the Iris API, major version 0. This is currently the latest
 * version of the API.
 * <p>
 * To access the API, use {@link #getInstance()}.
 */
public interface IrisApi {
	/**
	 * @since API v0.0
	 */
	static IrisApi getInstance() {
		return IrisApiV0Impl.INSTANCE;
	}

    /**
	 * Checks whether a shader pack is currently in use and being used
	 * for rendering. If there is no shader pack enabled or a shader
	 * pack failed to compile and is therefore not in use, this will
	 * return false.
	 *
	 * <p>Mods that need to enable custom workarounds for shaders
	 * should use this method.
	 *
	 * @return Whether shaders are being used for rendering.
	 */
	boolean isShaderPackInUse();

}
