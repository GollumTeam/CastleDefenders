package cpw.mods.fml.common.modloader;

import cpw.mods.fml.common.*;
import java.util.Map;

public interface BaseMod extends IWorldGenerator, IPickupNotifier, IDispenseHandler, ICraftingHandler, INetworkHandler, IConsoleHandler, IPlayerTracker
{
    public abstract void modsLoaded();

    public abstract void load();

    public abstract boolean doTickInGame(TickType ticktype, boolean flag, Object obj, Object aobj[]);

    public abstract String getName();

    public abstract String getPriorities();

    public abstract int addFuel(int i, int j);

    public abstract void onRenderHarvest(Map map);

    public abstract void onRegisterAnimations();

    public abstract String getVersion();

    public abstract void keyBindingEvent(Object obj);
}
