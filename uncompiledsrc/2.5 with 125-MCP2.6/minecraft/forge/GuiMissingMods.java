package forge;

import forge.packets.PacketMissingMods;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;

public class GuiMissingMods extends GuiScreen
{
    PacketMissingMods packet;

    public GuiMissingMods(PacketMissingMods packetmissingmods)
    {
        packet = packetmissingmods;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        controlList.clear();
        controlList.add(new GuiButton(0, width / 2 - 100, height - 60, StringTranslate.getInstance().translateKey("gui.toMenu")));
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton guibutton)
    {
        if (guibutton.id == 0)
        {
            mc.displayGuiScreen(new GuiMainMenu());
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int i, int j, float f)
    {
        drawDefaultBackground();
        drawCenteredString(fontRenderer, "The server requires you to have the following mods:", width / 2, 50, 0xffffff);
        int k = 0;
        String as[] = packet.Mods;
        int l = as.length;

        for (int i1 = 0; i1 < l; i1++)
        {
            String s = as[i1];
            drawCenteredString(fontRenderer, s, width / 2, 80 + k++ * 10, 0xffffff);
        }

        super.drawScreen(i, j, f);
    }
}
