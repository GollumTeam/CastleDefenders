package cpw.mods.fml.common.event;

import cpw.mods.fml.common.LoaderState$ModState;

public class FMLServerStoppingEvent extends FMLStateEvent
{
    public FMLServerStoppingEvent(Object ... var1)
    {
        super(var1);
    }

    public LoaderState$ModState getModState()
    {
        return LoaderState$ModState.AVAILABLE;
    }
}
