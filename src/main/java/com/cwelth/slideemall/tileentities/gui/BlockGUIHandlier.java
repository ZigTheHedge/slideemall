package com.cwelth.slideemall.tileentities.gui;

import com.cwelth.slideemall.tileentities.BlockSliderTE;
import com.cwelth.slideemall.tileentities.gui.server.BlockSliderContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * Created by ZtH on 23.02.2017.
 */
public class BlockGUIHandlier implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        if(ID == 0) {
            BlockSliderTE te = (BlockSliderTE) world.getTileEntity(pos);
            return new BlockSliderContainer(player.inventory, te);
        } else
            return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        if(ID == 0) {
            BlockSliderTE te = (BlockSliderTE) world.getTileEntity(pos);
            return new BlockSliderGUIContainer(te, new BlockSliderContainer(player.inventory, te), "textures/gui/blockslider.png", player);
        } else
            return null;
    }
}
