package net.minecraft.src;

import forge.*;

public class mod_MinecraftForge extends NetworkMod
{
    public static boolean SPAWNER_ALLOW_ON_INVERTED = true;

    public mod_MinecraftForge()
    {
    }

    public String getVersion()
    {
        return String.format("%d.%d.%d.%d", new Object[]
                {
                    Integer.valueOf(3), Integer.valueOf(3), Integer.valueOf(7), Integer.valueOf(135)
                });
    }

    public void load()
    {
        MinecraftForge.getDungeonLootTries();
        MinecraftForgeClient.init();
    }
}
