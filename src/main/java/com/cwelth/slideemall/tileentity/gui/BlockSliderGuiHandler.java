package com.cwelth.slideemall.tileentity.gui;

import com.cwelth.slideemall.tileentity.BlockSliderTE;
import com.cwelth.slideemall.tileentity.gui.server.BlockSliderContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * @author zth
 */
public final class BlockSliderGuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        BlockSliderTE te = (BlockSliderTE) world.getTileEntity(pos);
        return new BlockSliderContainer(player.inventory, te);
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        BlockSliderTE te = (BlockSliderTE) world.getTileEntity(pos);
        return new BlockSliderGuiContainer(te, new BlockSliderContainer(player.inventory, te), "textures/gui/blockslider.png", player);
    }
}
