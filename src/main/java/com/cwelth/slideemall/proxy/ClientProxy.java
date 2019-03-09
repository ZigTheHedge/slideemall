package com.cwelth.slideemall.proxy;

import com.cwelth.slideemall.ContentInitializer;
import com.cwelth.slideemall.bakes.BlockSliderModelLoader;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public final class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        ModelLoaderRegistry.registerLoader(new BlockSliderModelLoader());
        super.preInit(event);
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        ContentInitializer.initializeModels();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        ContentInitializer.initializeItemModels();
        super.postInit(event);
    }
}
