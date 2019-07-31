package com.cwelth.slideemall.bakes;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.property.IUnlistedProperty;

public class UnlistedPropertyDisguiseItem implements IUnlistedProperty<IBlockState> {

    private final String name;

    public UnlistedPropertyDisguiseItem(String name) {
        this.name = name;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isValid(IBlockState value) {
        return true;
    }

    @Override
    public Class<IBlockState> getType() {
        return IBlockState.class;
    }

    @Override
    public String valueToString(IBlockState value) {
        return value.toString();
    }
}
