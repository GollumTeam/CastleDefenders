package cpw.mods.fml.client;

import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.ModMetadata;
import java.util.ArrayList;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Tessellator;

public class GuiSlotModList extends GuiScrollingList
{
    private GuiModList parent;
    private ArrayList mods;

    public GuiSlotModList(GuiModList guimodlist, ArrayList arraylist, int i)
    {
        super(guimodlist.getMinecraftInstance(), i, guimodlist.height, 32, (guimodlist.height - 65) + 4, 10, 35);
        parent = guimodlist;
        mods = arraylist;
    }

    protected int getSize()
    {
        return mods.size();
    }

    protected void elementClicked(int i, boolean flag)
    {
        parent.selectModIndex(i);
    }

    protected boolean isSelected(int i)
    {
        return parent.modIndexSelected(i);
    }

    protected void drawBackground()
    {
        parent.drawDefaultBackground();
    }

    protected int getContentHeight()
    {
        return getSize() * 35 + 1;
    }

    protected void drawSlot(int i, int j, int k, int l, Tessellator tessellator)
    {
        ModContainer modcontainer = (ModContainer)mods.get(i);
        parent.getFontRenderer().drawString(parent.getFontRenderer().func_50107_a(modcontainer.getName(), listWidth - 11), left + 3, k + 2, 0xffffff);
        parent.getFontRenderer().drawString(parent.getFontRenderer().func_50107_a(modcontainer.getVersion(), listWidth - 11), left + 3, k + 12, 0xcccccc);
        parent.getFontRenderer().drawString(parent.getFontRenderer().func_50107_a(modcontainer.getMetadata() == null ? "Metadata not found" : modcontainer.getMetadata().getChildModCountString(), listWidth - 9), left + 3, k + 22, 0xcccccc);
    }
}
