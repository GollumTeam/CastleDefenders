package net.minecraft.client.gui;

import net.minecraft.util.Icon;
import org.lwjgl.opengl.GL11;

public class Gui
{
    protected float zLevel = 0.0F;

    protected void drawHorizontalLine(int par1, int par2, int par3, int par4)
    {
        if (par2 < par1)
        {
            int var5 = par1;
            par1 = par2;
            par2 = var5;
        }

        drawRect(par1, par3, par2 + 1, par3 + 1, par4);
    }

    protected void drawVerticalLine(int par1, int par2, int par3, int par4)
    {
        if (par3 < par2)
        {
            int var5 = par2;
            par2 = par3;
            par3 = var5;
        }

        drawRect(par1, par2 + 1, par1 + 1, par3, par4);
    }

    /**
     * Draws a solid color rectangle with the specified coordinates and color. Args: x1, y1, x2, y2, color
     */
    public static void drawRect(int par0, int par1, int par2, int par3, int par4)
    {
        int var5;

        if (par0 < par2)
        {
            var5 = par0;
            par0 = par2;
            par2 = var5;
        }

        if (par1 < par3)
        {
            var5 = par1;
            par1 = par3;
            par3 = var5;
        }

        float var10 = (float)(par4 >> 24 & 255) / 255.0F;
        float var6 = (float)(par4 >> 16 & 255) / 255.0F;
        float var7 = (float)(par4 >> 8 & 255) / 255.0F;
        float var8 = (float)(par4 & 255) / 255.0F;
        bgd var9 = bgd.a;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(var6, var7, var8, var10);
        var9.b();
        var9.a((double)par0, (double)par3, 0.0D);
        var9.a((double)par2, (double)par3, 0.0D);
        var9.a((double)par2, (double)par1, 0.0D);
        var9.a((double)par0, (double)par1, 0.0D);
        var9.a();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }

    /**
     * Draws a rectangle with a vertical gradient between the specified colors.
     */
    protected void drawGradientRect(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        float var7 = (float)(par5 >> 24 & 255) / 255.0F;
        float var8 = (float)(par5 >> 16 & 255) / 255.0F;
        float var9 = (float)(par5 >> 8 & 255) / 255.0F;
        float var10 = (float)(par5 & 255) / 255.0F;
        float var11 = (float)(par6 >> 24 & 255) / 255.0F;
        float var12 = (float)(par6 >> 16 & 255) / 255.0F;
        float var13 = (float)(par6 >> 8 & 255) / 255.0F;
        float var14 = (float)(par6 & 255) / 255.0F;
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glBlendFunc(770, 771);
        GL11.glShadeModel(7425);
        bgd var15 = bgd.a;
        var15.b();
        var15.a(var8, var9, var10, var7);
        var15.a((double)par3, (double)par2, (double)this.zLevel);
        var15.a((double)par1, (double)par2, (double)this.zLevel);
        var15.a(var12, var13, var14, var11);
        var15.a((double)par1, (double)par4, (double)this.zLevel);
        var15.a((double)par3, (double)par4, (double)this.zLevel);
        var15.a();
        GL11.glShadeModel(7424);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glEnable(3553);
    }

    /**
     * Renders the specified text to the screen, center-aligned.
     */
    public void drawCenteredString(awv var1, String var2, int var3, int var4, int var5)
    {
        var1.a(var2, var3 - var1.a(var2) / 2, var4, var5);
    }

    /**
     * Renders the specified text to the screen.
     */
    public void drawString(awv var1, String var2, int var3, int var4, int var5)
    {
        var1.a(var2, var3, var4, var5);
    }

    /**
     * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
     */
    public void drawTexturedModalRect(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        float var7 = 0.00390625F;
        float var8 = 0.00390625F;
        bgd var9 = bgd.a;
        var9.b();
        var9.a((double)(par1 + 0), (double)(par2 + par6), (double)this.zLevel, (double)((float)(par3 + 0) * var7), (double)((float)(par4 + par6) * var8));
        var9.a((double)(par1 + par5), (double)(par2 + par6), (double)this.zLevel, (double)((float)(par3 + par5) * var7), (double)((float)(par4 + par6) * var8));
        var9.a((double)(par1 + par5), (double)(par2 + 0), (double)this.zLevel, (double)((float)(par3 + par5) * var7), (double)((float)(par4 + 0) * var8));
        var9.a((double)(par1 + 0), (double)(par2 + 0), (double)this.zLevel, (double)((float)(par3 + 0) * var7), (double)((float)(par4 + 0) * var8));
        var9.a();
    }

    public void drawTexturedModelRectFromIcon(int par1, int par2, Icon par3Icon, int par4, int par5)
    {
        bgd var6 = bgd.a;
        var6.b();
        var6.a((double)(par1 + 0), (double)(par2 + par5), (double)this.zLevel, (double)par3Icon.e(), (double)par3Icon.h());
        var6.a((double)(par1 + par4), (double)(par2 + par5), (double)this.zLevel, (double)par3Icon.f(), (double)par3Icon.h());
        var6.a((double)(par1 + par4), (double)(par2 + 0), (double)this.zLevel, (double)par3Icon.f(), (double)par3Icon.g());
        var6.a((double)(par1 + 0), (double)(par2 + 0), (double)this.zLevel, (double)par3Icon.e(), (double)par3Icon.g());
        var6.a();
    }
}
