package net.minecraft.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.net.URL;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.HttpUtil;

@SideOnly(Side.CLIENT)
class RunnableTitleScreen implements Runnable
{
    final GuiMainMenu field_104058_d;

    RunnableTitleScreen(GuiMainMenu par1GuiMainMenu)
    {
        this.field_104058_d = par1GuiMainMenu;
    }

    public void run()
    {
        try
        {
            String s = HttpUtil.func_104145_a(new URL("http://assets.minecraft.net/1_6_has_been_released.flag"));

            if (s != null && s.length() > 0)
            {
                s = s.trim();

                synchronized (GuiMainMenu.func_104004_a(this.field_104058_d))
                {
                    GuiMainMenu.func_104005_a(this.field_104058_d, EnumChatFormatting.BOLD + "Notice!" + EnumChatFormatting.RESET + " Minecraft 1.6 is available for manual download.");
                    GuiMainMenu.func_104013_b(this.field_104058_d, s);
                    GuiMainMenu.func_104006_a(this.field_104058_d, GuiMainMenu.func_104022_c(this.field_104058_d).getStringWidth(GuiMainMenu.func_104023_b(this.field_104058_d)));
                    GuiMainMenu.func_104014_b(this.field_104058_d, GuiMainMenu.func_104007_d(this.field_104058_d).getStringWidth(GuiMainMenu.field_96138_a));
                    int i = Math.max(GuiMainMenu.func_104016_e(this.field_104058_d), GuiMainMenu.func_104015_f(this.field_104058_d));
                    GuiMainMenu.func_104008_c(this.field_104058_d, (this.field_104058_d.width - i) / 2);
                    GuiMainMenu.func_104009_d(this.field_104058_d, ((GuiButton)GuiMainMenu.func_104019_g(this.field_104058_d).get(0)).yPosition - 24);
                    GuiMainMenu.func_104011_e(this.field_104058_d, GuiMainMenu.func_104018_h(this.field_104058_d) + i);
                    GuiMainMenu.func_104012_f(this.field_104058_d, GuiMainMenu.func_104020_i(this.field_104058_d) + 24);
                }
            }
        }
        catch (java.io.FileNotFoundException e)
        {
            //NomNomNom, Don't print the error when the flag isn't found.
        }
        catch (Throwable throwable)
        {
            throwable.printStackTrace();
        }
    }
}
