package com.cwelth.slideemall.tes;

import com.cwelth.slideemall.bakes.EnumHoleTypes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public class BlockProfileRailTE extends CommonTE implements ITickable {
    public int FACING;
    public int handlePosition;
    public int state;
    public boolean isEmpty;

    public BlockProfileRailTE() {
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

    @Override
    public void update() {
        if(state != 0) {
            handlePosition += state;
            if (handlePosition < 0) handlePosition = 100 + handlePosition;
            if (handlePosition > 100) handlePosition = handlePosition - 100;
        }
    }
}
