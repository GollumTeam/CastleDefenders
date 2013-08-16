package cpw.mods.fml.client;

import com.google.common.collect.Lists;
import com.google.common.collect.MapDifference;
import com.google.common.collect.MapDifference.ValueDifference;
import cpw.mods.fml.common.registry.ItemData;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.util.StringTranslate;

public class GuiIdMismatchScreen extends GuiYesNo
{
    private List missingIds = Lists.newArrayList();
    private List mismatchedIds = Lists.newArrayList();
    private boolean allowContinue;

    public GuiIdMismatchScreen(MapDifference var1, boolean var2)
    {
        super((GuiScreen)null, "ID mismatch", "Should I continue?", 1);
        this.parentScreen = this;
        Iterator var3 = var1.entriesOnlyOnLeft().entrySet().iterator();
        Entry var4;

        while (var3.hasNext())
        {
            var4 = (Entry)var3.next();
            this.missingIds.add(String.format("ID %d from Mod %s is missing", new Object[] {Integer.valueOf(((ItemData)var4.getValue()).getItemId()), ((ItemData)var4.getValue()).getModId(), ((ItemData)var4.getValue()).getItemType()}));
        }

        var3 = var1.entriesDiffering().entrySet().iterator();

        while (var3.hasNext())
        {
            var4 = (Entry)var3.next();
            ItemData var5 = (ItemData)((ValueDifference)var4.getValue()).leftValue();
            ItemData var6 = (ItemData)((ValueDifference)var4.getValue()).rightValue();
            this.mismatchedIds.add(String.format("ID %d is mismatched between world and game", new Object[] {Integer.valueOf(var5.getItemId())}));
        }

        this.allowContinue = var2;
    }

    public void confirmClicked(boolean var1, int var2)
    {
        FMLClientHandler.instance().callbackIdDifferenceResponse(var1);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int var1, int var2, float var3)
    {
        this.drawDefaultBackground();

        if (!this.allowContinue && this.buttonList.size() == 2)
        {
            this.buttonList.remove(0);
        }

        int var4 = Math.max(85 - (this.missingIds.size() + this.mismatchedIds.size()) * 10, 30);
        this.drawCenteredString(this.fontRenderer, "Forge Mod Loader has found ID mismatches", this.width / 2, 10, 16777215);
        this.drawCenteredString(this.fontRenderer, "Complete details are in the log file", this.width / 2, 20, 16777215);
        int var5 = 20;
        Iterator var6 = this.missingIds.iterator();
        String var7;

        while (var6.hasNext())
        {
            var7 = (String)var6.next();
            this.drawCenteredString(this.fontRenderer, var7, this.width / 2, var4, 15658734);
            var4 += 10;
            --var5;

            if (var5 < 0 || var4 >= this.height - 30)
            {
                break;
            }
        }

        if (var5 > 0 && var4 < this.height - 30)
        {
            var6 = this.mismatchedIds.iterator();

            while (var6.hasNext())
            {
                var7 = (String)var6.next();
                this.drawCenteredString(this.fontRenderer, var7, this.width / 2, var4, 15658734);
                var4 += 10;
                --var5;

                if (var5 < 0 || var4 >= this.height - 30)
                {
                    break;
                }
            }
        }

        if (this.allowContinue)
        {
            this.drawCenteredString(this.fontRenderer, "Do you wish to continue loading?", this.width / 2, this.height - 30, 16777215);
        }
        else
        {
            this.drawCenteredString(this.fontRenderer, "You cannot connect to this server", this.width / 2, this.height - 30, 16777215);
        }

        for (int var8 = 0; var8 < this.buttonList.size(); ++var8)
        {
            GuiButton var9 = (GuiButton)this.buttonList.get(var8);
            var9.yPosition = this.height - 20;

            if (!this.allowContinue)
            {
                var9.xPosition = this.width / 2 - 75;
                var9.displayString = StringTranslate.getInstance().translateKey("gui.done");
            }

            var9.drawButton(this.mc, var1, var2);
        }
    }
}
