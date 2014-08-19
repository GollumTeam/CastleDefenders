package mods.CastleDef;

import net.minecraft.client.renderer.texture.IconRegister;
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

    public void registerIcons(IconRegister var1)
    {
        this.itemIcon = var1.registerIcon("CastleDef:Medallion");
    }
}
