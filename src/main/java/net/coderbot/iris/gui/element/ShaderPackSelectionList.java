package net.coderbot.iris.gui.element;

import lombok.Getter;
import lombok.Setter;
import net.coderbot.iris.Iris;
import net.coderbot.iris.gui.element.shaderselection.BaseEntry;
import net.coderbot.iris.gui.element.shaderselection.ShaderPackEntry;
import net.coderbot.iris.gui.element.shaderselection.TopButtonRowEntry;
import net.coderbot.iris.gui.screen.ShaderPackScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;

import java.util.ArrayList;
import java.util.List;

public class ShaderPackSelectionList extends IrisGuiSlot {
    @Getter
    private final ShaderPackScreen screen;
    @Getter
    private final TopButtonRowEntry topButtonRow;

    @Setter
    @Getter
    private ShaderPackEntry applied = null;

    @Setter
    @Getter
    private ShaderPackEntry selected = null;

    private final List<BaseEntry> entries = new ArrayList<>();

    public ShaderPackSelectionList(ShaderPackScreen screen, Minecraft client, int width, int height, int top, int bottom, int left, int right) {
        super(client, width, height, top, bottom, 20);

        this.screen = screen;
        this.topButtonRow = new TopButtonRowEntry(this, Iris.getIrisConfig().areShadersEnabled());

        refresh();
    }

    public void refresh() {
    }

    @Override
    protected int getSize() {
        return this.entries.size();
    }

    @Override
    protected boolean elementClicked(int index, boolean doubleClick, int mouseX, int mouseY, int mouseButton) {
        // Only do anything on left-click
        if (mouseButton != 0) {
            return false;
        }
        final BaseEntry entry = this.entries.get(index);
        if(entry instanceof ShaderPackEntry shaderPackEntry) {
            this.setSelected(shaderPackEntry);
            if (!topButtonRow.shadersEnabled) {
                topButtonRow.setShadersEnabled(true);
            }
            return true;
        } else if( entry instanceof TopButtonRowEntry topButtonRowEntry) {
            return topButtonRowEntry.mouseClicked(mouseX, mouseY, 0);
        }
        return false;
   }

    @Override
    protected boolean isSelected(int idx) {
        return this.entries.get(idx).equals(this.selected);
    }

    @Override
    public int getListWidth() {
        return Math.min(308, width - 50);
    }

    @Override
    protected void drawBackground() {
        // Do nothing
    }


    @Override
    protected void drawSlot(int index, int x, int y, int i1, Tessellator tessellator, int mouseX, int mouseY) {
    }

}
