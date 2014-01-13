package cpw.mods.fml.common;

import java.util.EnumSet;

public enum TickType
{
    WORLD,
    RENDER,
    GUI,
    WORLDGUI,
    WORLDLOAD,
    GUILOAD,
    GAME;

    public EnumSet partnerTicks()
    {
        if (this == GAME)
        {
            return EnumSet.of(RENDER, WORLDLOAD);
        }

        if (this == RENDER)
        {
            return EnumSet.of(GAME, WORLDLOAD);
        }

        if (this == GUI)
        {
            return EnumSet.of(WORLDGUI, GUILOAD);
        }

        if (this == WORLDGUI)
        {
            return EnumSet.of(GUI, GUILOAD);
        }

        if (this == WORLDLOAD)
        {
            return EnumSet.of(GAME, RENDER);
        }

        if (this == GUILOAD)
        {
            return EnumSet.of(GUI, WORLDGUI);
        }
        else
        {
            return null;
        }
    }
}
