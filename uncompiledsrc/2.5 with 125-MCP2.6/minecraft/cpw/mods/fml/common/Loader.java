package cpw.mods.fml.common;

import cpw.mods.fml.common.toposort.ModSorter;
import java.io.*;
import java.net.MalformedURLException;
import java.util.*;
import java.util.logging.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Loader
{
    private static final class State extends Enum
    {
        public static final State NOINIT;
        public static final State LOADING;
        public static final State PREINIT;
        public static final State INIT;
        public static final State POSTINIT;
        public static final State UP;
        public static final State ERRORED;
        private static final State $VALUES[];

        public static State[] values()
        {
            return (State[])$VALUES.clone();
        }

        public static State valueOf(String s)
        {
            return (State)Enum.valueOf(cpw.mods.fml.common.Loader$State.class, s);
        }

        static
        {
            NOINIT = new State("NOINIT", 0);
            LOADING = new State("LOADING", 1);
            PREINIT = new State("PREINIT", 2);
            INIT = new State("INIT", 3);
            POSTINIT = new State("POSTINIT", 4);
            UP = new State("UP", 5);
            ERRORED = new State("ERRORED", 6);
            $VALUES = (new State[]
                    {
                        NOINIT, LOADING, PREINIT, INIT, POSTINIT, UP, ERRORED
                    });
        }

        private State(String s, int i)
        {
            super(s, i);
        }
    }

    private static Pattern zipJar = Pattern.compile("(.+).(zip|jar)$");
    private static Pattern modClass = Pattern.compile("(.+/|)(mod\\_[^\\s$]+).class$");
    private static Loader instance;
    public static Logger log = Logger.getLogger("ForgeModLoader");
    private static String major;
    private static String minor;
    private static String rev;
    private static String build;
    private static String mcversion;
    private State state;
    private ModClassLoader modClassLoader;
    private List mods;
    private Map namedMods;
    private File canonicalConfigDir;
    private File canonicalMinecraftDir;
    private Exception capturedError;

    public static Loader instance()
    {
        if (instance == null)
        {
            instance = new Loader();
        }

        return instance;
    }

    private Loader()
    {
        FMLLogFormatter fmllogformatter = new FMLLogFormatter();

        if (FMLCommonHandler.instance().getMinecraftLogger() != null)
        {
            log.setParent(FMLCommonHandler.instance().getMinecraftLogger());
        }
        else
        {
            ConsoleHandler consolehandler = new ConsoleHandler();
            log.setUseParentHandlers(false);
            log.addHandler(consolehandler);
            consolehandler.setFormatter(fmllogformatter);
        }

        log.setLevel(Level.ALL);

        try
        {
            File file = new File(FMLCommonHandler.instance().getMinecraftRootDirectory().getCanonicalPath(), "ForgeModLoader-%g.log");
            FileHandler filehandler = new FileHandler(file.getPath(), 0, 3);
            filehandler.setFormatter(new FMLLogFormatter());
            filehandler.setLevel(Level.ALL);
            log.addHandler(filehandler);
        }
        catch (Exception exception) { }

        InputStream inputstream = (cpw.mods.fml.common.Loader.class).getClassLoader().getResourceAsStream("fmlversion.properties");
        Properties properties = new Properties();

        if (inputstream != null)
        {
            try
            {
                properties.load(inputstream);
                major = properties.getProperty("fmlbuild.major.number");
                minor = properties.getProperty("fmlbuild.minor.number");
                rev = properties.getProperty("fmlbuild.revision.number");
                build = properties.getProperty("fmlbuild.build.number");
                mcversion = properties.getProperty("fmlbuild.mcversion");
            }
            catch (IOException ioexception)
            {
                log.log(Level.SEVERE, "Could not get FML version information - corrupted installation detected!", ioexception);
                throw new LoaderException(ioexception);
            }
        }

        log.info(String.format("Forge Mod Loader version %s.%s.%s.%s for Minecraft %s loading", new Object[]
                {
                    major, minor, rev, build, mcversion
                }));
        modClassLoader = new ModClassLoader();
    }

    private void sortModList()
    {
        log.fine("Verifying mod dependencies are satisfied");

        for (Iterator iterator = mods.iterator(); iterator.hasNext();)
        {
            ModContainer modcontainer = (ModContainer)iterator.next();

            if (!namedMods.keySet().containsAll(modcontainer.getDependencies()))
            {
                log.severe(String.format("The mod %s requires mods %s to be available, one or more are not", new Object[]
                        {
                            modcontainer.getName(), modcontainer.getDependencies()
                        }));
                LoaderException loaderexception = new LoaderException();
                log.throwing("Loader", "sortModList", loaderexception);
                throw new LoaderException();
            }
        }

        log.fine("All dependencies are satisfied");
        ModSorter modsorter = new ModSorter(mods, namedMods);

        try
        {
            log.fine("Sorting mods into an ordered list");
            mods = modsorter.sort();
            log.fine("Sorted mod list:");
            ModContainer modcontainer1;

            for (Iterator iterator1 = mods.iterator(); iterator1.hasNext(); log.fine(String.format("\t%s: %s (%s)", new Object[]
                    {
                        modcontainer1.getName(), modcontainer1.getSource().getName(), modcontainer1.getSortingRules()
                    })))
            {
                modcontainer1 = (ModContainer)iterator1.next();
            }
        }
        catch (IllegalArgumentException illegalargumentexception)
        {
            log.severe("A dependency cycle was detected in the input mod set so they cannot be loaded in order");
            log.throwing("Loader", "sortModList", illegalargumentexception);
            throw new LoaderException(illegalargumentexception);
        }
    }

    private void preModInit()
    {
        state = State.PREINIT;
        log.fine("Beginning mod pre-initialization");
        ModContainer modcontainer;

        for (Iterator iterator = mods.iterator(); iterator.hasNext(); modcontainer.nextState())
        {
            modcontainer = (ModContainer)iterator.next();

            if (modcontainer.wantsPreInit())
            {
                log.finer(String.format("Pre-initializing %s", new Object[]
                        {
                            modcontainer.getSource()
                        }));
                modcontainer.preInit();
                namedMods.put(modcontainer.getName(), modcontainer);
            }
        }

        ModContainer modcontainer1;

        for (Iterator iterator1 = mods.iterator(); iterator1.hasNext(); FMLCommonHandler.instance().injectSidedProxyDelegate(modcontainer1))
        {
            modcontainer1 = (ModContainer)iterator1.next();

            if (modcontainer1.getMetadata() != null)
            {
                modcontainer1.getMetadata().associate(namedMods);
            }
        }

        log.fine("Mod pre-initialization complete");
    }

    private void modInit()
    {
        state = State.INIT;
        log.fine("Beginning mod initialization");
        ModContainer modcontainer;

        for (Iterator iterator = mods.iterator(); iterator.hasNext(); modcontainer.nextState())
        {
            modcontainer = (ModContainer)iterator.next();
            log.finer(String.format("Initializing %s", new Object[]
                    {
                        modcontainer.getName()
                    }));
            modcontainer.init();
        }

        log.fine("Mod initialization complete");
    }

    private void postModInit()
    {
        state = State.POSTINIT;
        log.fine("Beginning mod post-initialization");
        Iterator iterator = mods.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            ModContainer modcontainer = (ModContainer)iterator.next();

            if (modcontainer.wantsPostInit())
            {
                log.finer(String.format("Post-initializing %s", new Object[]
                        {
                            modcontainer.getName()
                        }));
                modcontainer.postInit();
                modcontainer.nextState();
            }
        }
        while (true);

        log.fine("Mod post-initialization complete");
    }

    private void load()
    {
        File file = FMLCommonHandler.instance().getMinecraftRootDirectory();
        File file1 = new File(file, "mods");
        File file2 = new File(file, "config");
        String s;
        String s1;

        try
        {
            canonicalMinecraftDir = file.getCanonicalFile();
            s = file1.getCanonicalPath();
            s1 = file2.getCanonicalPath();
            canonicalConfigDir = file2.getCanonicalFile();
        }
        catch (IOException ioexception)
        {
            log.severe(String.format("Failed to resolve mods directory mods %s", new Object[]
                    {
                        file1.getAbsolutePath()
                    }));
            log.throwing("fml.server.Loader", "initialize", ioexception);
            throw new LoaderException(ioexception);
        }

        if (!file1.exists())
        {
            log.fine(String.format("No mod directory found, creating one: %s", new Object[]
                    {
                        s
                    }));

            try
            {
                file1.mkdir();
            }
            catch (Exception exception)
            {
                log.throwing("fml.server.Loader", "initialize", exception);
                throw new LoaderException(exception);
            }
        }

        if (!file2.exists())
        {
            log.fine(String.format("No config directory found, creating one: %s", new Object[]
                    {
                        s1
                    }));

            try
            {
                file2.mkdir();
            }
            catch (Exception exception1)
            {
                log.throwing("fml.server.Loader", "initialize", exception1);
                throw new LoaderException(exception1);
            }
        }

        if (!file1.isDirectory())
        {
            log.severe(String.format("Attempting to load mods from %s, which is not a directory", new Object[]
                    {
                        s
                    }));
            LoaderException loaderexception = new LoaderException();
            log.throwing("fml.server.Loader", "initialize", loaderexception);
            throw loaderexception;
        }

        if (!file2.isDirectory())
        {
            log.severe(String.format("Attempting to load configuration from %s, which is not a directory", new Object[]
                    {
                        s1
                    }));
            LoaderException loaderexception1 = new LoaderException();
            log.throwing("fml.server.Loader", "initialize", loaderexception1);
            throw loaderexception1;
        }

        state = State.LOADING;
        log.fine("Attempting to load mods contained in the minecraft jar file and associated classes");
        File afile[] = modClassLoader.getParentSources();

        if (afile.length == 1 && afile[0].isFile())
        {
            log.fine(String.format("Minecraft is a file at %s, loading", new Object[]
                    {
                        afile[0].getAbsolutePath()
                    }));
            attemptFileLoad(afile[0], ModContainer.SourceType.CLASSPATH);
        }
        else
        {
            for (int i = 0; i < afile.length; i++)
            {
                if (afile[i].isFile())
                {
                    log.fine(String.format("Found a minecraft related file at %s, loading", new Object[]
                            {
                                afile[i].getAbsolutePath()
                            }));
                    attemptFileLoad(afile[i], ModContainer.SourceType.CLASSPATH);
                    continue;
                }

                if (afile[i].isDirectory())
                {
                    log.fine(String.format("Found a minecraft related directory at %s, loading", new Object[]
                            {
                                afile[i].getAbsolutePath()
                            }));
                    attemptDirLoad(afile[i], "", ModContainer.SourceType.CLASSPATH);
                }
            }
        }

        log.fine("Minecraft jar mods loaded successfully");
        log.info(String.format("Loading mods from %s", new Object[]
                {
                    s
                }));
        File afile1[] = file1.listFiles();
        Arrays.sort(afile1);
        File afile2[] = afile1;
        int j = afile2.length;

        for (int k = 0; k < j; k++)
        {
            File file3 = afile2[k];

            if (file3.isDirectory())
            {
                log.fine(String.format("Found a directory %s, attempting to load it", new Object[]
                        {
                            file3.getName()
                        }));
                boolean flag = attemptDirLoad(file3, "", ModContainer.SourceType.DIR);

                if (flag)
                {
                    log.fine(String.format("Directory %s loaded successfully", new Object[]
                            {
                                file3.getName()
                            }));
                }
                else
                {
                    log.info(String.format("Directory %s contained no mods", new Object[]
                            {
                                file3.getName()
                            }));
                }

                continue;
            }

            Matcher matcher = zipJar.matcher(file3.getName());

            if (!matcher.matches())
            {
                continue;
            }

            log.fine(String.format("Found a zip or jar file %s, attempting to load it", new Object[]
                    {
                        matcher.group(0)
                    }));
            boolean flag1 = attemptFileLoad(file3, ModContainer.SourceType.JAR);

            if (flag1)
            {
                log.fine(String.format("File %s loaded successfully", new Object[]
                        {
                            matcher.group(0)
                        }));
            }
            else
            {
                log.info(String.format("File %s contained no mods", new Object[]
                        {
                            matcher.group(0)
                        }));
            }
        }

        if (state == State.ERRORED)
        {
            log.severe("A problem has occured during mod loading. Likely a corrupt jar is located in your mods directory");
            throw new LoaderException(capturedError);
        }
        else
        {
            log.info(String.format("Forge Mod Loader has loaded %d mods", new Object[]
                    {
                        Integer.valueOf(mods.size())
                    }));
            return;
        }
    }

    private boolean attemptDirLoad(File file, String s, ModContainer.SourceType sourcetype)
    {
        if (s.length() == 0)
        {
            extendClassLoader(file);
        }

        boolean flag = false;
        File afile[] = file.listFiles(new FileFilter()
        {
            public boolean accept(File file2)
            {
                return file2.isFile() && Loader.modClass.matcher(file2.getName()).find() || file2.isDirectory();
            }
        }
                                     );
        Arrays.sort(afile);
        File afile1[] = afile;
        int i = afile1.length;

        for (int j = 0; j < i;)
        {
            File file1 = afile1[j];

            if (file1.isDirectory())
            {
                log.finest(String.format("Recursing into package %s", new Object[]
                        {
                            (new StringBuilder()).append(s).append(file1.getName()).toString()
                        }));
                flag |= attemptDirLoad(file1, (new StringBuilder()).append(s).append(file1.getName()).append(".").toString(), sourcetype);
                continue;
            }

            Matcher matcher = modClass.matcher(file1.getName());

            if (!matcher.find())
            {
                continue;
            }

            String s1 = (new StringBuilder()).append(s).append(matcher.group(2)).toString();

            try
            {
                log.fine(String.format("Found a mod class %s in directory %s, attempting to load it", new Object[]
                        {
                            s1, file.getName()
                        }));
                loadModClass(file, file1.getName(), s1, sourcetype);
                log.fine(String.format("Successfully loaded mod class %s", new Object[]
                        {
                            file1.getName()
                        }));
                flag = true;
                continue;
            }
            catch (Exception exception)
            {
                log.severe(String.format("File %s failed to read properly", new Object[]
                        {
                            file1.getName()
                        }));
                log.throwing("fml.server.Loader", "attemptDirLoad", exception);
                state = State.ERRORED;
                capturedError = exception;
                j++;
            }
        }

        return flag;
    }

    private void loadModClass(File file, String s, String s1, ModContainer.SourceType sourcetype)
    {
        try
        {
            Class class1 = Class.forName(s1, false, modClassLoader);
            ModContainer modcontainer = null;

            if (class1.isAnnotationPresent(cpw.mods.fml.common.Mod.class))
            {
                log.severe("Currently, the FML mod type is disabled");
                throw new LoaderException();
            }

            if (FMLCommonHandler.instance().isModLoaderMod(class1))
            {
                log.fine(String.format("ModLoader BaseMod class %s found, loading", new Object[]
                        {
                            s1
                        }));
                modcontainer = FMLCommonHandler.instance().loadBaseModMod(class1, file.getCanonicalFile());
                log.fine(String.format("ModLoader BaseMod class %s loaded", new Object[]
                        {
                            s1
                        }));
            }

            if (modcontainer != null)
            {
                modcontainer.setSourceType(sourcetype);
                FMLCommonHandler.instance().loadMetadataFor(modcontainer);
                mods.add(modcontainer);
                modcontainer.nextState();
            }
        }
        catch (Throwable throwable)
        {
            log.warning(String.format("Failed to load mod class %s in %s", new Object[]
                    {
                        s, file.getAbsoluteFile()
                    }));
            log.throwing("fml.server.Loader", "attemptLoad", throwable);
            throw new LoaderException(throwable);
        }
    }

    private void extendClassLoader(File file)
    {
        try
        {
            modClassLoader.addFile(file);
        }
        catch (MalformedURLException malformedurlexception)
        {
            throw new LoaderException(malformedurlexception);
        }
    }

    private boolean attemptFileLoad(File file, ModContainer.SourceType sourcetype)
    {
        extendClassLoader(file);
        boolean flag = false;

        try
        {
            ZipFile zipfile = new ZipFile(file);
            Iterator iterator = Collections.list(zipfile.entries()).iterator();

            do
            {
                if (!iterator.hasNext())
                {
                    break;
                }

                ZipEntry zipentry = (ZipEntry)iterator.next();
                Matcher matcher = modClass.matcher(zipentry.getName());

                if (matcher.matches())
                {
                    String s = matcher.group(1).replace('/', '.');
                    String s1 = (new StringBuilder()).append(s).append(matcher.group(2)).toString();
                    log.fine(String.format("Found a mod class %s in file %s, attempting to load it", new Object[]
                            {
                                s1, file.getName()
                            }));
                    loadModClass(file, zipentry.getName(), s1, sourcetype);
                    log.fine(String.format("Mod class %s loaded successfully", new Object[]
                            {
                                s1, file.getName()
                            }));
                    flag = true;
                }
            }
            while (true);
        }
        catch (Exception exception)
        {
            log.severe(String.format("Zip file %s failed to read properly", new Object[]
                    {
                        file.getName()
                    }));
            log.throwing("fml.server.Loader", "attemptFileLoad", exception);
            state = State.ERRORED;
            capturedError = exception;
        }

        return flag;
    }

    public static List getModList()
    {
        return instance().mods;
    }

    public void loadMods()
    {
        state = State.NOINIT;
        mods = new ArrayList();
        namedMods = new HashMap();
        load();
        preModInit();
        sortModList();
        mods = Collections.unmodifiableList(mods);
    }

    public void initializeMods()
    {
        modInit();
        postModInit();
        ModContainer modcontainer;

        for (Iterator iterator = getModList().iterator(); iterator.hasNext(); modcontainer.nextState())
        {
            modcontainer = (ModContainer)iterator.next();
        }

        state = State.UP;
        log.info(String.format("Forge Mod Loader load complete, %d mods loaded", new Object[]
                {
                    Integer.valueOf(mods.size())
                }));
    }

    public static boolean isModLoaded(String s)
    {
        return instance().namedMods.containsKey(s);
    }

    public File getConfigDir()
    {
        return canonicalConfigDir;
    }

    public String getCrashInformation()
    {
        StringBuffer stringbuffer = new StringBuffer();
        String as[] = FMLCommonHandler.instance().getBrandingStrings(String.format("Forge Mod Loader version %s.%s.%s.%s for Minecraft %s", new Object[]
                {
                    major, minor, rev, build, mcversion
                }));
        int i = as.length;

        for (int j = 0; j < i; j++)
        {
            String s = as[j];
            stringbuffer.append(s).append("\n");
        }

        ModContainer modcontainer;

        for (Iterator iterator = mods.iterator(); iterator.hasNext(); stringbuffer.append(String.format("\t%s : %s (%s)\n", new Object[]
                {
                    modcontainer.getName(), modcontainer.getModState(), modcontainer.getSource().getName()
                })))
        {
            modcontainer = (ModContainer)iterator.next();
        }
        return stringbuffer.toString();
    }

    public String getFMLVersionString()
    {
        return String.format("FML v%s.%s.%s.%s", new Object[]
                {
                    major, minor, rev, build
                });
    }

    public ClassLoader getModClassLoader()
    {
        return modClassLoader;
    }
}
