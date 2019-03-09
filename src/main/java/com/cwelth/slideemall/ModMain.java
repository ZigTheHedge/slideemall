package com.cwelth.slideemall;

import com.cwelth.slideemall.network.SliderGuiSync;
import com.cwelth.slideemall.proxy.CommonProxy;
import com.cwelth.slideemall.tes.gui.BlockSliderGuiHandler;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author zth
 */
@Mod(modid = ModMain.MODID, name = "Slide'em All!", version = "0.94")
public final class ModMain {
    public Configuration config;
    public static final String MODID = "slideemall";
    public static int maxExtend = 64;
    public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
    @Mod.Instance("slideemall")
    public static ModMain instance;
    @SidedProxy(
            clientSide = "com.cwelth.slideemall.proxy.ClientProxy",
            serverSide = "com.cwelth.slideemall.proxy.CommonProxy"
    )
    public static CommonProxy proxy;

    public void saveConfig() {
        config.load();
        config.get("Slider params", "maxExtend", 64).set(maxExtend);
        config.save();
    }

    public void loadConfig() {
        config.load();
        maxExtend = config.get("Slider params", "maxExtend", 64).getInt();
        config.save();
    }


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        config = new Configuration(e.getSuggestedConfigurationFile());
        loadConfig();
        proxy.preInit(e);
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new BlockSliderGuiHandler());
        network.registerMessage(SliderGuiSync.class, SliderGuiSync.Packet.class, 1, Side.SERVER);
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }
}
