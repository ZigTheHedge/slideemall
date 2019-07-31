package com.cwelth.slideemall;

import com.cwelth.slideemall.network.DisguiseGUISync;
import com.cwelth.slideemall.network.SliderDropModule;
import com.cwelth.slideemall.network.SliderGUISync;
import com.cwelth.slideemall.proxy.CommonProxy;
import com.cwelth.slideemall.tileentities.gui.BlockGUIHandlier;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.util.logging.Logger;

/**
 * Created by zth on 24/08/16.
 */


@Mod(modid = ModMain.MODID, name = ModMain.NAME, version = ModMain.VERSION)
public class ModMain {

    public static final String NAME = "Slide'em All!";
    public static final String MODID = "slideemall";
    public static final String VERSION = "0.96";

    public Configuration config;
    public static int maxExtend = 64;
    public static final Logger logger = Logger.getLogger(NAME);

    public void saveConfig()
    {
        config.load();
        config.get("Slider params", "maxExtend", 64).set(maxExtend);
        config.save();
    }

    public void loadConfig()
    {
        config.load();
        maxExtend = config.get("Slider params", "maxExtend", 64).getInt();
        config.save();
    }

    public static SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

    @SidedProxy(clientSide = "com.cwelth.slideemall.proxy.ClientProxy", serverSide = "com.cwelth.slideemall.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance("slideemall")
    public static ModMain instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e)
    {
        config = new Configuration(e.getSuggestedConfigurationFile());
        loadConfig();
        proxy.preInit(e);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e)
    {
        proxy.init(e);
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new BlockGUIHandlier());

        network.registerMessage(DisguiseGUISync.class, DisguiseGUISync.Packet.class, 2, Side.SERVER);
        network.registerMessage(SliderGUISync.class, SliderGUISync.Packet.class, 1, Side.SERVER);
        network.registerMessage(SliderDropModule.class, SliderDropModule.Packet.class, 0, Side.SERVER);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e)
    {
        proxy.postInit(e);
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
    }

}
