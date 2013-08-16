package cpw.mods.fml.client;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderState$ModState;
import cpw.mods.fml.common.ModContainer;
import java.util.ArrayList;
import net.minecraft.client.renderer.Tessellator;

public class GuiSlotModList extends GuiScrollingList
{
    private GuiModList parent;
    private ArrayList mods;

    public GuiSlotModList(GuiModList var1, ArrayList var2, int var3)
    {
        super(var1.getMinecraftInstance(), var3, var1.height, 32, var1.height - 65 + 4, 10, 35);
        this.parent = var1;
        this.mods = var2;
    }

    protected int getSize()
    {
        return this.mods.size();
    }

    protected void elementClicked(int var1, boolean var2)
    {
        this.parent.selectModIndex(var1);
    }

    protected boolean isSelected(int var1)
    {
        return this.parent.modIndexSelected(var1);
    }

    protected void drawBackground()
    {
        this.parent.drawDefaultBackground();
    }

    protected int getContentHeight()
    {
        return this.getSize() * 35 + 1;
    }

    protected void drawSlot(int var1, int var2, int var3, int var4, Tessellator var5)
    {
        ModContainer var6 = (ModContainer)this.mods.get(var1);

        if (Loader.instance().getModState(var6) == LoaderState$ModState.DISABLED)
        {
            this.parent.getFontRenderer().drawString(this.parent.getFontRenderer().trimStringToWidth(var6.getName(), this.listWidth - 10), this.left + 3, var3 + 2, 16720418);
            this.parent.getFontRenderer().drawString(this.parent.getFontRenderer().trimStringToWidth(var6.getDisplayVersion(), this.listWidth - 10), this.left + 3, var3 + 12, 16720418);
            this.parent.getFontRenderer().drawString(this.parent.getFontRenderer().trimStringToWidth("DISABLED", this.listWidth - 10), this.left + 3, var3 + 22, 16720418);
        }
        else
        {
            this.parent.getFontRenderer().drawString(this.parent.getFontRenderer().trimStringToWidth(var6.getName(), this.listWidth - 10), this.left + 3, var3 + 2, 16777215);
            this.parent.getFontRenderer().drawString(this.parent.getFontRenderer().trimStringToWidth(var6.getDisplayVersion(), this.listWidth - 10), this.left + 3, var3 + 12, 13421772);
            this.parent.getFontRenderer().drawString(this.parent.getFontRenderer().trimStringToWidth(var6.getMetadata() != null ? var6.getMetadata().getChildModCountString() : "Metadata not found", this.listWidth - 10), this.left + 3, var3 + 22, 13421772);
        }
    }
}
