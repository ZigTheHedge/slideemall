package com.cwelth.slideemall.tileentities.gui;

import com.cwelth.slideemall.ModMain;
import com.cwelth.slideemall.network.DisguiseGUISync;
import com.cwelth.slideemall.tileentities.BlockHiddenManagerTE;
import com.cwelth.slideemall.tileentities.CommonTE;
import com.cwelth.slideemall.tileentities.gui.server.CommonContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class BlockHiddenManagerGUIContainer<TE extends CommonTE, CNT extends CommonContainer> extends GuiContainer {
    public static final int WIDTH = 174;
    public static final int HEIGHT = 186;
    private TE te;
    private EntityPlayer player;

    private boolean isGuiInitialized = false;
    private static ResourceLocation background;

    public BlockHiddenManagerGUIContainer(TE tileEntity, CNT container, String bg, EntityPlayer player) {
        super(container);
        this.player = player;
        te = tileEntity;
        background = new ResourceLocation(ModMain.MODID, bg);
        xSize = WIDTH;
        ySize = HEIGHT;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    public void onGuiClosed() {
        DisguiseGUISync.send((BlockHiddenManagerTE)te);
        super.onGuiClosed();
    }
}
