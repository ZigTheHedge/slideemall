package com.cwelth.slideemall.bakes;

import com.cwelth.slideemall.ModMain;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

public class DisguiseModelLoader implements ICustomModelLoader {
    public static final BlockSliderModel SLIDER_MODEL = new BlockSliderModel();

    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        if(modelLocation.getResourceDomain().equals(ModMain.MODID)) {
            return "blockslider".equals(modelLocation.getResourcePath());
        } else
            return false;
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) throws Exception {
        if("blockslider".equals(modelLocation.getResourcePath()))
            return SLIDER_MODEL;
        return null;
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }

}
