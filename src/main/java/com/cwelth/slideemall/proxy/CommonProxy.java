package com.cwelth.slideemall.proxy;

import com.cwelth.slideemall.InitContent;
import com.cwelth.slideemall.ModMain;
import com.cwelth.slideemall.blocks.*;
import com.cwelth.slideemall.items.ItemLiquidModule;
import com.cwelth.slideemall.tileentities.BlockHiddenManagerTE;
import com.cwelth.slideemall.tileentities.BlockSliderTE;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
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
        event.getRegistry().register(new BlockHiddenManager(Material.ROCK, "blockhm").setHardness(5F).setCreativeTab(CreativeTabs.MATERIALS));
        event.getRegistry().register(new BlockShaft().setHardness(5F).setCreativeTab(CreativeTabs.MATERIALS));
        event.getRegistry().register(new SlidingPanel().setHardness(5F).setCreativeTab(CreativeTabs.MATERIALS));

         */

        GameRegistry.registerTileEntity(BlockSliderTE.class, ModMain.MODID + "_blocksliderte");
        /*
        GameRegistry.registerTileEntity(BlockHiddenManagerTE.class, ModMain.MODID + "_blockhmte");
        GameRegistry.registerTileEntity(SlidingPanelTE.class, ModMain.MODID+":slidingpanelte");

         */
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(InitContent.blockSlider).setRegistryName(InitContent.blockSlider.getRegistryName()));
        /*
        event.getRegistry().register(new ItemBlock(InitContent.blockHiddenManager).setRegistryName(InitContent.blockHiddenManager.getRegistryName()));
        event.getRegistry().register(new ItemBlock(InitContent.blockShaft).setRegistryName(InitContent.blockShaft.getRegistryName()));
        event.getRegistry().register(new ItemBlock(InitContent.slidingPanel).setRegistryName(InitContent.slidingPanel.getRegistryName()));

         */

        event.getRegistry().register(new ItemLiquidModule().setCreativeTab(CreativeTabs.MATERIALS));
    }
}
