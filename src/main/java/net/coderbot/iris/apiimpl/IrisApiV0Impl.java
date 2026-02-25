package net.coderbot.iris.apiimpl;

import net.irisshaders.iris.api.v0.IrisApi;


public class IrisApiV0Impl implements IrisApi {
	public static final IrisApiV0Impl INSTANCE = new IrisApiV0Impl();

    @Override
	public boolean isShaderPackInUse() {
        return false;
	}

}
