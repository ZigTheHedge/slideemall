package com.cwelth.slideemall;

import com.cwelth.slideemall.block.BlockSlider;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class ContentInitializer {
    private ContentInitializer() {
    }

    @GameRegistry.ObjectHolder("slideemall:blockslider")
    public static BlockSlider blockSlider;

    @SideOnly(Side.CLIENT)
    public static void initializeModels() {
        blockSlider.initModel();
    }

    @SideOnly(Side.CLIENT)
    public static void initializeItemModels() {
        blockSlider.initItemModel();
    }
}
