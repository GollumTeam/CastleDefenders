package net.minecraft.src;

import forge.GuiControlsScrollPanel;
import java.util.List;
import net.minecraft.client.Minecraft;

public class GuiControls extends GuiScreen
{
    /**
     * A reference to the screen object that created this. Used for navigating between screens.
     */
    private GuiScreen parentScreen;

    /** The title string that is displayed in the top-center of the screen. */
    protected String screenTitle;

    /** Reference to the GameSettings object. */
    private GameSettings options;

    /** The ID of the  button that has been pressed. */
    private int buttonId;
    private GuiControlsScrollPanel scrollPane;

    public GuiControls(GuiScreen par1GuiScreen, GameSettings par2GameSettings)
    {
        screenTitle = "Controls";
        buttonId = -1;
        parentScreen = par1GuiScreen;
        options = par2GameSettings;
    }

    private int func_20080_j()
    {
        return width / 2 - 155;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        scrollPane = new GuiControlsScrollPanel(this, options, mc);
        StringTranslate stringtranslate = StringTranslate.getInstance();
        int i = func_20080_j();
        controlList.add(new GuiButton(200, width / 2 - 100, height - 28, stringtranslate.translateKey("gui.done")));
        scrollPane.registerScrollButtons(controlList, 7, 8);
        screenTitle = stringtranslate.translateKey("controls.title");
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.id == 200)
        {
            mc.displayGuiScreen(parentScreen);
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        if (scrollPane.keyTyped(par1, par2))
        {
            super.keyTyped(par1, par2);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        drawDefaultBackground();
        scrollPane.drawScreen(par1, par2, par3);
        drawCenteredString(fontRenderer, screenTitle, width / 2, 4, 0xffffff);
        super.drawScreen(par1, par2, par3);
    }
}
