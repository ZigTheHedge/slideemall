package com.cwelth.slideemall.proxy;

import com.cwelth.slideemall.InitContent;
import com.cwelth.slideemall.ModMain;
import com.cwelth.slideemall.blocks.BlockSlider;
import com.cwelth.slideemall.blocks.CommonTEBlock;
import com.cwelth.slideemall.tes.BlockSliderTE;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e)
    {

    }
    public void init(FMLInitializationEvent e)
    {

    }
    public void postInit(FMLPostInitializationEvent e)
    {

    }
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new BlockSlider(Material.ROCK, "blockslider").setHardness(5F).setCreativeTab(CreativeTabs.MATERIALS));
        GameRegistry.registerTileEntity(BlockSliderTE.class, ModMain.MODID + "_blocksliderte");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(InitContent.blockSlider).setRegistryName(InitContent.blockSlider.getRegistryName()));
    }

    public void registerItemRenderer(Item item, int meta, String id) {
    }
}
