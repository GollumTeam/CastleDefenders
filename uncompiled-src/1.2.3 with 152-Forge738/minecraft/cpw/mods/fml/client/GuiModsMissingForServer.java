package cpw.mods.fml.client;

import cpw.mods.fml.common.network.ModMissingPacket;
import cpw.mods.fml.common.versioning.ArtifactVersion;
import java.util.Iterator;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSmallButton;
import net.minecraft.util.StringTranslate;

public class GuiModsMissingForServer extends GuiScreen
{
    private ModMissingPacket modsMissing;

    public GuiModsMissingForServer(ModMissingPacket var1)
    {
        this.modsMissing = var1;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        StringTranslate var1 = StringTranslate.getInstance();
        this.buttonList.add(new GuiSmallButton(1, this.width / 2 - 75, this.height - 38, var1.translateKey("gui.done")));
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton var1)
    {
        if (var1.enabled && var1.id == 1)
        {
            FMLClientHandler.instance().getClient().displayGuiScreen((GuiScreen)null);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int var1, int var2, float var3)
    {
        this.drawDefaultBackground();
        int var4 = Math.max(85 - this.modsMissing.getModList().size() * 10, 10);
        this.drawCenteredString(this.fontRenderer, "Forge Mod Loader could not connect to this server", this.width / 2, var4, 16777215);
        var4 += 10;
        this.drawCenteredString(this.fontRenderer, "The mods and versions listed below could not be found", this.width / 2, var4, 16777215);
        var4 += 10;
        this.drawCenteredString(this.fontRenderer, "They are required to play on this server", this.width / 2, var4, 16777215);
        var4 += 5;
        Iterator var5 = this.modsMissing.getModList().iterator();

        while (var5.hasNext())
        {
            ArtifactVersion var6 = (ArtifactVersion)var5.next();
            var4 += 10;
            this.drawCenteredString(this.fontRenderer, String.format("%s : %s", new Object[] {var6.getLabel(), var6.getRangeString()}), this.width / 2, var4, 15658734);
        }

        super.drawScreen(var1, var2, var3);
    }
}
