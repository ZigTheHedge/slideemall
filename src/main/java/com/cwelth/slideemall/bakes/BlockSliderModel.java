package com.cwelth.slideemall.bakes;

import com.cwelth.slideemall.ModMain;
import com.google.common.collect.ImmutableSet;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

public class BlockSliderModel implements IModel {

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return new BlockSliderBakedModel(state, format, bakedTextureGetter);
    }
    @Override
    public Collection<ResourceLocation> getDependencies() {
        return Collections.emptySet();
    }

    @Override
    public Collection<ResourceLocation> getTextures() {
        return ImmutableSet.of(
                new ResourceLocation(ModMain.MODID, "blocks/blockslider"),
                new ResourceLocation(ModMain.MODID, "blocks/blockslider_east"),
                new ResourceLocation(ModMain.MODID, "blocks/blockslider_west"),
                new ResourceLocation(ModMain.MODID, "blocks/blockslider_top"),
                new ResourceLocation(ModMain.MODID, "blocks/blockslider_bottom"),
                new ResourceLocation(ModMain.MODID, "blocks/blocksliderhole"),
                new ResourceLocation(ModMain.MODID, "blocks/blocksliderhole_round"),
                new ResourceLocation(ModMain.MODID, "blocks/blocksliderhole_cross"),
                new ResourceLocation(ModMain.MODID, "blocks/blocksliderhole_box"));
    }

    @Override
    public IModelState getDefaultState() {
        return TRSRTransformation.identity();
    }
}
