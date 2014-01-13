package cpw.mods.fml.common.registry;

import com.google.common.base.Objects;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderException;
import cpw.mods.fml.common.ModContainer;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.nbt.NBTTagCompound;

public class ItemData
{
    private static Map modOrdinals = Maps.newHashMap();
    private final String modId;
    private final String itemType;
    private final int itemId;
    private final int ordinal;
    private String forcedModId;
    private String forcedName;

    public ItemData(Item var1, ModContainer var2)
    {
        this.itemId = var1.itemID;

        if (var1.getClass().equals(ItemBlock.class))
        {
            this.itemType = Block.blocksList[this.getItemId()].getClass().getName();
        }
        else
        {
            this.itemType = var1.getClass().getName();
        }

        this.modId = var2.getModId();

        if (!modOrdinals.containsKey(var2.getModId()))
        {
            modOrdinals.put(var2.getModId(), HashMultiset.create());
        }

        this.ordinal = ((Multiset)modOrdinals.get(var2.getModId())).add(this.itemType, 1);
    }

    public ItemData(NBTTagCompound var1)
    {
        this.modId = var1.getString("ModId");
        this.itemType = var1.getString("ItemType");
        this.itemId = var1.getInteger("ItemId");
        this.ordinal = var1.getInteger("ordinal");
        this.forcedModId = var1.hasKey("ForcedModId") ? var1.getString("ForcedModId") : null;
        this.forcedName = var1.hasKey("ForcedName") ? var1.getString("ForcedName") : null;
    }

    public String getItemType()
    {
        return this.forcedName != null ? this.forcedName : this.itemType;
    }

    public String getModId()
    {
        return this.forcedModId != null ? this.forcedModId : this.modId;
    }

    public int getOrdinal()
    {
        return this.ordinal;
    }

    public int getItemId()
    {
        return this.itemId;
    }

    public NBTTagCompound toNBT()
    {
        NBTTagCompound var1 = new NBTTagCompound();
        var1.setString("ModId", this.modId);
        var1.setString("ItemType", this.itemType);
        var1.setInteger("ItemId", this.itemId);
        var1.setInteger("ordinal", this.ordinal);

        if (this.forcedModId != null)
        {
            var1.setString("ForcedModId", this.forcedModId);
        }

        if (this.forcedName != null)
        {
            var1.setString("ForcedName", this.forcedName);
        }

        return var1;
    }

    public int hashCode()
    {
        return Objects.hashCode(new Object[] {Integer.valueOf(this.itemId), Integer.valueOf(this.ordinal)});
    }

    public boolean equals(Object var1)
    {
        try
        {
            ItemData var2 = (ItemData)var1;
            return Objects.equal(this.getModId(), var2.getModId()) && Objects.equal(this.getItemType(), var2.getItemType()) && Objects.equal(Integer.valueOf(this.itemId), Integer.valueOf(var2.itemId)) && (this.isOveridden() || Objects.equal(Integer.valueOf(this.ordinal), Integer.valueOf(var2.ordinal)));
        }
        catch (ClassCastException var3)
        {
            return false;
        }
    }

    public String toString()
    {
        return String.format("Item %d, Type %s, owned by %s, ordinal %d, name %s, claimedModId %s", new Object[] {Integer.valueOf(this.itemId), this.itemType, this.modId, Integer.valueOf(this.ordinal), this.forcedName, this.forcedModId});
    }

    public boolean mayDifferByOrdinal(ItemData var1)
    {
        return Objects.equal(this.getItemType(), var1.getItemType()) && Objects.equal(this.getModId(), var1.getModId());
    }

    public boolean isOveridden()
    {
        return this.forcedName != null;
    }

    public void setName(String var1, String var2)
    {
        if (var1 == null)
        {
            this.forcedName = null;
            this.forcedModId = null;
        }
        else
        {
            String var3 = var2;

            if (var2 == null)
            {
                var3 = Loader.instance().activeModContainer().getModId();
            }

            if (((Multiset)modOrdinals.get(var3)).count(var1) > 0)
            {
                FMLLog.severe("The mod %s is attempting to redefine the item at id %d with a non-unique name (%s.%s)", new Object[] {Loader.instance().activeModContainer(), Integer.valueOf(this.itemId), var3, var1});
                throw new LoaderException();
            }
            else
            {
                ((Multiset)modOrdinals.get(var3)).add(var1);
                this.forcedModId = var2;
                this.forcedName = var1;
            }
        }
    }
}
