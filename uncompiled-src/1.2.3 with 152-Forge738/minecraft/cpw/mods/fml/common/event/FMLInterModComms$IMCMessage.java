package cpw.mods.fml.common.event;

import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.event.FMLInterModComms$1;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public final class FMLInterModComms$IMCMessage
{
    private String sender;
    public final String key;
    private Object value;

    private FMLInterModComms$IMCMessage(String var1, Object var2)
    {
        this.key = var1;
        this.value = var2;
    }

    public String toString()
    {
        return this.sender;
    }

    public String getSender()
    {
        return this.sender;
    }

    void setSender(ModContainer var1)
    {
        this.sender = var1.getModId();
    }

    public String getStringValue()
    {
        return (String)this.value;
    }

    public NBTTagCompound getNBTValue()
    {
        return (NBTTagCompound)this.value;
    }

    public ItemStack getItemStackValue()
    {
        return (ItemStack)this.value;
    }

    public Class getMessageType()
    {
        return this.value.getClass();
    }

    public boolean isStringMessage()
    {
        return String.class.isAssignableFrom(this.getMessageType());
    }

    public boolean isItemStackMessage()
    {
        return ItemStack.class.isAssignableFrom(this.getMessageType());
    }

    public boolean isNBTMessage()
    {
        return NBTTagCompound.class.isAssignableFrom(this.getMessageType());
    }

    FMLInterModComms$IMCMessage(String var1, Object var2, FMLInterModComms$1 var3)
    {
        this(var1, var2);
    }
}
