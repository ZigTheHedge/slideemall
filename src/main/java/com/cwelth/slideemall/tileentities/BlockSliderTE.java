package com.cwelth.slideemall.tileentities;

import com.cwelth.slideemall.InitContent;
import com.cwelth.slideemall.ModMain;
import com.cwelth.slideemall.blocks.CommonBlock;
import com.cwelth.slideemall.utils.EnumHoleTypes;
import com.mojang.authlib.GameProfile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;

public class BlockSliderTE extends CommonTE implements ITickable {
    public int FACING;
    public int STATE;
    public int BLOCKSEXTENDED;
    public boolean WATERTOLERANT = false;
    public EnumHoleTypes HOLE_TYPE;
    private int delayCounter = 10;
    public boolean isRedstoneHigh = true;
    public int disguiseFacing = 0;
    public int possibleFacing = 0;

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
        if(compound.hasKey("watertolerant"))
            WATERTOLERANT = compound.getBoolean("watertolerant");
        if(compound.hasKey("disguiseFacing"))
            disguiseFacing = compound.getInteger("disguiseFacing");
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
        compound.setBoolean("watertolerant", WATERTOLERANT);
        compound.setInteger("disguiseFacing", disguiseFacing);
        this.markDirty();
        return compound;
    }

    public void setActive(int direction)
    {
        STATE = direction;
        delayCounter = 5;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        //boolean retvalue = newState.getBlock() == InitContent.blockSlider || newState.getBlock() == InitContent.blockSliderCutout;
        boolean retvalue = newState.getBlock() == oldState.getBlock();
        return !retvalue;
    }

    public void acceptFacing()
    {
        disguiseFacing = possibleFacing;
        markDirty();
    }

    @Override
    public void update() {
        if(world.isRemote)return;
        FACING = world.getBlockState(pos).getValue(CommonBlock.FACING).getIndex();
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
                if(piston.getCount() > 0 && BLOCKSEXTENDED < ModMain.maxExtend)
                {
                    BlockPos pos = this.getPos();
                    pos = pos.add(deltaX * (BLOCKSEXTENDED + 1), deltaY * (BLOCKSEXTENDED + 1), deltaZ * (BLOCKSEXTENDED + 1));
                    boolean isValidBlock = false;
                    Block blockInQ = getWorld().getBlockState(pos).getBlock();
                    if(blockInQ == Blocks.AIR) isValidBlock = true;
                    if(WATERTOLERANT)
                    {
                        if(blockInQ instanceof BlockFluidBase || blockInQ instanceof BlockLiquid)isValidBlock = true;
                    }
                    if(isValidBlock)
                    {
                        BLOCKSEXTENDED++;
                        ItemBlock blockToPlace = (ItemBlock)piston.getItem();
                        BlockPos posToPlace = pos.add(deltaX, deltaY, deltaZ);
                        IBlockState newBlock = blockToPlace.getBlock().getStateForPlacement(getWorld(), pos, EnumFacing.getFront(FACING & 7), posToPlace.getX(), posToPlace.getY(), posToPlace.getZ(), piston.getMetadata(), ModMain.getFakePlayer(this.world));
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
                    boolean shouldHarvest = false;
                    Block checkBlock = getWorld().getBlockState(pos).getBlock();
                    float blockHardness = checkBlock.getBlockHardness(checkBlock.getDefaultState(), getWorld(), pos);
                    if(blockHardness > 0)
                        shouldHarvest = true;
                    if(getWorld().getBlockState(pos).getBlock() != Blocks.AIR) {
                        if(piston.getCount() == 0) {
                            RayTraceResult rayTraceResult = new RayTraceResult(ModMain.getFakePlayer(this.world));
                            if(shouldHarvest)
                                piston =  checkBlock.getPickBlock(getWorld().getBlockState(pos), rayTraceResult, getWorld(), pos, ModMain.getFakePlayer(this.world));
                        } else {
                            IBlockState newBlock = ((ItemBlock)piston.getItem()).getBlock().getStateForPlacement(getWorld(), pos, EnumFacing.getFront(FACING & 7), pos.getX(), pos.getY(), pos.getZ(), piston.getMetadata(), ModMain.getFakePlayer(this.world));
                            //if (getWorld().getBlockState(pos).getBlock() == ((ItemBlock) piston.getItem()).getBlock() && getWorld().getBlockState(pos).getBlock().getMetaFromState(getWorld().getBlockState(pos)) == piston.getMetadata())
                            if (getWorld().getBlockState(pos) == newBlock)
                                piston.setCount(piston.getCount() + 1);
                            else
                            {
                                if(shouldHarvest)
                                    InventoryHelper.spawnItemStack(getWorld(), pos.getX(), pos.getY(), pos.getZ(), checkBlock.getPickBlock(getWorld().getBlockState(pos), null, getWorld(), pos, null));
                            }
                        }
                        if(shouldHarvest)
                        {
                            getWorld().setBlockToAir(pos);
                            itemStackHandler.setStackInSlot(0, piston);
                            getWorld().playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_PISTON_CONTRACT, SoundCategory.BLOCKS, 0.5F, getWorld().rand.nextFloat() * 0.25F + 0.6F);
                        }
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
