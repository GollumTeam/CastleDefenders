package cpw.mods.fml.common.event;

import cpw.mods.fml.common.LoaderState$ModState;

public class FMLInitializationEvent extends FMLStateEvent
{
    public FMLInitializationEvent(Object ... var1)
    {
        super(var1);
    }

    public LoaderState$ModState getModState()
    {
        return LoaderState$ModState.INITIALIZED;
    }
}
