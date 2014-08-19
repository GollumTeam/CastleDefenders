package cpw.mods.fml.client.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class ClientRegistry
{
    public static void registerTileEntity(Class var0, String var1, TileEntitySpecialRenderer var2)
    {
        GameRegistry.registerTileEntity(var0, var1);
        bindTileEntitySpecialRenderer(var0, var2);
    }

    public static void bindTileEntitySpecialRenderer(Class var0, TileEntitySpecialRenderer var1)
    {
        TileEntityRenderer.instance.specialRendererMap.put(var0, var1);
        var1.setTileEntityRenderer(TileEntityRenderer.instance);
    }
}
