package cpw.mods.fml.client;

import net.minecraft.client.gui.GuiErrorScreen;

public class GuiCustomModLoadingErrorScreen extends GuiErrorScreen
{
    private CustomModLoadingErrorDisplayException customException;

    public GuiCustomModLoadingErrorScreen(CustomModLoadingErrorDisplayException var1)
    {
        this.customException = var1;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.customException.initGui(this, this.fontRenderer);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int var1, int var2, float var3)
    {
        this.drawDefaultBackground();
        this.customException.drawScreen(this, this.fontRenderer, var1, var2, var3);
    }
}
