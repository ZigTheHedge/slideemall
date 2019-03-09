package com.cwelth.slideemall.bakes;

import com.cwelth.slideemall.SlideEmAll;
import com.google.common.collect.ImmutableSet;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

public final class BlockSliderModel implements IModel {
    @Override
    public IBakedModel bake(
            @Nonnull IModelState state,
            @Nonnull VertexFormat format,
            @Nonnull Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return new BlockSliderBakedModel(state, format, bakedTextureGetter);
    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public Collection<ResourceLocation> getTextures() {
        return ImmutableSet.of(
                new ResourceLocation(SlideEmAll.MODID, "blocks/blockslider"),
                new ResourceLocation(SlideEmAll.MODID, "blocks/blockslider_east"),
                new ResourceLocation(SlideEmAll.MODID, "blocks/blockslider_west"),
                new ResourceLocation(SlideEmAll.MODID, "blocks/blockslider_top"),
                new ResourceLocation(SlideEmAll.MODID, "blocks/blockslider_bottom"),
                new ResourceLocation(SlideEmAll.MODID, "blocks/blocksliderhole"),
                new ResourceLocation(SlideEmAll.MODID, "blocks/blocksliderhole_round"),
                new ResourceLocation(SlideEmAll.MODID, "blocks/blocksliderhole_cross"),
                new ResourceLocation(SlideEmAll.MODID, "blocks/blocksliderhole_box"));
    }

    @Nonnull
    @Override
    public IModelState getDefaultState() {
        return TRSRTransformation.identity();
    }
}
