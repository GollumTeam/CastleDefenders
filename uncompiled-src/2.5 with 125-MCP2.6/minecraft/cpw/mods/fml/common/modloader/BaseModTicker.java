package cpw.mods.fml.common.modloader;

import cpw.mods.fml.common.*;
import java.util.EnumSet;
import java.util.Iterator;

public class BaseModTicker implements ITickHandler
{
    private BaseMod mod;
    private EnumSet ticks;

    BaseModTicker(BaseMod basemod)
    {
        mod = basemod;
        ticks = EnumSet.noneOf(cpw.mods.fml.common.TickType.class);
    }

    BaseModTicker(EnumSet enumset)
    {
        ticks = enumset;
    }

    public void tickStart(EnumSet enumset, Object aobj[])
    {
        tickBaseMod(enumset, false, aobj);
    }

    public void tickEnd(EnumSet enumset, Object aobj[])
    {
        tickBaseMod(ticks, true, aobj);
    }

    private void tickBaseMod(EnumSet enumset, boolean flag, Object aobj[])
    {
        Iterator iterator = enumset.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            TickType ticktype = (TickType)iterator.next();

            if (ticks.contains(ticktype))
            {
                boolean flag1 = mod.doTickInGame(ticktype, flag, FMLCommonHandler.instance().getMinecraftInstance(), aobj);

                if (!flag1)
                {
                    ticks.remove(ticktype);
                    ticks.removeAll(ticktype.partnerTicks());
                }
            }
        }
        while (true);
    }

    public EnumSet ticks()
    {
        return ticks;
    }

    public String getLabel()
    {
        return mod.getClass().getSimpleName();
    }

    public void setMod(BaseMod basemod)
    {
        mod = basemod;
    }
}
