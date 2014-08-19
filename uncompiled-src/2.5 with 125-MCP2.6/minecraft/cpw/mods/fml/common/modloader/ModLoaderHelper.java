package cpw.mods.fml.common.modloader;

import cpw.mods.fml.common.*;
import java.util.*;

public class ModLoaderHelper
{
    private static Map notModCallbacks = new HashMap();

    public ModLoaderHelper()
    {
    }

    public static void updateStandardTicks(BaseMod basemod, boolean flag, boolean flag1)
    {
        ModLoaderModContainer modloadermodcontainer = findOrBuildModContainer(basemod);
        BaseModTicker basemodticker = modloadermodcontainer.getTickHandler();
        EnumSet enumset = basemodticker.ticks();

        if (flag && !flag1 && FMLCommonHandler.instance().getSide().isClient())
        {
            enumset.add(TickType.RENDER);
        }
        else
        {
            enumset.remove(TickType.RENDER);
        }

        if (flag && (flag1 || FMLCommonHandler.instance().getSide().isServer()))
        {
            enumset.add(TickType.GAME);
        }
        else
        {
            enumset.remove(TickType.GAME);
        }

        if (flag)
        {
            enumset.add(TickType.WORLDLOAD);
        }
    }

    public static void updateGUITicks(BaseMod basemod, boolean flag, boolean flag1)
    {
        ModLoaderModContainer modloadermodcontainer = findOrBuildModContainer(basemod);
        EnumSet enumset = modloadermodcontainer.getTickHandler().ticks();

        if (flag && !flag1 && FMLCommonHandler.instance().getSide().isClient())
        {
            enumset.add(TickType.GUI);
        }
        else
        {
            enumset.remove(TickType.GUI);
        }

        if (flag && (flag1 || FMLCommonHandler.instance().getSide().isServer()))
        {
            enumset.add(TickType.WORLDGUI);
        }
        else
        {
            enumset.remove(TickType.WORLDGUI);
        }

        if (flag)
        {
            enumset.add(TickType.GUILOAD);
        }
    }

    private static ModLoaderModContainer findOrBuildModContainer(BaseMod basemod)
    {
        ModLoaderModContainer modloadermodcontainer = (ModLoaderModContainer)ModLoaderModContainer.findContainerFor(basemod);

        if (modloadermodcontainer == null)
        {
            modloadermodcontainer = (ModLoaderModContainer)notModCallbacks.get(basemod);

            if (modloadermodcontainer == null)
            {
                modloadermodcontainer = new ModLoaderModContainer(basemod);
                notModCallbacks.put(basemod, modloadermodcontainer);
            }
        }

        return modloadermodcontainer;
    }

    public static ModLoaderModContainer registerRenderHelper(BaseMod basemod)
    {
        ModLoaderModContainer modloadermodcontainer = findOrBuildModContainer(basemod);
        return modloadermodcontainer;
    }

    public static ModLoaderModContainer registerKeyHelper(BaseMod basemod)
    {
        ModLoaderModContainer modloadermodcontainer = findOrBuildModContainer(basemod);
        return modloadermodcontainer;
    }
}
