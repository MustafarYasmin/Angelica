package me.jellysquid.mods.sodium.client.gui.options.control;

import me.jellysquid.mods.sodium.client.gui.options.Option;
import me.jellysquid.mods.sodium.client.gui.options.control.element.ControlElementFactory;
import me.jellysquid.mods.sodium.client.util.Dim2i;

public record TickBoxControl(Option<Boolean> option) implements Control<Boolean> {

    @Override
    public ControlElement<Boolean> createElement(Dim2i dim, ControlElementFactory factory) {
        return factory.tickBoxElement(this.option, dim);
    }

    @Override
    public int getMaxWidth() {
        return 30;
    }

}
