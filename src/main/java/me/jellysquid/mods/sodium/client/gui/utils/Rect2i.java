package me.jellysquid.mods.sodium.client.gui.utils;

public record Rect2i(int x, int y, int width, int height) {

    public boolean contains(int x, int y) {
        return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height;
    }
}
