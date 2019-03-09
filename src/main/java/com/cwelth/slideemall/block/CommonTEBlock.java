package com.cwelth.slideemall.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class CommonTEBlock<E extends TileEntity> extends CommonBlock {
    public CommonTEBlock(Material material, String name) {
        super(material, name);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public abstract E createTileEntity(@Nonnull World worldIn, @Nonnull IBlockState meta);

    public abstract void initModel();
}
