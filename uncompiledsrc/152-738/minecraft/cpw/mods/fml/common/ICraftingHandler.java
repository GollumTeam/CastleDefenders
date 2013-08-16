package cpw.mods.fml.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public interface ICraftingHandler
{
    void onCrafting(EntityPlayer var1, ItemStack var2, IInventory var3);

    void onSmelting(EntityPlayer var1, ItemStack var2);
}
