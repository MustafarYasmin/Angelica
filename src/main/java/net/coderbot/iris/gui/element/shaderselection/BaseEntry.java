package net.coderbot.iris.gui.element.shaderselection;

import net.coderbot.iris.gui.element.IrisGuiSlot;

public abstract class BaseEntry {
    protected IrisGuiSlot list;
    protected BaseEntry(IrisGuiSlot list) {
        this.list = list;
    }

}
