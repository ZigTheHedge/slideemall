package com.cwelth.slideemall.utils;

import net.minecraft.util.IStringSerializable;

public enum EnumHiddenPanelState implements IStringSerializable {
    CLOSED(0, "closed"),
    OPENING(1, "opening"),
    CLOSING(2, "closing"),
    OPENED(3, "opened");

    private final int index;
    private final String name;

    private EnumHiddenPanelState(int index, String name)
    {
        this.index = index;
        this.name = name;
    }

    public int getIndex()
    {
        return index;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
