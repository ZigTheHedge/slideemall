package com.cwelth.slideemall.tileentities;

import com.cwelth.slideemall.InitContent;
import com.cwelth.slideemall.ModMain;
import com.cwelth.slideemall.blocks.CommonBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class CommonTE extends TileEntity {
    public static int INVSIZE;

    public ItemStackHandler itemStackHandler;

    public CommonTE(int invsize) {
        INVSIZE = invsize;

        itemStackHandler = new ItemStackHandler(INVSIZE) {
            @Override
            protected void onContentsChanged(int slot) {
                // We need to tell the tile entity that something has changed so
                // that the chest contents is persisted
                if(CommonTE.this instanceof BlockSliderTE)
                {
                    if(!world.isRemote) {
                        if (slot == 1) {
                            ((BlockSliderTE) CommonTE.this).acceptFacing();
                            Block biq = Block.getBlockFromItem(this.getStackInSlot(slot).getItem());
                            IBlockState oldState = world.getBlockState(pos);
                            if (biq == Blocks.GRASS) {
                                world.setBlockState(pos, InitContent.blockSliderCutout.getDefaultState().withProperty(CommonBlock.FACING, oldState.getValue(CommonBlock.FACING)), 3);
                            } else {
                                world.setBlockState(pos, InitContent.blockSlider.getDefaultState().withProperty(CommonBlock.FACING, oldState.getValue(CommonBlock.FACING)), 3);
                            }
                            if(CommonTE.this.shouldRefresh(world, pos, oldState, world.getBlockState(pos))) {
                                CommonTE.this.validate();
                                world.setTileEntity(pos, CommonTE.this);
                            }
                        }
                    }
                }

                world.markBlockRangeForRenderUpdate(pos, pos);
                CommonTE.this.markDirty();

            }
        };

    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        // If we are too far away from this tile entity you cannot use it
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T) itemStackHandler;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        // getUpdateTag() is called whenever the chunkdata is sent to the
        // server. In contrast getUpdatePacket() is called when the tile entity
        // itself wants to sync to the server. In many cases you want to send
        // over the same information in getUpdateTag() as in getUpdatePacket().
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        // Prepare a packet for syncing our TE to the server. Since we only have to sync the stack
        // and that's all we have we just write our entire NBT here. If you have a complex
        // tile entity that doesn't need to have all information on the server you can write
        // a more optimal NBT here.
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        super.onDataPacket(net, packet);
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
        // Here we get the packet from the server and read it into our server side tile entity
        this.readFromNBT(packet.getNbtCompound());
    }

}
