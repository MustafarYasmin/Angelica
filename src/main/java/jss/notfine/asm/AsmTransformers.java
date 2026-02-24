package jss.notfine.asm;

import com.gtnewhorizon.gtnhmixins.builders.ITransformers;
import com.gtnewhorizon.gtnhmixins.builders.TransformerBuilder;
import com.gtnewhorizons.angelica.config.AngelicaConfig;
import jss.notfine.config.MCPatcherForgeConfig;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public enum AsmTransformers implements ITransformers {

    RENDERBLOCKS(
        () -> AngelicaConfig.enableMCPatcherForgeFeatures && MCPatcherForgeConfig.CustomColors.enabled,
        "jss.notfine.asm.RenderBlocksTransformer");

    private final TransformerBuilder builder;

    AsmTransformers(Supplier<Boolean> applyIf, String transformer) {
        this.builder = new TransformerBuilder().setApplyIf(applyIf).addClientTransformers(transformer);
    }

    @NotNull
    @Override
    public TransformerBuilder getBuilder() {
        return builder;
    }

}
