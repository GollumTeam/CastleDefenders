package net.minecraft.src;

import java.util.Map;

public class mod_KnightE extends BaseMod
{
    public String getVersion()
    {
        return "1.0.0";
    }

    public mod_KnightE()
    {
        ModLoader.registerEntityID(net.minecraft.src.EntityKnightE.class, "KnightE", ModLoader.getUniqueEntityId());
        ModLoader.addSpawn(net.minecraft.src.EntityKnightE.class, 8, 0, 0, EnumCreatureType.creature);
    }

    public void addRenderer(Map map)
    {
        map.put(net.minecraft.src.EntityKnightE.class, new RenderBiped(new ModelBiped(), 0.5F));
    }

    public void load()
    {
    }
}
