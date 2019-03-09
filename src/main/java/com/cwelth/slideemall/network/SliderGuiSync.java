package com.cwelth.slideemall.network;

import com.cwelth.slideemall.SlideEmAll;
import com.cwelth.slideemall.bakes.EnumHoleTypes;
import com.cwelth.slideemall.tileentity.BlockSliderTE;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Objects;

public final class SliderGuiSync implements IMessageHandler<SliderGuiSync.Packet, IMessage> {

    @Override
    public IMessage onMessage(Packet message, MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().player;
        player.getServerWorld().addScheduledTask(() -> {
            World world = player.world;
            BlockSliderTE tileEntity = (BlockSliderTE) world.getTileEntity(message.tePos);
            Objects.requireNonNull(tileEntity).holeType = message.holeType;
            tileEntity.isRedstoneHigh = message.isRedstoneHigh;
            // te.sendUpdates();
            world.notifyBlockUpdate(message.tePos, world.getBlockState(message.tePos), world.getBlockState(message.tePos), 3);
            // SlideEmAll.logger.warning("TE synced. Side: " + ctx.side + ", Data follows: X: "+message.tePos.getX() + ", Y: "+message.tePos.getY()+", Z: "+message.tePos.getZ()+", HOLE_TYPE:"+message.holeType);
        });
        return null;
    }

    public static void send(BlockSliderTE tileEntity) {
        SlideEmAll.network.sendToServer(new Packet(tileEntity));
    }

    public static class Packet implements IMessage {
        public BlockPos tePos;
        public EnumHoleTypes holeType;
        public boolean isRedstoneHigh;

        public Packet(BlockSliderTE te) {
            tePos = te.getPos();
            holeType = te.holeType;
            isRedstoneHigh = te.isRedstoneHigh;
        }

        public Packet() {
        }

        @Override
        public void fromBytes(ByteBuf buf) {
            tePos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
            holeType = EnumHoleTypes.values()[buf.readInt()];
            isRedstoneHigh = buf.readBoolean();
        }

        @Override
        public void toBytes(ByteBuf buffer) {
            buffer.writeInt(tePos.getX());
            buffer.writeInt(tePos.getY());
            buffer.writeInt(tePos.getZ());
            buffer.writeInt(holeType.getIndex());
            buffer.writeBoolean(isRedstoneHigh);
        }

    }
}
