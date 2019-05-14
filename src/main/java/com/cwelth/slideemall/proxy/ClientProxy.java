package com.cwelth.slideemall.proxy;

import com.cwelth.slideemall.InitContent;
import com.cwelth.slideemall.bakes.BlockSliderModelLoader;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;


@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        ModelLoaderRegistry.registerLoader(new BlockSliderModelLoader());
        super.preInit(e);
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        InitContent.initModels();
        InitContent.initItemModels();
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        InitContent.initBlockItemModels();
        super.postInit(e);
    }
}
