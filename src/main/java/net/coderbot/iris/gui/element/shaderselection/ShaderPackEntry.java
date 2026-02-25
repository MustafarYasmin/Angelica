package net.coderbot.iris.gui.element.shaderselection;

import lombok.Getter;
import net.coderbot.iris.gui.element.ShaderPackSelectionList;
import net.coderbot.iris.gui.screen.ShaderPackScreen;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.EnumChatFormatting;

public class ShaderPackEntry extends BaseEntry {
    @Getter private final String packName;
    private final ShaderPackSelectionList shaderPackSelectionList;

    public ShaderPackEntry(ShaderPackSelectionList list, String packName) {
        super(list);
        this.packName = packName;
        this.shaderPackSelectionList = list;
    }

    public boolean isApplied() {
        return shaderPackSelectionList.getApplied() == this;
    }

    public boolean isSelected() {
        return shaderPackSelectionList.getSelected() == this;
    }

    @Override
    public void drawEntry(ShaderPackScreen screen, int index, int x, int y, int listWidth, Tessellator tessellator, int mouseX, int mouseY, boolean isMouseOver) {
    }
}
