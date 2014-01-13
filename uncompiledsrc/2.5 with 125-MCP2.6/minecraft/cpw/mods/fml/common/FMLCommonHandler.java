package cpw.mods.fml.common;

import java.io.*;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FMLCommonHandler
{
    private static final FMLCommonHandler INSTANCE = new FMLCommonHandler();
    private static final Pattern metadataFile = Pattern.compile("$/modinfo.json$");
    private Map channelList;
    private Map modChannels;
    private Map activeChannels;
    private IFMLSidedHandler sidedDelegate;
    private int uniqueEntityListId;
    private List auxilliaryContainers;
    private Map modLanguageData;
    private Set tickHandlers;
    private Set worldGenerators;
    private Class forge;
    private boolean noForge;

    public FMLCommonHandler()
    {
        channelList = new HashMap();
        modChannels = new HashMap();
        activeChannels = new HashMap();
        uniqueEntityListId = 220;
        auxilliaryContainers = new ArrayList();
        modLanguageData = new HashMap();
        tickHandlers = new HashSet();
        worldGenerators = new HashSet();
    }

    public void beginLoading(IFMLSidedHandler ifmlsidedhandler)
    {
        sidedDelegate = ifmlsidedhandler;
        getFMLLogger().info("Attempting early MinecraftForge initialization");
        callForgeMethod("initialize");
        getFMLLogger().info("Completed early MinecraftForge initialization");
    }

    public void tickStart(EnumSet enumset, Object aobj[])
    {
        sidedDelegate.profileStart((new StringBuilder()).append("modTickStart$").append(enumset).toString());
        Iterator iterator = tickHandlers.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            ITickHandler itickhandler = (ITickHandler)iterator.next();
            EnumSet enumset1 = EnumSet.copyOf(itickhandler.ticks());
            enumset1.removeAll(EnumSet.complementOf(enumset));

            if (!enumset1.isEmpty())
            {
                sidedDelegate.profileStart(itickhandler.getLabel());
                itickhandler.tickStart(enumset1, aobj);
                sidedDelegate.profileEnd();
            }
        }
        while (true);

        sidedDelegate.profileEnd();
    }

    public void tickEnd(EnumSet enumset, Object aobj[])
    {
        sidedDelegate.profileStart((new StringBuilder()).append("modTickEnd$").append(enumset).toString());
        Iterator iterator = tickHandlers.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            ITickHandler itickhandler = (ITickHandler)iterator.next();
            EnumSet enumset1 = EnumSet.copyOf(itickhandler.ticks());
            enumset1.removeAll(EnumSet.complementOf(enumset));

            if (!enumset1.isEmpty())
            {
                sidedDelegate.profileStart(itickhandler.getLabel());
                itickhandler.tickEnd(enumset1, aobj);
                sidedDelegate.profileEnd();
            }
        }
        while (true);

        sidedDelegate.profileEnd();
    }

    public List gatherKeyBindings()
    {
        ArrayList arraylist = new ArrayList();
        ModContainer modcontainer;

        for (Iterator iterator = Loader.getModList().iterator(); iterator.hasNext(); arraylist.addAll(modcontainer.getKeys()))
        {
            modcontainer = (ModContainer)iterator.next();
        }

        ModContainer modcontainer1;

        for (Iterator iterator1 = auxilliaryContainers.iterator(); iterator1.hasNext(); arraylist.addAll(modcontainer1.getKeys()))
        {
            modcontainer1 = (ModContainer)iterator1.next();
        }

        return arraylist;
    }

    public static FMLCommonHandler instance()
    {
        return INSTANCE;
    }

    public ModContainer getModForChannel(String s)
    {
        return (ModContainer)modChannels.get(s);
    }

    public Set getChannelListFor(ModContainer modcontainer)
    {
        return (Set)channelList.get(modcontainer);
    }

    public void registerChannel(ModContainer modcontainer, String s)
    {
        if (!modChannels.containsKey(s));

        Object obj = (Set)channelList.get(modcontainer);

        if (obj == null)
        {
            obj = new HashSet();
            channelList.put(modcontainer, obj);
        }

        ((Set)(obj)).add(s);
        modChannels.put(s, modcontainer);
    }

    public void activateChannel(Object obj, String s)
    {
        Object obj1 = (Set)activeChannels.get(obj);

        if (obj1 == null)
        {
            obj1 = new HashSet();
            activeChannels.put(obj, obj1);
        }

        ((Set)(obj1)).add(s);
    }

    public void deactivateChannel(Object obj, String s)
    {
        Object obj1 = (Set)activeChannels.get(obj);

        if (obj1 == null)
        {
            obj1 = new HashSet();
            activeChannels.put(obj, obj1);
        }

        ((Set)(obj1)).remove(s);
    }

    public byte[] getPacketRegistry()
    {
        StringBuffer stringbuffer = new StringBuffer();
        String s;

        for (Iterator iterator = modChannels.keySet().iterator(); iterator.hasNext(); stringbuffer.append(s).append("\0"))
        {
            s = (String)iterator.next();
        }

        try
        {
            return stringbuffer.toString().getBytes("UTF8");
        }
        catch (UnsupportedEncodingException unsupportedencodingexception)
        {
            Loader.log.warning("Error building registration list");
            Loader.log.throwing("FMLHooks", "getPacketRegistry", unsupportedencodingexception);
            return new byte[0];
        }
    }

    public boolean isChannelActive(String s, Object obj)
    {
        return ((Set)activeChannels.get(obj)).contains(s);
    }

    public Logger getFMLLogger()
    {
        return Loader.log;
    }

    public Logger getMinecraftLogger()
    {
        return sidedDelegate.getMinecraftLogger();
    }

    public boolean isModLoaderMod(Class class1)
    {
        return sidedDelegate.isModLoaderMod(class1);
    }

    public ModContainer loadBaseModMod(Class class1, File file)
    {
        return sidedDelegate.loadBaseModMod(class1, file);
    }

    public File getMinecraftRootDirectory()
    {
        return sidedDelegate.getMinecraftRootDirectory();
    }

    public Object getMinecraftInstance()
    {
        return sidedDelegate.getMinecraftInstance();
    }

    public int nextUniqueEntityListId()
    {
        return uniqueEntityListId++;
    }

    public void addStringLocalization(String s, String s1, String s2)
    {
        Properties properties = (Properties)modLanguageData.get(s1);

        if (properties == null)
        {
            properties = new Properties();
            modLanguageData.put(s1, properties);
        }

        properties.put(s, s2);
        handleLanguageLoad(sidedDelegate.getCurrentLanguageTable(), s1);
    }

    public void handleLanguageLoad(Properties properties, String s)
    {
        Properties properties1 = (Properties)modLanguageData.get("en_US");

        if (properties1 != null)
        {
            properties.putAll(properties1);
        }

        Properties properties2 = (Properties)modLanguageData.get(s);

        if (properties2 == null)
        {
            return;
        }
        else
        {
            properties.putAll(properties2);
            return;
        }
    }

    public Side getSide()
    {
        return sidedDelegate.getSide();
    }

    public void addAuxilliaryModContainer(ModContainer modcontainer)
    {
        auxilliaryContainers.add(modcontainer);
    }

    public int fuelLookup(int i, int j)
    {
        int k = 0;

        for (Iterator iterator = Loader.getModList().iterator(); iterator.hasNext();)
        {
            ModContainer modcontainer = (ModContainer)iterator.next();
            k = Math.max(k, modcontainer.lookupFuelValue(i, j));
        }

        return k;
    }

    public void addNameForObject(Object obj, String s, String s1)
    {
        String s2 = sidedDelegate.getObjectName(obj);
        addStringLocalization(s2, s, s1);
    }

    public void raiseException(Throwable throwable, String s, boolean flag)
    {
        instance().getFMLLogger().throwing("FMLHandler", "raiseException", throwable);
        throw new RuntimeException(throwable);
    }

    private Class findMinecraftForge()
    {
        if (forge == null && !noForge)
        {
            try
            {
                forge = Class.forName("forge.MinecraftForge");
            }
            catch (Exception exception)
            {
                try
                {
                    forge = Class.forName("forge.MinecraftForge");
                }
                catch (Exception exception1)
                {
                    noForge = true;
                }
            }
        }

        return forge;
    }

    private Object callForgeMethod(String s)
    {
        if (noForge)
        {
            return null;
        }

        try
        {
            return findMinecraftForge().getMethod(s, new Class[0]).invoke(null, new Object[0]);
        }
        catch (Exception exception)
        {
            return null;
        }
    }

    public String[] getBrandingStrings(String s)
    {
        ArrayList arraylist = new ArrayList();
        arraylist.add(s);
        arraylist.add(Loader.instance().getFMLVersionString());
        String s1 = (String)callForgeMethod("getVersionString");

        if (s1 != null)
        {
            arraylist.add(s1);
        }

        arraylist.addAll(sidedDelegate.getAdditionalBrandingInformation());

        try
        {
            Properties properties = new Properties();
            properties.load((cpw.mods.fml.common.FMLCommonHandler.class).getClassLoader().getResourceAsStream("fmlbranding.properties"));
            arraylist.add(properties.getProperty("fmlbranding"));
        }
        catch (Exception exception) { }

        arraylist.add(String.format("%d mod%s loaded", new Object[]
                {
                    Integer.valueOf(Loader.getModList().size()), Loader.getModList().size() == 1 ? "" : "s"
                }));
        Collections.reverse(arraylist);
        return (String[])arraylist.toArray(new String[arraylist.size()]);
    }

    public void loadMetadataFor(ModContainer modcontainer)
    {
        if (modcontainer.getSourceType() == ModContainer.SourceType.JAR)
        {
            try
            {
                ZipFile zipfile = new ZipFile(modcontainer.getSource());
                ZipEntry zipentry = zipfile.getEntry("mcmod.info");

                if (zipentry != null)
                {
                    InputStream inputstream1 = zipfile.getInputStream(zipentry);
                    ModMetadata modmetadata1 = sidedDelegate.readMetadataFrom(inputstream1, modcontainer);
                    modcontainer.setMetadata(modmetadata1);
                }
                else
                {
                    getFMLLogger().fine(String.format("Failed to find mcmod.info file in %s for %s", new Object[]
                            {
                                modcontainer.getSource().getName(), modcontainer.getName()
                            }));
                }
            }
            catch (Exception exception)
            {
                getFMLLogger().fine(String.format("Failed to find mcmod.info file in %s for %s", new Object[]
                        {
                            modcontainer.getSource().getName(), modcontainer.getName()
                        }));
                getFMLLogger().throwing("FMLCommonHandler", "loadMetadataFor", exception);
            }
        }
        else
        {
            try
            {
                InputStream inputstream = Loader.instance().getModClassLoader().getResourceAsStream((new StringBuilder()).append(modcontainer.getName()).append(".info").toString());

                if (inputstream == null)
                {
                    inputstream = Loader.instance().getModClassLoader().getResourceAsStream((new StringBuilder()).append("net/minecraft/src/").append(modcontainer.getName()).append(".info").toString());
                }

                if (inputstream != null)
                {
                    ModMetadata modmetadata = sidedDelegate.readMetadataFrom(inputstream, modcontainer);
                    modcontainer.setMetadata(modmetadata);
                }
            }
            catch (Exception exception1)
            {
                getFMLLogger().fine(String.format("Failed to find %s.info file in %s for %s", new Object[]
                        {
                            modcontainer.getName(), modcontainer.getSource().getName(), modcontainer.getName()
                        }));
                getFMLLogger().throwing("FMLCommonHandler", "loadMetadataFor", exception1);
            }
        }
    }

    public IFMLSidedHandler getSidedDelegate()
    {
        return sidedDelegate;
    }

    public void injectSidedProxyDelegate(ModContainer modcontainer)
    {
        ProxyInjector proxyinjector = modcontainer.findSidedProxy();

        if (proxyinjector != null)
        {
            proxyinjector.inject(modcontainer, sidedDelegate.getSide());
        }
    }

    public void handleWorldGeneration(int i, int j, long l, Object aobj[])
    {
        Random random = new Random(l);
        long l1 = random.nextLong() >> 3;
        long l2 = random.nextLong() >> 3;
        random.setSeed(l1 * (long)i + l2 * (long)j ^ l);
        IWorldGenerator iworldgenerator;

        for (Iterator iterator = worldGenerators.iterator(); iterator.hasNext(); iworldgenerator.generate(random, i, j, aobj))
        {
            iworldgenerator = (IWorldGenerator)iterator.next();
        }
    }

    public void registerTickHandler(ITickHandler itickhandler)
    {
        tickHandlers.add(itickhandler);
    }

    public void registerWorldGenerator(IWorldGenerator iworldgenerator)
    {
        worldGenerators.add(iworldgenerator);
    }
}
