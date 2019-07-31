package com.cwelth.slideemall.tileentities.gui.server;

import com.cwelth.slideemall.tileentities.BlockHiddenManagerTE;
import com.cwelth.slideemall.tileentities.BlockSliderTE;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BlockHiddenManagerContainer extends CommonContainer<BlockHiddenManagerTE> {

    public BlockHiddenManagerContainer(IInventory playerInventory, BlockHiddenManagerTE te) {
        super(playerInventory, te);
    }

    @Override
    protected void addOwnSlots() {
        IItemHandler itemHandler = this.tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        addSlotToContainer(new SlotDisguiseItem(itemHandler, 0, 79, 51)); //50
    }
}
