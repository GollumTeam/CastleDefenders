package forge.oredict;

import java.util.*;
import net.minecraft.src.*;

public class ShapedOreRecipe implements IRecipe
{
    private static final int MAX_CRAFT_GRID_WIDTH = 3;
    private static final int MAX_CRAFT_GRID_HEIGHT = 3;
    private ItemStack output;
    private Object input[];
    private int width;
    private int height;
    private boolean mirriored;

    public ShapedOreRecipe(Block block, Object aobj[])
    {
        this(block, true, aobj);
    }

    public ShapedOreRecipe(Item item, Object aobj[])
    {
        this(item, true, aobj);
    }

    public ShapedOreRecipe(ItemStack itemstack, Object aobj[])
    {
        this(itemstack, true, aobj);
    }

    public ShapedOreRecipe(Block block, boolean flag, Object aobj[])
    {
        this(new ItemStack(block), flag, aobj);
    }

    public ShapedOreRecipe(Item item, boolean flag, Object aobj[])
    {
        this(new ItemStack(item), flag, aobj);
    }

    public ShapedOreRecipe(ItemStack itemstack, boolean flag, Object aobj[])
    {
        output = null;
        input = null;
        width = 0;
        height = 0;
        mirriored = true;
        output = itemstack.copy();
        mirriored = flag;
        String s = "";
        int i = 0;

        if (aobj[i] instanceof String[])
        {
            String as[] = (String[])aobj[i++];
            String as1[] = as;
            int k = as1.length;

            for (int i1 = 0; i1 < k; i1++)
            {
                String s3 = as1[i1];
                width = s3.length();
                s = (new StringBuilder()).append(s).append(s3).toString();
            }

            height = as.length;
        }
        else
        {
            while (aobj[i] instanceof String)
            {
                String s1 = (String)aobj[i++];
                s = (new StringBuilder()).append(s).append(s1).toString();
                width = s1.length();
                height++;
            }
        }

        if (width * height != s.length())
        {
            String s2 = "Invalid shaped ore recipe: ";
            Object aobj1[] = aobj;
            int l = aobj1.length;

            for (int j1 = 0; j1 < l; j1++)
            {
                Object obj2 = aobj1[j1];
                s2 = (new StringBuilder()).append(s2).append(obj2).append(", ").toString();
            }

            s2 = (new StringBuilder()).append(s2).append(output).toString();
            throw new RuntimeException(s2);
        }

        HashMap hashmap = new HashMap();

        for (; i < aobj.length; i += 2)
        {
            Character character = (Character)aobj[i];
            Object obj = aobj[i + 1];
            Object obj1 = null;

            if (obj instanceof ItemStack)
            {
                hashmap.put(character, ((ItemStack)obj).copy());
                continue;
            }

            if (obj instanceof Item)
            {
                hashmap.put(character, new ItemStack((Item)obj));
                continue;
            }

            if (obj instanceof Block)
            {
                hashmap.put(character, new ItemStack((Block)obj, 1, -1));
                continue;
            }

            if (obj instanceof String)
            {
                hashmap.put(character, OreDictionary.getOres((String)obj));
                continue;
            }

            String s4 = "Invalid shaped ore recipe: ";
            Object aobj2[] = aobj;
            int i2 = aobj2.length;

            for (int j2 = 0; j2 < i2; j2++)
            {
                Object obj3 = aobj2[j2];
                s4 = (new StringBuilder()).append(s4).append(obj3).append(", ").toString();
            }

            s4 = (new StringBuilder()).append(s4).append(output).toString();
            throw new RuntimeException(s4);
        }

        input = new Object[width * height];
        int j = 0;
        char ac[] = s.toCharArray();
        int k1 = ac.length;

        for (int l1 = 0; l1 < k1; l1++)
        {
            char c = ac[l1];
            input[j++] = hashmap.get(Character.valueOf(c));
        }
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    public ItemStack getCraftingResult(InventoryCrafting inventorycrafting)
    {
        return output.copy();
    }

    /**
     * Returns the size of the recipe area
     */
    public int getRecipeSize()
    {
        return input.length;
    }

    public ItemStack getRecipeOutput()
    {
        return output;
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    public boolean matches(InventoryCrafting inventorycrafting)
    {
        for (int i = 0; i <= 3 - width; i++)
        {
            for (int j = 0; j <= 3 - height; j++)
            {
                if (checkMatch(inventorycrafting, i, j, true))
                {
                    return true;
                }

                if (mirriored && checkMatch(inventorycrafting, i, j, false))
                {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkMatch(InventoryCrafting inventorycrafting, int i, int j, boolean flag)
    {
        for (int k = 0; k < 3; k++)
        {
            for (int l = 0; l < 3; l++)
            {
                int i1 = k - i;
                int j1 = l - j;
                Object obj = null;

                if (i1 >= 0 && j1 >= 0 && i1 < width && j1 < height)
                {
                    if (flag)
                    {
                        obj = input[(width - i1 - 1) + j1 * width];
                    }
                    else
                    {
                        obj = input[i1 + j1 * width];
                    }
                }

                ItemStack itemstack = inventorycrafting.getStackInRowAndColumn(k, l);

                if (obj instanceof ItemStack)
                {
                    if (!checkItemEquals((ItemStack)obj, itemstack))
                    {
                        return false;
                    }

                    continue;
                }

                if (obj instanceof ArrayList)
                {
                    boolean flag1 = false;

                    for (Iterator iterator = ((ArrayList)obj).iterator(); iterator.hasNext();)
                    {
                        ItemStack itemstack1 = (ItemStack)iterator.next();
                        flag1 = flag1 || checkItemEquals(itemstack1, itemstack);
                    }

                    if (!flag1)
                    {
                        return false;
                    }

                    continue;
                }

                if (obj == null && itemstack != null)
                {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean checkItemEquals(ItemStack itemstack, ItemStack itemstack1)
    {
        if (itemstack1 == null && itemstack != null || itemstack1 != null && itemstack == null)
        {
            return false;
        }
        else
        {
            return itemstack.itemID == itemstack1.itemID && (itemstack.getItemDamage() == -1 || itemstack.getItemDamage() == itemstack1.getItemDamage());
        }
    }

    public void setMirriored(boolean flag)
    {
        mirriored = flag;
    }
}
