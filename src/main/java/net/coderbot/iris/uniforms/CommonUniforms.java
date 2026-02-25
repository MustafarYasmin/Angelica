package net.coderbot.iris.uniforms;

import com.gtnewhorizons.angelica.glsm.GLStateManager;
import com.gtnewhorizons.angelica.glsm.states.BlendState;
import com.gtnewhorizons.angelica.glsm.texture.TextureInfo;
import com.gtnewhorizons.angelica.glsm.texture.TextureInfoCache;
import com.gtnewhorizons.angelica.glsm.texture.TextureTracker;
import com.gtnewhorizons.angelica.mixins.interfaces.EntityRendererAccessor;
import net.coderbot.iris.gl.state.FogMode;
import net.coderbot.iris.gl.state.ValueUpdateNotifier;
import net.coderbot.iris.gl.uniform.DynamicUniformHolder;
import net.coderbot.iris.layer.GbufferPrograms;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Vec3;
import org.joml.Math;
import org.joml.Vector2i;
import org.joml.Vector3d;
import org.joml.Vector4i;

public final class CommonUniforms {
	private static final Minecraft client = Minecraft.getMinecraft();
	private static final Vector2i ZERO_VECTOR_2i = new Vector2i();
	private static final Vector4i ZERO_VECTOR_4i = new Vector4i(0, 0, 0, 0);
	private static final Vector3d ZERO_VECTOR_3d = new Vector3d();

	private CommonUniforms() {
		// no construction allowed
	}

    // Needs to use a LocationalUniformHolder as we need it for the common uniforms
	public static void addDynamicUniforms(DynamicUniformHolder uniforms, FogMode fogMode) {
		ExternallyManagedUniforms.addExternallyManagedUniforms116(uniforms);
        IdMapUniforms.addEntityIdMapUniforms(uniforms);
		FogUniforms.addFogUniforms(uniforms, fogMode);
		IrisInternalUniforms.addFogUniforms(uniforms, fogMode);

		uniforms.uniform2i("atlasSize", () -> {
			final int glId = GLStateManager.getBoundTextureForServerState(0);

			final AbstractTexture texture = TextureTracker.INSTANCE.getTexture(glId);
			if (texture instanceof TextureMap) {
				final TextureInfo info = TextureInfoCache.INSTANCE.getInfo(glId);
				return new Vector2i(info.getWidth(), info.getHeight());
			}

			return ZERO_VECTOR_2i;
		}, ValueUpdateNotifier.NONE);

		uniforms.uniform2i("gtextureSize", () -> {
			final int glId = GLStateManager.getBoundTextureForServerState(0);

			final TextureInfo info = TextureInfoCache.INSTANCE.getInfo(glId);
			return new Vector2i(info.getWidth(), info.getHeight());

		}, ValueUpdateNotifier.NONE);

		uniforms.uniform4i("blendFunc", () -> {
            if(GLStateManager.getBlendMode().isEnabled()) {
                final BlendState blend = GLStateManager.getBlendState();
                return new Vector4i(blend.getSrcRgb(), blend.getDstRgb(), blend.getSrcAlpha(), blend.getDstAlpha());
            }
            return ZERO_VECTOR_4i;
		}, ValueUpdateNotifier.NONE);

		uniforms.uniform1i("renderStage", () -> GbufferPrograms.getCurrentPhase().ordinal(), ValueUpdateNotifier.NONE);

        uniforms.uniform4f("entityColor", CapturedRenderingState.INSTANCE::getCurrentEntityColor, CapturedRenderingState.INSTANCE.getEntityColorNotifier());

	}

    private static boolean isOnGround() {
        return client.thePlayer != null && client.thePlayer.onGround;
    }

    private static boolean isHurt() {
        // Do not use isHurt, that's not what we want!
        return (client.thePlayer != null &&  client.thePlayer.hurtTime > 0);
    }

	private static boolean isInvisible() {
        return (client.thePlayer != null &&  client.thePlayer.isInvisible());
    }

    private static boolean isBurning() {
        return client.thePlayer != null && client.thePlayer.fire > 0 && !client.thePlayer.isImmuneToFire();
    }

    private static boolean isSneaking() {
        return (client.thePlayer != null && client.thePlayer.isSneaking());
    }

    private static boolean isSprinting() {
        return (client.thePlayer != null && client.thePlayer.isSprinting());
    }

	private static Vector3d getSkyColor() {
        if (client.theWorld == null || client.renderViewEntity == null) {
			return ZERO_VECTOR_3d;
		}
        final Vec3 skyColor = client.theWorld.getSkyColor(client.renderViewEntity, CapturedRenderingState.INSTANCE.getTickDelta());
        return new Vector3d(skyColor.xCoord, skyColor.yCoord, skyColor.zCoord);
	}

	static float getBlindness() {
        final EntityLivingBase cameraEntity = client.renderViewEntity;

        if (cameraEntity instanceof EntityPlayer livingEntity && livingEntity.isPotionActive(Potion.blindness)) {
            final PotionEffect blindness = livingEntity.getActivePotionEffect(Potion.blindness);

			if (blindness != null) {
				// Guessing that this is what OF uses, based on how vanilla calculates the fog value in BackgroundRenderer
				// TODO: Add this to ShaderDoc
				return Math.clamp(0.0F, 1.0F, blindness.getDuration() / 20.0F);
			}
		}

		return 0.0F;
	}

	private static float getPlayerMood() {
        // TODO: What should this be?
        return 0.0F;
//		if (!(client.cameraEntity instanceof LocalPlayer)) {
//			return 0.0F;
//		}
//
//		// This should always be 0 to 1 anyways but just making sure
//		return Math.clamp(0.0F, 1.0F, ((LocalPlayer) client.cameraEntity).getCurrentMood());
	}

	static float getRainStrength() {
        if (client.theWorld == null) {
			return 0f;
		}

		// Note: Ensure this is in the range of 0 to 1 - some custom servers send out of range values.
        return Math.clamp(0.0F, 1.0F, client.theWorld.getRainStrength(CapturedRenderingState.INSTANCE.getTickDelta()));

	}

	private static Vector2i getEyeBrightness() {
        if (client.renderViewEntity == null || client.theWorld == null) {
			return ZERO_VECTOR_2i;
		}
        // This is what ShadersMod did in 1.7.10
        final int eyeBrightness = client.renderViewEntity.getBrightnessForRender(CapturedRenderingState.INSTANCE.getTickDelta());
        return new Vector2i((eyeBrightness & 0xffff), (eyeBrightness >> 16));

//		Vec3 feet = client.cameraEntity.position();
//		Vec3 eyes = new Vec3(feet.x, client.cameraEntity.getEyeY(), feet.z);
//		BlockPos eyeBlockPos = new BlockPos(eyes);
//
//		int blockLight = client.level.getBrightness(LightLayer.BLOCK, eyeBlockPos);
//		int skyLight = client.level.getBrightness(LightLayer.SKY, eyeBlockPos);
//
//		return new Vector2i(blockLight * 16, skyLight * 16);
	}

	private static float getNightVision() {
        Entity cameraEntity = client.renderViewEntity;

        if (cameraEntity instanceof EntityPlayer entityPlayer) {
            if (!entityPlayer.isPotionActive(Potion.nightVision)) {
                return 0.0F;
            }
            float nightVisionStrength = ((EntityRendererAccessor)client.entityRenderer).invokeGetNightVisionBrightness(entityPlayer, CapturedRenderingState.INSTANCE.getTickDelta());

			try {
				if (nightVisionStrength > 0) {
					// Just protecting against potential weird mod behavior
					return Math.clamp(0.0F, 1.0F, nightVisionStrength);
				}
			} catch (NullPointerException e) {
				return 0.0F;
			}
		}

		return 0.0F;
	}

	static int isEyeInWater() {
        if (client.gameSettings.thirdPersonView == 0 && !client.renderViewEntity.isPlayerSleeping()) {
            if (client.thePlayer.isInsideOfMaterial(Material.water))
			return 1;
            else if (client.thePlayer.isInsideOfMaterial(Material.lava))
			return 2;
        }
			return 0;
		}

	static {
		GbufferPrograms.init();
	}
}
