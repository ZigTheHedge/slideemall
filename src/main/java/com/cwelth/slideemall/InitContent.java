package com.cwelth.slideemall;

import com.cwelth.slideemall.blocks.*;
import com.cwelth.slideemall.items.ItemLiquidModule;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class InitContent {

    @GameRegistry.ObjectHolder("slideemall:blockslider")
    public static BlockSlider blockSlider;

    @GameRegistry.ObjectHolder("slideemall:itemliquidmodule")
    public static ItemLiquidModule itemLiquidModule;

    /*
    @GameRegistry.ObjectHolder("slideemall:blockhm")
    public static BlockHiddenManager blockHiddenManager;

    @GameRegistry.ObjectHolder("slideemall:blockshaft")
    public static BlockShaft blockShaft;

    @GameRegistry.ObjectHolder("slideemall:slidingpanel")
    public static SlidingPanel slidingPanel;

     */


    @SideOnly(Side.CLIENT)
    public static void initModels() {
        blockSlider.initModel();

        /*
        blockHiddenManager.initModel();
        blockShaft.initModel();
        slidingPanel.initModel();

         */

    }

    @SideOnly(Side.CLIENT)
    public static void initBlockItemModels() {
        blockSlider.initItemModel();
        /*
        blockHiddenManager.initItemModel();
        blockShaft.initItemModel();
        slidingPanel.initItemModel();

         */

    }

    @SideOnly(Side.CLIENT)
    public static void initItemModels() {
        itemLiquidModule.initItemModel();
    }
}
