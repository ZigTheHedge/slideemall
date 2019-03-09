package com.cwelth.slideemall.bakes;

import com.cwelth.slideemall.SlideEmAll;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

import javax.annotation.Nonnull;

public final class BlockSliderModelLoader implements ICustomModelLoader {
    public static final BlockSliderModel SLIDER_MODEL = new BlockSliderModel();

    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        return modelLocation.getResourceDomain().equals(SlideEmAll.MODID) && "blockslider".equals(modelLocation.getResourcePath());
    }

    @Nonnull
    @Override
    public IModel loadModel(@Nonnull ResourceLocation modelLocation) {
        return SLIDER_MODEL;
    }

    @Override
    public void onResourceManagerReload(@Nonnull IResourceManager resourceManager) {

    }
}
