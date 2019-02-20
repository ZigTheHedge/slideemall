package com.cwelth.slideemall.tes.gui;

import com.cwelth.slideemall.ModMain;
import com.cwelth.slideemall.bakes.EnumHoleTypes;
import com.cwelth.slideemall.network.SliderGUISync;
import com.cwelth.slideemall.tes.BlockSliderTE;
import com.cwelth.slideemall.tes.CommonTE;
import com.cwelth.slideemall.tes.gui.server.CommonContainer;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonImage;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.List;

public class BlockSliderGUIContainer<TE extends CommonTE, CNT extends CommonContainer> extends GuiContainer {
    public static final int WIDTH = 174;
    public static final int HEIGHT = 186;
    private TE te;
    private EntityPlayer player;

    private List<GuiButton> holeTypeButtons = Lists.<GuiButton>newArrayList();

    private boolean isGuiInitialized = false;
    private static ResourceLocation background;

    public BlockSliderGUIContainer(TE tileEntity, CNT container, String bg, EntityPlayer player) {
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
        drawString(fontRenderer, "Drawbridge blocks:", guiLeft + 18, guiTop + 28, 0xFFFFFF);
        drawString(fontRenderer, "Disguise block:", guiLeft + 40, guiTop + 54, 0xFFFFFF);
        drawString(fontRenderer, "Hole type:", guiLeft + 23, guiTop + 80, 0xFFFFFF);

        if(((BlockSliderTE)te).HOLE_TYPE == EnumHoleTypes.ROUND)
            holeTypeButtons.get(3).drawButton(mc, mouseX, mouseY, 0);
        else
            holeTypeButtons.get(0).drawButton(mc, mouseX, mouseY, 0);
        if(((BlockSliderTE)te).HOLE_TYPE == EnumHoleTypes.SQUARE)
            holeTypeButtons.get(4).drawButton(mc, mouseX, mouseY, 0);
        else
            holeTypeButtons.get(1).drawButton(mc, mouseX, mouseY, 0);
        if(((BlockSliderTE)te).HOLE_TYPE == EnumHoleTypes.CROSS)
            holeTypeButtons.get(5).drawButton(mc, mouseX, mouseY, 0);
        else
            holeTypeButtons.get(2).drawButton(mc, mouseX, mouseY, 0);

    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for (int i = 0; i < holeTypeButtons.size(); i++)
            if (holeTypeButtons.get(i).mousePressed(mc, mouseX, mouseY)) {
                int rem = i % 3;
                if(rem == 0) ((BlockSliderTE)te).HOLE_TYPE = EnumHoleTypes.ROUND;
                if(rem == 1) ((BlockSliderTE)te).HOLE_TYPE = EnumHoleTypes.SQUARE;
                if(rem == 2) ((BlockSliderTE)te).HOLE_TYPE = EnumHoleTypes.CROSS;
            }
    }

    @Override
    public void initGui() {
        int xCenter = width / 2 - xSize / 2;
        int yCenter = height / 2 - ySize / 2;
        if(!isGuiInitialized)
        {
            holeTypeButtons.add(new GuiButtonImage(0,  xCenter + 74,  yCenter + 76, 18, 18, 0, 0, 18, new ResourceLocation(ModMain.MODID, "textures/gui/holetypesbuttons.png")));
            holeTypeButtons.add(new GuiButtonImage(1,  xCenter + 94,  yCenter + 76, 18, 18, 18, 0, 18, new ResourceLocation(ModMain.MODID, "textures/gui/holetypesbuttons.png")));
            holeTypeButtons.add(new GuiButtonImage(2,  xCenter + 114,  yCenter + 76, 18, 18, 36, 0, 18, new ResourceLocation(ModMain.MODID, "textures/gui/holetypesbuttons.png")));
            holeTypeButtons.add(new GuiButtonImage(3,  xCenter + 74,  yCenter + 76, 18, 18, 0, 36, 18, new ResourceLocation(ModMain.MODID, "textures/gui/holetypesbuttons.png")));
            holeTypeButtons.add(new GuiButtonImage(4,  xCenter + 94,  yCenter + 76, 18, 18, 18, 36, 18, new ResourceLocation(ModMain.MODID, "textures/gui/holetypesbuttons.png")));
            holeTypeButtons.add(new GuiButtonImage(5,  xCenter + 114,  yCenter + 76, 18, 18, 36, 36, 18, new ResourceLocation(ModMain.MODID, "textures/gui/holetypesbuttons.png")));
            isGuiInitialized = true;
        } else
        {
            for(int i = 0; i < holeTypeButtons.size(); i++)
            {
                holeTypeButtons.get(i).x = xCenter + 74 + ((i%3)*20);
                holeTypeButtons.get(i).y = yCenter + 76;
            }
        }

        super.initGui();
    }

    @Override
    public void onGuiClosed() {
        SliderGUISync.send((BlockSliderTE)te);
        super.onGuiClosed();
    }
}
