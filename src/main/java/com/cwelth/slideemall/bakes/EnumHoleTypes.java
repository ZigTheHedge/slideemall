package com.cwelth.slideemall.bakes;

import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

public enum EnumHoleTypes implements IStringSerializable {
    ROUND(0, "round"),
    SQUARE(1, "square"),
    CROSS(2, "cross");

    private final int index;
    private final String name;

    EnumHoleTypes(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    @Nonnull
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
