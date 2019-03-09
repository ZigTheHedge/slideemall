package com.cwelth.slideemall.tes.gui.server;

import com.cwelth.slideemall.tes.BlockSliderTE;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public final class BlockSliderContainer extends CommonContainer<BlockSliderTE> {
    public BlockSliderContainer(IInventory playerInventory, BlockSliderTE te) {
        super(playerInventory, te);
    }

    @Override
    protected void addOwnSlots() {
        IItemHandler itemHandler =
                this.tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        addSlotToContainer(new BlockSliderSlotDrawbridge(itemHandler, 0, 115, 14)); //24
        addSlotToContainer(new BlockSliderSlotDisguise(itemHandler, 1, 115, 35)); //50
    }
}
