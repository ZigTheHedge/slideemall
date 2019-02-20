package com.cwelth.slideemall.network;

import com.cwelth.slideemall.ModMain;
import com.cwelth.slideemall.bakes.EnumHoleTypes;
import com.cwelth.slideemall.tes.BlockSliderTE;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SliderGUISync implements IMessageHandler<SliderGUISync.Packet, IMessage> {

    @Override
    public IMessage onMessage(SliderGUISync.Packet message, MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().player;
        player.getServerWorld().addScheduledTask(() -> {
            World world = player.world;
            BlockSliderTE te = (BlockSliderTE) world.getTileEntity(message.tePos);
            te.HOLE_TYPE = message.holeType;
            te.sendUpdates();
            //ModMain.logger.warning("TE synced. Side: " + ctx.side + ", Data follows: X: "+message.tePos.getX() + ", Y: "+message.tePos.getY()+", Z: "+message.tePos.getZ()+", HOLE_TYPE:"+message.holeType);
        });
        return null;
    }

    public static void send(BlockSliderTE te)
    {
        ModMain.network.sendToServer(new Packet(te));
    }

    public static class Packet implements IMessage {
        public BlockPos tePos;
        public EnumHoleTypes holeType;

        public Packet(BlockSliderTE te) {
            tePos = te.getPos();
            holeType = te.HOLE_TYPE;
        }

        public Packet(){}

        @Override
        public void fromBytes(ByteBuf buf) {
            tePos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
            holeType = EnumHoleTypes.values()[buf.readInt()];
        }

        @Override
        public void toBytes(ByteBuf buf) {
            buf.writeInt(tePos.getX());
            buf.writeInt(tePos.getY());
            buf.writeInt(tePos.getZ());
            buf.writeInt(holeType.getIndex());
        }

    }
}