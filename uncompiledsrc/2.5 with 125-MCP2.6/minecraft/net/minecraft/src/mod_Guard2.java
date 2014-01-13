package net.minecraft.src;

import java.util.Map;

public class mod_Guard2 extends BaseMod
{
    public String getVersion()
    {
        return "1.0.0";
    }

    public mod_Guard2()
    {
        ModLoader.registerEntityID(net.minecraft.src.EntityGuard2.class, "Guard2", ModLoader.getUniqueEntityId());
        ModLoader.addSpawn(net.minecraft.src.EntityGuard2.class, 8, 0, 0, EnumCreatureType.creature);
    }

    public void addRenderer(Map map)
    {
        map.put(net.minecraft.src.EntityGuard2.class, new RenderBiped(new ModelBiped(), 0.5F));
    }

    public void load()
    {
    }
}
