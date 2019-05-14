package com.cwelth.slideemall.tes;

import net.minecraft.nbt.NBTTagCompound;

public class BlockGatePanelTE extends CommonTE {
    public int FACING;
    public int handlePosition;
    public int state;
    public boolean isEmpty;

    public BlockGatePanelTE() {
        super(0);
        handlePosition = 50;
        state = -3;
        isEmpty = true;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if(compound.hasKey("facing"))
            FACING = compound.getInteger("facing");
        if(compound.hasKey("handlePosition"))
            handlePosition = compound.getInteger("handlePosition");
        if(compound.hasKey("state"))
            state = compound.getInteger("state");
        if(compound.hasKey("isEmpty"))
            isEmpty = compound.getBoolean("isEmpty");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("facing", FACING);
        compound.setInteger("handlePosition", handlePosition);
        compound.setInteger("state", state);
        compound.setBoolean("isEmpty", isEmpty);
        this.markDirty();
        return compound;
    }

}
