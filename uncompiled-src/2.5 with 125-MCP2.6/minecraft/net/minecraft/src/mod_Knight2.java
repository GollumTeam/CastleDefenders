package net.minecraft.src;

import java.util.Map;

public class mod_Knight2 extends BaseMod
{
    public String getVersion()
    {
        return "1.2.3";
    }

    public mod_Knight2()
    {
        ModLoader.registerEntityID(net.minecraft.src.EntityKnight2.class, "Knight2", ModLoader.getUniqueEntityId());
        ModLoader.addSpawn(net.minecraft.src.EntityKnight2.class, 8, 0, 1, EnumCreatureType.creature);
    }

    public void addRenderer(Map map)
    {
        map.put(net.minecraft.src.EntityKnight2.class, new RenderBiped(new ModelBiped(), 0.5F));
    }

    public void load()
    {
    }
}
