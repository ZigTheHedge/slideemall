package com.cwelth.slideemall.items;

import com.cwelth.slideemall.ModMain;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemLiquidModule extends Item {

    public ItemLiquidModule()
    {
        setRegistryName("itemliquidmodule");
        setUnlocalizedName(ModMain.MODID + ".itemliquidmodule");
    }

    @SideOnly(Side.CLIENT)
    public void initItemModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

}
