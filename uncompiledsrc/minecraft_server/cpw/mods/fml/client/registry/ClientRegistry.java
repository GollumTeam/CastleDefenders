package cpw.mods.fml.client.registry;

import cpw.mods.fml.common.registry.GameRegistry;

public class ClientRegistry
{
    public static void registerTileEntity(Class var0, String var1, bje var2)
    {
        GameRegistry.registerTileEntity(var0, var1);
        bindTileEntitySpecialRenderer(var0, var2);
    }

    public static void bindTileEntitySpecialRenderer(Class var0, bje var1)
    {
        bjd.a.m.put(var0, var1);
        var1.a(bjd.a);
    }
}
