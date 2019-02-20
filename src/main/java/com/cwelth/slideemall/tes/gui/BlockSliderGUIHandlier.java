package com.cwelth.slideemall.tes.gui;

import com.cwelth.slideemall.tes.BlockSliderTE;
import com.cwelth.slideemall.tes.gui.server.BlockSliderContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * Created by ZtH on 23.02.2017.
 */
public class BlockSliderGUIHandlier implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        BlockSliderTE te = (BlockSliderTE)world.getTileEntity(pos);
        return new BlockSliderContainer(player.inventory, te);
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        BlockSliderTE te = (BlockSliderTE) world.getTileEntity(pos);
        return new BlockSliderGUIContainer(te, new BlockSliderContainer(player.inventory, te), "textures/gui/blockslider.png", player);
    }
}
