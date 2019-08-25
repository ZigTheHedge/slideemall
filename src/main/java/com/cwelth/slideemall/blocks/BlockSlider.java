package com.cwelth.slideemall.blocks;

import com.cwelth.slideemall.InitContent;
import com.cwelth.slideemall.ModMain;
import com.cwelth.slideemall.bakes.UnlistedPropertyDisguiseItem;
import com.cwelth.slideemall.bakes.UnlistedPropertyHoleType;
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
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class BlockSlider extends CommonTEBlock<BlockSliderTE> {
    public static final UnlistedPropertyDisguiseItem DISGUISE_ITEM = new UnlistedPropertyDisguiseItem("disguise");
    public static final UnlistedPropertyHoleType HOLE_TYPE = new UnlistedPropertyHoleType("hole");
    public BlockSlider(Material material, String name) {
        super(material, name);
    }

    @Override
    public Class<BlockSliderTE> getTileEntityClass() {
        return BlockSliderTE.class;
    }

    @Nullable
    @Override
    public BlockSliderTE createTileEntity(World world, IBlockState state) {
        return new BlockSliderTE();
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        //Replace with BakedModel
        //ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
        /*
        StateMapperBase ignoreState = new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState iBlockState) {
                return BlockSliderBakedModel.BAKED_MODEL;
            }
        };
        ModelLoader.setCustomStateMapper(this, ignoreState);
        */
    }

    @SideOnly(Side.CLIENT)
    public void initItemModel() {

        Item itemBlock = Item.REGISTRY.getObject(new ResourceLocation(ModMain.MODID, "blockslider"));
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
        if (!(te instanceof BlockSliderTE)) {
            return false;
        }

        if(playerIn.isSneaking()) {
            playerIn.openGui(ModMain.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }

        ItemStack currentItem = playerIn.inventory.getCurrentItem();
        if(currentItem != null)
        {
            if(currentItem.getItem().equals(InitContent.itemLiquidModule))
            {
                ((BlockSliderTE) te).WATERTOLERANT = true;
                playerIn.inventory.getCurrentItem().setCount(currentItem.getCount() - 1);
                te.markDirty();
                worldIn.notifyBlockUpdate(pos, worldIn.getBlockState(pos), worldIn.getBlockState(pos), 3);
                return true;
            }
        }

        return false;
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return super.withRotation(state, rot);
    }



    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if(!(tileentity instanceof BlockSliderTE))
        {
            return;
        }
        ((BlockSliderTE) tileentity).FACING = getFacingFromEntity(pos, placer).getIndex();
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!worldIn.isRemote)
        {
            BlockSliderTE te = (BlockSliderTE)worldIn.getTileEntity(pos);
            if (worldIn.isBlockPowered(pos))
            {
                if(te.isRedstoneHigh)
                    te.setActive(1);
                else
                    te.setActive(-1);
            }
            else
            {
                if(te.isRedstoneHigh)
                    te.setActive(-1);
                else
                    te.setActive(1);
            }
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof BlockSliderTE)
        {
            InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), ((BlockSliderTE)tileentity).itemStackHandler.getStackInSlot(0));
            InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), ((BlockSliderTE)tileentity).itemStackHandler.getStackInSlot(1));
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
        IUnlistedProperty[] unlistedProperties = new IUnlistedProperty[] { DISGUISE_ITEM, HOLE_TYPE };
        return new ExtendedBlockState(this, listedProperties, unlistedProperties);
    }

    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
        IExtendedBlockState extendedBlockState = (IExtendedBlockState) state;

        TileEntity te = world.getTileEntity(pos);
        if(te instanceof BlockSliderTE)
        {
                EnumFacing facing = state.getValue(FACING);
                IBlockState disguiseBS = null;
                if(((BlockSliderTE) te).itemStackHandler.getStackInSlot(1).getCount() > 0)
                    disguiseBS = ((ItemBlock) ((BlockSliderTE) te).itemStackHandler.getStackInSlot(1).getItem()).getBlock().getStateForPlacement(
                        te.getWorld(), te.getPos(), facing, 0, 0, 0, ((BlockSliderTE) te).itemStackHandler.getStackInSlot(1).getMetadata(), ModMain.getFakePlayer(null), null);
            return extendedBlockState.withProperty(DISGUISE_ITEM, disguiseBS).withProperty(HOLE_TYPE, ((BlockSliderTE)te).HOLE_TYPE).withProperty(FACING, state.getValue(FACING));
        } else
            throw new UnsupportedOperationException("Tileentity is NULL");
    }
}
