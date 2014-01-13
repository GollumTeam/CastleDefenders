package cpw.mods.fml.client;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.ModMetadata;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSmallButton;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

public class GuiModList extends GuiScreen
{
    private GuiScreen mainMenu;
    private GuiSlotModList modList;
    private int selected;
    private ModContainer selectedMod;
    private int listWidth;
    private ArrayList mods;

    public GuiModList(GuiScreen guiscreen)
    {
        selected = -1;
        mainMenu = guiscreen;
        mods = new ArrayList();
        FMLClientHandler.instance().addSpecialModEntries(mods);
        Iterator iterator = Loader.getModList().iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            ModContainer modcontainer = (ModContainer)iterator.next();

            if (modcontainer.getMetadata() == null || modcontainer.getMetadata().parentMod == null)
            {
                mods.add(modcontainer);
            }
        }
        while (true);
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        for (Iterator iterator = mods.iterator(); iterator.hasNext();)
        {
            ModContainer modcontainer = (ModContainer)iterator.next();
            listWidth = Math.max(listWidth, getFontRenderer().getStringWidth(modcontainer.getName()) + 10);
            listWidth = Math.max(listWidth, getFontRenderer().getStringWidth(modcontainer.getVersion()) + 10);
        }

        listWidth = Math.min(listWidth, 150);
        StringTranslate stringtranslate = StringTranslate.getInstance();
        controlList.add(new GuiSmallButton(6, width / 2 - 75, height - 38, stringtranslate.translateKey("gui.done")));
        modList = new GuiSlotModList(this, mods, listWidth);
        modList.registerScrollButtons(controlList, 7, 8);
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton guibutton)
    {
        if (guibutton.enabled)
        {
            switch (guibutton.id)
            {
                case 6:
                    mc.displayGuiScreen(mainMenu);
                    return;
            }
        }

        super.actionPerformed(guibutton);
    }

    public int drawLine(String s, int i, int j)
    {
        fontRenderer.drawString(s, i, j, 0xd7edea);
        return j + 10;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int i, int j, float f)
    {
        modList.drawScreen(i, j, f);
        drawCenteredString(fontRenderer, "Mod List", width / 2, 16, 0xffffff);
        int k = listWidth + 20;

        if (selectedMod != null)
        {
            if (selectedMod.getMetadata() != null)
            {
                int i1 = 35;

                if (!selectedMod.getMetadata().logoFile.isEmpty())
                {
                    int j1 = mc.renderEngine.getTexture(selectedMod.getMetadata().logoFile);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    mc.renderEngine.bindTexture(j1);
                    java.awt.Dimension dimension = FMLClientHandler.instance().getTextureDimensions(j1);
                    int k1 = 32;
                    Tessellator tessellator = Tessellator.instance;
                    tessellator.startDrawingQuads();
                    tessellator.addVertexWithUV(k, k1 + dimension.height, zLevel, 0.0D, 1.0D);
                    tessellator.addVertexWithUV(k + dimension.width, k1 + dimension.height, zLevel, 1.0D, 1.0D);
                    tessellator.addVertexWithUV(k + dimension.width, k1, zLevel, 1.0D, 0.0D);
                    tessellator.addVertexWithUV(k, k1, zLevel, 0.0D, 0.0D);
                    tessellator.draw();
                    i1 += 65;
                }

                fontRenderer.drawStringWithShadow(selectedMod.getMetadata().name, k, i1, 0xffffff);
                i1 += 12;
                i1 = drawLine(String.format("Version: %s (%s)", new Object[]
                        {
                            selectedMod.getMetadata().version, selectedMod.getVersion()
                        }), k, i1);

                if (!selectedMod.getMetadata().credits.isEmpty())
                {
                    i1 = drawLine(String.format("Credits: %s", new Object[]
                            {
                                selectedMod.getMetadata().credits
                            }), k, i1);
                }

                i1 = drawLine(String.format("Authors: %s", new Object[]
                        {
                            selectedMod.getMetadata().getAuthorList()
                        }), k, i1);
                i1 = drawLine(String.format("URL: %s", new Object[]
                        {
                            selectedMod.getMetadata().url
                        }), k, i1);
                i1 = drawLine(selectedMod.getMetadata().childMods.isEmpty() ? "No child mods for this mod" : String.format("Child mods: %s", new Object[]
                        {
                            selectedMod.getMetadata().getChildModList()
                        }), k, i1);
                getFontRenderer().drawSplitString(selectedMod.getMetadata().description, k, i1 + 10, width - k - 20, 0xdddddd);
            }
            else
            {
                int l = (listWidth + width) / 2;
                drawCenteredString(fontRenderer, selectedMod.getName(), l, 35, 0xffffff);
                drawCenteredString(fontRenderer, String.format("Version: %s", new Object[]
                        {
                            selectedMod.getVersion()
                        }), l, 45, 0xffffff);
                drawCenteredString(fontRenderer, "No mod information found", l, 55, 0xdddddd);
                drawCenteredString(fontRenderer, "Ask your mod author to provide a mod .info file", l, 65, 0xdddddd);
            }
        }

        super.drawScreen(i, j, f);
    }

    Minecraft getMinecraftInstance()
    {
        return mc;
    }

    FontRenderer getFontRenderer()
    {
        return fontRenderer;
    }

    public void selectModIndex(int i)
    {
        selected = i;

        if (i >= 0 && i <= mods.size())
        {
            selectedMod = (ModContainer)mods.get(selected);
        }
        else
        {
            selectedMod = null;
        }
    }

    public boolean modIndexSelected(int i)
    {
        return i == selected;
    }
}
