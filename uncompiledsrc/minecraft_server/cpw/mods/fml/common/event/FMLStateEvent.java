package cpw.mods.fml.common.event;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.LoaderState$ModState;
import cpw.mods.fml.relauncher.Side;

public abstract class FMLStateEvent extends FMLEvent
{
    public FMLStateEvent(Object ... var1) {}

    public abstract LoaderState$ModState getModState();

    public Side getSide()
    {
        return FMLCommonHandler.instance().getSide();
    }
}
