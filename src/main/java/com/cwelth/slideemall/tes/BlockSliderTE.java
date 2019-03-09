package com.cwelth.slideemall.tes;

import com.cwelth.slideemall.ModMain;
import com.cwelth.slideemall.bakes.EnumHoleTypes;
import com.mojang.authlib.GameProfile;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.UUID;

public final class BlockSliderTE extends CommonTE implements ITickable {
    public int facing;
    public int state;
    public int blocksExtended;
    public EnumHoleTypes holeType;
    private int delayCounter = 10;
    public boolean isRedstoneHigh = true;

    public BlockSliderTE() {
        super(2);
        state = 0;
        blocksExtended = 0;
        holeType = EnumHoleTypes.ROUND;

    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("items")) {
            itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
        }
        if (compound.hasKey("facing"))
            facing = compound.getInteger("facing");
        if (compound.hasKey("state"))
            state = compound.getInteger("state");
        if (compound.hasKey("blocksextended"))
            blocksExtended = compound.getInteger("blocksextended");
        if (compound.hasKey("holetype"))
            holeType = EnumHoleTypes.values()[compound.getInteger("holetype")];
        if (compound.hasKey("redstonehigh"))
            isRedstoneHigh = compound.getBoolean("redstonehigh");
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("items", itemStackHandler.serializeNBT());
        compound.setInteger("facing", facing);
        compound.setInteger("state", state);
        compound.setInteger("blocksextended", blocksExtended);
        compound.setInteger("holetype", holeType.getIndex());
        compound.setBoolean("redstonehigh", isRedstoneHigh);
        this.markDirty();
        return compound;
    }

    public void setActive(int direction) {
        state = direction;
        delayCounter = 5;
    }

    @Override
    public void update() {
        int deltaX = 0;
        int deltaY = 0;
        int deltaZ = 0;

        if (facing == 0)
            deltaY = -1;
        if (facing == 1)
            deltaY = 1;
        if (facing == 2)
            deltaZ = -1;
        if (facing == 3)
            deltaZ = 1;
        if (facing == 4)
            deltaX = -1;
        if (facing == 5)
            deltaX = 1;

        if (state == 1) {
            if (delayCounter > 0) {
                delayCounter--;
            } else {
                delayCounter = 5;
                ItemStack piston = itemStackHandler.getStackInSlot(0);
                if (piston.getCount() > 0 && blocksExtended < ModMain.maxExtend) {
                    BlockPos pos = this.getPos();
                    pos = pos.add(
                            deltaX * (blocksExtended + 1),
                            deltaY * (blocksExtended + 1),
                            deltaZ * (blocksExtended + 1));
                    if (getWorld().getBlockState(pos).getBlock() == Blocks.AIR) {
                        blocksExtended++;
                        ItemBlock itemBlockToPlace = (ItemBlock) piston.getItem();
                        BlockPos posToPlace = pos.add(deltaX, deltaY, deltaZ);
                        FakePlayer fakePlayer = FakePlayerFactory.getMinecraft((WorldServer) getWorld());
                        IBlockState newBlock = itemBlockToPlace
                                .getBlock()
                                .getStateForPlacement(
                                        getWorld(),
                                        pos,
                                        EnumFacing.getFront(facing & 7),
                                        posToPlace.getX(),
                                        posToPlace.getY(),
                                        posToPlace.getZ(),
                                        piston.getMetadata(),
                                        fakePlayer,
                                        EnumHand.MAIN_HAND);
                        getWorld().setBlockState(pos, newBlock);
                        piston.setCount(piston.getCount() - 1);
                        itemStackHandler.setStackInSlot(0, piston);
                        getWorld().playSound(
                                null,
                                pos,
                                SoundEvents.BLOCK_PISTON_EXTEND,
                                SoundCategory.BLOCKS,
                                0.5F,
                                getWorld().rand.nextFloat() * 0.25F + 0.6F);
                    } else {
                        state = 0;
                        markDirty();
                    }
                } else {
                    state = 0;
                    markDirty();
                }
            }
        }

        if (state == -1) {
            if (delayCounter > 0)
                delayCounter--;
            else {
                delayCounter = 5;
                if (blocksExtended > 0) {
                    BlockPos pos = this.getPos();
                    pos = pos.add(
                            deltaX * (blocksExtended),
                            deltaY * (blocksExtended),
                            deltaZ * (blocksExtended));
                    ItemStack piston = itemStackHandler.getStackInSlot(0);
                    if (getWorld().getBlockState(pos).getBlock() != Blocks.AIR) {
                        if (piston.getCount() == 0) {
                            piston = getWorld()
                                    .getBlockState(pos)
                                    .getBlock()
                                    .getPickBlock(
                                            getWorld().getBlockState(pos),
                                            null,
                                            getWorld(),
                                            pos,
                                            null);
                        } else {
                            FakePlayer fakePlayer = new FakePlayer(
                                    Objects
                                            .requireNonNull(
                                                    getWorld().getMinecraftServer())
                                            .getWorld(0),
                                    new GameProfile(
                                            new UUID(0, 0), "fakePlayerSlider"));
                            IBlockState newBlock = ((ItemBlock) piston.getItem())
                                    .getBlock()
                                    .getStateForPlacement(
                                            getWorld(),
                                            pos,
                                            EnumFacing.getFront(facing & 7),
                                            pos.getX(),
                                            pos.getY(),
                                            pos.getZ(),
                                            piston.getMetadata(),
                                            fakePlayer,
                                            EnumHand.MAIN_HAND);
                            if (getWorld().getBlockState(pos) == newBlock)
                                piston.setCount(piston.getCount() + 1);
                            else {
                                InventoryHelper.spawnItemStack(
                                        getWorld(),
                                        pos.getX(),
                                        pos.getY(),
                                        pos.getZ(),
                                        getWorld()
                                                .getBlockState(pos)
                                                .getBlock()
                                                .getPickBlock(
                                                        getWorld().getBlockState(pos),
                                                        null,
                                                        getWorld(),
                                                        pos,
                                                        null));
                            }
                        }
                        getWorld().setBlockToAir(pos);
                        itemStackHandler.setStackInSlot(0, piston);
                        getWorld().playSound(
                                null,
                                pos,
                                SoundEvents.BLOCK_PISTON_CONTRACT,
                                SoundCategory.BLOCKS,
                                0.5F,
                                getWorld().rand.nextFloat() * 0.25F + 0.6F);
                        blocksExtended--;
                    } else {
                        blocksExtended = 0;
                        state = 0;
                        markDirty();
                    }
                } else {
                    state = 0;
                    markDirty();
                }
            }
        }
    }
}
