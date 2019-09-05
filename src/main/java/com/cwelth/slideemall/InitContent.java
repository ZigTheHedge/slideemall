package com.cwelth.slideemall;

import com.cwelth.slideemall.blocks.*;
import com.cwelth.slideemall.items.ItemLiquidModule;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class InitContent {

    @GameRegistry.ObjectHolder("slideemall:blockslider")
    public static BlockSlider blockSlider;

    @GameRegistry.ObjectHolder("slideemall:blockslidercutout")
    public static BlockSlider blockSliderCutout;


    @GameRegistry.ObjectHolder("slideemall:itemliquidmodule")
    public static ItemLiquidModule itemLiquidModule;


    @SideOnly(Side.CLIENT)
    public static void initModels() {
        blockSlider.initModel();
        blockSliderCutout.initModel();
    }

    @SideOnly(Side.CLIENT)
    public static void initBlockItemModels() {
        blockSlider.initItemModel();
    }

    @SideOnly(Side.CLIENT)
    public static void initItemModels() {
        itemLiquidModule.initItemModel();
    }
}
