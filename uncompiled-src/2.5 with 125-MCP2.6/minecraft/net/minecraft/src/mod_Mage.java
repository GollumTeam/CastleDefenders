package net.minecraft.src;

import java.util.Map;

public class mod_Mage extends BaseMod
{
    public String getVersion()
    {
        return "1.0.0";
    }

    public mod_Mage()
    {
        ModLoader.registerEntityID(net.minecraft.src.EntityAIMageAttack.class, "MageAttack", ModLoader.getUniqueEntityId());
        ModLoader.registerEntityID(net.minecraft.src.EntityMage.class, "Mage", ModLoader.getUniqueEntityId());
        ModLoader.addSpawn(net.minecraft.src.EntityMage.class, 8, 0, 0, EnumCreatureType.creature);
    }

    public void addRenderer(Map map)
    {
        map.put(net.minecraft.src.EntityMage.class, new RenderBiped(new ModelBiped(), 0.5F));
    }

    public void load()
    {
    }
}
