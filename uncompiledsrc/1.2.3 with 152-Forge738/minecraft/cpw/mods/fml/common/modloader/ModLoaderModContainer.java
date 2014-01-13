package cpw.mods.fml.common.modloader;

import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ILanguageAdapter$JavaAdapter;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderException;
import cpw.mods.fml.common.MetadataCollection;
import cpw.mods.fml.common.ModClassLoader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.ProxyInjector;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.discovery.ASMDataTable;
import cpw.mods.fml.common.discovery.ASMDataTable$ASMData;
import cpw.mods.fml.common.discovery.ContainerType;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.common.versioning.ArtifactVersion;
import cpw.mods.fml.common.versioning.DefaultArtifactVersion;
import cpw.mods.fml.common.versioning.VersionRange;
import cpw.mods.fml.relauncher.Side;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import net.minecraft.command.ICommand;

public class ModLoaderModContainer implements ModContainer
{
    public BaseModProxy mod;
    private File modSource;
    public Set requirements = Sets.newHashSet();
    public ArrayList dependencies = Lists.newArrayList();
    public ArrayList dependants = Lists.newArrayList();
    private ContainerType sourceType;
    private ModMetadata metadata;
    private ProxyInjector sidedProxy;
    private BaseModTicker gameTickHandler;
    private BaseModTicker guiTickHandler;
    private String modClazzName;
    private String modId;
    private EventBus bus;
    private LoadController controller;
    private boolean enabled = true;
    private String sortingProperties;
    private ArtifactVersion processedVersion;
    private boolean isNetworkMod;
    private List serverCommands = Lists.newArrayList();

    public ModLoaderModContainer(String var1, File var2, String var3)
    {
        this.modClazzName = var1;
        this.modSource = var2;
        this.modId = var1.contains(".") ? var1.substring(var1.lastIndexOf(46) + 1) : var1;
        this.sortingProperties = Strings.isNullOrEmpty(var3) ? "" : var3;
    }

    ModLoaderModContainer(BaseModProxy var1)
    {
        this.mod = var1;
        this.gameTickHandler = new BaseModTicker(var1, false);
        this.guiTickHandler = new BaseModTicker(var1, true);
    }

    private void configureMod(Class var1, ASMDataTable var2)
    {
        File var3 = Loader.instance().getConfigDir();
        File var4 = new File(var3, String.format("%s.cfg", new Object[] {this.getModId()}));
        Properties var5 = new Properties();
        boolean var6 = false;
        boolean var7 = false;

        if (var4.exists())
        {
            try
            {
                FMLLog.fine("Reading existing configuration file for %s : %s", new Object[] {this.getModId(), var4.getName()});
                FileReader var8 = new FileReader(var4);
                var5.load(var8);
                var8.close();
            }
            catch (Exception var38)
            {
                FMLLog.log(Level.SEVERE, (Throwable)var38, "Error occured reading mod configuration file %s", new Object[] {var4.getName()});
                throw new LoaderException(var38);
            }

            var6 = true;
        }

        StringBuffer var43 = new StringBuffer();
        var43.append("MLProperties: name (type:default) min:max -- information\n");
        ArrayList var9 = Lists.newArrayList();
        boolean var28 = false;

        try
        {
            var28 = true;
            Iterator var10 = Sets.union(var2.getAnnotationsFor(this).get("net.minecraft.src.MLProp"), var2.getAnnotationsFor(this).get("MLProp")).iterator();

            while (var10.hasNext())
            {
                ASMDataTable$ASMData var11 = (ASMDataTable$ASMData)var10.next();

                if (var11.getClassName().equals(this.modClazzName))
                {
                    try
                    {
                        var9.add(new ModProperty(var1.getDeclaredField(var11.getObjectName()), var11.getAnnotationInfo()));
                        FMLLog.finest("Found an MLProp field %s in %s", new Object[] {var11.getObjectName(), this.getModId()});
                    }
                    catch (Exception var39)
                    {
                        FMLLog.log(Level.WARNING, (Throwable)var39, "An error occured trying to access field %s in mod %s", new Object[] {var11.getObjectName(), this.getModId()});
                    }
                }
            }

            var10 = var9.iterator();

            while (true)
            {
                if (!var10.hasNext())
                {
                    var28 = false;
                    break;
                }

                ModProperty var45 = (ModProperty)var10.next();

                if (!Modifier.isStatic(var45.field().getModifiers()))
                {
                    FMLLog.info("The MLProp field %s in mod %s appears not to be static", new Object[] {var45.field().getName(), this.getModId()});
                }
                else
                {
                    FMLLog.finest("Considering MLProp field %s", new Object[] {var45.field().getName()});
                    Field var12 = var45.field();
                    String var13 = !Strings.nullToEmpty(var45.name()).isEmpty() ? var45.name() : var12.getName();
                    String var14 = null;
                    Object var15 = null;

                    try
                    {
                        var15 = var12.get((Object)null);
                        var14 = var5.getProperty(var13, this.extractValue(var15));
                        Object var16 = this.parseValue(var14, var45, var12.getType(), var13);
                        FMLLog.finest("Configuration for %s.%s found values default: %s, configured: %s, interpreted: %s", new Object[] {this.modClazzName, var13, var15, var14, var16});

                        if (var16 != null && !var16.equals(var15))
                        {
                            FMLLog.finest("Configuration for %s.%s value set to: %s", new Object[] {this.modClazzName, var13, var16});
                            var12.set((Object)null, var16);
                        }
                    }
                    catch (Exception var40)
                    {
                        FMLLog.log(Level.SEVERE, (Throwable)var40, "Invalid configuration found for %s in %s", new Object[] {var13, var4.getName()});
                        throw new LoaderException(var40);
                    }
                    finally
                    {
                        var43.append(String.format("MLProp : %s (%s:%s", new Object[] {var13, var12.getType().getName(), var15}));

                        if (var45.min() != Double.MIN_VALUE)
                        {
                            var43.append(",>=").append(String.format("%.1f", new Object[] {Double.valueOf(var45.min())}));
                        }

                        if (var45.max() != Double.MAX_VALUE)
                        {
                            var43.append(",<=").append(String.format("%.1f", new Object[] {Double.valueOf(var45.max())}));
                        }

                        var43.append(")");

                        if (!Strings.nullToEmpty(var45.info()).isEmpty())
                        {
                            var43.append(" -- ").append(var45.info());
                        }

                        if (var14 != null)
                        {
                            var5.setProperty(var13, this.extractValue(var14));
                        }

                        var43.append("\n");
                    }

                    var7 = true;
                }
            }
        }
        finally
        {
            if (var28)
            {
                if (!var7 && !var6)
                {
                    FMLLog.fine("No MLProp configuration for %s found or required. No file written", new Object[] {this.getModId()});
                    return;
                }

                if (!var7 && var6)
                {
                    File var48 = new File(var4.getParent(), var4.getName() + ".bak");
                    FMLLog.fine("MLProp configuration file for %s found but not required. Attempting to rename file to %s", new Object[] {this.getModId(), var48.getName()});
                    boolean var20 = var4.renameTo(var48);

                    if (var20)
                    {
                        FMLLog.fine("Unused MLProp configuration file for %s renamed successfully to %s", new Object[] {this.getModId(), var48.getName()});
                    }
                    else
                    {
                        FMLLog.fine("Unused MLProp configuration file for %s renamed UNSUCCESSFULLY to %s", new Object[] {this.getModId(), var48.getName()});
                    }

                    return;
                }

                try
                {
                    FileWriter var19 = new FileWriter(var4);
                    var5.store(var19, var43.toString());
                    var19.close();
                    FMLLog.fine("Configuration for %s written to %s", new Object[] {this.getModId(), var4.getName()});
                }
                catch (IOException var36)
                {
                    FMLLog.log(Level.SEVERE, (Throwable)var36, "Error trying to write the config file %s", new Object[] {var4.getName()});
                    throw new LoaderException(var36);
                }
            }
        }

        if (!var7 && !var6)
        {
            FMLLog.fine("No MLProp configuration for %s found or required. No file written", new Object[] {this.getModId()});
        }
        else if (!var7 && var6)
        {
            File var44 = new File(var4.getParent(), var4.getName() + ".bak");
            FMLLog.fine("MLProp configuration file for %s found but not required. Attempting to rename file to %s", new Object[] {this.getModId(), var44.getName()});
            boolean var47 = var4.renameTo(var44);

            if (var47)
            {
                FMLLog.fine("Unused MLProp configuration file for %s renamed successfully to %s", new Object[] {this.getModId(), var44.getName()});
            }
            else
            {
                FMLLog.fine("Unused MLProp configuration file for %s renamed UNSUCCESSFULLY to %s", new Object[] {this.getModId(), var44.getName()});
            }
        }
        else
        {
            try
            {
                FileWriter var46 = new FileWriter(var4);
                var5.store(var46, var43.toString());
                var46.close();
                FMLLog.fine("Configuration for %s written to %s", new Object[] {this.getModId(), var4.getName()});
            }
            catch (IOException var37)
            {
                FMLLog.log(Level.SEVERE, (Throwable)var37, "Error trying to write the config file %s", new Object[] {var4.getName()});
                throw new LoaderException(var37);
            }
        }
    }

    private Object parseValue(String var1, ModProperty var2, Class var3, String var4)
    {
        if (var3.isAssignableFrom(String.class))
        {
            return var1;
        }
        else if (!var3.isAssignableFrom(Boolean.TYPE) && !var3.isAssignableFrom(Boolean.class))
        {
            if (!Number.class.isAssignableFrom(var3) && !var3.isPrimitive())
            {
                throw new IllegalArgumentException(String.format("MLProp declared on %s of type %s, an unsupported type", new Object[] {var4, var3.getName()}));
            }
            else
            {
                Object var5 = null;

                if (!var3.isAssignableFrom(Double.TYPE) && !Double.class.isAssignableFrom(var3))
                {
                    if (!var3.isAssignableFrom(Float.TYPE) && !Float.class.isAssignableFrom(var3))
                    {
                        if (!var3.isAssignableFrom(Long.TYPE) && !Long.class.isAssignableFrom(var3))
                        {
                            if (!var3.isAssignableFrom(Integer.TYPE) && !Integer.class.isAssignableFrom(var3))
                            {
                                if (!var3.isAssignableFrom(Short.TYPE) && !Short.class.isAssignableFrom(var3))
                                {
                                    if (!var3.isAssignableFrom(Byte.TYPE) && !Byte.class.isAssignableFrom(var3))
                                    {
                                        throw new IllegalArgumentException(String.format("MLProp declared on %s of type %s, an unsupported type", new Object[] {var4, var3.getName()}));
                                    }

                                    var5 = Byte.valueOf(Byte.parseByte(var1));
                                }
                                else
                                {
                                    var5 = Short.valueOf(Short.parseShort(var1));
                                }
                            }
                            else
                            {
                                var5 = Integer.valueOf(Integer.parseInt(var1));
                            }
                        }
                        else
                        {
                            var5 = Long.valueOf(Long.parseLong(var1));
                        }
                    }
                    else
                    {
                        var5 = Float.valueOf(Float.parseFloat(var1));
                    }
                }
                else
                {
                    var5 = Double.valueOf(Double.parseDouble(var1));
                }

                double var6 = ((Number)var5).doubleValue();

                if ((var2.min() == Double.MIN_VALUE || var6 >= var2.min()) && (var2.max() == Double.MAX_VALUE || var6 <= var2.max()))
                {
                    return var5;
                }
                else
                {
                    FMLLog.warning("Configuration for %s.%s found value %s outside acceptable range %s,%s", new Object[] {this.modClazzName, var4, var5, Double.valueOf(var2.min()), Double.valueOf(var2.max())});
                    return null;
                }
            }
        }
        else
        {
            return Boolean.valueOf(Boolean.parseBoolean(var1));
        }
    }

    private String extractValue(Object var1)
    {
        if (String.class.isInstance(var1))
        {
            return (String)var1;
        }
        else if (!Number.class.isInstance(var1) && !Boolean.class.isInstance(var1))
        {
            throw new IllegalArgumentException("MLProp declared on non-standard type");
        }
        else
        {
            return String.valueOf(var1);
        }
    }

    public String getName()
    {
        return this.mod != null ? this.mod.getName() : this.modId;
    }

    public String getSortingRules()
    {
        return this.sortingProperties;
    }

    public boolean matches(Object var1)
    {
        return this.mod == var1;
    }

    public static List findAll(Class var0)
    {
        ArrayList var1 = new ArrayList();
        Iterator var2 = Loader.instance().getActiveModList().iterator();

        while (var2.hasNext())
        {
            ModContainer var3 = (ModContainer)var2.next();

            if (var3 instanceof ModLoaderModContainer && var3.getMod() != null)
            {
                var1.add(((ModLoaderModContainer)var3).mod);
            }
        }

        return var1;
    }

    public File getSource()
    {
        return this.modSource;
    }

    public Object getMod()
    {
        return this.mod;
    }

    public Set getRequirements()
    {
        return this.requirements;
    }

    public List getDependants()
    {
        return this.dependants;
    }

    public List getDependencies()
    {
        return this.dependencies;
    }

    public String toString()
    {
        return this.modId;
    }

    public ModMetadata getMetadata()
    {
        return this.metadata;
    }

    public String getVersion()
    {
        return this.mod != null && this.mod.getVersion() != null ? this.mod.getVersion() : "Not available";
    }

    public BaseModTicker getGameTickHandler()
    {
        return this.gameTickHandler;
    }

    public BaseModTicker getGUITickHandler()
    {
        return this.guiTickHandler;
    }

    public String getModId()
    {
        return this.modId;
    }

    public void bindMetadata(MetadataCollection var1)
    {
        ImmutableMap var2 = ImmutableMap.builder().put("name", this.modId).put("version", "1.0").build();
        this.metadata = var1.getMetadataForId(this.modId, var2);
        Loader.instance().computeDependencies(this.sortingProperties, this.getRequirements(), this.getDependencies(), this.getDependants());
    }

    public void setEnabledState(boolean var1)
    {
        this.enabled = var1;
    }

    public boolean registerBus(EventBus var1, LoadController var2)
    {
        if (this.enabled)
        {
            FMLLog.fine("Enabling mod %s", new Object[] {this.getModId()});
            this.bus = var1;
            this.controller = var2;
            var1.register(this);
            return true;
        }
        else
        {
            return false;
        }
    }

    @Subscribe
    public void constructMod(FMLConstructionEvent var1)
    {
        try
        {
            ModClassLoader var2 = var1.getModClassLoader();
            var2.addFile(this.modSource);
            EnumSet var3 = EnumSet.noneOf(TickType.class);
            this.gameTickHandler = new BaseModTicker(var3, false);
            this.guiTickHandler = new BaseModTicker(var3.clone(), true);
            Class var4 = var2.loadBaseModClass(this.modClazzName);
            this.configureMod(var4, var1.getASMHarvestedData());
            this.isNetworkMod = FMLNetworkHandler.instance().registerNetworkMod(this, var4, var1.getASMHarvestedData());
            ModLoaderNetworkHandler var5 = null;

            if (!this.isNetworkMod)
            {
                FMLLog.fine("Injecting dummy network mod handler for BaseMod %s", new Object[] {this.getModId()});
                var5 = new ModLoaderNetworkHandler(this);
                FMLNetworkHandler.instance().registerNetworkMod(var5);
            }

            Constructor var6 = var4.getConstructor(new Class[0]);
            var6.setAccessible(true);
            this.mod = (BaseModProxy)var4.newInstance();

            if (var5 != null)
            {
                var5.setBaseMod(this.mod);
            }

            ProxyInjector.inject(this, var1.getASMHarvestedData(), FMLCommonHandler.instance().getSide(), new ILanguageAdapter$JavaAdapter());
        }
        catch (Exception var7)
        {
            this.controller.errorOccurred(this, var7);
            Throwables.propagateIfPossible(var7);
        }
    }

    @Subscribe
    public void preInit(FMLPreInitializationEvent var1)
    {
        try
        {
            this.gameTickHandler.setMod(this.mod);
            this.guiTickHandler.setMod(this.mod);
            TickRegistry.registerTickHandler(this.gameTickHandler, Side.CLIENT);
            TickRegistry.registerTickHandler(this.guiTickHandler, Side.CLIENT);
            GameRegistry.registerWorldGenerator(ModLoaderHelper.buildWorldGenHelper(this.mod));
            GameRegistry.registerFuelHandler(ModLoaderHelper.buildFuelHelper(this.mod));
            GameRegistry.registerCraftingHandler(ModLoaderHelper.buildCraftingHelper(this.mod));
            GameRegistry.registerPickupHandler(ModLoaderHelper.buildPickupHelper(this.mod));
            NetworkRegistry.instance().registerChatListener(ModLoaderHelper.buildChatListener(this.mod));
            NetworkRegistry.instance().registerConnectionHandler(ModLoaderHelper.buildConnectionHelper(this.mod));
        }
        catch (Exception var3)
        {
            this.controller.errorOccurred(this, var3);
            Throwables.propagateIfPossible(var3);
        }
    }

    @Subscribe
    public void init(FMLInitializationEvent var1)
    {
        try
        {
            this.mod.load();
        }
        catch (Throwable var3)
        {
            this.controller.errorOccurred(this, var3);
            Throwables.propagateIfPossible(var3);
        }
    }

    @Subscribe
    public void postInit(FMLPostInitializationEvent var1)
    {
        try
        {
            this.mod.modsLoaded();
        }
        catch (Throwable var3)
        {
            this.controller.errorOccurred(this, var3);
            Throwables.propagateIfPossible(var3);
        }
    }

    @Subscribe
    public void loadComplete(FMLLoadCompleteEvent var1)
    {
        ModLoaderHelper.finishModLoading(this);
    }

    @Subscribe
    public void serverStarting(FMLServerStartingEvent var1)
    {
        Iterator var2 = this.serverCommands.iterator();

        while (var2.hasNext())
        {
            ICommand var3 = (ICommand)var2.next();
            var1.registerServerCommand(var3);
        }
    }

    public ArtifactVersion getProcessedVersion()
    {
        if (this.processedVersion == null)
        {
            this.processedVersion = new DefaultArtifactVersion(this.modId, this.getVersion());
        }

        return this.processedVersion;
    }

    public boolean isImmutable()
    {
        return false;
    }

    public boolean isNetworkMod()
    {
        return this.isNetworkMod;
    }

    public String getDisplayVersion()
    {
        return this.metadata != null ? this.metadata.version : this.getVersion();
    }

    public void addServerCommand(ICommand var1)
    {
        this.serverCommands.add(var1);
    }

    public VersionRange acceptableMinecraftVersionRange()
    {
        return Loader.instance().getMinecraftModContainer().getStaticVersionRange();
    }

    public Certificate getSigningCertificate()
    {
        return null;
    }
}
