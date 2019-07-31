package com.cwelth.slideemall.tileentities.gui;

import com.cwelth.slideemall.ModMain;
import com.cwelth.slideemall.utils.EnumHoleTypes;
import com.cwelth.slideemall.network.SliderDropModule;
import com.cwelth.slideemall.network.SliderGUISync;
import com.cwelth.slideemall.tileentities.BlockSliderTE;
import com.cwelth.slideemall.tileentities.CommonTE;
import com.cwelth.slideemall.tileentities.gui.server.CommonContainer;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiButtonImage;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
        int len = fontRenderer.getStringWidth(I18n.format("slider.slot0.desc"));
        drawString(fontRenderer, I18n.format("slider.slot0.desc"), guiLeft + 115 - len - 3, guiTop + 18, 0xFFFFFF);
        len = fontRenderer.getStringWidth(I18n.format("slider.slot1.desc"));
        drawString(fontRenderer, I18n.format("slider.slot1.desc"), guiLeft + 115 - len - 3, guiTop + 39, 0xFFFFFF);
        len = fontRenderer.getStringWidth(I18n.format("slider.texture.desc"));
        drawString(fontRenderer, I18n.format("slider.texture.desc"), guiLeft + 75 - len - 3, guiTop + 60, 0xFFFFFF);
        len = fontRenderer.getStringWidth(I18n.format("slider.redstone.desc"));
        drawString(fontRenderer, I18n.format("slider.redstone.desc"), guiLeft + 115 - len - 3, guiTop + 81, 0xFFFFFF);

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
        if(((BlockSliderTE)te).isRedstoneHigh)
            holeTypeButtons.get(6).drawButton(mc, mouseX, mouseY, 0);
        else
            holeTypeButtons.get(7).drawButton(mc, mouseX, mouseY, 0);
        if(((BlockSliderTE)te).WATERTOLERANT)
            holeTypeButtons.get(8).drawButton(mc, mouseX, mouseY, 0);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        int mouseClicked = -1;
        for (int i = 0; i < 6; i++) {
            if (holeTypeButtons.get(i).mousePressed(mc, mouseX, mouseY)) {
                int rem = i % 3;
                if (rem == 0) ((BlockSliderTE) te).HOLE_TYPE = EnumHoleTypes.ROUND;
                if (rem == 1) ((BlockSliderTE) te).HOLE_TYPE = EnumHoleTypes.SQUARE;
                if (rem == 2) ((BlockSliderTE) te).HOLE_TYPE = EnumHoleTypes.CROSS;
                mouseClicked = i;
            }
        }
        if(holeTypeButtons.get(6).mousePressed(mc, mouseX, mouseY) || holeTypeButtons.get(7).mousePressed(mc, mouseX, mouseY))
        {
            ((BlockSliderTE)te).isRedstoneHigh = !((BlockSliderTE)te).isRedstoneHigh;
            mouseClicked = 6;
        }

        if(holeTypeButtons.get(8).mousePressed(mc, mouseX, mouseY))
        {
            if(((BlockSliderTE)te).WATERTOLERANT) {
                ((BlockSliderTE) te).WATERTOLERANT = false;
                World worldIn = te.getWorld();
                BlockPos pos = te.getPos();

                SliderGUISync.send((BlockSliderTE) te);
                SliderDropModule.send((BlockSliderTE) te);
                mouseClicked = 8;
            }
        }

        if(mouseClicked != -1)
        {
            holeTypeButtons.get(mouseClicked).playPressSound(this.mc.getSoundHandler());
        }
    }

    @Override
    public void initGui() {
        int xCenter = width / 2 - xSize / 2;
        int yCenter = height / 2 - ySize / 2;
        if(!isGuiInitialized)
        {
            holeTypeButtons.add(new GuiButtonImage(0,  xCenter + 74,  yCenter + 56, 18, 18, 0, 0, 18, new ResourceLocation(ModMain.MODID, "textures/gui/holetypesbuttons.png")));
            holeTypeButtons.add(new GuiButtonImage(1,  xCenter + 94,  yCenter + 56, 18, 18, 18, 0, 18, new ResourceLocation(ModMain.MODID, "textures/gui/holetypesbuttons.png")));
            holeTypeButtons.add(new GuiButtonImage(2,  xCenter + 114,  yCenter + 56, 18, 18, 36, 0, 18, new ResourceLocation(ModMain.MODID, "textures/gui/holetypesbuttons.png")));
            holeTypeButtons.add(new GuiButtonImage(3,  xCenter + 74,  yCenter + 56, 18, 18, 0, 36, 18, new ResourceLocation(ModMain.MODID, "textures/gui/holetypesbuttons.png")));
            holeTypeButtons.add(new GuiButtonImage(4,  xCenter + 94,  yCenter + 56, 18, 18, 18, 36, 18, new ResourceLocation(ModMain.MODID, "textures/gui/holetypesbuttons.png")));
            holeTypeButtons.add(new GuiButtonImage(5,  xCenter + 114,  yCenter + 56, 18, 18, 36, 36, 18, new ResourceLocation(ModMain.MODID, "textures/gui/holetypesbuttons.png")));
            holeTypeButtons.add(new GuiButtonImage(6,  xCenter + 114,  yCenter + 77, 18, 18, 54, 0, 18, new ResourceLocation(ModMain.MODID, "textures/gui/holetypesbuttons.png")));
            holeTypeButtons.add(new GuiButtonImage(7,  xCenter + 114,  yCenter + 77, 18, 18, 54, 36, 18, new ResourceLocation(ModMain.MODID, "textures/gui/holetypesbuttons.png")));
            holeTypeButtons.add(new GuiButtonImage(8,  xCenter + 134,  yCenter + 77, 18, 18, 72, 0, 18, new ResourceLocation(ModMain.MODID, "textures/gui/holetypesbuttons.png")));
            isGuiInitialized = true;
        } else
        {
            for(int i = 0; i < holeTypeButtons.size(); i++)
            {
                if(i < 6) {
                    holeTypeButtons.get(i).x = xCenter + 74 + ((i % 3) * 20);
                    holeTypeButtons.get(i).y = yCenter + 56;
                } else
                {
                    if(i == 8)
                    {
                        holeTypeButtons.get(i).x = xCenter + 134;
                        holeTypeButtons.get(i).y = yCenter + 77;
                    } else {
                        holeTypeButtons.get(i).x = xCenter + 114;
                        holeTypeButtons.get(i).y = yCenter + 77;
                    }
                }
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
