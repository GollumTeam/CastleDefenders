package forge.oredict;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.src.*;

public class ShapelessOreRecipe implements IRecipe
{
    private ItemStack output;
    private ArrayList input;

    public ShapelessOreRecipe(Block block, Object aobj[])
    {
        this(new ItemStack(block), aobj);
    }

    public ShapelessOreRecipe(Item item, Object aobj[])
    {
        this(new ItemStack(item), aobj);
    }

    public ShapelessOreRecipe(ItemStack itemstack, Object aobj[])
    {
        output = null;
        input = new ArrayList();
        output = itemstack.copy();
        Object aobj1[] = aobj;
        int i = aobj1.length;

        for (int j = 0; j < i; j++)
        {
            Object obj = aobj1[j];

            if (obj instanceof ItemStack)
            {
                input.add(((ItemStack)obj).copy());
                continue;
            }

            if (obj instanceof Item)
            {
                input.add(new ItemStack((Item)obj));
                continue;
            }

            if (obj instanceof Block)
            {
                input.add(new ItemStack((Block)obj));
                continue;
            }

            if (obj instanceof String)
            {
                input.add(OreDictionary.getOres((String)obj));
                continue;
            }

            String s = "Invalid shapeless ore recipe: ";
            Object aobj2[] = aobj;
            int k = aobj2.length;

            for (int l = 0; l < k; l++)
            {
                Object obj1 = aobj2[l];
                s = (new StringBuilder()).append(s).append(obj1).append(", ").toString();
            }

            s = (new StringBuilder()).append(s).append(output).toString();
            throw new RuntimeException(s);
        }
    }

    /**
     * Returns the size of the recipe area
     */
    public int getRecipeSize()
    {
        return input.size();
    }

    public ItemStack getRecipeOutput()
    {
        return output;
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(InventoryCrafting inventorycrafting)
    {
        return output.copy();
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(InventoryCrafting inventorycrafting)
    {
        ArrayList arraylist = new ArrayList(input);

        for (int i = 0; i < inventorycrafting.getSizeInventory(); i++)
        {
            ItemStack itemstack = inventorycrafting.getStackInSlot(i);

            if (itemstack == null)
            {
                continue;
            }

            boolean flag = false;
            Iterator iterator = arraylist.iterator();

            do
            {
                if (!iterator.hasNext())
                {
                    break;
                }

                boolean flag1 = false;
                Object obj = iterator.next();

                if (obj instanceof ItemStack)
                {
                    flag1 = checkItemEquals((ItemStack)obj, itemstack);
                }
                else if (obj instanceof ArrayList)
                {
                    for (Iterator iterator1 = ((ArrayList)obj).iterator(); iterator1.hasNext();)
                    {
                        ItemStack itemstack1 = (ItemStack)iterator1.next();
                        flag1 = flag1 || checkItemEquals(itemstack1, itemstack);
                    }
                }

                if (!flag1)
                {
                    continue;
                }

                flag = true;
                arraylist.remove(obj);
                break;
            }
            while (true);

            if (!flag)
            {
                return false;
            }
        }

        return arraylist.isEmpty();
    }

    private boolean checkItemEquals(ItemStack itemstack, ItemStack itemstack1)
    {
        return itemstack.itemID == itemstack1.itemID && (itemstack.getItemDamage() == -1 || itemstack.getItemDamage() == itemstack1.getItemDamage());
    }
}
