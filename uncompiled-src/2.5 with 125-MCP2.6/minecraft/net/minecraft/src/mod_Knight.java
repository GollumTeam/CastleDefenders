package net.minecraft.src;

import java.util.Map;

public class mod_Knight extends BaseMod
{
    public String getVersion()
    {
        return "1.0.0";
    }

    public mod_Knight()
    {
        ModLoader.registerEntityID(net.minecraft.src.EntityDefender.class, "Defender", ModLoader.getUniqueEntityId());
        ModLoader.registerEntityID(net.minecraft.src.EntityKnight.class, "Knight", ModLoader.getUniqueEntityId());
        ModLoader.addSpawn(net.minecraft.src.EntityKnight.class, 8, 0, 0, EnumCreatureType.creature);
    }

    public void addRenderer(Map map)
    {
        map.put(net.minecraft.src.EntityKnight.class, new RenderBiped(new ModelBiped(), 0.5F));
    }

    public void load()
    {
    }
}
