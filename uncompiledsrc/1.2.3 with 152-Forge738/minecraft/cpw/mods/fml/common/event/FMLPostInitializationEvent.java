package cpw.mods.fml.common.event;

import com.google.common.base.Throwables;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderState$ModState;

public class FMLPostInitializationEvent extends FMLStateEvent
{
    public FMLPostInitializationEvent(Object ... var1)
    {
        super(var1);
    }

    public LoaderState$ModState getModState()
    {
        return LoaderState$ModState.POSTINITIALIZED;
    }

    public Object buildSoftDependProxy(String var1, String var2)
    {
        if (Loader.isModLoaded(var1))
        {
            try
            {
                Class var3 = Class.forName(var2, true, Loader.instance().getModClassLoader());
                return var3.newInstance();
            }
            catch (Exception var4)
            {
                Throwables.propagateIfPossible(var4);
                return null;
            }
        }
        else
        {
            return null;
        }
    }
}
