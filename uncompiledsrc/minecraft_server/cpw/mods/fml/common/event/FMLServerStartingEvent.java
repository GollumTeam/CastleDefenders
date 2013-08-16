package cpw.mods.fml.common.event;

import cpw.mods.fml.common.LoaderState$ModState;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommand;
import net.minecraft.server.MinecraftServer;

public class FMLServerStartingEvent extends FMLStateEvent
{
    private MinecraftServer server;

    public FMLServerStartingEvent(Object ... var1)
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

    public void registerServerCommand(ICommand var1)
    {
        CommandHandler var2 = (CommandHandler)this.getServer().getCommandManager();
        var2.registerCommand(var1);
    }
}
