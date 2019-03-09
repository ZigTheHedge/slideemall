package com.cwelth.slideemall.bakes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.property.IUnlistedProperty;

public final class UnlistedPropertyDisguiseItem implements IUnlistedProperty<ItemStack> {
    private final String name;

    public UnlistedPropertyDisguiseItem(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isValid(ItemStack value) {
        return true;
    }

    @Override
    public Class<ItemStack> getType() {
        return ItemStack.class;
    }

    @Override
    public String valueToString(ItemStack value) {
        return value.toString();
    }
}
