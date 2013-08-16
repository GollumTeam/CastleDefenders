package cpw.mods.fml.client;

import cpw.mods.fml.common.DuplicateModsFoundException;
import cpw.mods.fml.common.ModContainer;
import java.io.File;
import java.util.Iterator;
import java.util.Map.Entry;
import net.minecraft.client.gui.GuiErrorScreen;

public class GuiDupesFound extends GuiErrorScreen
{
    private DuplicateModsFoundException dupes;

    public GuiDupesFound(DuplicateModsFoundException var1)
    {
        this.dupes = var1;
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
        int var4 = Math.max(85 - this.dupes.dupes.size() * 10, 10);
        this.drawCenteredString(this.fontRenderer, "Forge Mod Loader has found a problem with your minecraft installation", this.width / 2, var4, 16777215);
        var4 += 10;
        this.drawCenteredString(this.fontRenderer, "You have mod sources that are duplicate within your system", this.width / 2, var4, 16777215);
        var4 += 10;
        this.drawCenteredString(this.fontRenderer, "Mod Id : File name", this.width / 2, var4, 16777215);
        var4 += 5;
        Iterator var5 = this.dupes.dupes.entries().iterator();

        while (var5.hasNext())
        {
            Entry var6 = (Entry)var5.next();
            var4 += 10;
            this.drawCenteredString(this.fontRenderer, String.format("%s : %s", new Object[] {((ModContainer)var6.getKey()).getModId(), ((File)var6.getValue()).getName()}), this.width / 2, var4, 15658734);
        }
    }
}
