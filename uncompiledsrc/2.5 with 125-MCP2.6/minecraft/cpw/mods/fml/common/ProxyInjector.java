package cpw.mods.fml.common;

import java.lang.reflect.Field;
import java.util.logging.Logger;

public class ProxyInjector
{
    private String clientName;
    private String serverName;
    private String bukkitName;
    private Field target;

    public ProxyInjector(String s, String s1, String s2, Field field)
    {
        clientName = s;
        serverName = s1;
        bukkitName = s2;
        target = field;
    }

    public boolean isValidFor(Side side)
    {
        if (side == Side.CLIENT)
        {
            return !clientName.isEmpty();
        }

        if (side == Side.SERVER)
        {
            return !serverName.isEmpty();
        }

        if (side == Side.BUKKIT)
        {
            return bukkitName.isEmpty();
        }
        else
        {
            return false;
        }
    }

    public void inject(ModContainer modcontainer, Side side)
    {
        String s = side != Side.CLIENT ? serverName : clientName;

        try
        {
            Object obj = Class.forName(s, false, Loader.instance().getModClassLoader()).newInstance();

            if (target.getType().isAssignableFrom(obj.getClass()))
            {
                target.set(modcontainer.getMod(), obj);
            }
            else
            {
                FMLCommonHandler.instance().getFMLLogger().severe(String.format("Attempted to load a proxy type %s into %s, but the types don't match", new Object[]
                        {
                            s, target.getName()
                        }));
                throw new LoaderException();
            }
        }
        catch (Exception exception)
        {
            FMLCommonHandler.instance().getFMLLogger().severe(String.format("An error occured trying to load a proxy type %s into %s", new Object[]
                    {
                        s, target.getName()
                    }));
            FMLCommonHandler.instance().getFMLLogger().throwing("ProxyInjector", "inject", exception);
            throw new LoaderException(exception);
        }
    }
}
