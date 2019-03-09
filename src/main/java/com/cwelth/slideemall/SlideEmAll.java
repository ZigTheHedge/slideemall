package com.cwelth.slideemall;

import com.cwelth.slideemall.network.SliderGuiSync;
import com.cwelth.slideemall.proxy.CommonProxy;
import com.cwelth.slideemall.tileentity.gui.BlockSliderGuiHandler;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nonnull;

/**
 * @author zth
 */
@Mod(modid = SlideEmAll.MODID, name = "Slide'em All!", version = "0.94")
public final class SlideEmAll {
    @Mod.Instance("slideemall")
    public static SlideEmAll instance;
    @Nonnull
    public static final String MODID = "slideemall";
    @SidedProxy(
            clientSide = "com.cwelth.slideemall.proxy.ClientProxy",
            serverSide = "com.cwelth.slideemall.proxy.CommonProxy"
    )
    public static CommonProxy proxy;
    private static Configuration config;
    public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new BlockSliderGuiHandler());
        network.registerMessage(SliderGuiSync.class, SliderGuiSync.Packet.class, 1, Side.SERVER);
    }

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    public static int getMaxExtend() {
        return config.get("slider settings", "maxExtend", 64).getInt();
    }

}
