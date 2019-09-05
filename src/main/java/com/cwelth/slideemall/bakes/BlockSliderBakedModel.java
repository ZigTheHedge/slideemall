package com.cwelth.slideemall.bakes;

import com.cwelth.slideemall.ModMain;
import com.cwelth.slideemall.blocks.BlockSlider;

import com.cwelth.slideemall.utils.EnumHoleTypes;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.property.IExtendedBlockState;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class BlockSliderBakedModel extends BakedGenericModel {

    public BlockSliderBakedModel(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        super(state, format, bakedTextureGetter);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        if (side != null) {
            return Collections.emptyList();
        }

        List<BakedQuad> quads = new ArrayList<>();

        EnumFacing facing;
        IBlockState newBlock = null;
        EnumHoleTypes holeType = null;
        Integer tint = 0xFFFFFFFF;

        if(state != null) {
            IExtendedBlockState extendedBlockState = (IExtendedBlockState) state;
            facing = extendedBlockState.getValue(BlockSlider.FACING);
            newBlock = extendedBlockState.getValue(BlockSlider.DISGUISE_ITEM);
            holeType = extendedBlockState.getValue(BlockSlider.HOLE_TYPE);
            if(newBlock != null && newBlock.getBlock() == Blocks.GRASS)
                tint = extendedBlockState.getValue(BlockSlider.TINT);

        } else
            facing = EnumFacing.SOUTH;

        String holeTexture = "blocksliderhole_round";

        if(holeType == EnumHoleTypes.ROUND)holeTexture = "blocksliderhole_round";
        if(holeType == EnumHoleTypes.SQUARE)holeTexture = "blocksliderhole_box";
        if(holeType == EnumHoleTypes.CROSS)holeTexture = "blocksliderhole_cross";


        if(newBlock == null) {
            if (facing == EnumFacing.UP) {

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/" + holeTexture));
                quads.add(createQuadCW(new Vec3d(0, 1, 0), new Vec3d(1, 1, 0), new Vec3d(1, 1, 1), new Vec3d(0, 1, 1), sprite, tint));

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider"));
                quads.add(createQuadCW(new Vec3d(1, 0, 1), new Vec3d(1, 0, 0), new Vec3d(0, 0, 0), new Vec3d(0, 0, 1), sprite, tint));

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_side"));
                quads.add(createQuadCW(new Vec3d(1, 0, 0), new Vec3d(1, 1, 0), new Vec3d(0, 1, 0), new Vec3d(0, 0, 0), sprite, tint));

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_side"));
                quads.add(createQuadCW(new Vec3d(0, 0, 1), new Vec3d(0, 1, 1), new Vec3d(1, 1, 1), new Vec3d(1, 0, 1), sprite, tint));

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_side"));
                quads.add(createQuadCW(new Vec3d(0, 0, 0), new Vec3d(0, 1, 0), new Vec3d(0, 1, 1), new Vec3d(0, 0, 1), sprite, tint));

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_side"));
                quads.add(createQuadCW(new Vec3d(1, 0, 1), new Vec3d(1, 1, 1), new Vec3d(1, 1, 0), new Vec3d(1, 0, 0), sprite, tint));
            }

            if (facing == EnumFacing.DOWN) {

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/" + holeTexture));
                quads.add(createQuadCW(new Vec3d(1, 0, 1), new Vec3d(1, 0, 0), new Vec3d(0, 0, 0), new Vec3d(0, 0, 1), sprite, tint));

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider"));
                quads.add(createQuadCW(new Vec3d(0, 1, 0), new Vec3d(1, 1, 0), new Vec3d(1, 1, 1), new Vec3d(0, 1, 1), sprite, tint));

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_side"));
                quads.add(createQuadCW(new Vec3d(0, 1, 0), new Vec3d(0, 0, 0), new Vec3d(1, 0, 0), new Vec3d(1, 1, 0), sprite, tint));

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_side"));
                quads.add(createQuadCW(new Vec3d(1, 1, 1), new Vec3d(1, 0, 1), new Vec3d(0, 0, 1), new Vec3d(0, 1, 1), sprite, tint));

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_side"));
                quads.add(createQuadCW(new Vec3d(0, 1, 1), new Vec3d(0, 0, 1), new Vec3d(0, 0, 0), new Vec3d(0, 1, 0), sprite, tint));

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_side"));
                quads.add(createQuadCW(new Vec3d(1, 1, 0), new Vec3d(1, 0, 0), new Vec3d(1, 0, 1), new Vec3d(1, 1, 1), sprite, tint));
            }

            if (facing == EnumFacing.NORTH) {

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/" + holeTexture));
                quads.add(createQuadCW(new Vec3d(1, 0, 0), new Vec3d(1, 1, 0), new Vec3d(0, 1, 0), new Vec3d(0, 0, 0), sprite, tint));

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider"));
                quads.add(createQuadCW(new Vec3d(0, 0, 1), new Vec3d(0, 1, 1), new Vec3d(1, 1, 1), new Vec3d(1, 0, 1), sprite, tint));

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_side"));
                quads.add(createQuadCW(new Vec3d(1, 0, 1), new Vec3d(1, 0, 0), new Vec3d(0, 0, 0), new Vec3d(0, 0, 1), sprite, tint));

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_side"));
                quads.add(createQuadCW(new Vec3d(0, 1, 1), new Vec3d(0, 1, 0), new Vec3d(1, 1, 0), new Vec3d(1, 1, 1), sprite, tint));

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_side"));
                quads.add(createQuadCW(new Vec3d(0, 0, 1), new Vec3d(0, 0, 0), new Vec3d(0, 1, 0), new Vec3d(0, 1, 1), sprite, tint));

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_side"));
                quads.add(createQuadCW(new Vec3d(1, 1, 1), new Vec3d(1, 1, 0), new Vec3d(1, 0, 0), new Vec3d(1, 0, 1), sprite, tint));
            }

            if (facing == EnumFacing.SOUTH) {

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/" + holeTexture));
                quads.add(createQuadCW(new Vec3d(1, 1, 1), new Vec3d(1, 0, 1), new Vec3d(0, 0, 1), new Vec3d(0, 1, 1), sprite, tint));

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider"));
                quads.add(createQuadCW(new Vec3d(1, 0, 0), new Vec3d(1, 1, 0), new Vec3d(0, 1, 0), new Vec3d(0, 0, 0), sprite, tint));

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_side"));
                quads.add(createQuadCW(new Vec3d(1, 1, 0), new Vec3d(1, 1, 1), new Vec3d(0, 1, 1), new Vec3d(0, 1, 0), sprite, tint));

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_side"));
                quads.add(createQuadCW(new Vec3d(0, 0, 0), new Vec3d(0, 0, 1), new Vec3d(1, 0, 1), new Vec3d(1, 0, 0), sprite, tint));

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_side"));
                quads.add(createQuadCW(new Vec3d(0, 1, 0), new Vec3d(0, 1, 1), new Vec3d(0, 0, 1), new Vec3d(0, 0, 0), sprite, tint));

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_side"));
                quads.add(createQuadCW(new Vec3d(1, 0, 0), new Vec3d(1, 0, 1), new Vec3d(1, 1, 1), new Vec3d(1, 1, 0), sprite, tint));
            }


            if (facing == EnumFacing.WEST) {

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/" + holeTexture));
                quads.add(createQuadCW(new Vec3d(0, 0, 0), new Vec3d(0, 1, 0), new Vec3d(0, 1, 1), new Vec3d(0, 0, 1), sprite, tint));

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider"));
                quads.add(createQuadCW(new Vec3d(1, 0, 1), new Vec3d(1, 1, 1), new Vec3d(1, 1, 0), new Vec3d(1, 0, 0), sprite, tint));

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_side"));
                quads.add(createQuadCW(new Vec3d(1, 1, 0), new Vec3d(0, 1, 0), new Vec3d(0, 0, 0), new Vec3d(1, 0, 0), sprite, tint));

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_side"));
                quads.add(createQuadCW(new Vec3d(1, 0, 1), new Vec3d(0, 0, 1), new Vec3d(0, 1, 1), new Vec3d(1, 1, 1), sprite, tint));

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_side"));
                quads.add(createQuadCW(new Vec3d(1, 0, 0), new Vec3d(0, 0, 0), new Vec3d(0, 0, 1), new Vec3d(1, 0, 1), sprite, tint));

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_side"));
                quads.add(createQuadCW(new Vec3d(1, 1, 1), new Vec3d(0, 1, 1), new Vec3d(0, 1, 0), new Vec3d(1, 1, 0), sprite, tint));
            }

            if (facing == EnumFacing.EAST) {

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/" + holeTexture));
                quads.add(createQuadCW(new Vec3d(1, 0, 1), new Vec3d(1, 1, 1), new Vec3d(1, 1, 0), new Vec3d(1, 0, 0), sprite, tint));

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider"));
                quads.add(createQuadCW(new Vec3d(0, 0, 0), new Vec3d(0, 1, 0), new Vec3d(0, 1, 1), new Vec3d(0, 0, 1), sprite, tint));

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_side"));
                quads.add(createQuadCW(new Vec3d(0, 0, 0), new Vec3d(1, 0, 0), new Vec3d(1, 1, 0), new Vec3d(0, 1, 0), sprite, tint));

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_side"));
                quads.add(createQuadCW(new Vec3d(0, 1, 1), new Vec3d(1, 1, 1), new Vec3d(1, 0, 1), new Vec3d(0, 0, 1), sprite, tint));

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_side"));
                quads.add(createQuadCW(new Vec3d(0, 1, 0), new Vec3d(1, 1, 0), new Vec3d(1, 1, 1), new Vec3d(0, 1, 1), sprite, tint));

                    sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_side"));
                quads.add(createQuadCW(new Vec3d(0, 0, 1), new Vec3d(1, 0, 1), new Vec3d(1, 0, 0), new Vec3d(0, 0, 0), sprite, tint));
            }
        } else
        {
            List<BakedQuad> quadsList = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.DOWN, 0);
            sprite = quadsList.get(0).getSprite();
            quads.add(createQuadCW(new Vec3d(1, 0, 1), new Vec3d(1, 0, 0), new Vec3d(0, 0, 0), new Vec3d(0, 0, 1), sprite, 0xFFFFFFFF));

            sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.UP, 0).get(0).getSprite();
            quads.add(createQuadCW(new Vec3d(1, 1, 0), new Vec3d(1, 1, 1), new Vec3d(0, 1, 1), new Vec3d(0, 1, 0), sprite, tint));

            sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.NORTH, 0).get(0).getSprite();
            quads.add(createQuadCW(new Vec3d(1, 1, 1), new Vec3d(1, 0, 1), new Vec3d(0, 0, 1), new Vec3d(0, 1, 1), sprite, 0xFFFFFFFF));
            if(newBlock.getBlock() == Blocks.GRASS) {
                sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.NORTH, 0).get(1).getSprite();
                quads.add(createQuadCW(new Vec3d(1, 1, 1), new Vec3d(1, 0, 1), new Vec3d(0, 0, 1), new Vec3d(0, 1, 1), sprite, tint));
            }

            sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.SOUTH, 0).get(0).getSprite();
            quads.add(createQuadCW(new Vec3d(0, 1, 0), new Vec3d(0, 0, 0), new Vec3d(1, 0, 0), new Vec3d(1, 1, 0), sprite, 0xFFFFFFFF));
            if(newBlock.getBlock() == Blocks.GRASS) {
                sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.SOUTH, 0).get(1).getSprite();
                quads.add(createQuadCW(new Vec3d(0, 1, 0), new Vec3d(0, 0, 0), new Vec3d(1, 0, 0), new Vec3d(1, 1, 0), sprite, tint));
            }

            sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.WEST, 0).get(0).getSprite();
            quads.add(createQuadCW(new Vec3d(1, 1, 0), new Vec3d(1, 0, 0), new Vec3d(1, 0, 1), new Vec3d(1, 1, 1), sprite, 0xFFFFFFFF));
            if(newBlock.getBlock() == Blocks.GRASS) {
                sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.WEST, 0).get(1).getSprite();
                quads.add(createQuadCW(new Vec3d(1, 1, 0), new Vec3d(1, 0, 0), new Vec3d(1, 0, 1), new Vec3d(1, 1, 1), sprite, tint));
            }

            sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.EAST, 0).get(0).getSprite();
            quads.add(createQuadCW(new Vec3d(0, 1, 1), new Vec3d(0, 0, 1), new Vec3d(0, 0, 0), new Vec3d(0, 1, 0), sprite, 0xFFFFFFFF));
            if(newBlock.getBlock() == Blocks.GRASS) {
                sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.EAST, 0).get(1).getSprite();
                quads.add(createQuadCW(new Vec3d(0, 1, 1), new Vec3d(0, 0, 1), new Vec3d(0, 0, 0), new Vec3d(0, 1, 0), sprite, tint));
            }

        }
        return quads;
    }

}
