package cpw.mods.fml.common;

import java.util.EnumSet;

public interface ITickHandler
{
    public abstract void tickStart(EnumSet enumset, Object aobj[]);

    public abstract void tickEnd(EnumSet enumset, Object aobj[]);

    public abstract EnumSet ticks();

    public abstract String getLabel();
}
