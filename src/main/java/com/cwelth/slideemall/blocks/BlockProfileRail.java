package com.cwelth.slideemall.blocks;

import com.cwelth.slideemall.ModMain;
import com.cwelth.slideemall.bakes.BlockProfileRailTESR;
import com.cwelth.slideemall.tes.BlockProfileRailTE;
import com.cwelth.slideemall.tes.BlockSliderTE;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class BlockProfileRail extends CommonTEBlock<BlockProfileRailTE> {
    public static final IProperty<Boolean> IS_HANDLE = PropertyBool.create("is_handle");

    AxisAlignedBB aabbNORTH = new AxisAlignedBB(0, 0.5F, 0.375F, 1F, 1F, 0.625F);
    AxisAlignedBB aabbSOUTH = new AxisAlignedBB(0, 0.5F, 0.375F, 1F, 1F, 0.625F);
    AxisAlignedBB aabbWEST = new AxisAlignedBB(0.375F, 0.5F, 0, 0.625F, 1F, 1F);
    AxisAlignedBB aabbEAST = new AxisAlignedBB(0.375F, 0.5F, 0, 0.625F, 1F, 1F);

    public BlockProfileRail(Material material, String name) {
        super(material, name);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(IS_HANDLE, false));
    }

    @Override
    public Class<BlockProfileRailTE> getTileEntityClass() {
        return BlockProfileRailTE.class;
    }

    @Nullable
    @Override
    public BlockProfileRailTE createTileEntity(World worldIn, IBlockState meta) {
        return new BlockProfileRailTE();
    }


    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
        ClientRegistry.bindTileEntitySpecialRenderer(BlockProfileRailTE.class, new BlockProfileRailTESR());
    }

    @SideOnly(Side.CLIENT)
    public void initItemModel() {

        Item itemBlock = Item.REGISTRY.getObject(new ResourceLocation(ModMain.MODID, "blockprofilerail"));
        ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(getRegistryName(), "inventory");
        final int DEFAULT_ITEM_SUBTYPE = 0;
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemBlock, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
    }

    @Override
    public boolean isBlockNormalCube(IBlockState blockState) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState blockState) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState p_isFullCube_1_) {
        return false;
    }

    @Override
    public void addCollisionBoxToList(IBlockState iBlockState, World world, BlockPos blockPos, AxisAlignedBB aaBB, List<AxisAlignedBB> aaBBList, @Nullable Entity entity, boolean wtf) {
        if(iBlockState.getValue(FACING) == EnumFacing.NORTH) addCollisionBoxToList(blockPos, aaBB, aaBBList, aabbNORTH);
        if(iBlockState.getValue(FACING) == EnumFacing.SOUTH) addCollisionBoxToList(blockPos, aaBB, aaBBList, aabbSOUTH);
        if(iBlockState.getValue(FACING) == EnumFacing.WEST) addCollisionBoxToList(blockPos, aaBB, aaBBList, aabbWEST);
        if(iBlockState.getValue(FACING) == EnumFacing.EAST) addCollisionBoxToList(blockPos, aaBB, aaBBList, aabbEAST);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        EnumFacing facing = EnumFacing.getHorizontal(MathHelper.floor((double)(placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3).getOpposite();
        world.setBlockState(pos, state.withProperty(FACING, facing), 2);
        TileEntity tileentity = world.getTileEntity(pos);
        if(!(tileentity instanceof BlockProfileRailTE))
        {
            return;
        }
        ((BlockProfileRailTE) tileentity).FACING = facing.getIndex();
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState blockState, IBlockAccess blockAccess, BlockPos blockPos) {
        if(blockState.getValue(FACING) == EnumFacing.NORTH)return aabbNORTH;
        if(blockState.getValue(FACING) == EnumFacing.SOUTH)return aabbSOUTH;
        if(blockState.getValue(FACING) == EnumFacing.EAST)return aabbEAST;
        if(blockState.getValue(FACING) == EnumFacing.WEST)return aabbWEST;
        return null;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, IS_HANDLE);
    }
}
