package cpw.mods.fml.client;

import cpw.mods.fml.common.IFMLHandledException;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiErrorScreen;

@SideOnly(Side.CLIENT)
public abstract class CustomModLoadingErrorDisplayException extends RuntimeException implements IFMLHandledException
{
    public abstract void initGui(GuiErrorScreen var1, awv var2);

    public abstract void drawScreen(GuiErrorScreen var1, awv var2, int var3, int var4, float var5);
}
