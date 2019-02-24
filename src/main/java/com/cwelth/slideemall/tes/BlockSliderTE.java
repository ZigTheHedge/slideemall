package com.cwelth.slideemall.tes;

import com.cwelth.slideemall.ModMain;
import com.cwelth.slideemall.bakes.EnumHoleTypes;
import com.mojang.authlib.GameProfile;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

import java.util.UUID;

public class BlockSliderTE extends CommonTE implements ITickable {
    public int FACING;
    public int STATE;
    public int BLOCKSEXTENDED;
    public EnumHoleTypes HOLE_TYPE;
    private int delayCounter = 10;
    public boolean isRedstoneHigh = true;

    public BlockSliderTE() {
        super(2);
        STATE = 0;
        BLOCKSEXTENDED = 0;
        HOLE_TYPE = EnumHoleTypes.ROUND;

    }
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("items")) {
            itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
        }
        if(compound.hasKey("facing"))
            FACING = compound.getInteger("facing");
        if(compound.hasKey("state"))
            STATE = compound.getInteger("state");
        if(compound.hasKey("blocksextended"))
            BLOCKSEXTENDED = compound.getInteger("blocksextended");
        if(compound.hasKey("holetype"))
            HOLE_TYPE = EnumHoleTypes.values()[compound.getInteger("holetype")];
        if(compound.hasKey("redstonehigh"))
            isRedstoneHigh = compound.getBoolean("redstonehigh");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("items", itemStackHandler.serializeNBT());
        compound.setInteger("facing", FACING);
        compound.setInteger("state", STATE);
        compound.setInteger("blocksextended", BLOCKSEXTENDED);
        compound.setInteger("holetype", HOLE_TYPE.getIndex());
        compound.setBoolean("redstonehigh", isRedstoneHigh);
        this.markDirty();
        return compound;
    }

    public void setActive(int direction)
    {
        STATE = direction;
        delayCounter = 5;
    }

    @Override
    public void update() {
        int deltaX = 0;
        int deltaY = 0;
        int deltaZ = 0;
        if(FACING == 0)deltaY = -1;
        if(FACING == 1)deltaY = 1;
        if(FACING == 2)deltaZ = -1;
        if(FACING == 3)deltaZ = 1;
        if(FACING == 4)deltaX = -1;
        if(FACING == 5)deltaX = 1;
        if(STATE == 1) {
            if(delayCounter > 0) {
                delayCounter--;
            } else {
                delayCounter = 5;
                ItemStack piston = itemStackHandler.getStackInSlot(0);
                if(piston.getCount() > 0)
                {
                    BlockPos pos = this.getPos();
                    pos = pos.add(deltaX * (BLOCKSEXTENDED + 1), deltaY * (BLOCKSEXTENDED + 1), deltaZ * (BLOCKSEXTENDED + 1));
                    if(getWorld().getBlockState(pos).getBlock() == Blocks.AIR)
                    {
                        BLOCKSEXTENDED++;
                        ItemBlock blockToPlace = (ItemBlock)piston.getItem();
                        BlockPos posToPlace = pos.add(deltaX, deltaY, deltaZ);
                        //FakePlayer fakePlayer = new FakePlayer(getWorld().getMinecraftServer().getWorld(0), new GameProfile(new UUID(0, 0), "fakePlayerSlider"));
                        FakePlayer fakePlayer = FakePlayerFactory.getMinecraft((WorldServer)getWorld());
                        IBlockState newBlock = blockToPlace.getBlock().getStateForPlacement(getWorld(), pos, EnumFacing.getFront(FACING & 7), posToPlace.getX(), posToPlace.getY(), posToPlace.getZ(), piston.getMetadata(), fakePlayer);
                        getWorld().setBlockState(pos, newBlock);
                        piston.setCount(piston.getCount() - 1);
                        itemStackHandler.setStackInSlot(0, piston);
                        getWorld().playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.BLOCKS, 0.5F, getWorld().rand.nextFloat() * 0.25F + 0.6F);
                    } else
                    {
                        STATE = 0;
                        markDirty();
                    }
                } else
                {
                    STATE = 0;
                    markDirty();
                }
            }
        }
        if(STATE == -1) {
            if(delayCounter > 0) {
                delayCounter--;
            } else {
                delayCounter = 5;
                if (BLOCKSEXTENDED > 0)
                {
                    BlockPos pos = this.getPos();
                    pos = pos.add(deltaX * (BLOCKSEXTENDED), deltaY * (BLOCKSEXTENDED), deltaZ * (BLOCKSEXTENDED));
                    ItemStack piston = itemStackHandler.getStackInSlot(0);
                    if(getWorld().getBlockState(pos).getBlock() != Blocks.AIR) {
                        if(piston.getCount() == 0) {
                            piston = getWorld().getBlockState(pos).getBlock().getPickBlock(getWorld().getBlockState(pos), null, getWorld(), pos, null);
                        } else {
                            FakePlayer fakePlayer = new FakePlayer(getWorld().getMinecraftServer().getWorld(0),new GameProfile(new UUID(0, 0), "fakePlayerSlider"));
                            IBlockState newBlock = ((ItemBlock)piston.getItem()).getBlock().getStateForPlacement(getWorld(), pos, EnumFacing.getFront(FACING & 7), pos.getX(), pos.getY(), pos.getZ(), piston.getMetadata(), fakePlayer);
                            //if (getWorld().getBlockState(pos).getBlock() == ((ItemBlock) piston.getItem()).getBlock() && getWorld().getBlockState(pos).getBlock().getMetaFromState(getWorld().getBlockState(pos)) == piston.getMetadata())
                            if (getWorld().getBlockState(pos) == newBlock)
                                piston.setCount(piston.getCount() + 1);
                            else
                            {
                                InventoryHelper.spawnItemStack(getWorld(), pos.getX(), pos.getY(), pos.getZ(), getWorld().getBlockState(pos).getBlock().getPickBlock(getWorld().getBlockState(pos), null, getWorld(), pos, null));
                            }
                        }
                        getWorld().setBlockToAir(pos);
                        itemStackHandler.setStackInSlot(0, piston);
                        getWorld().playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_PISTON_CONTRACT, SoundCategory.BLOCKS, 0.5F, getWorld().rand.nextFloat() * 0.25F + 0.6F);
                        BLOCKSEXTENDED--;
                    } else
                    {
                        BLOCKSEXTENDED = 0;
                        STATE = 0;
                        markDirty();
                    }
                } else {
                    STATE = 0;
                    markDirty();
                }
            }
        }
    }
}
