package forge;

import java.io.PrintStream;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiControlsScrollPanel extends GuiSlot
{
    private GuiControls controls;
    private GameSettings options;
    private Minecraft mc;
    private String message[];
    private int _mouseX;
    private int _mouseY;
    private int selected;

    public GuiControlsScrollPanel(GuiControls guicontrols, GameSettings gamesettings, Minecraft minecraft)
    {
        super(minecraft, guicontrols.width, guicontrols.height, 16, (guicontrols.height - 32) + 4, 25);
        selected = -1;
        controls = guicontrols;
        options = gamesettings;
        mc = minecraft;
    }

    /**
     * Gets the size of the current slot list.
     */
    protected int getSize()
    {
        return options.keyBindings.length;
    }

    /**
     * the element in the slot that was clicked, boolean for wether it was double clicked or not
     */
    protected void elementClicked(int i, boolean flag)
    {
        if (!flag)
        {
            if (selected == -1)
            {
                selected = i;
            }
            else
            {
                options.setKeyBinding(selected, -100);
                selected = -1;
                KeyBinding.resetKeyBindingArrayAndHash();
            }
        }
    }

    /**
     * returns true if the element passed in is currently selected
     */
    protected boolean isSelected(int i)
    {
        return false;
    }

    protected void drawBackground()
    {
    }

    /**
     * draws the slot to the screen, pass in mouse's current x and y and partial ticks
     */
    public void drawScreen(int i, int j, float f)
    {
        _mouseX = i;
        _mouseY = j;

        if (selected != -1 && !Mouse.isButtonDown(0) && Mouse.getDWheel() == 0 && Mouse.next() && Mouse.getEventButtonState())
        {
            System.out.println(Mouse.getEventButton());
            options.setKeyBinding(selected, -100 + Mouse.getEventButton());
            selected = -1;
            KeyBinding.resetKeyBindingArrayAndHash();
        }

        super.drawScreen(i, j, f);
    }

    protected void drawSlot(int i, int j, int k, int l, Tessellator tessellator)
    {
        byte byte0 = 70;
        byte byte1 = 20;
        j -= 20;
        boolean flag = _mouseX >= j && _mouseY >= k && _mouseX < j + byte0 && _mouseY < k + byte1;
        byte byte2 = ((byte)(flag ? 2 : 1));
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/gui/gui.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        controls.drawTexturedModalRect(j, k, 0, 46 + byte2 * 20, byte0 / 2, byte1);
        controls.drawTexturedModalRect(j + byte0 / 2, k, 200 - byte0 / 2, 46 + byte2 * 20, byte0 / 2, byte1);
        controls.drawString(mc.fontRenderer, options.getKeyBindingDescription(i), j + byte0 + 4, k + 6, -1);
        boolean flag1 = false;
        int i1 = 0;

        do
        {
            if (i1 >= options.keyBindings.length)
            {
                break;
            }

            if (i1 != i && options.keyBindings[i1].keyCode == options.keyBindings[i].keyCode)
            {
                flag1 = true;
                break;
            }

            i1++;
        }
        while (true);

        String s = (new StringBuilder()).append(flag1 ? "\247c" : "").append(options.getOptionDisplayString(i)).toString();
        s = i != selected ? s : "\247f> \247e??? \247f<";
        controls.drawCenteredString(mc.fontRenderer, s, j + byte0 / 2, k + (byte1 - 8) / 2, -1);
    }

    public boolean keyTyped(char c, int i)
    {
        if (selected != -1)
        {
            options.setKeyBinding(selected, i);
            selected = -1;
            KeyBinding.resetKeyBindingArrayAndHash();
            return false;
        }
        else
        {
            return true;
        }
    }
}
