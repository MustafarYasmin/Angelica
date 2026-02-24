package com.gtnewhorizons.angelica.mixins.late.notfine.leaves.twilightforest;

import jss.notfine.util.LeafRenderUtil;
import net.minecraft.block.BlockLeaves;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import twilightforest.block.BlockTFMagicLeaves;

@Mixin(value = BlockTFMagicLeaves.class)
public abstract class MixinBlockTFMagicLeaves extends BlockLeaves {
    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        int maskedMeta = world.getBlockMetadata(x, y, z) & 3;
        final boolean renderMode = LeafRenderUtil.selectRenderMode(world, x, y, z, side);
        return (maskedMeta == 1) ? (renderMode ? SPR_TRANSLEAVES_OPAQUE : SPR_TRANSLEAVES) : (renderMode ? SPR_TIMELEAVES_OPAQUE : SPR_TIMELEAVES);
    }

    @Shadow(remap = false)
    public static IIcon SPR_TIMELEAVES;
    @Shadow(remap = false)
    public static IIcon SPR_TIMELEAVES_OPAQUE;
    @Shadow(remap = false)
    public static IIcon SPR_TRANSLEAVES;
    @Shadow(remap = false)
    public static IIcon SPR_TRANSLEAVES_OPAQUE;

}
