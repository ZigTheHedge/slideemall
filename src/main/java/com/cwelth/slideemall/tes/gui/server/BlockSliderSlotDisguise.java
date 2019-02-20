package com.cwelth.slideemall.tes.gui.server;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class BlockSliderSlotDisguise extends SlotItemHandler {
    public BlockSliderSlotDisguise(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        if(stack.getItem() instanceof ItemBlock)
        {
            if(Block.getBlockFromItem(stack.getItem()).hasTileEntity(Block.getBlockFromItem(stack.getItem()).getDefaultState()))
                return false;
            else {
                if(!Block.getBlockFromItem(stack.getItem()).isOpaqueCube(Block.getBlockFromItem(stack.getItem()).getDefaultState()))
                    return false;
                else
                    return true;
            }
        }
        else return false;
    }
}
