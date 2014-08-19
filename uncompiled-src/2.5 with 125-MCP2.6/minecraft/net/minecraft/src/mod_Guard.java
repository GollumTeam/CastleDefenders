package net.minecraft.src;

import java.util.Map;

public class mod_Guard extends BaseMod
{
    public String getVersion()
    {
        return "1.0.0";
    }

    public mod_Guard()
    {
        ModLoader.registerEntityID(net.minecraft.src.EntityGuard.class, "Guard", ModLoader.getUniqueEntityId());
        ModLoader.addSpawn(net.minecraft.src.EntityGuard.class, 8, 0, 0, EnumCreatureType.creature);
    }

    public void addRenderer(Map map)
    {
        map.put(net.minecraft.src.EntityGuard.class, new RenderBiped(new ModelBiped(), 0.5F));
    }

    public void load()
    {
    }
}
