package net.minecraft.src;

import java.util.Map;

public class mod_Archer2 extends BaseMod
{
    public String getVersion()
    {
        return "1.0.0";
    }

    public mod_Archer2()
    {
        ModLoader.registerEntityID(net.minecraft.src.EntityArcher2.class, "Archer2", ModLoader.getUniqueEntityId());
        ModLoader.addSpawn(net.minecraft.src.EntityArcher2.class, 8, 0, 0, EnumCreatureType.creature);
    }

    public void addRenderer(Map map)
    {
        map.put(net.minecraft.src.EntityArcher2.class, new RenderBiped(new ModelBiped(), 0.5F));
    }

    public void load()
    {
    }
}
