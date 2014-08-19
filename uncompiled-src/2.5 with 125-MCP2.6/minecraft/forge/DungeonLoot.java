package forge;

import java.util.Random;
import net.minecraft.src.ItemStack;

public class DungeonLoot
{
    private ItemStack itemStack;
    private int minCount;
    private int maxCount;

    public DungeonLoot(ItemStack itemstack, int i, int j)
    {
        minCount = 1;
        maxCount = 1;
        itemStack = itemstack;
        minCount = i;
        maxCount = j;
    }

    public ItemStack generateStack(Random random)
    {
        ItemStack itemstack = itemStack.copy();
        itemstack.stackSize = minCount + random.nextInt((maxCount - minCount) + 1);
        return itemstack;
    }

    public boolean equals(ItemStack itemstack, int i, int j)
    {
        return i == minCount && j == maxCount && itemstack.isItemEqual(itemStack);
    }

    public boolean equals(ItemStack itemstack)
    {
        return itemstack.isItemEqual(itemStack);
    }
}
