package com.cwelth.slideemall.network;

import com.cwelth.slideemall.ModMain;
import com.cwelth.slideemall.tileentities.CommonTE;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class DisguiseGUISync implements IMessageHandler<DisguiseGUISync.Packet, IMessage> {

    @Override
    public IMessage onMessage(DisguiseGUISync.Packet message, MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().player;
        player.getServerWorld().addScheduledTask(() -> {
            World world = player.world;
            world.notifyBlockUpdate(message.tePos, world.getBlockState(message.tePos), world.getBlockState(message.tePos), 3);
        });
        return null;
    }

    public static void send(CommonTE te)
    {
        ModMain.network.sendToServer(new DisguiseGUISync.Packet(te));
    }

    public static class Packet implements IMessage {
        public BlockPos tePos;

        public Packet(CommonTE te) {
            tePos = te.getPos();
        }

        public Packet(){}

        @Override
        public void fromBytes(ByteBuf buf) {
            tePos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        }

        @Override
        public void toBytes(ByteBuf buf) {
            buf.writeInt(tePos.getX());
            buf.writeInt(tePos.getY());
            buf.writeInt(tePos.getZ());
        }

    }
}
