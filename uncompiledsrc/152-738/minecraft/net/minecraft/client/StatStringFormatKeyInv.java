package net.minecraft.client;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.stats.IStatStringFormat;

public class StatStringFormatKeyInv implements IStatStringFormat
{
    /** Minecraft instance */
    final Minecraft mc;

    public StatStringFormatKeyInv(Minecraft par1Minecraft)
    {
        this.mc = par1Minecraft;
    }

    /**
     * Formats the strings based on 'IStatStringFormat' interface.
     */
    public String formatString(String par1Str)
    {
        try
        {
            return String.format(par1Str, new Object[] {GameSettings.getKeyDisplayString(this.mc.gameSettings.keyBindInventory.keyCode)});
        }
        catch (Exception var3)
        {
            return "Error: " + var3.getLocalizedMessage();
        }
    }
}
