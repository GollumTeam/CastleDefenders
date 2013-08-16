package cpw.mods.fml.common.event;

import cpw.mods.fml.common.LoaderState$ModState;

public class FMLServerStoppedEvent extends FMLStateEvent
{
    public FMLServerStoppedEvent(Object ... var1)
    {
        super(var1);
    }

    public LoaderState$ModState getModState()
    {
        return LoaderState$ModState.AVAILABLE;
    }
}
