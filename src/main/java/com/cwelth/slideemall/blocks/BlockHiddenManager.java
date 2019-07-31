package com.cwelth.slideemall.blocks;

import com.cwelth.slideemall.InitContent;
import com.cwelth.slideemall.ModMain;
import com.cwelth.slideemall.bakes.UnlistedPropertyDisguiseItem;
import com.cwelth.slideemall.bakes.UnlistedPropertyHoleType;
import com.cwelth.slideemall.tileentities.BlockHiddenManagerTE;
import com.cwelth.slideemall.tileentities.BlockSliderTE;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockHiddenManager extends CommonTEBlock<BlockHiddenManagerTE> {
    public static final UnlistedPropertyDisguiseItem DISGUISE_ITEM = new UnlistedPropertyDisguiseItem("disguise");
    public BlockHiddenManager(Material material, String name) {
        super(material, name);
    }

    @Override
    public Class<BlockHiddenManagerTE> getTileEntityClass() {
        return BlockHiddenManagerTE.class;
    }

    @Nullable
    @Override
    public BlockHiddenManagerTE createTileEntity(World world, IBlockState state) {
        return new BlockHiddenManagerTE();
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {

    }

    @SideOnly(Side.CLIENT)
    public void initItemModel() {

        Item itemBlock = Item.REGISTRY.getObject(new ResourceLocation(ModMain.MODID, "blockhm"));
        ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(getRegistryName(), "inventory");
        final int DEFAULT_ITEM_SUBTYPE = 0;
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(itemBlock, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return true;
        }

        TileEntity te = worldIn.getTileEntity(pos);
        if (!(te instanceof BlockHiddenManagerTE)) {
            return false;
        }

        if(playerIn.isSneaking()) {
            playerIn.openGui(ModMain.instance, 1, worldIn, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }

        return false;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof BlockHiddenManagerTE)
        {
            InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), ((BlockHiddenManagerTE)tileentity).itemStackHandler.getStackInSlot(0));
        }
        super.breakBlock(worldIn, pos, state);
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
    protected BlockStateContainer createBlockState() {
        IProperty[] listedProperties = new IProperty[] { FACING };
        IUnlistedProperty[] unlistedProperties = new IUnlistedProperty[] { DISGUISE_ITEM };
        return new ExtendedBlockState(this, listedProperties, unlistedProperties);
    }

    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
        IExtendedBlockState extendedBlockState = (IExtendedBlockState) state;

        TileEntity te = world.getTileEntity(pos);
        if(te instanceof BlockHiddenManagerTE)
        {
            EnumFacing facing = state.getValue(FACING);
            FakePlayer fakePlayer = FakePlayerFactory.getMinecraft(DimensionManager.getWorld(0));

            IBlockState disguiseBS = null;
            if(((BlockHiddenManagerTE) te).itemStackHandler.getStackInSlot(0).getCount() > 0)
                disguiseBS = ((ItemBlock) ((BlockHiddenManagerTE) te).itemStackHandler.getStackInSlot(0).getItem()).getBlock().getStateForPlacement(
                        te.getWorld(), te.getPos(), facing, 0, 0, 0, ((BlockHiddenManagerTE) te).itemStackHandler.getStackInSlot(0).getMetadata(), fakePlayer, null);
            return extendedBlockState.withProperty(DISGUISE_ITEM, disguiseBS).withProperty(FACING, state.getValue(FACING));
        } else
            throw new UnsupportedOperationException("Tileentity is NULL");
    }
}
