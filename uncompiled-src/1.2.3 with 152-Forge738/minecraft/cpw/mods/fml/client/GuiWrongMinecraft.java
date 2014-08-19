package cpw.mods.fml.client;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.WrongMinecraftVersionException;
import net.minecraft.client.gui.GuiErrorScreen;

public class GuiWrongMinecraft extends GuiErrorScreen
{
    private WrongMinecraftVersionException wrongMC;

    public GuiWrongMinecraft(WrongMinecraftVersionException var1)
    {
        this.wrongMC = var1;
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
        byte var4 = 75;
        this.drawCenteredString(this.fontRenderer, "Forge Mod Loader has found a problem with your minecraft installation", this.width / 2, var4, 16777215);
        int var5 = var4 + 10;
        this.drawCenteredString(this.fontRenderer, String.format("The mod listed below does not want to run in Minecraft version %s", new Object[] {Loader.instance().getMinecraftModContainer().getVersion()}), this.width / 2, var5, 16777215);
        var5 += 5;
        var5 += 10;
        this.drawCenteredString(this.fontRenderer, String.format("%s (%s) wants Minecraft %s", new Object[] {this.wrongMC.mod.getName(), this.wrongMC.mod.getModId(), this.wrongMC.mod.acceptableMinecraftVersionRange()}), this.width / 2, var5, 15658734);
        var5 += 20;
        this.drawCenteredString(this.fontRenderer, "The file \'ForgeModLoader-client-0.log\' contains more information", this.width / 2, var5, 16777215);
    }
}
