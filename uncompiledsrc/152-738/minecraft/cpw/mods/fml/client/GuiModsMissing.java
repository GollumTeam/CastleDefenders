package cpw.mods.fml.client;

import cpw.mods.fml.common.MissingModsException;
import cpw.mods.fml.common.versioning.ArtifactVersion;
import cpw.mods.fml.common.versioning.DefaultArtifactVersion;
import java.util.Iterator;
import net.minecraft.client.gui.GuiErrorScreen;

public class GuiModsMissing extends GuiErrorScreen
{
    private MissingModsException modsMissing;

    public GuiModsMissing(MissingModsException var1)
    {
        this.modsMissing = var1;
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
        int var4 = Math.max(85 - this.modsMissing.missingMods.size() * 10, 10);
        this.drawCenteredString(this.fontRenderer, "Forge Mod Loader has found a problem with your minecraft installation", this.width / 2, var4, 16777215);
        var4 += 10;
        this.drawCenteredString(this.fontRenderer, "The mods and versions listed below could not be found", this.width / 2, var4, 16777215);
        var4 += 5;
        Iterator var5 = this.modsMissing.missingMods.iterator();

        while (var5.hasNext())
        {
            ArtifactVersion var6 = (ArtifactVersion)var5.next();
            var4 += 10;

            if (var6 instanceof DefaultArtifactVersion)
            {
                DefaultArtifactVersion var7 = (DefaultArtifactVersion)var6;

                if (var7.getRange() != null && var7.getRange().isUnboundedAbove())
                {
                    this.drawCenteredString(this.fontRenderer, String.format("%s : minimum version required is %s", new Object[] {var6.getLabel(), var7.getRange().getLowerBoundString()}), this.width / 2, var4, 15658734);
                    continue;
                }
            }

            this.drawCenteredString(this.fontRenderer, String.format("%s : %s", new Object[] {var6.getLabel(), var6.getRangeString()}), this.width / 2, var4, 15658734);
        }

        var4 += 20;
        this.drawCenteredString(this.fontRenderer, "The file \'ForgeModLoader-client-0.log\' contains more information", this.width / 2, var4, 16777215);
    }
}
