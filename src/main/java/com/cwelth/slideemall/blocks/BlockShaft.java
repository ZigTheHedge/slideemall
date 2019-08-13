package com.cwelth.slideemall.blocks;

import com.cwelth.slideemall.ModMain;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class BlockShaft extends CommonBlock {

    public enum ShaftShape implements IStringSerializable {
        FULL        (true, true, true, true),
        U_SHAPED    (true, true, false, true),
        RR_SHAPED   (true, false, false, true),
        S_SHAPED    (false, false, false, true),
        EQ_SHAPED   (false, false, true, true),
        X_SHAPED    (false, false, false, false);

        final boolean hasEast;
        final boolean hasWest;
        final boolean hasNorth;
        final boolean hasSouth;

        ShaftShape(boolean hasEast, boolean hasWest, boolean hasNorth, boolean hasSouth)
        {
            this.hasEast = hasEast;
            this.hasWest = hasWest;
            this.hasNorth = hasNorth;
            this.hasSouth = hasSouth;
        }

        public boolean hasEast(EnumFacing facing)
        {
            if(facing == EnumFacing.NORTH) return hasEast;
            if(facing == EnumFacing.WEST) return this.hasNorth(EnumFacing.NORTH);
            if(facing == EnumFacing.SOUTH) return this.hasWest(EnumFacing.NORTH);
            if(facing == EnumFacing.EAST) return this.hasSouth(EnumFacing.NORTH);
            return false;
        }
        public boolean hasWest(EnumFacing facing)
        {
            if(facing == EnumFacing.NORTH) return hasWest;
            if(facing == EnumFacing.WEST) return this.hasSouth(EnumFacing.NORTH);
            if(facing == EnumFacing.SOUTH) return this.hasEast(EnumFacing.NORTH);
            if(facing == EnumFacing.EAST) return this.hasNorth(EnumFacing.NORTH);
            return false;
        }
        public boolean hasNorth(EnumFacing facing)
        {
            if(facing == EnumFacing.NORTH) return hasNorth;
            if(facing == EnumFacing.WEST) return this.hasWest(EnumFacing.NORTH);
            if(facing == EnumFacing.SOUTH) return this.hasSouth(EnumFacing.NORTH);
            if(facing == EnumFacing.EAST) return this.hasEast(EnumFacing.NORTH);
            return false;
        }
        public boolean hasSouth(EnumFacing facing)
        {
            if(facing == EnumFacing.NORTH) return hasSouth;
            if(facing == EnumFacing.WEST) return this.hasEast(EnumFacing.NORTH);
            if(facing == EnumFacing.SOUTH) return this.hasNorth(EnumFacing.NORTH);
            if(facing == EnumFacing.EAST) return this.hasWest(EnumFacing.NORTH);
            return false;
        }

        @Override
        public String toString() {
            if(this == FULL)return "full";
            if(this == U_SHAPED)return "u";
            if(this == RR_SHAPED)return "rr";
            if(this == S_SHAPED)return "_";
            if(this == EQ_SHAPED)return "eq";
            if(this == X_SHAPED)return "x";
            return null;
        }

        @Override
        public String getName() {
            return this.toString();
        }
    }

    public static final PropertyEnum<ShaftShape> SHAFT_SHAPE = PropertyEnum.create("shape", ShaftShape.class);

    public static final AxisAlignedBB AABB_WEST = new AxisAlignedBB(0F, 0F, 0F, 0.0625F, 1F, 1F);
    public static final AxisAlignedBB AABB_NORTH = new AxisAlignedBB(0F, 0F, 0.9357F, 1F, 1F, 1F);
    public static final AxisAlignedBB AABB_EAST = new AxisAlignedBB(0.9357F, 0F, 0F, 1F, 1F, 1F);
    public static final AxisAlignedBB AABB_SOUTH = new AxisAlignedBB(0F, 0F, 0F, 1F, 1F, 0.9357F);

    public BlockShaft() {
        super(Material.IRON, "blockshaft");
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(SHAFT_SHAPE, ShaftShape.FULL));
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return true;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, SHAFT_SHAPE);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @SideOnly(Side.CLIENT)
    public void initItemModel() {

        Item itemBlock = Item.REGISTRY.getObject(new ResourceLocation(ModMain.MODID, this.name));
        ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(getRegistryName(), "inventory");
        final int DEFAULT_ITEM_SUBTYPE = 0;
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemBlock, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        IBlockState blockAtWest = worldIn.getBlockState(pos.west());
        IBlockState blockAtNorth = worldIn.getBlockState(pos.north());
        IBlockState blockAtEast = worldIn.getBlockState(pos.east());
        IBlockState blockAtSouth = worldIn.getBlockState(pos.south());

        if(blockAtWest.getBlock() == this && blockAtEast.getBlock() == this && blockAtNorth.getBlock() == this && blockAtSouth.getBlock() == this)
            return state.withProperty(SHAFT_SHAPE, ShaftShape.X_SHAPED);
        if(blockAtWest.getBlock() == this && blockAtEast.getBlock() != this && blockAtNorth.getBlock() != this && blockAtSouth.getBlock() != this)
            return state.withProperty(SHAFT_SHAPE, ShaftShape.U_SHAPED).withProperty(FACING, EnumFacing.WEST);
        if(blockAtWest.getBlock() != this && blockAtEast.getBlock() == this && blockAtNorth.getBlock() != this && blockAtSouth.getBlock() != this)
            return state.withProperty(SHAFT_SHAPE, ShaftShape.U_SHAPED).withProperty(FACING, EnumFacing.EAST);
        if(blockAtWest.getBlock() != this && blockAtEast.getBlock() != this && blockAtNorth.getBlock() == this && blockAtSouth.getBlock() != this)
            return state.withProperty(SHAFT_SHAPE, ShaftShape.U_SHAPED).withProperty(FACING, EnumFacing.NORTH);
        if(blockAtWest.getBlock() != this && blockAtEast.getBlock() != this && blockAtNorth.getBlock() != this && blockAtSouth.getBlock() == this)
            return state.withProperty(SHAFT_SHAPE, ShaftShape.U_SHAPED).withProperty(FACING, EnumFacing.SOUTH);
        if(blockAtWest.getBlock() == this && blockAtEast.getBlock() != this && blockAtNorth.getBlock() == this && blockAtSouth.getBlock() != this)
            return state.withProperty(SHAFT_SHAPE, ShaftShape.RR_SHAPED).withProperty(FACING, EnumFacing.NORTH);
        if(blockAtWest.getBlock() == this && blockAtEast.getBlock() != this && blockAtNorth.getBlock() != this && blockAtSouth.getBlock() == this)
            return state.withProperty(SHAFT_SHAPE, ShaftShape.RR_SHAPED).withProperty(FACING, EnumFacing.WEST);
        if(blockAtWest.getBlock() != this && blockAtEast.getBlock() == this && blockAtNorth.getBlock() != this && blockAtSouth.getBlock() == this)
            return state.withProperty(SHAFT_SHAPE, ShaftShape.RR_SHAPED).withProperty(FACING, EnumFacing.SOUTH);
        if(blockAtWest.getBlock() != this && blockAtEast.getBlock() == this && blockAtNorth.getBlock() == this && blockAtSouth.getBlock() != this)
            return state.withProperty(SHAFT_SHAPE, ShaftShape.RR_SHAPED).withProperty(FACING, EnumFacing.EAST);
        if(blockAtWest.getBlock() == this && blockAtEast.getBlock() == this && blockAtNorth.getBlock() != this && blockAtSouth.getBlock() != this)
            return state.withProperty(SHAFT_SHAPE, ShaftShape.EQ_SHAPED).withProperty(FACING, EnumFacing.NORTH);
        if(blockAtWest.getBlock() != this && blockAtEast.getBlock() != this && blockAtNorth.getBlock() == this && blockAtSouth.getBlock() == this)
            return state.withProperty(SHAFT_SHAPE, ShaftShape.EQ_SHAPED).withProperty(FACING, EnumFacing.WEST);
        if(blockAtWest.getBlock() == this && blockAtEast.getBlock() == this && blockAtNorth.getBlock() == this && blockAtSouth.getBlock() != this)
            return state.withProperty(SHAFT_SHAPE, ShaftShape.S_SHAPED).withProperty(FACING, EnumFacing.NORTH);
        if(blockAtWest.getBlock() == this && blockAtEast.getBlock() != this && blockAtNorth.getBlock() == this && blockAtSouth.getBlock() == this)
            return state.withProperty(SHAFT_SHAPE, ShaftShape.S_SHAPED).withProperty(FACING, EnumFacing.WEST);
        if(blockAtWest.getBlock() == this && blockAtEast.getBlock() == this && blockAtNorth.getBlock() != this && blockAtSouth.getBlock() == this)
            return state.withProperty(SHAFT_SHAPE, ShaftShape.S_SHAPED).withProperty(FACING, EnumFacing.SOUTH);
        if(blockAtWest.getBlock() != this && blockAtEast.getBlock() == this && blockAtNorth.getBlock() == this && blockAtSouth.getBlock() == this)
            return state.withProperty(SHAFT_SHAPE, ShaftShape.S_SHAPED).withProperty(FACING, EnumFacing.EAST);
        if(blockAtWest.getBlock() == this && blockAtEast.getBlock() == this && blockAtNorth.getBlock() == this && blockAtSouth.getBlock() == this)
            return state.withProperty(SHAFT_SHAPE, ShaftShape.X_SHAPED).withProperty(FACING, EnumFacing.NORTH);

        return super.getActualState(state, worldIn, pos);
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

}
