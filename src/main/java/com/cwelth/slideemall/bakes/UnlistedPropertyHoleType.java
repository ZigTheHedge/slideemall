package com.cwelth.slideemall.bakes;

import net.minecraftforge.common.property.IUnlistedProperty;

public class UnlistedPropertyHoleType implements IUnlistedProperty<EnumHoleTypes> {
    private final String name;

    public UnlistedPropertyHoleType(String name)
    {
        this.name = name;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isValid(EnumHoleTypes value) {
        return true;
    }

    @Override
    public Class<EnumHoleTypes> getType() {
        return EnumHoleTypes.class;
    }

    @Override
    public String valueToString(EnumHoleTypes value) {
        return value.toString();
    }
}
