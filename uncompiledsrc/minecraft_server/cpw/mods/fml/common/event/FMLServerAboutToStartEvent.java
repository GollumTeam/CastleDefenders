package cpw.mods.fml.common.event;

import cpw.mods.fml.common.LoaderState$ModState;
import net.minecraft.server.MinecraftServer;

public class FMLServerAboutToStartEvent extends FMLStateEvent
{
    private MinecraftServer server;

    public FMLServerAboutToStartEvent(Object ... var1)
    {
        super(var1);
        this.server = (MinecraftServer)var1[0];
    }

    public LoaderState$ModState getModState()
    {
        return LoaderState$ModState.AVAILABLE;
    }

    public MinecraftServer getServer()
    {
        return this.server;
    }
}
