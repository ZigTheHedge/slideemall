package com.cwelth.slideemall.tileentity.gui.server;

import com.cwelth.slideemall.tileentity.CommonTE;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public abstract class CommonContainer<TE extends CommonTE> extends Container {
    protected final TE tileEntity;

    public CommonContainer(IInventory playerInventory, TE te) {
        this.tileEntity = te;

        addOwnSlots();
        addPlayerSlots(playerInventory);
    }

    private void addPlayerSlots(IInventory playerInventory) {
        // Slots for the main inventory
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                int x = 7 + col * 18;
                int y = row * 18 + 105;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 10, x, y));
            }
        }

        // Slots for the hotbar
        for (int row = 0; row < 9; ++row) {
            int x = 7 + row * 18;
            int y = 58 + 105;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        }
    }

    protected abstract void addOwnSlots();

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack first = null;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack second = slot.getStack();
            first = second.copy();

            if (index < TE.inventorySize) {
                if (!this.mergeItemStack(
                        second,
                        TE.inventorySize,
                        this.inventorySlots.size(),
                        true))
                    return null;
            } else if (!this.mergeItemStack(second, 0, TE.inventorySize, false))
                return null;

            if (second.isEmpty())
                slot.putStack(ItemStack.EMPTY);
            else
                slot.onSlotChanged();

        }

        return first;
    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer playerIn) {
        return tileEntity.canInteractWith(playerIn);
    }
}
