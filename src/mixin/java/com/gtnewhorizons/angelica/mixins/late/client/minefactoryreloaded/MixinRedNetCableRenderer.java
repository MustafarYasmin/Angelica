package com.gtnewhorizons.angelica.mixins.late.client.minefactoryreloaded;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.world.IBlockAccess;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import powercrystals.minefactoryreloaded.render.block.RedNetCableRenderer;
import powercrystals.minefactoryreloaded.tile.rednet.TileEntityRedNetCable;

@Mixin(value = RedNetCableRenderer.class)
public abstract class MixinRedNetCableRenderer extends TileEntitySpecialRenderer {

    /**
     * @author JL2210
     * @reason Thread safety compat (check result of getTileEntity for null)
     */
    @Inject(method = "renderWorldBlock", at = @At(value = "FIELD", target = "Lpowercrystals/minefactoryreloaded/setup/MFRConfig;TESRCables*:Z"), cancellable = true, remap = false)
    public void angelica$checkNullBeforeAccess(IBlockAccess world, int x, int y, int z, Block block, int modelId,
                                               RenderBlocks renderer, CallbackInfoReturnable<Boolean> cir, @Local TileEntityRedNetCable _cable) {
        if (_cable == null) {
             cir.setReturnValue(false);
        }
    }
}
