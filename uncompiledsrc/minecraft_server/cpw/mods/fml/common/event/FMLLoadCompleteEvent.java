package cpw.mods.fml.common.event;

import cpw.mods.fml.common.LoaderState$ModState;

public class FMLLoadCompleteEvent extends FMLStateEvent
{
    public FMLLoadCompleteEvent(Object ... var1)
    {
        super(var1);
    }

    public LoaderState$ModState getModState()
    {
        return LoaderState$ModState.AVAILABLE;
    }
}
