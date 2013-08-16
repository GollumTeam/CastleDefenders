package cpw.mods.fml.common.event;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderState;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.event.FMLInterModComms$1;
import cpw.mods.fml.common.event.FMLInterModComms$IMCMessage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class FMLInterModComms
{
    private static final ImmutableList emptyIMCList = ImmutableList.of();
    private static ArrayListMultimap modMessages = ArrayListMultimap.create();

    public static boolean sendMessage(String var0, String var1, NBTTagCompound var2)
    {
        return enqueueStartupMessage(var0, new FMLInterModComms$IMCMessage(var1, var2, (FMLInterModComms$1)null));
    }

    public static boolean sendMessage(String var0, String var1, ItemStack var2)
    {
        return enqueueStartupMessage(var0, new FMLInterModComms$IMCMessage(var1, var2, (FMLInterModComms$1)null));
    }

    public static boolean sendMessage(String var0, String var1, String var2)
    {
        return enqueueStartupMessage(var0, new FMLInterModComms$IMCMessage(var1, var2, (FMLInterModComms$1)null));
    }

    public static void sendRuntimeMessage(Object var0, String var1, String var2, NBTTagCompound var3)
    {
        enqueueMessage(var0, var1, new FMLInterModComms$IMCMessage(var2, var3, (FMLInterModComms$1)null));
    }

    public static void sendRuntimeMessage(Object var0, String var1, String var2, ItemStack var3)
    {
        enqueueMessage(var0, var1, new FMLInterModComms$IMCMessage(var2, var3, (FMLInterModComms$1)null));
    }

    public static void sendRuntimeMessage(Object var0, String var1, String var2, String var3)
    {
        enqueueMessage(var0, var1, new FMLInterModComms$IMCMessage(var2, var3, (FMLInterModComms$1)null));
    }

    private static boolean enqueueStartupMessage(String var0, FMLInterModComms$IMCMessage var1)
    {
        if (Loader.instance().activeModContainer() == null)
        {
            return false;
        }
        else
        {
            enqueueMessage(Loader.instance().activeModContainer(), var0, var1);
            return Loader.isModLoaded(var0) && !Loader.instance().hasReachedState(LoaderState.POSTINITIALIZATION);
        }
    }

    private static void enqueueMessage(Object var0, String var1, FMLInterModComms$IMCMessage var2)
    {
        ModContainer var3;

        if (var0 instanceof ModContainer)
        {
            var3 = (ModContainer)var0;
        }
        else
        {
            var3 = FMLCommonHandler.instance().findContainerFor(var0);
        }

        if (var3 != null && Loader.isModLoaded(var1))
        {
            var2.setSender(var3);
            modMessages.put(var1, var2);
        }
    }

    public static ImmutableList fetchRuntimeMessages(Object var0)
    {
        ModContainer var1 = FMLCommonHandler.instance().findContainerFor(var0);
        return var1 != null ? ImmutableList.copyOf(modMessages.removeAll(var1.getModId())) : emptyIMCList;
    }

    static ArrayListMultimap access$000()
    {
        return modMessages;
    }
}
