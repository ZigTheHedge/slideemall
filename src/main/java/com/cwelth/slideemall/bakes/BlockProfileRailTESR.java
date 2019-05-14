package com.cwelth.slideemall.bakes;

import com.cwelth.slideemall.InitContent;
import com.cwelth.slideemall.blocks.BlockProfileRail;
import com.cwelth.slideemall.tes.BlockProfileRailTE;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class BlockProfileRailTESR extends TileEntitySpecialRenderer<BlockProfileRailTE> {
    private int oldHandlePos = -1;


    @Override
    public void render(BlockProfileRailTE te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        GlStateManager.translate(x, y, z);
        GlStateManager.disableRescaleNormal();

        renderHandle(te, partialTicks);

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    public void renderHandle(BlockProfileRailTE te, float partialTicks)
    {
        GlStateManager.pushMatrix();

        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        if (Minecraft.isAmbientOcclusionEnabled()) {
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
        } else {
            GlStateManager.shadeModel(GL11.GL_FLAT);
        }

        World world = te.getWorld();
        GlStateManager.translate(-te.getPos().getX(), -te.getPos().getY(), -te.getPos().getZ());
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);

        IBlockState state = InitContent.blockProfileRail.getDefaultState().withProperty(BlockProfileRail.IS_HANDLE, true);
        BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        IBakedModel model = dispatcher.getModelForState(state);
        double delta = (((te.handlePosition + (partialTicks * te.state)) - 50) * 16F / 100F) / 16F;

        GlStateManager.translate(delta, 0, 0);
        dispatcher.getBlockModelRenderer().renderModel(world, model, state, te.getPos(), bufferBuilder, true);

        tessellator.draw();
        GlStateManager.popMatrix();
    }
}
