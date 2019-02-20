package com.cwelth.slideemall;

import com.cwelth.slideemall.blocks.BlockSlider;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class InitContent {

    @GameRegistry.ObjectHolder("slideemall:blockslider")
    public static BlockSlider blockSlider;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        blockSlider.initModel();
    }

    @SideOnly(Side.CLIENT)
    public static void initItemModels() {
        blockSlider.initItemModel();
    }
}
