package cpw.mods.fml.common;

import java.util.EnumSet;

public enum TickType
{
    WORLD,
    RENDER,
    WORLDLOAD,
    CLIENT,
    PLAYER,
    SERVER;

    public EnumSet partnerTicks()
    {
        return this == CLIENT ? EnumSet.of(RENDER) : (this == RENDER ? EnumSet.of(CLIENT) : EnumSet.noneOf(TickType.class));
    }
}
