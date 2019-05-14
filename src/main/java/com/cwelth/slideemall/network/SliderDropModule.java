package com.cwelth.slideemall.network;

import com.cwelth.slideemall.InitContent;
import com.cwelth.slideemall.ModMain;
import com.cwelth.slideemall.tes.BlockSliderTE;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SliderDropModule implements IMessageHandler<SliderDropModule.Packet, IMessage> {

    @Override
    public IMessage onMessage(SliderDropModule.Packet message, MessageContext ctx) {
        EntityPlayerMP player = ctx.getServerHandler().player;
        player.getServerWorld().addScheduledTask(() -> {
            World world = player.world;
            InventoryHelper.spawnItemStack(world, message.tePos.getX(), message.tePos.getY() + 1, message.tePos.getZ(), new ItemStack(InitContent.itemLiquidModule));
            //ModMain.logger.warning("TE synced. Side: " + ctx.side + ", Data follows: X: "+message.tePos.getX() + ", Y: "+message.tePos.getY()+", Z: "+message.tePos.getZ()+", HOLE_TYPE:"+message.holeType);
        });
        return null;
    }

    public static void send(BlockSliderTE te)
    {
        ModMain.network.sendToServer(new SliderDropModule.Packet(te));
    }

    public static class Packet implements IMessage {
        public BlockPos tePos;

        public Packet(BlockSliderTE te) {
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
