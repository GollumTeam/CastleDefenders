package cpw.mods.fml.client;

import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.toposort.ModSortingException;
import cpw.mods.fml.common.toposort.ModSortingException$SortingExceptionData;
import java.util.Iterator;
import net.minecraft.client.gui.GuiScreen;

public class GuiSortingProblem extends GuiScreen
{
    private ModSortingException modSorting;
    private ModSortingException$SortingExceptionData failedList;

    public GuiSortingProblem(ModSortingException var1)
    {
        this.modSorting = var1;
        this.failedList = var1.getExceptionData();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int var1, int var2, float var3)
    {
        this.drawDefaultBackground();
        int var4 = Math.max(85 - (this.failedList.getVisitedNodes().size() + 3) * 10, 10);
        this.drawCenteredString(this.fontRenderer, "Forge Mod Loader has found a problem with your minecraft installation", this.width / 2, var4, 16777215);
        var4 += 10;
        this.drawCenteredString(this.fontRenderer, "A mod sorting cycle was detected and loading cannot continue", this.width / 2, var4, 16777215);
        var4 += 10;
        this.drawCenteredString(this.fontRenderer, String.format("The first mod in the cycle is %s", new Object[] {this.failedList.getFirstBadNode()}), this.width / 2, var4, 16777215);
        var4 += 10;
        this.drawCenteredString(this.fontRenderer, "The remainder of the cycle involves these mods", this.width / 2, var4, 16777215);
        var4 += 5;
        Iterator var5 = this.failedList.getVisitedNodes().iterator();

        while (var5.hasNext())
        {
            ModContainer var6 = (ModContainer)var5.next();
            var4 += 10;
            this.drawCenteredString(this.fontRenderer, String.format("%s : before: %s, after: %s", new Object[] {var6.toString(), var6.getDependants(), var6.getDependencies()}), this.width / 2, var4, 15658734);
        }

        var4 += 20;
        this.drawCenteredString(this.fontRenderer, "The file \'ForgeModLoader-client-0.log\' contains more information", this.width / 2, var4, 16777215);
    }
}
