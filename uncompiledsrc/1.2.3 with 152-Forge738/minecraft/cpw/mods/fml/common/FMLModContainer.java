package cpw.mods.fml.common;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import cpw.mods.fml.common.FMLModContainer$1;
import cpw.mods.fml.common.FMLModContainer$2;
import cpw.mods.fml.common.ILanguageAdapter$JavaAdapter;
import cpw.mods.fml.common.ILanguageAdapter$ScalaAdapter;
import cpw.mods.fml.common.Mod$FingerprintWarning;
import cpw.mods.fml.common.Mod$IMCCallback;
import cpw.mods.fml.common.Mod$Init;
import cpw.mods.fml.common.Mod$Instance;
import cpw.mods.fml.common.Mod$Metadata;
import cpw.mods.fml.common.Mod$PostInit;
import cpw.mods.fml.common.Mod$PreInit;
import cpw.mods.fml.common.Mod$ServerAboutToStart;
import cpw.mods.fml.common.Mod$ServerStarted;
import cpw.mods.fml.common.Mod$ServerStarting;
import cpw.mods.fml.common.Mod$ServerStopped;
import cpw.mods.fml.common.Mod$ServerStopping;
import cpw.mods.fml.common.discovery.ASMDataTable;
import cpw.mods.fml.common.discovery.ASMDataTable$ASMData;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.event.FMLEvent;
import cpw.mods.fml.common.event.FMLFingerprintViolationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms$IMCEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.common.versioning.ArtifactVersion;
import cpw.mods.fml.common.versioning.DefaultArtifactVersion;
import cpw.mods.fml.common.versioning.VersionParser;
import cpw.mods.fml.common.versioning.VersionRange;
import java.io.File;
import java.io.FileInputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FMLModContainer implements ModContainer
{
    private Mod modDescriptor;
    private Object modInstance;
    private File source;
    private ModMetadata modMetadata;
    private String className;
    private Map descriptor;
    private boolean enabled = true;
    private String internalVersion;
    private boolean overridesMetadata;
    private EventBus eventBus;
    private LoadController controller;
    private Multimap annotations;
    private DefaultArtifactVersion processedVersion;
    private boolean isNetworkMod;
    private static final BiMap modAnnotationTypes = ImmutableBiMap.builder().put(FMLPreInitializationEvent.class, Mod$PreInit.class).put(FMLInitializationEvent.class, Mod$Init.class).put(FMLPostInitializationEvent.class, Mod$PostInit.class).put(FMLServerAboutToStartEvent.class, Mod$ServerAboutToStart.class).put(FMLServerStartingEvent.class, Mod$ServerStarting.class).put(FMLServerStartedEvent.class, Mod$ServerStarted.class).put(FMLServerStoppingEvent.class, Mod$ServerStopping.class).put(FMLServerStoppedEvent.class, Mod$ServerStopped.class).put(FMLInterModComms$IMCEvent.class, Mod$IMCCallback.class).put(FMLFingerprintViolationEvent.class, Mod$FingerprintWarning.class).build();
    private static final BiMap modTypeAnnotations = modAnnotationTypes.inverse();
    private String annotationDependencies;
    private VersionRange minecraftAccepted;
    private boolean fingerprintNotPresent;
    private Set sourceFingerprints;
    private Certificate certificate;
    private String modLanguage;
    private ILanguageAdapter languageAdapter;

    public FMLModContainer(String var1, File var2, Map var3)
    {
        this.className = var1;
        this.source = var2;
        this.descriptor = var3;
        this.modLanguage = (String)var3.get("modLanguage");
        this.languageAdapter = (ILanguageAdapter)("scala".equals(this.modLanguage) ? new ILanguageAdapter$ScalaAdapter() : new ILanguageAdapter$JavaAdapter());
    }

    private ILanguageAdapter getLanguageAdapter()
    {
        return this.languageAdapter;
    }

    public String getModId()
    {
        return (String)this.descriptor.get("modid");
    }

    public String getName()
    {
        return this.modMetadata.name;
    }

    public String getVersion()
    {
        return this.internalVersion;
    }

    public File getSource()
    {
        return this.source;
    }

    public ModMetadata getMetadata()
    {
        return this.modMetadata;
    }

    public void bindMetadata(MetadataCollection var1)
    {
        this.modMetadata = var1.getMetadataForId(this.getModId(), this.descriptor);

        if (this.descriptor.containsKey("useMetadata"))
        {
            this.overridesMetadata = !((Boolean)this.descriptor.get("useMetadata")).booleanValue();
        }

        if (!this.overridesMetadata && this.modMetadata.useDependencyInformation)
        {
            FMLLog.log(this.getModId(), Level.FINEST, "Using mcmod dependency info : %s %s %s", new Object[] {this.modMetadata.requiredMods, this.modMetadata.dependencies, this.modMetadata.dependants});
        }
        else
        {
            HashSet var2 = Sets.newHashSet();
            ArrayList var3 = Lists.newArrayList();
            ArrayList var4 = Lists.newArrayList();
            this.annotationDependencies = (String)this.descriptor.get("dependencies");
            Loader.instance().computeDependencies(this.annotationDependencies, var2, var3, var4);
            this.modMetadata.requiredMods = var2;
            this.modMetadata.dependencies = var3;
            this.modMetadata.dependants = var4;
            FMLLog.log(this.getModId(), Level.FINEST, "Parsed dependency info : %s %s %s", new Object[] {var2, var3, var4});
        }

        if (Strings.isNullOrEmpty(this.modMetadata.name))
        {
            FMLLog.log(this.getModId(), Level.INFO, "Mod %s is missing the required element \'name\'. Substituting %s", new Object[] {this.getModId(), this.getModId()});
            this.modMetadata.name = this.getModId();
        }

        this.internalVersion = (String)this.descriptor.get("version");

        if (Strings.isNullOrEmpty(this.internalVersion))
        {
            Properties var5 = this.searchForVersionProperties();

            if (var5 != null)
            {
                this.internalVersion = var5.getProperty(this.getModId() + ".version");
                FMLLog.log(this.getModId(), Level.FINE, "Found version %s for mod %s in version.properties, using", new Object[] {this.internalVersion, this.getModId()});
            }
        }

        if (Strings.isNullOrEmpty(this.internalVersion) && !Strings.isNullOrEmpty(this.modMetadata.version))
        {
            FMLLog.log(this.getModId(), Level.WARNING, "Mod %s is missing the required element \'version\' and a version.properties file could not be found. Falling back to metadata version %s", new Object[] {this.getModId(), this.modMetadata.version});
            this.internalVersion = this.modMetadata.version;
        }

        if (Strings.isNullOrEmpty(this.internalVersion))
        {
            FMLLog.log(this.getModId(), Level.WARNING, "Mod %s is missing the required element \'version\' and no fallback can be found. Substituting \'1.0\'.", new Object[] {this.getModId()});
            this.modMetadata.version = this.internalVersion = "1.0";
        }

        String var6 = (String)this.descriptor.get("acceptedMinecraftVersions");

        if (!Strings.isNullOrEmpty(var6))
        {
            this.minecraftAccepted = VersionParser.parseRange(var6);
        }
        else
        {
            this.minecraftAccepted = Loader.instance().getMinecraftModContainer().getStaticVersionRange();
        }
    }

    public Properties searchForVersionProperties()
    {
        try
        {
            FMLLog.log(this.getModId(), Level.FINE, "Attempting to load the file version.properties from %s to locate a version number for %s", new Object[] {this.getSource().getName(), this.getModId()});
            Properties var1 = null;

            if (this.getSource().isFile())
            {
                ZipFile var2 = new ZipFile(this.getSource());
                ZipEntry var3 = var2.getEntry("version.properties");

                if (var3 != null)
                {
                    var1 = new Properties();
                    var1.load(var2.getInputStream(var3));
                }

                var2.close();
            }
            else if (this.getSource().isDirectory())
            {
                File var5 = new File(this.getSource(), "version.properties");

                if (var5.exists() && var5.isFile())
                {
                    var1 = new Properties();
                    FileInputStream var6 = new FileInputStream(var5);
                    var1.load(var6);
                    var6.close();
                }
            }

            return var1;
        }
        catch (Exception var4)
        {
            Throwables.propagateIfPossible(var4);
            FMLLog.log(this.getModId(), Level.FINEST, "Failed to find a usable version.properties file", new Object[0]);
            return null;
        }
    }

    public void setEnabledState(boolean var1)
    {
        this.enabled = var1;
    }

    public Set getRequirements()
    {
        return this.modMetadata.requiredMods;
    }

    public List getDependencies()
    {
        return this.modMetadata.dependencies;
    }

    public List getDependants()
    {
        return this.modMetadata.dependants;
    }

    public String getSortingRules()
    {
        return !this.overridesMetadata && this.modMetadata.useDependencyInformation ? this.modMetadata.printableSortingRules() : Strings.nullToEmpty(this.annotationDependencies);
    }

    public boolean matches(Object var1)
    {
        return var1 == this.modInstance;
    }

    public Object getMod()
    {
        return this.modInstance;
    }

    public boolean registerBus(EventBus var1, LoadController var2)
    {
        if (this.enabled)
        {
            FMLLog.log(this.getModId(), Level.FINE, "Enabling mod %s", new Object[] {this.getModId()});
            this.eventBus = var1;
            this.controller = var2;
            this.eventBus.register(this);
            return true;
        }
        else
        {
            return false;
        }
    }

    private Multimap gatherAnnotations(Class var1) throws Exception
    {
        ArrayListMultimap var2 = ArrayListMultimap.create();
        Method[] var3 = var1.getDeclaredMethods();
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5)
        {
            Method var6 = var3[var5];
            Annotation[] var7 = var6.getAnnotations();
            int var8 = var7.length;

            for (int var9 = 0; var9 < var8; ++var9)
            {
                Annotation var10 = var7[var9];

                if (modTypeAnnotations.containsKey(var10.annotationType()))
                {
                    Class[] var11 = new Class[] {(Class)modTypeAnnotations.get(var10.annotationType())};

                    if (Arrays.equals(var6.getParameterTypes(), var11))
                    {
                        var6.setAccessible(true);
                        var2.put(var10.annotationType(), var6);
                    }
                    else
                    {
                        FMLLog.log(this.getModId(), Level.SEVERE, "The mod %s appears to have an invalid method annotation %s. This annotation can only apply to methods with argument types %s -it will not be called", new Object[] {this.getModId(), var10.annotationType().getSimpleName(), Arrays.toString(var11)});
                    }
                }
            }
        }

        return var2;
    }

    private void processFieldAnnotations(ASMDataTable var1) throws Exception
    {
        SetMultimap var2 = var1.getAnnotationsFor(this);
        this.parseSimpleFieldAnnotation(var2, Mod$Instance.class.getName(), new FMLModContainer$1(this));
        this.parseSimpleFieldAnnotation(var2, Mod$Metadata.class.getName(), new FMLModContainer$2(this));
    }

    private void parseSimpleFieldAnnotation(SetMultimap var1, String var2, Function var3) throws IllegalAccessException
    {
        String[] var4 = var2.split("\\.");
        String var5 = var4[var4.length - 1];
        Iterator var6 = var1.get(var2).iterator();

        while (var6.hasNext())
        {
            ASMDataTable$ASMData var7 = (ASMDataTable$ASMData)var6.next();
            String var8 = (String)var7.getAnnotationInfo().get("value");
            Field var9 = null;
            Object var10 = null;
            Object var11 = this;
            boolean var12 = false;
            Class var13 = this.modInstance.getClass();

            if (!Strings.isNullOrEmpty(var8))
            {
                if (Loader.isModLoaded(var8))
                {
                    var11 = (ModContainer)Loader.instance().getIndexedModList().get(var8);
                }
                else
                {
                    var11 = null;
                }
            }

            if (var11 != null)
            {
                try
                {
                    var13 = Class.forName(var7.getClassName(), true, Loader.instance().getModClassLoader());
                    var9 = var13.getDeclaredField(var7.getObjectName());
                    var9.setAccessible(true);
                    var12 = Modifier.isStatic(var9.getModifiers());
                    var10 = var3.apply(var11);
                }
                catch (Exception var15)
                {
                    Throwables.propagateIfPossible(var15);
                    FMLLog.log(this.getModId(), Level.WARNING, var15, "Attempting to load @%s in class %s for %s and failing", new Object[] {var5, var7.getClassName(), ((ModContainer)var11).getModId()});
                }
            }

            if (var9 != null)
            {
                Object var14 = null;

                if (!var12)
                {
                    var14 = this.modInstance;

                    if (!this.modInstance.getClass().equals(var13))
                    {
                        FMLLog.log(this.getModId(), Level.WARNING, "Unable to inject @%s in non-static field %s.%s for %s as it is NOT the primary mod instance", new Object[] {var5, var7.getClassName(), var7.getObjectName(), ((ModContainer)var11).getModId()});
                        continue;
                    }
                }

                var9.set(var14, var10);
            }
        }
    }

    @Subscribe
    public void constructMod(FMLConstructionEvent var1)
    {
        try
        {
            ModClassLoader var2 = var1.getModClassLoader();
            var2.addFile(this.source);
            Class var3 = Class.forName(this.className, true, var2);
            Certificate[] var4 = var3.getProtectionDomain().getCodeSource().getCertificates();
            int var5 = 0;

            if (var4 != null)
            {
                var5 = var4.length;
            }

            Builder var6 = ImmutableList.builder();

            for (int var7 = 0; var7 < var5; ++var7)
            {
                var6.add(CertificateHelper.getFingerprint(var4[var7]));
            }

            ImmutableList var11 = var6.build();
            this.sourceFingerprints = ImmutableSet.copyOf(var11);
            String var8 = (String)this.descriptor.get("certificateFingerprint");
            this.fingerprintNotPresent = true;

            if (var8 != null && !var8.isEmpty())
            {
                if (!this.sourceFingerprints.contains(var8))
                {
                    Level var9 = Level.SEVERE;

                    if (this.source.isDirectory())
                    {
                        var9 = Level.FINER;
                    }

                    FMLLog.log(this.getModId(), var9, "The mod %s is expecting signature %s for source %s, however there is no signature matching that description", new Object[] {this.getModId(), var8, this.source.getName()});
                }
                else
                {
                    this.certificate = var4[var11.indexOf(var8)];
                    this.fingerprintNotPresent = false;
                }
            }

            this.annotations = this.gatherAnnotations(var3);
            this.isNetworkMod = FMLNetworkHandler.instance().registerNetworkMod(this, var3, var1.getASMHarvestedData());
            this.modInstance = this.getLanguageAdapter().getNewInstance(this, var3, var2);

            if (this.fingerprintNotPresent)
            {
                this.eventBus.post(new FMLFingerprintViolationEvent(this.source.isDirectory(), this.source, ImmutableSet.copyOf(this.sourceFingerprints), var8));
            }

            ProxyInjector.inject(this, var1.getASMHarvestedData(), FMLCommonHandler.instance().getSide(), this.getLanguageAdapter());
            this.processFieldAnnotations(var1.getASMHarvestedData());
        }
        catch (Throwable var10)
        {
            this.controller.errorOccurred(this, var10);
            Throwables.propagateIfPossible(var10);
        }
    }

    @Subscribe
    public void handleModStateEvent(FMLEvent var1)
    {
        Class var2 = (Class)modAnnotationTypes.get(var1.getClass());

        if (var2 != null)
        {
            try
            {
                Iterator var3 = this.annotations.get(var2).iterator();

                while (var3.hasNext())
                {
                    Object var4 = var3.next();
                    Method var5 = (Method)var4;
                    var5.invoke(this.modInstance, new Object[] {var1});
                }
            }
            catch (Throwable var6)
            {
                this.controller.errorOccurred(this, var6);
                Throwables.propagateIfPossible(var6);
            }
        }
    }

    public ArtifactVersion getProcessedVersion()
    {
        if (this.processedVersion == null)
        {
            this.processedVersion = new DefaultArtifactVersion(this.getModId(), this.getVersion());
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
        return this.modMetadata.version;
    }

    public VersionRange acceptableMinecraftVersionRange()
    {
        return this.minecraftAccepted;
    }

    public Certificate getSigningCertificate()
    {
        return this.certificate;
    }

    public String toString()
    {
        return "FMLMod:" + this.getModId() + "{" + this.getVersion() + "}";
    }
}
