package mods.CastleDef;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemMedallion extends Item
{
    public ItemMedallion(int var1)
    {
        super(var1);
        this.maxStackSize = 64;
        this.setCreativeTab(CreativeTabs.tabAllSearch);
    }

    public void a(ly var1)
    {
        this.ct = var1.a("CastleDef:Medallion");
    }
}
