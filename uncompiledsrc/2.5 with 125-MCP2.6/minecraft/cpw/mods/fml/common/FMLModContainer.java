package cpw.mods.fml.common;

import java.io.File;
import java.util.*;

public class FMLModContainer implements ModContainer
{
    private Mod modDescriptor;
    private Object modInstance;
    private File source;
    private ModMetadata modMetadata;

    public FMLModContainer(String s)
    {
        this(new File(s));
    }

    public FMLModContainer(File file)
    {
        source = file;
    }

    public FMLModContainer(Class class1)
    {
        if (class1 == null)
        {
            return;
        }

        modDescriptor = (Mod)class1.getAnnotation(cpw.mods.fml.common.Mod.class);

        try
        {
            modInstance = class1.newInstance();
        }
        catch (Exception exception)
        {
            throw new RuntimeException(exception);
        }
    }

    public boolean wantsPreInit()
    {
        return modDescriptor.wantsPreInit();
    }

    public boolean wantsPostInit()
    {
        return modDescriptor.wantsPostInit();
    }

    public void preInit()
    {
    }

    public void init()
    {
    }

    public void postInit()
    {
    }

    public static ModContainer buildFor(Class class1)
    {
        return new FMLModContainer(class1);
    }

    public String getName()
    {
        return null;
    }

    public ModContainer.ModState getModState()
    {
        return null;
    }

    public void nextState()
    {
    }

    public String getSortingRules()
    {
        return null;
    }

    public boolean matches(Object obj)
    {
        return false;
    }

    public File getSource()
    {
        return source;
    }

    public Object getMod()
    {
        return null;
    }

    public int lookupFuelValue(int i, int j)
    {
        return 0;
    }

    public boolean wantsPickupNotification()
    {
        return false;
    }

    public IPickupNotifier getPickupNotifier()
    {
        return null;
    }

    public boolean wantsToDispense()
    {
        return false;
    }

    public IDispenseHandler getDispenseHandler()
    {
        return null;
    }

    public boolean wantsCraftingNotification()
    {
        return false;
    }

    public ICraftingHandler getCraftingHandler()
    {
        return null;
    }

    public List getDependencies()
    {
        return new ArrayList(0);
    }

    public List getPreDepends()
    {
        return new ArrayList(0);
    }

    public List getPostDepends()
    {
        return new ArrayList(0);
    }

    public String toString()
    {
        return getSource().getName();
    }

    public boolean wantsNetworkPackets()
    {
        return false;
    }

    public INetworkHandler getNetworkHandler()
    {
        return null;
    }

    public boolean ownsNetworkChannel(String s)
    {
        return false;
    }

    public boolean wantsConsoleCommands()
    {
        return false;
    }

    public IConsoleHandler getConsoleHandler()
    {
        return null;
    }

    public boolean wantsPlayerTracking()
    {
        return false;
    }

    public IPlayerTracker getPlayerTracker()
    {
        return null;
    }

    public List getKeys()
    {
        return null;
    }

    public ModContainer.SourceType getSourceType()
    {
        return null;
    }

    public void setSourceType(ModContainer.SourceType sourcetype)
    {
    }

    public ModMetadata getMetadata()
    {
        return modMetadata;
    }

    public void setMetadata(ModMetadata modmetadata)
    {
        modMetadata = modmetadata;
    }

    public void gatherRenderers(Map map)
    {
    }

    public void requestAnimations()
    {
    }

    public String getVersion()
    {
        return null;
    }

    public ProxyInjector findSidedProxy()
    {
        return null;
    }

    public void keyBindEvent(Object obj)
    {
    }
}
