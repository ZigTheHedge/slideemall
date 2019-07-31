package com.cwelth.slideemall.tileentities;

import net.minecraft.nbt.NBTTagCompound;

public class BlockHiddenManagerTE extends CommonTE {
    public BlockHiddenManagerTE() {
        super( 1 );
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("items", itemStackHandler.serializeNBT());
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("items")) {
            itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
        }
    }
}
