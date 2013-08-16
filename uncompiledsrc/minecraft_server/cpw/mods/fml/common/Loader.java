package cpw.mods.fml.common;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multisets;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;
import com.google.common.collect.TreeMultimap;
import com.google.common.collect.Multiset.Entry;
import com.google.common.collect.Sets.SetView;
import cpw.mods.fml.common.Loader$1;
import cpw.mods.fml.common.Loader$2;
import cpw.mods.fml.common.Loader$ModIdComparator;
import cpw.mods.fml.common.LoaderState$ModState;
import cpw.mods.fml.common.discovery.ModDiscoverer;
import cpw.mods.fml.common.event.FMLInterModComms$IMCEvent;
import cpw.mods.fml.common.event.FMLLoadEvent;
import cpw.mods.fml.common.functions.ArtifactVersionNameFunction;
import cpw.mods.fml.common.functions.ModIdFunction;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.toposort.ModSorter;
import cpw.mods.fml.common.toposort.ModSortingException;
import cpw.mods.fml.common.toposort.ModSortingException$SortingExceptionData;
import cpw.mods.fml.common.versioning.ArtifactVersion;
import cpw.mods.fml.common.versioning.VersionParser;
import cpw.mods.fml.relauncher.FMLRelaunchLog;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import net.minecraft.crash.CallableMinecraftVersion;
import net.minecraft.crash.CrashReport;

public class Loader
{
    private static final Splitter DEPENDENCYPARTSPLITTER = Splitter.on(":").omitEmptyStrings().trimResults();
    private static final Splitter DEPENDENCYSPLITTER = Splitter.on(";").omitEmptyStrings().trimResults();
    private static Loader instance;
    private static String major;
    private static String minor;
    private static String rev;
    private static String build;
    private static String mccversion;
    private static String mcpversion;
    private ModClassLoader modClassLoader = new ModClassLoader(this.getClass().getClassLoader());
    private List mods;
    private Map namedMods;
    private File canonicalConfigDir;
    private File canonicalMinecraftDir;
    private Exception capturedError;
    private File canonicalModsDir;
    private LoadController modController;
    private MinecraftDummyContainer minecraft;
    private MCPDummyContainer mcp;
    private static File minecraftDir;
    private static List injectedContainers;
    private File loggingProperties;
    private ImmutableMap fmlBrandingProperties;

    public static Loader instance()
    {
        if (instance == null)
        {
            instance = new Loader();
        }

        return instance;
    }

    public static void injectData(Object ... var0)
    {
        major = (String)var0[0];
        minor = (String)var0[1];
        rev = (String)var0[2];
        build = (String)var0[3];
        mccversion = (String)var0[4];
        mcpversion = (String)var0[5];
        minecraftDir = (File)var0[6];
        injectedContainers = (List)var0[7];
    }

    private Loader()
    {
        String var1 = (new CallableMinecraftVersion((CrashReport)null)).minecraftVersion();

        if (!mccversion.equals(var1))
        {
            FMLLog.severe("This version of FML is built for Minecraft %s, we have detected Minecraft %s in your minecraft jar file", new Object[] {mccversion, var1});
            throw new LoaderException();
        }
        else
        {
            this.minecraft = new MinecraftDummyContainer(var1);
            this.mcp = new MCPDummyContainer(MetadataCollection.from(this.getClass().getResourceAsStream("/mcpmod.info"), "MCP").getMetadataForId("mcp", (Map)null));
        }
    }

    private void sortModList()
    {
        FMLLog.finer("Verifying mod requirements are satisfied", new Object[0]);
        boolean var16 = false;
        Iterator var2;
        ModContainer var3;

        try
        {
            var16 = true;
            HashBiMap var1 = HashBiMap.create();
            var2 = this.getActiveModList().iterator();
            label205:

            while (true)
            {
                if (!var2.hasNext())
                {
                    var2 = this.getActiveModList().iterator();
                    HashSet var5;

                    do
                    {
                        if (!var2.hasNext())
                        {
                            FMLLog.finer("All mod requirements are satisfied", new Object[0]);
                            ModSorter var20 = new ModSorter(this.getActiveModList(), this.namedMods);

                            try
                            {
                                FMLLog.finer("Sorting mods into an ordered list", new Object[0]);
                                List var21 = var20.sort();
                                this.modController.getActiveModList().clear();
                                this.modController.getActiveModList().addAll(var21);
                                this.mods.removeAll(var21);
                                var21.addAll(this.mods);
                                this.mods = var21;
                                FMLLog.finer("Mod sorting completed successfully", new Object[0]);
                                var16 = false;
                                break label205;
                            }
                            catch (ModSortingException var17)
                            {
                                FMLLog.severe("A dependency cycle was detected in the input mod set so an ordering cannot be determined", new Object[0]);
                                ModSortingException$SortingExceptionData var22 = var17.getExceptionData();
                                FMLLog.severe("The first mod in the cycle is %s", new Object[] {var22.getFirstBadNode()});
                                FMLLog.severe("The mod cycle involves", new Object[0]);
                                Iterator var23 = var22.getVisitedNodes().iterator();

                                while (var23.hasNext())
                                {
                                    ModContainer var24 = (ModContainer)var23.next();
                                    FMLLog.severe("%s : before: %s, after: %s", new Object[] {var24.toString(), var24.getDependants(), var24.getDependencies()});
                                }

                                FMLLog.log(Level.SEVERE, (Throwable)var17, "The full error", new Object[0]);
                                throw var17;
                            }
                        }

                        var3 = (ModContainer)var2.next();

                        if (!var3.acceptableMinecraftVersionRange().containsVersion(this.minecraft.getProcessedVersion()))
                        {
                            FMLLog.severe("The mod %s does not wish to run in Minecraft version %s. You will have to remove it to play.", new Object[] {var3.getModId(), this.getMCVersionString()});
                            throw new WrongMinecraftVersionException(var3);
                        }

                        ImmutableMap var4 = Maps.uniqueIndex(var3.getRequirements(), new ArtifactVersionNameFunction());
                        var5 = Sets.newHashSet();
                        SetView var6 = Sets.difference(var4.keySet(), var1.keySet());

                        if (!var6.isEmpty())
                        {
                            FMLLog.severe("The mod %s (%s) requires mods %s to be available", new Object[] {var3.getModId(), var3.getName(), var6});
                            Iterator var25 = var6.iterator();

                            while (var25.hasNext())
                            {
                                String var26 = (String)var25.next();
                                var5.add(var4.get(var26));
                            }

                            throw new MissingModsException(var5);
                        }

                        ImmutableList var7 = ImmutableList.builder().addAll(var3.getDependants()).addAll(var3.getDependencies()).build();
                        Iterator var8 = var7.iterator();

                        while (var8.hasNext())
                        {
                            ArtifactVersion var9 = (ArtifactVersion)var8.next();

                            if (var1.containsKey(var9.getLabel()) && !var9.containsVersion((ArtifactVersion)var1.get(var9.getLabel())))
                            {
                                var5.add(var9);
                            }
                        }
                    }
                    while (var5.isEmpty());

                    FMLLog.severe("The mod %s (%s) requires mod versions %s to be available", new Object[] {var3.getModId(), var3.getName(), var5});
                    throw new MissingModsException(var5);
                }

                var3 = (ModContainer)var2.next();
                var1.put(var3.getModId(), var3.getProcessedVersion());
            }
        }
        finally
        {
            if (var16)
            {
                FMLLog.fine("Mod sorting data", new Object[0]);
                int var11 = this.mods.size();
                Iterator var12 = this.getActiveModList().iterator();

                while (var12.hasNext())
                {
                    ModContainer var13 = (ModContainer)var12.next();

                    if (!var13.isImmutable())
                    {
                        FMLLog.fine("\t%s(%s:%s): %s (%s)", new Object[] {var13.getModId(), var13.getName(), var13.getVersion(), var13.getSource().getName(), var13.getSortingRules()});
                        --var11;
                    }
                }

                if (var11 == this.mods.size())
                {
                    FMLLog.fine("No user mods found to sort", new Object[0]);
                }
            }
        }

        FMLLog.fine("Mod sorting data", new Object[0]);
        int var19 = this.mods.size();
        var2 = this.getActiveModList().iterator();

        while (var2.hasNext())
        {
            var3 = (ModContainer)var2.next();

            if (!var3.isImmutable())
            {
                FMLLog.fine("\t%s(%s:%s): %s (%s)", new Object[] {var3.getModId(), var3.getName(), var3.getVersion(), var3.getSource().getName(), var3.getSortingRules()});
                --var19;
            }
        }

        if (var19 == this.mods.size())
        {
            FMLLog.fine("No user mods found to sort", new Object[0]);
        }
    }

    private ModDiscoverer identifyMods()
    {
        FMLLog.fine("Building injected Mod Containers %s", new Object[] {injectedContainers});
        this.mods.add(new InjectedModContainer(this.mcp, new File("minecraft.jar")));
        File var1 = new File(minecraftDir, "coremods");
        ModContainer var4;

        for (Iterator var2 = injectedContainers.iterator(); var2.hasNext(); this.mods.add(new InjectedModContainer(var4, var1)))
        {
            String var3 = (String)var2.next();

            try
            {
                var4 = (ModContainer)Class.forName(var3, true, this.modClassLoader).newInstance();
            }
            catch (Exception var6)
            {
                FMLLog.log(Level.SEVERE, (Throwable)var6, "A problem occured instantiating the injected mod container %s", new Object[] {var3});
                throw new LoaderException(var6);
            }
        }

        ModDiscoverer var7 = new ModDiscoverer();
        FMLLog.fine("Attempting to load mods contained in the minecraft jar file and associated classes", new Object[0]);
        var7.findClasspathMods(this.modClassLoader);
        FMLLog.fine("Minecraft jar mods loaded successfully", new Object[0]);
        FMLLog.info("Searching %s for mods", new Object[] {this.canonicalModsDir.getAbsolutePath()});
        var7.findModDirMods(this.canonicalModsDir);
        this.mods.addAll(var7.identifyMods());
        this.identifyDuplicates(this.mods);
        this.namedMods = Maps.uniqueIndex(this.mods, new ModIdFunction());
        FMLLog.info("Forge Mod Loader has identified %d mod%s to load", new Object[] {Integer.valueOf(this.mods.size()), this.mods.size() != 1 ? "s" : ""});
        Iterator var8 = this.namedMods.keySet().iterator();

        while (var8.hasNext())
        {
            String var9 = (String)var8.next();
            FMLLog.makeLog(var9);
        }

        return var7;
    }

    private void identifyDuplicates(List var1)
    {
        TreeMultimap var2 = TreeMultimap.create(new Loader$ModIdComparator(this, (Loader$1)null), Ordering.arbitrary());
        Iterator var3 = var1.iterator();

        while (var3.hasNext())
        {
            ModContainer var4 = (ModContainer)var3.next();

            if (var4.getSource() != null)
            {
                var2.put(var4, var4.getSource());
            }
        }

        ImmutableMultiset var7 = Multisets.copyHighestCountFirst(var2.keys());
        LinkedHashMultimap var8 = LinkedHashMultimap.create();
        Iterator var5 = var7.entrySet().iterator();

        while (var5.hasNext())
        {
            Entry var6 = (Entry)var5.next();

            if (var6.getCount() > 1)
            {
                FMLLog.severe("Found a duplicate mod %s at %s", new Object[] {((ModContainer)var6.getElement()).getModId(), var2.get(var6.getElement())});
                var8.putAll(var6.getElement(), var2.get(var6.getElement()));
            }
        }

        if (!var8.isEmpty())
        {
            throw new DuplicateModsFoundException(var8);
        }
    }

    private void initializeLoader()
    {
        File var1 = new File(minecraftDir, "mods");
        File var2 = new File(minecraftDir, "config");
        String var3;
        String var4;

        try
        {
            this.canonicalMinecraftDir = minecraftDir.getCanonicalFile();
            var3 = var1.getCanonicalPath();
            var4 = var2.getCanonicalPath();
            this.canonicalConfigDir = var2.getCanonicalFile();
            this.canonicalModsDir = var1.getCanonicalFile();
        }
        catch (IOException var6)
        {
            FMLLog.log(Level.SEVERE, (Throwable)var6, "Failed to resolve loader directories: mods : %s ; config %s", new Object[] {this.canonicalModsDir.getAbsolutePath(), var2.getAbsolutePath()});
            throw new LoaderException(var6);
        }

        boolean var5;

        if (!this.canonicalModsDir.exists())
        {
            FMLLog.info("No mod directory found, creating one: %s", new Object[] {var3});
            var5 = this.canonicalModsDir.mkdir();

            if (!var5)
            {
                FMLLog.severe("Unable to create the mod directory %s", new Object[] {var3});
                throw new LoaderException();
            }

            FMLLog.info("Mod directory created successfully", new Object[0]);
        }

        if (!this.canonicalConfigDir.exists())
        {
            FMLLog.fine("No config directory found, creating one: %s", new Object[] {var4});
            var5 = this.canonicalConfigDir.mkdir();

            if (!var5)
            {
                FMLLog.severe("Unable to create the config directory %s", new Object[] {var4});
                throw new LoaderException();
            }

            FMLLog.info("Config directory created successfully", new Object[0]);
        }

        if (!this.canonicalModsDir.isDirectory())
        {
            FMLLog.severe("Attempting to load mods from %s, which is not a directory", new Object[] {var3});
            throw new LoaderException();
        }
        else if (!var2.isDirectory())
        {
            FMLLog.severe("Attempting to load configuration from %s, which is not a directory", new Object[] {var4});
            throw new LoaderException();
        }
        else
        {
            this.loggingProperties = new File(this.canonicalConfigDir, "logging.properties");
            FMLLog.info("Reading custom logging properties from %s", new Object[] {this.loggingProperties.getPath()});
            FMLRelaunchLog.loadLogConfiguration(this.loggingProperties);
            FMLLog.log(Level.OFF, "Logging level for ForgeModLoader logging is set to %s", new Object[] {FMLRelaunchLog.log.getLogger().getLevel()});
        }
    }

    public List getModList()
    {
        return instance().mods != null ? ImmutableList.copyOf(instance().mods) : ImmutableList.of();
    }

    public void loadMods()
    {
        this.initializeLoader();
        this.mods = Lists.newArrayList();
        this.namedMods = Maps.newHashMap();
        this.modController = new LoadController(this);
        this.modController.transition(LoaderState.LOADING, false);
        ModDiscoverer var1 = this.identifyMods();
        this.disableRequestedMods();
        FMLLog.fine("Reloading logging properties from %s", new Object[] {this.loggingProperties.getPath()});
        FMLRelaunchLog.loadLogConfiguration(this.loggingProperties);
        FMLLog.fine("Reloaded logging properties", new Object[0]);
        this.modController.distributeStateMessage(FMLLoadEvent.class);
        this.sortModList();
        this.mods = ImmutableList.copyOf(this.mods);
        Iterator var2 = var1.getNonModLibs().iterator();

        while (var2.hasNext())
        {
            File var3 = (File)var2.next();

            if (var3.isFile())
            {
                FMLLog.info("FML has found a non-mod file %s in your mods directory. It will now be injected into your classpath. This could severe stability issues, it should be removed if possible.", new Object[] {var3.getName()});

                try
                {
                    this.modClassLoader.addFile(var3);
                }
                catch (MalformedURLException var5)
                {
                    FMLLog.log(Level.SEVERE, (Throwable)var5, "Encountered a weird problem with non-mod file injection : %s", new Object[] {var3.getName()});
                }
            }
        }

        this.modController.transition(LoaderState.CONSTRUCTING, false);
        this.modController.distributeStateMessage(LoaderState.CONSTRUCTING, new Object[] {this.modClassLoader, var1.getASMTable()});
        FMLLog.fine("Mod signature data", new Object[0]);
        var2 = this.getActiveModList().iterator();

        while (var2.hasNext())
        {
            ModContainer var6 = (ModContainer)var2.next();
            FMLLog.fine("\t%s(%s:%s): %s (%s)", new Object[] {var6.getModId(), var6.getName(), var6.getVersion(), var6.getSource().getName(), CertificateHelper.getFingerprint(var6.getSigningCertificate())});
        }

        if (this.getActiveModList().isEmpty())
        {
            FMLLog.fine("No user mod signature data found", new Object[0]);
        }

        this.modController.transition(LoaderState.PREINITIALIZATION, false);
        this.modController.distributeStateMessage(LoaderState.PREINITIALIZATION, new Object[] {var1.getASMTable(), this.canonicalConfigDir});
        this.modController.transition(LoaderState.INITIALIZATION, false);
    }

    private void disableRequestedMods()
    {
        String var1 = System.getProperty("fml.modStates", "");
        FMLLog.finer("Received a system property request \'%s\'", new Object[] {var1});
        Map var2 = Splitter.on(CharMatcher.anyOf(";:")).omitEmptyStrings().trimResults().withKeyValueSeparator("=").split(var1);
        FMLLog.finer("System property request managing the state of %d mods", new Object[] {Integer.valueOf(var2.size())});
        HashMap var3 = Maps.newHashMap();
        File var4 = new File(this.canonicalConfigDir, "fmlModState.properties");
        Properties var5 = new Properties();

        if (var4.exists() && var4.isFile())
        {
            FMLLog.finer("Found a mod state file %s", new Object[] {var4.getName()});

            try
            {
                var5.load(new FileReader(var4));
                FMLLog.finer("Loaded states for %d mods from file", new Object[] {Integer.valueOf(var5.size())});
            }
            catch (Exception var9)
            {
                FMLLog.log(Level.INFO, (Throwable)var9, "An error occurred reading the fmlModState.properties file", new Object[0]);
            }
        }

        var3.putAll(Maps.fromProperties(var5));
        var3.putAll(var2);
        FMLLog.fine("After merging, found state information for %d mods", new Object[] {Integer.valueOf(var3.size())});
        Map var6 = Maps.transformValues(var3, new Loader$1(this));
        Iterator var7 = var6.entrySet().iterator();

        while (var7.hasNext())
        {
            java.util.Map.Entry var8 = (java.util.Map.Entry)var7.next();

            if (this.namedMods.containsKey(var8.getKey()))
            {
                FMLLog.info("Setting mod %s to enabled state %b", new Object[] {var8.getKey(), var8.getValue()});
                ((ModContainer)this.namedMods.get(var8.getKey())).setEnabledState(((Boolean)var8.getValue()).booleanValue());
            }
        }
    }

    public static boolean isModLoaded(String var0)
    {
        return instance().namedMods.containsKey(var0) && instance().modController.getModState((ModContainer)instance.namedMods.get(var0)) != LoaderState$ModState.DISABLED;
    }

    public File getConfigDir()
    {
        return this.canonicalConfigDir;
    }

    public String getCrashInformation()
    {
        if (this.modController == null)
        {
            return "";
        }
        else
        {
            StringBuilder var1 = new StringBuilder();
            List var2 = FMLCommonHandler.instance().getBrandings();
            Joiner.on(' ').skipNulls().appendTo(var1, var2.subList(1, var2.size()));

            if (this.modController != null)
            {
                this.modController.printModStates(var1);
            }

            return var1.toString();
        }
    }

    public String getFMLVersionString()
    {
        return String.format("%s.%s.%s.%s", new Object[] {major, minor, rev, build});
    }

    public ClassLoader getModClassLoader()
    {
        return this.modClassLoader;
    }

    public void computeDependencies(String var1, Set var2, List var3, List var4)
    {
        if (var1 != null && var1.length() != 0)
        {
            boolean var5 = false;
            Iterator var6 = DEPENDENCYSPLITTER.split(var1).iterator();

            while (var6.hasNext())
            {
                String var7 = (String)var6.next();
                ArrayList var8 = Lists.newArrayList(DEPENDENCYPARTSPLITTER.split(var7));

                if (var8.size() != 2)
                {
                    var5 = true;
                }
                else
                {
                    String var9 = (String)var8.get(0);
                    String var10 = (String)var8.get(1);
                    boolean var11 = var10.startsWith("*");

                    if (var11 && var10.length() > 1)
                    {
                        var5 = true;
                    }
                    else
                    {
                        if ("required-before".equals(var9) || "required-after".equals(var9))
                        {
                            if (var11)
                            {
                                var5 = true;
                                continue;
                            }

                            var2.add(VersionParser.parseVersionReference(var10));
                        }

                        if (var11 && var10.indexOf(64) > -1)
                        {
                            var5 = true;
                        }
                        else if (!"required-before".equals(var9) && !"before".equals(var9))
                        {
                            if (!"required-after".equals(var9) && !"after".equals(var9))
                            {
                                var5 = true;
                            }
                            else
                            {
                                var3.add(VersionParser.parseVersionReference(var10));
                            }
                        }
                        else
                        {
                            var4.add(VersionParser.parseVersionReference(var10));
                        }
                    }
                }
            }

            if (var5)
            {
                FMLLog.log(Level.WARNING, "Unable to parse dependency string %s", new Object[] {var1});
                throw new LoaderException();
            }
        }
    }

    public Map getIndexedModList()
    {
        return ImmutableMap.copyOf(this.namedMods);
    }

    public void initializeMods()
    {
        this.modController.distributeStateMessage(LoaderState.INITIALIZATION, new Object[0]);
        this.modController.transition(LoaderState.POSTINITIALIZATION, false);
        GameData.buildModObjectTable();
        this.modController.distributeStateMessage(FMLInterModComms$IMCEvent.class);
        this.modController.distributeStateMessage(LoaderState.POSTINITIALIZATION, new Object[0]);
        this.modController.transition(LoaderState.AVAILABLE, false);
        this.modController.distributeStateMessage(LoaderState.AVAILABLE, new Object[0]);
        GameData.dumpRegistry(minecraftDir);
        FMLLog.info("Forge Mod Loader has successfully loaded %d mod%s", new Object[] {Integer.valueOf(this.mods.size()), this.mods.size() == 1 ? "" : "s"});
    }

    public ICrashCallable getCallableCrashInformation()
    {
        return new Loader$2(this);
    }

    public List getActiveModList()
    {
        return (List)(this.modController != null ? this.modController.getActiveModList() : ImmutableList.of());
    }

    public LoaderState$ModState getModState(ModContainer var1)
    {
        return this.modController.getModState(var1);
    }

    public String getMCVersionString()
    {
        return "Minecraft " + mccversion;
    }

    public boolean serverStarting(Object var1)
    {
        try
        {
            this.modController.distributeStateMessage(LoaderState.SERVER_STARTING, new Object[] {var1});
            this.modController.transition(LoaderState.SERVER_STARTING, false);
            return true;
        }
        catch (Throwable var3)
        {
            FMLLog.log(Level.SEVERE, var3, "A fatal exception occurred during the server starting event", new Object[0]);
            return false;
        }
    }

    public void serverStarted()
    {
        this.modController.distributeStateMessage(LoaderState.SERVER_STARTED, new Object[0]);
        this.modController.transition(LoaderState.SERVER_STARTED, false);
    }

    public void serverStopping()
    {
        this.modController.distributeStateMessage(LoaderState.SERVER_STOPPING, new Object[0]);
        this.modController.transition(LoaderState.SERVER_STOPPING, false);
    }

    public BiMap getModObjectList()
    {
        return this.modController.getModObjectList();
    }

    public BiMap getReversedModObjectList()
    {
        return this.getModObjectList().inverse();
    }

    public ModContainer activeModContainer()
    {
        return this.modController != null ? this.modController.activeContainer() : null;
    }

    public boolean isInState(LoaderState var1)
    {
        return this.modController.isInState(var1);
    }

    public MinecraftDummyContainer getMinecraftModContainer()
    {
        return this.minecraft;
    }

    public boolean hasReachedState(LoaderState var1)
    {
        return this.modController != null ? this.modController.hasReachedState(var1) : false;
    }

    public String getMCPVersionString()
    {
        return String.format("MCP v%s", new Object[] {mcpversion});
    }

    public void serverStopped()
    {
        this.modController.distributeStateMessage(LoaderState.SERVER_STOPPED, new Object[0]);
        this.modController.transition(LoaderState.SERVER_STOPPED, true);
        this.modController.transition(LoaderState.AVAILABLE, true);
    }

    public boolean serverAboutToStart(Object var1)
    {
        try
        {
            this.modController.distributeStateMessage(LoaderState.SERVER_ABOUT_TO_START, new Object[] {var1});
            this.modController.transition(LoaderState.SERVER_ABOUT_TO_START, false);
            return true;
        }
        catch (Throwable var3)
        {
            FMLLog.log(Level.SEVERE, var3, "A fatal exception occurred during the server about to start event", new Object[0]);
            return false;
        }
    }

    public Map getFMLBrandingProperties()
    {
        if (this.fmlBrandingProperties == null)
        {
            Properties var1 = new Properties();

            try
            {
                var1.load(this.getClass().getClassLoader().getResourceAsStream("fmlbranding.properties"));
            }
            catch (Exception var3)
            {
                ;
            }

            this.fmlBrandingProperties = Maps.fromProperties(var1);
        }

        return this.fmlBrandingProperties;
    }
}
