package com.cwelth.slideemall.proxy;

import com.cwelth.slideemall.InitContent;
import com.cwelth.slideemall.ModMain;
import com.cwelth.slideemall.blocks.*;
import com.cwelth.slideemall.items.ItemLiquidModule;
import com.cwelth.slideemall.tes.BlockGatePanelTE;
import com.cwelth.slideemall.tes.BlockProfileRailTE;
import com.cwelth.slideemall.tes.BlockSliderTE;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
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
        /*
        event.getRegistry().register(new BlockProfileRail(Material.IRON, "blockprofilerail").setHardness(5F).setCreativeTab(CreativeTabs.MATERIALS));
        event.getRegistry().register(new BlockGearBox(Material.IRON, "blockgearbox").setHardness(5F).setCreativeTab(CreativeTabs.MATERIALS));
        event.getRegistry().register(new BlockConnector(Material.IRON, "blockconnector").setHardness(5F).setCreativeTab(CreativeTabs.MATERIALS));
        event.getRegistry().register(new BlockGatePanel(Material.IRON, "blockgatepanel").setHardness(5F).setCreativeTab(CreativeTabs.MATERIALS));

        */

        GameRegistry.registerTileEntity(BlockSliderTE.class, ModMain.MODID + "_blocksliderte");
        //GameRegistry.registerTileEntity(BlockProfileRailTE.class, ModMain.MODID + "_blockprofilerailte");
        //GameRegistry.registerTileEntity(BlockGatePanelTE.class, ModMain.MODID + "_blockgatepanelte");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(InitContent.blockSlider).setRegistryName(InitContent.blockSlider.getRegistryName()));
        /*
        event.getRegistry().register(new ItemBlock(InitContent.blockProfileRail).setRegistryName(InitContent.blockProfileRail.getRegistryName()));
        event.getRegistry().register(new ItemBlock(InitContent.blockGearbox).setRegistryName(InitContent.blockGearbox.getRegistryName()));
        event.getRegistry().register(new ItemBlock(InitContent.blockConnector).setRegistryName(InitContent.blockConnector.getRegistryName()));
        event.getRegistry().register(new ItemBlock(InitContent.blockGatePanel).setRegistryName(InitContent.blockGatePanel.getRegistryName()));

        */
        event.getRegistry().register(new ItemLiquidModule().setCreativeTab(CreativeTabs.MATERIALS));
    }
}
