package com.cwelth.slideemall.bakes;

import com.cwelth.slideemall.ModMain;
import com.cwelth.slideemall.blocks.BlockSlider;

import com.cwelth.slideemall.utils.EnumHoleTypes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
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

        if(state != null) {
            IExtendedBlockState extendedBlockState = (IExtendedBlockState) state;
            facing = extendedBlockState.getValue(BlockSlider.FACING);
            newBlock = extendedBlockState.getValue(BlockSlider.DISGUISE_ITEM);
            holeType = extendedBlockState.getValue(BlockSlider.HOLE_TYPE);
        } else
            facing = EnumFacing.SOUTH;

        String holeTexture = "blocksliderhole_round";

        if(holeType == EnumHoleTypes.ROUND)holeTexture = "blocksliderhole_round";
        if(holeType == EnumHoleTypes.SQUARE)holeTexture = "blocksliderhole_box";
        if(holeType == EnumHoleTypes.CROSS)holeTexture = "blocksliderhole_cross";

        if(facing == EnumFacing.EAST) {

            //bottom
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_bottom"));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.DOWN, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(0,0,0), new Vec3d(1, 0, 0), new Vec3d(1, 0, 1), new Vec3d(0, 0, 1), sprite));
            //top
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_top"));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.UP, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(1,1,0), new Vec3d(0, 1, 0), new Vec3d(0, 1, 1), new Vec3d(1, 1, 1), sprite));
            //east
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_east"));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.EAST, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(0, 1, 1), new Vec3d(0, 0, 1), new Vec3d(1,0,1), new Vec3d(1, 1, 1), sprite));
            //west
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_west"));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.WEST, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(1, 1, 0), new Vec3d(1, 0, 0), new Vec3d(0,0,0), new Vec3d(0, 1, 0), sprite));
            //front
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/"+holeTexture));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.NORTH, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(1, 1, 1), new Vec3d(1, 0, 1), new Vec3d(1,0,0), new Vec3d(1, 1, 0), sprite));
            //back
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider"));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.SOUTH, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(0, 1, 0), new Vec3d(0, 0, 0), new Vec3d(0,0,1), new Vec3d(0, 1, 1), sprite));

        }
        if(facing == EnumFacing.SOUTH) {

            //bottom
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_bottom"));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.DOWN, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(1, 0, 0), new Vec3d(1, 0, 1), new Vec3d(0, 0, 1), new Vec3d(0,0,0), sprite));
            //top
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_top"));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.UP, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(1, 1, 1), new Vec3d(1,1,0), new Vec3d(0, 1, 0), new Vec3d(0, 1, 1), sprite));
            //east
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/"+holeTexture));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.NORTH, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(0, 1, 1), new Vec3d(0, 0, 1), new Vec3d(1,0,1), new Vec3d(1, 1, 1), sprite));
            //west
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider"));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.SOUTH, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(1, 1, 0), new Vec3d(1, 0, 0), new Vec3d(0,0,0), new Vec3d(0, 1, 0), sprite));
            //front
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_west"));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.WEST, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(1, 1, 1), new Vec3d(1, 0, 1), new Vec3d(1,0,0), new Vec3d(1, 1, 0), sprite));
            //back
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_east"));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.EAST, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(0, 1, 0), new Vec3d(0, 0, 0), new Vec3d(0,0,1), new Vec3d(0, 1, 1), sprite));

        }
        if(facing == EnumFacing.WEST) {

            //bottom
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_bottom"));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.DOWN, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(1, 0, 1), new Vec3d(0, 0, 1), new Vec3d(0,0,0), new Vec3d(1, 0, 0), sprite));
            //top
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_top"));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.UP, 0).get(0).getSprite();
            quads.add(createQuad( new Vec3d(0, 1, 1), new Vec3d(1, 1, 1), new Vec3d(1,1,0), new Vec3d(0, 1, 0),sprite));
            //east
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_west"));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.WEST, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(0, 1, 1), new Vec3d(0, 0, 1), new Vec3d(1,0,1), new Vec3d(1, 1, 1), sprite));
            //west
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_east"));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.EAST, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(1, 1, 0), new Vec3d(1, 0, 0), new Vec3d(0,0,0), new Vec3d(0, 1, 0), sprite));
            //front
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider"));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.SOUTH, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(1, 1, 1), new Vec3d(1, 0, 1), new Vec3d(1,0,0), new Vec3d(1, 1, 0), sprite));
            //back
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/"+holeTexture));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.NORTH, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(0, 1, 0), new Vec3d(0, 0, 0), new Vec3d(0,0,1), new Vec3d(0, 1, 1), sprite));

        }
        if(facing == EnumFacing.NORTH) {

            //bottom
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_bottom"));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.DOWN, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(0, 0, 1), new Vec3d(0,0,0), new Vec3d(1, 0, 0), new Vec3d(1, 0, 1), sprite));
            //top
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_top"));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.UP, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(0, 1, 0), new Vec3d(0, 1, 1), new Vec3d(1, 1, 1), new Vec3d(1,1,0), sprite));
            //east
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider"));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.SOUTH, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(0, 1, 1), new Vec3d(0, 0, 1), new Vec3d(1,0,1), new Vec3d(1, 1, 1), sprite));
            //west
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/"+holeTexture));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.NORTH, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(1, 1, 0), new Vec3d(1, 0, 0), new Vec3d(0,0,0), new Vec3d(0, 1, 0), sprite));
            //front
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_east"));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.EAST, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(1, 1, 1), new Vec3d(1, 0, 1), new Vec3d(1,0,0), new Vec3d(1, 1, 0), sprite));
            //back
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_west"));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.WEST, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(0, 1, 0), new Vec3d(0, 0, 0), new Vec3d(0,0,1), new Vec3d(0, 1, 1), sprite));

        }
        if(facing == EnumFacing.UP) {

            //bottom
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider"));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.SOUTH, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(0, 0, 1), new Vec3d(0,0,0), new Vec3d(1, 0, 0), new Vec3d(1, 0, 1), sprite));
            //top
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/"+holeTexture));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.NORTH, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(0, 1, 0), new Vec3d(0, 1, 1), new Vec3d(1, 1, 1), new Vec3d(1,1,0), sprite));
            //east
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_top"));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.UP, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(0, 1, 1), new Vec3d(0, 0, 1), new Vec3d(1,0,1), new Vec3d(1, 1, 1), sprite));
            //west
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_top"));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.DOWN, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(1, 1, 0), new Vec3d(1, 0, 0), new Vec3d(0,0,0), new Vec3d(0, 1, 0), sprite));
            //front
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_top"));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.EAST, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(1, 1, 1), new Vec3d(1, 0, 1), new Vec3d(1,0,0), new Vec3d(1, 1, 0), sprite));
            //back
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_top"));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.WEST, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(0, 1, 0), new Vec3d(0, 0, 0), new Vec3d(0,0,1), new Vec3d(0, 1, 1), sprite));

        }
        if(facing == EnumFacing.DOWN) {

            //bottom
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/"+holeTexture));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.NORTH, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(0, 0, 1), new Vec3d(0,0,0), new Vec3d(1, 0, 0), new Vec3d(1, 0, 1), sprite));
            //top
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider"));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.SOUTH, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(0, 1, 0), new Vec3d(0, 1, 1), new Vec3d(1, 1, 1), new Vec3d(1,1,0), sprite));
            //east
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_bottom"));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.UP, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(0, 1, 1), new Vec3d(0, 0, 1), new Vec3d(1,0,1), new Vec3d(1, 1, 1), sprite));
            //west
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_bottom"));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.DOWN, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(1, 1, 0), new Vec3d(1, 0, 0), new Vec3d(0,0,0), new Vec3d(0, 1, 0), sprite));
            //front
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_bottom"));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.EAST, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(1, 1, 1), new Vec3d(1, 0, 1), new Vec3d(1,0,0), new Vec3d(1, 1, 0), sprite));
            //back
            if(newBlock == null) sprite = tGetter.apply(new ResourceLocation(ModMain.MODID, "blocks/blockslider_bottom"));
            else sprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(newBlock).getQuads(newBlock, EnumFacing.WEST, 0).get(0).getSprite();
            quads.add(createQuad(new Vec3d(0, 1, 0), new Vec3d(0, 0, 0), new Vec3d(0,0,1), new Vec3d(0, 1, 1), sprite));

        }
        return quads;
    }

}
