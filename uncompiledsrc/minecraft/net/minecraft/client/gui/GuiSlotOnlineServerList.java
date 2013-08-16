package net.minecraft.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.mco.McoServer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
class GuiSlotOnlineServerList extends GuiScreenSelectLocation
{
    final GuiScreenOnlineServers field_96294_a;

    public GuiSlotOnlineServerList(GuiScreenOnlineServers par1)
    {
        super(GuiScreenOnlineServers.func_98075_b(par1), par1.width, par1.height, 32, par1.height - 64, 36);
        this.field_96294_a = par1;
    }

    /**
     * Gets the size of the current slot list.
     */
    protected int getSize()
    {
        return GuiScreenOnlineServers.func_98094_c(this.field_96294_a).size() + 1;
    }

    /**
     * the element in the slot that was clicked, boolean for wether it was double clicked or not
     */
    protected void elementClicked(int par1, boolean par2)
    {
        if (par1 < GuiScreenOnlineServers.func_98094_c(this.field_96294_a).size())
        {
            GuiScreenOnlineServers.func_98089_b(this.field_96294_a, par1);
            McoServer mcoserver = (McoServer)GuiScreenOnlineServers.func_98094_c(this.field_96294_a).get(GuiScreenOnlineServers.func_98072_d(this.field_96294_a));
            GuiScreenOnlineServers.func_96161_f(this.field_96294_a).enabled = GuiScreenOnlineServers.func_98076_f(this.field_96294_a).session.username.equals(mcoserver.field_96405_e);
            GuiScreenOnlineServers.func_98092_g(this.field_96294_a).enabled = mcoserver.field_96404_d.equals("OPEN") && !mcoserver.field_98166_h;

            if (par2 && GuiScreenOnlineServers.func_98092_g(this.field_96294_a).enabled)
            {
                GuiScreenOnlineServers.func_98078_c(this.field_96294_a, GuiScreenOnlineServers.func_98072_d(this.field_96294_a));
            }
        }
    }

    /**
     * returns true if the element passed in is currently selected
     */
    protected boolean isSelected(int par1)
    {
        return par1 == GuiScreenOnlineServers.func_98072_d(this.field_96294_a);
    }

    protected boolean func_104086_b(int par1)
    {
        return par1 < GuiScreenOnlineServers.func_98094_c(this.field_96294_a).size() && ((McoServer)GuiScreenOnlineServers.func_98094_c(this.field_96294_a).get(par1)).field_96405_e.toLowerCase().equals(GuiScreenOnlineServers.func_98091_h(this.field_96294_a).session.username);
    }

    /**
     * return the height of the content being scrolled
     */
    protected int getContentHeight()
    {
        return this.getSize() * 36;
    }

    protected void drawBackground()
    {
        this.field_96294_a.drawDefaultBackground();
    }

    protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
    {
        if (par1 < GuiScreenOnlineServers.func_98094_c(this.field_96294_a).size())
        {
            this.func_96292_b(par1, par2, par3, par4, par5Tessellator);
        }
    }

    private void func_96292_b(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
    {
        McoServer mcoserver = (McoServer)GuiScreenOnlineServers.func_98094_c(this.field_96294_a).get(par1);
        this.field_96294_a.drawString(GuiScreenOnlineServers.func_104038_i(this.field_96294_a), mcoserver.func_96398_b(), par2 + 2, par3 + 1, 16777215);
        short short1 = 207;
        byte b0 = 1;

        if (mcoserver.field_98166_h)
        {
            GuiScreenOnlineServers.func_101012_b(this.field_96294_a, par2 + short1, par3 + b0, this.field_104094_d, this.field_104095_e);
        }
        else if (mcoserver.field_96404_d.equals("CLOSED"))
        {
            GuiScreenOnlineServers.func_101009_c(this.field_96294_a, par2 + short1, par3 + b0, this.field_104094_d, this.field_104095_e);
        }
        else if (mcoserver.field_96405_e.equals(GuiScreenOnlineServers.func_104032_j(this.field_96294_a).session.username) && mcoserver.field_104063_i < 7)
        {
            this.func_96293_a(par1, par2 - 14, par3, mcoserver);
            GuiScreenOnlineServers.func_104030_a(this.field_96294_a, par2 + short1, par3 + b0, this.field_104094_d, this.field_104095_e, mcoserver.field_104063_i);
        }
        else if (mcoserver.field_96404_d.equals("OPEN"))
        {
            GuiScreenOnlineServers.func_104031_c(this.field_96294_a, par2 + short1, par3 + b0, this.field_104094_d, this.field_104095_e);
            this.func_96293_a(par1, par2 - 14, par3, mcoserver);
        }

        this.field_96294_a.drawString(GuiScreenOnlineServers.func_98084_i(this.field_96294_a), mcoserver.func_96397_a(), par2 + 2, par3 + 12, 7105644);
        this.field_96294_a.drawString(GuiScreenOnlineServers.func_101005_j(this.field_96294_a), mcoserver.field_96405_e, par2 + 2, par3 + 12 + 11, 5000268);
    }

    private void func_96293_a(int par1, int par2, int par3, McoServer par4McoServer)
    {
        if (par4McoServer.field_96403_g != null)
        {
            synchronized (GuiScreenOnlineServers.func_101007_h())
            {
                if (GuiScreenOnlineServers.func_101010_i() < 5 && (!par4McoServer.field_96411_l || par4McoServer.field_102022_m))
                {
                    (new ThreadConnectToOnlineServer(this, par4McoServer)).start();
                }
            }

            boolean flag = par4McoServer.field_96415_h > 61;
            boolean flag1 = par4McoServer.field_96415_h < 61;
            boolean flag2 = flag || flag1;

            if (par4McoServer.field_96414_k != null)
            {
                this.field_96294_a.drawString(GuiScreenOnlineServers.func_98079_k(this.field_96294_a), par4McoServer.field_96414_k, par2 + 215 - GuiScreenOnlineServers.func_98087_l(this.field_96294_a).getStringWidth(par4McoServer.field_96414_k), par3 + 1, 8421504);
            }

            if (flag2)
            {
                String s = EnumChatFormatting.DARK_RED + par4McoServer.field_96413_j;
                this.field_96294_a.drawString(GuiScreenOnlineServers.func_98074_m(this.field_96294_a), s, par2 + 200 - GuiScreenOnlineServers.func_101000_n(this.field_96294_a).getStringWidth(s), par3 + 1, 8421504);
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GuiScreenOnlineServers.func_101004_o(this.field_96294_a).renderEngine.bindTexture("/gui/icons.png");
            byte b0 = 0;
            boolean flag3 = false;
            String s1 = null;

            if (flag2)
            {
                s1 = flag ? "Client out of date!" : "Server out of date!";
                byte b1 = 5;
                this.field_96294_a.drawTexturedModalRect(par2 + 205, par3, b0 * 10, 176 + b1 * 8, 10, 8);
            }

            byte b2 = 4;

            if (this.field_104094_d >= par2 + 205 - b2 && this.field_104095_e >= par3 - b2 && this.field_104094_d <= par2 + 205 + 10 + b2 && this.field_104095_e <= par3 + 8 + b2)
            {
                GuiScreenOnlineServers.func_101011_a(this.field_96294_a, s1);
            }
        }
    }
}
