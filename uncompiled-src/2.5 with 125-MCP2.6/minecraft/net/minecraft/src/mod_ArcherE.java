package net.minecraft.src;

import java.util.Map;

public class mod_ArcherE extends BaseMod
{
    public String getVersion()
    {
        return "1.0.0";
    }

    public mod_ArcherE()
    {
        ModLoader.registerEntityID(net.minecraft.src.EntityArcherE.class, "ArcherE", ModLoader.getUniqueEntityId());
        ModLoader.addSpawn(net.minecraft.src.EntityArcherE.class, 8, 0, 0, EnumCreatureType.creature);
    }

    public void addRenderer(Map map)
    {
        map.put(net.minecraft.src.EntityArcherE.class, new RenderBiped(new ModelBiped(), 0.5F));
    }

    public void load()
    {
    }
}
