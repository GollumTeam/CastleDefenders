package cpw.mods.fml.common;

import java.util.EnumSet;

public class SingleIntervalHandler implements IScheduledTickHandler
{
    private ITickHandler wrapped;

    public SingleIntervalHandler(ITickHandler var1)
    {
        this.wrapped = var1;
    }

    public void tickStart(EnumSet var1, Object ... var2)
    {
        this.wrapped.tickStart(var1, var2);
    }

    public void tickEnd(EnumSet var1, Object ... var2)
    {
        this.wrapped.tickEnd(var1, var2);
    }

    public EnumSet ticks()
    {
        return this.wrapped.ticks();
    }

    public String getLabel()
    {
        return this.wrapped.getLabel();
    }

    public int nextTickSpacing()
    {
        return 1;
    }
}
