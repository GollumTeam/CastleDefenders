package cpw.mods.fml.common.modloader;

import cpw.mods.fml.common.*;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.logging.Logger;

public class ModLoaderModContainer implements ModContainer
{
    private static final ProxyInjector NULLPROXY = new ProxyInjector("", "", "", null);
    private Class modClazz;
    private BaseMod mod;
    private File modSource;
    private ArrayList dependencies;
    private ArrayList preDependencies;
    private ArrayList postDependencies;
    private ArrayList keyHandlers;
    private cpw.mods.fml.common.ModContainer.ModState state;
    private cpw.mods.fml.common.ModContainer.SourceType sourceType;
    private ModMetadata metadata;
    private ProxyInjector sidedProxy;
    private BaseModTicker tickHandler;

    public ModLoaderModContainer(Class class1, File file)
    {
        modClazz = class1;
        modSource = file;
        nextState();
    }

    ModLoaderModContainer(BaseMod basemod)
    {
        FMLCommonHandler.instance().addAuxilliaryModContainer(this);
        mod = basemod;
        tickHandler = new BaseModTicker(basemod);
    }

    public boolean wantsPreInit()
    {
        return true;
    }

    public boolean wantsPostInit()
    {
        return true;
    }

    public void preInit()
    {
        try
        {
            EnumSet enumset = EnumSet.noneOf(cpw.mods.fml.common.TickType.class);
            tickHandler = new BaseModTicker(enumset);
            configureMod();
            mod = (BaseMod)modClazz.newInstance();
            tickHandler.setMod(mod);
            FMLCommonHandler.instance().registerTickHandler(tickHandler);
            FMLCommonHandler.instance().registerWorldGenerator(mod);
        }
        catch (Exception exception)
        {
            throw new LoaderException(exception);
        }
    }

    public cpw.mods.fml.common.ModContainer.ModState getModState()
    {
        return state;
    }

    public void nextState()
    {
        if (state == null)
        {
            state = cpw.mods.fml.common.ModContainer.ModState.UNLOADED;
            return;
        }

        if (state.ordinal() + 1 < cpw.mods.fml.common.ModContainer.ModState.values().length)
        {
            state = cpw.mods.fml.common.ModContainer.ModState.values()[state.ordinal() + 1];
        }
    }

    private void configureMod()
    {
        IFMLSidedHandler ifmlsidedhandler = FMLCommonHandler.instance().getSidedDelegate();
        File file = Loader.instance().getConfigDir();
        String s = modClazz.getSimpleName();
        File file1 = new File(file, String.format("%s.cfg", new Object[]
                {
                    s
                }));
        Properties properties = new Properties();

        if (file1.exists())
        {
            try
            {
                Loader.log.fine(String.format("Reading existing configuration file for %s : %s", new Object[]
                        {
                            s, file1.getName()
                        }));
                FileReader filereader = new FileReader(file1);
                properties.load(filereader);
                filereader.close();
            }
            catch (Exception exception)
            {
                Loader.log.severe(String.format("Error occured reading mod configuration file %s", new Object[]
                        {
                            file1.getName()
                        }));
                Loader.log.throwing("ModLoaderModContainer", "configureMod", exception);
                throw new LoaderException(exception);
            }
        }

        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append("MLProperties: name (type:default) min:max -- information\n");

        try
        {
            Field afield[] = modClazz.getDeclaredFields();
            int i = afield.length;

            for (int j = 0; j < i; j++)
            {
                Field field = afield[j];

                if (!Modifier.isStatic(field.getModifiers()))
                {
                    continue;
                }

                ModProperty modproperty = ifmlsidedhandler.getModLoaderPropertyFor(field);

                if (modproperty == null)
                {
                    continue;
                }

                String s1 = modproperty.name().length() <= 0 ? field.getName() : modproperty.name();
                String s2 = null;
                Object obj = null;

                try
                {
                    obj = field.get(null);
                    s2 = properties.getProperty(s1, extractValue(obj));
                    Object obj1 = parseValue(s2, modproperty, field.getType(), s1, s);
                    Loader.log.finest(String.format("Configuration for %s.%s found values default: %s, configured: %s, interpreted: %s", new Object[]
                            {
                                s, s1, obj, s2, obj1
                            }));

                    if (obj1 != null && !obj1.equals(obj))
                    {
                        Loader.log.finest(String.format("Configuration for %s.%s value set to: %s", new Object[]
                                {
                                    s, s1, obj1
                                }));
                        field.set(null, obj1);
                    }

                    continue;
                }
                catch (Exception exception1)
                {
                    Loader.log.severe(String.format("Invalid configuration found for %s in %s", new Object[]
                            {
                                s1, file1.getName()
                            }));
                    Loader.log.throwing("ModLoaderModContainer", "configureMod", exception1);
                    throw new LoaderException(exception1);
                }
                finally
                {
                    stringbuffer.append(String.format("MLProp : %s (%s:%s", new Object[]
                            {
                                s1, field.getType().getName(), obj
                            }));

                    if (modproperty.min() != 4.9406564584124654E-324D)
                    {
                        stringbuffer.append(",>=").append(String.format("%.1f", new Object[]
                                {
                                    Double.valueOf(modproperty.min())
                                }));
                    }

                    if (modproperty.max() != Double.MAX_VALUE)
                    {
                        stringbuffer.append(",<=").append(String.format("%.1f", new Object[]
                                {
                                    Double.valueOf(modproperty.max())
                                }));
                    }

                    stringbuffer.append(")");

                    if (modproperty.info().length() > 0)
                    {
                        stringbuffer.append(" -- ").append(modproperty.info());
                    }

                    if (s2 != null)
                    {
                        properties.setProperty(s1, extractValue(s2));
                    }

                    stringbuffer.append("\n");
                }
            }
        }
        finally
        {
            try
            {
                FileWriter filewriter = new FileWriter(file1);
                properties.store(filewriter, stringbuffer.toString());
                filewriter.close();
                Loader.log.fine(String.format("Configuration for %s written to %s", new Object[]
                        {
                            s, file1.getName()
                        }));
            }
            catch (IOException ioexception)
            {
                Loader.log.warning(String.format("Error trying to write the config file %s", new Object[]
                        {
                            file1.getName()
                        }));
                Loader.log.throwing("ModLoaderModContainer", "configureMod", ioexception);
                throw new LoaderException(ioexception);
            }
        }
    }

    private Object parseValue(String s, ModProperty modproperty, Class class1, String s1, String s2)
    {
        if (class1.isAssignableFrom(java.lang.String.class))
        {
            return s;
        }

        if (class1.isAssignableFrom(Boolean.TYPE) || class1.isAssignableFrom(java.lang.Boolean.class))
        {
            return Boolean.valueOf(Boolean.parseBoolean(s));
        }

        if ((java.lang.Number.class).isAssignableFrom(class1) || class1.isPrimitive())
        {
            Object obj = null;

            if (class1.isAssignableFrom(Double.TYPE) || (java.lang.Double.class).isAssignableFrom(class1))
            {
                obj = Double.valueOf(Double.parseDouble(s));
            }
            else if (class1.isAssignableFrom(Float.TYPE) || (java.lang.Float.class).isAssignableFrom(class1))
            {
                obj = Float.valueOf(Float.parseFloat(s));
            }
            else if (class1.isAssignableFrom(Long.TYPE) || (java.lang.Long.class).isAssignableFrom(class1))
            {
                obj = Long.valueOf(Long.parseLong(s));
            }
            else if (class1.isAssignableFrom(Integer.TYPE) || (java.lang.Integer.class).isAssignableFrom(class1))
            {
                obj = Integer.valueOf(Integer.parseInt(s));
            }
            else if (class1.isAssignableFrom(Short.TYPE) || (java.lang.Short.class).isAssignableFrom(class1))
            {
                obj = Short.valueOf(Short.parseShort(s));
            }
            else if (class1.isAssignableFrom(Byte.TYPE) || (java.lang.Byte.class).isAssignableFrom(class1))
            {
                obj = Byte.valueOf(Byte.parseByte(s));
            }
            else
            {
                throw new IllegalArgumentException(String.format("MLProp declared on %s of type %s, an unsupported type", new Object[]
                        {
                            s1, class1.getName()
                        }));
            }

            if (((Number)(obj)).doubleValue() < modproperty.min() || ((Number)(obj)).doubleValue() > modproperty.max())
            {
                Loader.log.warning(String.format("Configuration for %s.%s found value %s outside acceptable range %s,%s", new Object[]
                        {
                            s2, s1, obj, Double.valueOf(modproperty.min()), Double.valueOf(modproperty.max())
                        }));
                return null;
            }
            else
            {
                return obj;
            }
        }
        else
        {
            throw new IllegalArgumentException(String.format("MLProp declared on %s of type %s, an unsupported type", new Object[]
                    {
                        s1, class1.getName()
                    }));
        }
    }

    private String extractValue(Object obj)
    {
        if ((java.lang.String.class).isInstance(obj))
        {
            return (String)obj;
        }

        if ((java.lang.Number.class).isInstance(obj) || (java.lang.Boolean.class).isInstance(obj))
        {
            return String.valueOf(obj);
        }
        else
        {
            throw new IllegalArgumentException("MLProp declared on non-standard type");
        }
    }

    public void init()
    {
        mod.load();
    }

    public void postInit()
    {
        mod.modsLoaded();
    }

    public String getName()
    {
        return mod == null ? modClazz.getSimpleName() : mod.getName();
    }

    public static ModContainer findContainerFor(BaseMod basemod)
    {
        for (Iterator iterator = Loader.getModList().iterator(); iterator.hasNext();)
        {
            ModContainer modcontainer = (ModContainer)iterator.next();

            if (modcontainer.matches(basemod))
            {
                return modcontainer;
            }
        }

        return null;
    }

    public String getSortingRules()
    {
        if (mod != null)
        {
            return mod.getPriorities();
        }
        else
        {
            return "";
        }
    }

    public boolean matches(Object obj)
    {
        return modClazz.isInstance(obj);
    }

    public static List findAll(Class class1)
    {
        ArrayList arraylist = new ArrayList();
        Iterator iterator = Loader.getModList().iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            ModContainer modcontainer = (ModContainer)iterator.next();

            if ((modcontainer instanceof ModLoaderModContainer) && modcontainer.getMod() != null)
            {
                arraylist.add(((ModLoaderModContainer)modcontainer).mod);
            }
        }
        while (true);

        return arraylist;
    }

    public File getSource()
    {
        return modSource;
    }

    public Object getMod()
    {
        return mod;
    }

    public int lookupFuelValue(int i, int j)
    {
        return mod.addFuel(i, j);
    }

    public boolean wantsPickupNotification()
    {
        return true;
    }

    public IPickupNotifier getPickupNotifier()
    {
        return mod;
    }

    public boolean wantsToDispense()
    {
        return true;
    }

    public IDispenseHandler getDispenseHandler()
    {
        return mod;
    }

    public boolean wantsCraftingNotification()
    {
        return true;
    }

    public ICraftingHandler getCraftingHandler()
    {
        return mod;
    }

    private void computeDependencies()
    {
        dependencies = new ArrayList();
        preDependencies = new ArrayList();
        postDependencies = new ArrayList();

        if (mod.getPriorities() == null || mod.getPriorities().length() == 0)
        {
            return;
        }

        boolean flag = false;

        for (StringTokenizer stringtokenizer = new StringTokenizer(mod.getPriorities(), ";"); stringtokenizer.hasMoreTokens();)
        {
            String s = stringtokenizer.nextToken();
            String as[] = s.split(":");

            if (as.length < 2)
            {
                flag = true;
            }
            else if ("required-before".equals(as[0]) || "required-after".equals(as[0]))
            {
                if (!as[1].trim().equals("*"))
                {
                    dependencies.add(as[1]);
                }
                else
                {
                    flag = true;
                }
            }
            else if ("required-before".equals(as[0]) || "before".equals(as[0]))
            {
                postDependencies.add(as[1]);
            }
            else if ("required-after".equals(as[0]) || "after".equals(as[0]))
            {
                preDependencies.add(as[1]);
            }
            else
            {
                flag = true;
            }
        }

        if (flag)
        {
            FMLCommonHandler.instance().getFMLLogger().warning(String.format("The mod %s has an incorrect dependency string {%s}", new Object[]
                    {
                        mod.getName(), mod.getPriorities()
                    }));
        }
    }

    public List getDependencies()
    {
        if (dependencies == null)
        {
            computeDependencies();
        }

        return dependencies;
    }

    public List getPostDepends()
    {
        if (dependencies == null)
        {
            computeDependencies();
        }

        return postDependencies;
    }

    public List getPreDepends()
    {
        if (dependencies == null)
        {
            computeDependencies();
        }

        return preDependencies;
    }

    public String toString()
    {
        return modClazz.getSimpleName();
    }

    public boolean wantsNetworkPackets()
    {
        return true;
    }

    public INetworkHandler getNetworkHandler()
    {
        return mod;
    }

    public boolean ownsNetworkChannel(String s)
    {
        return FMLCommonHandler.instance().getChannelListFor(this).contains(s);
    }

    public boolean wantsConsoleCommands()
    {
        return true;
    }

    public IConsoleHandler getConsoleHandler()
    {
        return mod;
    }

    public boolean wantsPlayerTracking()
    {
        return true;
    }

    public IPlayerTracker getPlayerTracker()
    {
        return mod;
    }

    public void addKeyHandler(IKeyHandler ikeyhandler)
    {
        if (keyHandlers == null)
        {
            keyHandlers = new ArrayList();
        }

        Iterator iterator = keyHandlers.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            IKeyHandler ikeyhandler1 = (IKeyHandler)iterator.next();

            if (ikeyhandler1.getKeyBinding() == ikeyhandler.getKeyBinding())
            {
                iterator.remove();
            }
        }
        while (true);

        keyHandlers.add(ikeyhandler);
    }

    public List getKeys()
    {
        if (keyHandlers == null)
        {
            return Collections.emptyList();
        }
        else
        {
            return keyHandlers;
        }
    }

    public void setSourceType(cpw.mods.fml.common.ModContainer.SourceType sourcetype)
    {
        sourceType = sourcetype;
    }

    public cpw.mods.fml.common.ModContainer.SourceType getSourceType()
    {
        return sourceType;
    }

    public ModMetadata getMetadata()
    {
        return metadata;
    }

    public void setMetadata(ModMetadata modmetadata)
    {
        metadata = modmetadata;
    }

    public void gatherRenderers(Map map)
    {
        mod.onRenderHarvest(map);
    }

    public void requestAnimations()
    {
        mod.onRegisterAnimations();
    }

    public String getVersion()
    {
        if (mod == null || mod.getVersion() == null)
        {
            return "Not available";
        }
        else
        {
            return mod.getVersion();
        }
    }

    public ProxyInjector findSidedProxy()
    {
        if (sidedProxy == null)
        {
            sidedProxy = FMLCommonHandler.instance().getSidedDelegate().findSidedProxyOn(mod);

            if (sidedProxy == null)
            {
                sidedProxy = NULLPROXY;
            }
        }

        return sidedProxy != NULLPROXY ? sidedProxy : null;
    }

    public void keyBindEvent(Object obj)
    {
        mod.keyBindingEvent(obj);
    }

    public BaseModTicker getTickHandler()
    {
        return tickHandler;
    }
}
