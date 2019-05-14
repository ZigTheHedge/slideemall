package com.cwelth.slideemall;

import com.cwelth.slideemall.blocks.*;
import com.cwelth.slideemall.items.ItemLiquidModule;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class InitContent {

    @GameRegistry.ObjectHolder("slideemall:blockslider")
    public static BlockSlider blockSlider;


    @GameRegistry.ObjectHolder("slideemall:blockprofilerail")
    public static BlockProfileRail blockProfileRail;

    @GameRegistry.ObjectHolder("slideemall:itemliquidmodule")
    public static ItemLiquidModule itemLiquidModule;
    /*
    @GameRegistry.ObjectHolder("slideemall:blockgearbox")
    public static BlockGearBox blockGearbox;

    @GameRegistry.ObjectHolder("slideemall:blockconnector")
    public static BlockConnector blockConnector;

    @GameRegistry.ObjectHolder("slideemall:blockgatepanel")
    public static BlockGatePanel blockGatePanel;

    */

    @SideOnly(Side.CLIENT)
    public static void initModels() {

        blockSlider.initModel();
        /*
        blockProfileRail.initModel();
        blockGearbox.initModel();
        blockConnector.initModel();
        blockGatePanel.initModel();

        */
    }

    @SideOnly(Side.CLIENT)
    public static void initBlockItemModels() {
        blockSlider.initItemModel();
        /*
        blockProfileRail.initItemModel();
        blockGearbox.initItemModel();
        blockConnector.initItemModel();
        blockGatePanel.initItemModel();
        */
    }

    @SideOnly(Side.CLIENT)
    public static void initItemModels() {
        itemLiquidModule.initItemModel();
    }
}
