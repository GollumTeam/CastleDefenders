package cpw.mods.fml.relauncher;

import cpw.mods.fml.common.CertificateHelper;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin$MCVersion;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin$TransformerExclusions;
import cpw.mods.fml.relauncher.RelaunchLibraryManager$1;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.logging.Level;

public class RelaunchLibraryManager
{
    private static String[] rootPlugins = new String[] {"cpw.mods.fml.relauncher.FMLCorePlugin", "net.minecraftforge.classloading.FMLForgePlugin"};
    private static List loadedLibraries = new ArrayList();
    private static Map pluginLocations;
    private static List loadPlugins;
    private static List libraries;
    private static boolean deobfuscatedEnvironment;
    private static ByteBuffer downloadBuffer = ByteBuffer.allocateDirect(8388608);
    static IDownloadDisplay downloadMonitor;

    public static void handleLaunch(File var0, RelaunchClassLoader var1)
    {
        try
        {
            byte[] var2 = var1.getClassBytes("net.minecraft.world.World");

            if (var2 != null)
            {
                FMLRelaunchLog.info("Managed to load a deobfuscated Minecraft name- we are in a deobfuscated environment. Skipping runtime deobfuscation", new Object[0]);
                deobfuscatedEnvironment = true;
            }
        }
        catch (IOException var38)
        {
            ;
        }

        if (!deobfuscatedEnvironment)
        {
            FMLRelaunchLog.fine("Enabling runtime deobfuscation", new Object[0]);
        }

        pluginLocations = new HashMap();
        loadPlugins = new ArrayList();
        libraries = new ArrayList();
        String[] var45 = rootPlugins;
        int var3 = var45.length;
        int var4;
        IFMLLoadingPlugin var6;
        int var8;
        int var9;
        String var10;

        for (var4 = 0; var4 < var3; ++var4)
        {
            String var5 = var45[var4];

            try
            {
                var6 = (IFMLLoadingPlugin)Class.forName(var5, true, var1).newInstance();
                loadPlugins.add(var6);
                String[] var7 = var6.getLibraryRequestClass();
                var8 = var7.length;

                for (var9 = 0; var9 < var8; ++var9)
                {
                    var10 = var7[var9];
                    libraries.add((ILibrarySet)Class.forName(var10, true, var1).newInstance());
                }
            }
            catch (Exception var44)
            {
                ;
            }
        }

        if (loadPlugins.isEmpty())
        {
            throw new RuntimeException("A fatal error has occured - no valid fml load plugin was found - this is a completely corrupt FML installation.");
        }
        else
        {
            downloadMonitor.updateProgressString("All core mods are successfully located", new Object[0]);
            String var46 = System.getProperty("fml.coreMods.load", "");
            String[] var47 = var46.split(",");
            var4 = var47.length;
            int var73;

            for (int var50 = 0; var50 < var4; ++var50)
            {
                String var56 = var47[var50];

                if (!var56.isEmpty())
                {
                    FMLRelaunchLog.info("Found a command line coremod : %s", new Object[] {var56});

                    try
                    {
                        var1.addTransformerExclusion(var56);
                        Class var58 = Class.forName(var56, true, var1);
                        IFMLLoadingPlugin$TransformerExclusions var67 = (IFMLLoadingPlugin$TransformerExclusions)var58.getAnnotation(IFMLLoadingPlugin$TransformerExclusions.class);
                        int var11;

                        if (var67 != null)
                        {
                            String[] var71 = var67.value();
                            var73 = var71.length;

                            for (var11 = 0; var11 < var73; ++var11)
                            {
                                String var12 = var71[var11];
                                var1.addTransformerExclusion(var12);
                            }
                        }

                        IFMLLoadingPlugin var69 = (IFMLLoadingPlugin)var58.newInstance();
                        loadPlugins.add(var69);

                        if (var69.getLibraryRequestClass() != null)
                        {
                            String[] var76 = var69.getLibraryRequestClass();
                            var11 = var76.length;

                            for (int var78 = 0; var78 < var11; ++var78)
                            {
                                String var13 = var76[var78];
                                libraries.add((ILibrarySet)Class.forName(var13, true, var1).newInstance());
                            }
                        }
                    }
                    catch (Throwable var43)
                    {
                        FMLRelaunchLog.log(Level.SEVERE, var43, "Exception occured trying to load coremod %s", new Object[] {var56});
                        throw new RuntimeException(var43);
                    }
                }
            }

            discoverCoreMods(var0, var1, loadPlugins, libraries);
            ArrayList var48 = new ArrayList();
            boolean var34 = false;
            Iterator var52;
            int var62;
            String var74;
            label705:
            {
                String var80;

                try
                {
                    label706:
                    {
                        File var51;

                        try
                        {
                            var34 = true;
                            var51 = setupLibDir(var0);
                        }
                        catch (Exception var41)
                        {
                            var48.add(var41);
                            var34 = false;
                            break label706;
                        }

                        var52 = libraries.iterator();

                        while (var52.hasNext())
                        {
                            ILibrarySet var54 = (ILibrarySet)var52.next();

                            for (var62 = 0; var62 < var54.getLibraries().length; ++var62)
                            {
                                boolean var66 = false;
                                var74 = var54.getLibraries()[var62];
                                var10 = var74.lastIndexOf(47) >= 0 ? var74.substring(var74.lastIndexOf(47)) : var74;
                                var80 = var54.getHashes()[var62];
                                File var81 = new File(var51, var10);

                                if (!var81.exists())
                                {
                                    try
                                    {
                                        downloadFile(var81, var54.getRootURL(), var74, var80);
                                        var66 = true;
                                    }
                                    catch (Throwable var40)
                                    {
                                        var48.add(var40);
                                        continue;
                                    }
                                }

                                if (var81.exists() && !var81.isFile())
                                {
                                    var48.add(new RuntimeException(String.format("Found a file %s that is not a normal file - you should clear this out of the way", new Object[] {var74})));
                                }
                                else
                                {
                                    if (!var66)
                                    {
                                        try
                                        {
                                            FileInputStream var82 = new FileInputStream(var81);
                                            FileChannel var14 = var82.getChannel();
                                            MappedByteBuffer var15 = var14.map(MapMode.READ_ONLY, 0L, var81.length());
                                            String var16 = generateChecksum(var15);
                                            var82.close();

                                            if (!var80.equals(var16))
                                            {
                                                var48.add(new RuntimeException(String.format("The file %s was found in your lib directory and has an invalid checksum %s (expecting %s) - it is unlikely to be the correct download, please move it out of the way and try again.", new Object[] {var74, var16, var80})));
                                                continue;
                                            }
                                        }
                                        catch (Exception var39)
                                        {
                                            FMLRelaunchLog.log(Level.SEVERE, (Throwable)var39, "The library file %s could not be validated", new Object[] {var81.getName()});
                                            var48.add(new RuntimeException(String.format("The library file %s could not be validated", new Object[] {var81.getName()}), var39));
                                            continue;
                                        }
                                    }

                                    if (!var66)
                                    {
                                        downloadMonitor.updateProgressString("Found library file %s present and correct in lib dir", new Object[] {var74});
                                    }
                                    else
                                    {
                                        downloadMonitor.updateProgressString("Library file %s was downloaded and verified successfully", new Object[] {var74});
                                    }

                                    try
                                    {
                                        var1.addURL(var81.toURI().toURL());
                                        loadedLibraries.add(var74);
                                    }
                                    catch (MalformedURLException var37)
                                    {
                                        var48.add(new RuntimeException(String.format("Should never happen - %s is broken - probably a somehow corrupted download. Delete it and try again.", new Object[] {var81.getName()}), var37));
                                    }
                                }
                            }
                        }

                        var34 = false;
                        break label705;
                    }
                }
                finally
                {
                    if (var34)
                    {
                        if (downloadMonitor.shouldStopIt())
                        {
                            return;
                        }

                        if (!var48.isEmpty())
                        {
                            FMLRelaunchLog.severe("There were errors during initial FML setup. Some files failed to download or were otherwise corrupted. You will need to manually obtain the following files from these download links and ensure your lib directory is clean. ", new Object[0]);
                            Iterator var18 = libraries.iterator();

                            while (var18.hasNext())
                            {
                                ILibrarySet var19 = (ILibrarySet)var18.next();
                                String[] var20 = var19.getLibraries();
                                int var21 = var20.length;

                                for (int var22 = 0; var22 < var21; ++var22)
                                {
                                    String var23 = var20[var22];
                                    FMLRelaunchLog.severe("*** Download " + var19.getRootURL(), new Object[] {var23});
                                }
                            }

                            FMLRelaunchLog.severe("<===========>", new Object[0]);
                            FMLRelaunchLog.severe("The following is the errors that caused the setup to fail. They may help you diagnose and resolve the issue", new Object[0]);
                            var18 = var48.iterator();
                            Throwable var83;

                            while (var18.hasNext())
                            {
                                var83 = (Throwable)var18.next();

                                if (var83.getMessage() != null)
                                {
                                    FMLRelaunchLog.severe(var83.getMessage(), new Object[0]);
                                }
                            }

                            FMLRelaunchLog.severe("<<< ==== >>>", new Object[0]);
                            FMLRelaunchLog.severe("The following is diagnostic information for developers to review.", new Object[0]);
                            var18 = var48.iterator();

                            while (var18.hasNext())
                            {
                                var83 = (Throwable)var18.next();
                                FMLRelaunchLog.log(Level.SEVERE, var83, "Error details", new Object[0]);
                            }

                            throw new RuntimeException("A fatal error occured and FML cannot continue");
                        }
                    }
                }

                if (downloadMonitor.shouldStopIt())
                {
                    return;
                }

                if (var48.isEmpty())
                {
                    return;
                }

                FMLRelaunchLog.severe("There were errors during initial FML setup. Some files failed to download or were otherwise corrupted. You will need to manually obtain the following files from these download links and ensure your lib directory is clean. ", new Object[0]);
                Iterator var65 = libraries.iterator();

                while (var65.hasNext())
                {
                    ILibrarySet var68 = (ILibrarySet)var65.next();
                    String[] var79 = var68.getLibraries();
                    var9 = var79.length;

                    for (var73 = 0; var73 < var9; ++var73)
                    {
                        var80 = var79[var73];
                        FMLRelaunchLog.severe("*** Download " + var68.getRootURL(), new Object[] {var80});
                    }
                }

                FMLRelaunchLog.severe("<===========>", new Object[0]);
                FMLRelaunchLog.severe("The following is the errors that caused the setup to fail. They may help you diagnose and resolve the issue", new Object[0]);
                var65 = var48.iterator();
                Throwable var70;

                while (var65.hasNext())
                {
                    var70 = (Throwable)var65.next();

                    if (var70.getMessage() != null)
                    {
                        FMLRelaunchLog.severe(var70.getMessage(), new Object[0]);
                    }
                }

                FMLRelaunchLog.severe("<<< ==== >>>", new Object[0]);
                FMLRelaunchLog.severe("The following is diagnostic information for developers to review.", new Object[0]);
                var65 = var48.iterator();

                while (var65.hasNext())
                {
                    var70 = (Throwable)var65.next();
                    FMLRelaunchLog.log(Level.SEVERE, var70, "Error details", new Object[0]);
                }

                throw new RuntimeException("A fatal error occured and FML cannot continue");
            }

            if (!downloadMonitor.shouldStopIt())
            {
                Iterator var49;
                String[] var59;

                if (!var48.isEmpty())
                {
                    FMLRelaunchLog.severe("There were errors during initial FML setup. Some files failed to download or were otherwise corrupted. You will need to manually obtain the following files from these download links and ensure your lib directory is clean. ", new Object[0]);
                    var49 = libraries.iterator();

                    while (var49.hasNext())
                    {
                        ILibrarySet var57 = (ILibrarySet)var49.next();
                        var59 = var57.getLibraries();
                        var62 = var59.length;

                        for (var8 = 0; var8 < var62; ++var8)
                        {
                            var74 = var59[var8];
                            FMLRelaunchLog.severe("*** Download " + var57.getRootURL(), new Object[] {var74});
                        }
                    }

                    FMLRelaunchLog.severe("<===========>", new Object[0]);
                    FMLRelaunchLog.severe("The following is the errors that caused the setup to fail. They may help you diagnose and resolve the issue", new Object[0]);
                    var49 = var48.iterator();
                    Throwable var61;

                    while (var49.hasNext())
                    {
                        var61 = (Throwable)var49.next();

                        if (var61.getMessage() != null)
                        {
                            FMLRelaunchLog.severe(var61.getMessage(), new Object[0]);
                        }
                    }

                    FMLRelaunchLog.severe("<<< ==== >>>", new Object[0]);
                    FMLRelaunchLog.severe("The following is diagnostic information for developers to review.", new Object[0]);
                    var49 = var48.iterator();

                    while (var49.hasNext())
                    {
                        var61 = (Throwable)var49.next();
                        FMLRelaunchLog.log(Level.SEVERE, var61, "Error details", new Object[0]);
                    }

                    throw new RuntimeException("A fatal error occured and FML cannot continue");
                }
                else
                {
                    var49 = loadPlugins.iterator();

                    while (var49.hasNext())
                    {
                        IFMLLoadingPlugin var55 = (IFMLLoadingPlugin)var49.next();

                        if (var55.getASMTransformerClass() != null)
                        {
                            var59 = var55.getASMTransformerClass();
                            var62 = var59.length;

                            for (var8 = 0; var8 < var62; ++var8)
                            {
                                var74 = var59[var8];
                                var1.registerTransformer(var74);
                            }
                        }
                    }

                    if (!deobfuscatedEnvironment)
                    {
                        var1.registerTransformer("cpw.mods.fml.common.asm.transformers.DeobfuscationTransformer");
                    }

                    downloadMonitor.updateProgressString("Running coremod plugins", new Object[0]);
                    HashMap var53 = new HashMap();
                    var53.put("mcLocation", var0);
                    var53.put("coremodList", loadPlugins);
                    var53.put("runtimeDeobfuscationEnabled", Boolean.valueOf(!deobfuscatedEnvironment));
                    var52 = loadPlugins.iterator();

                    while (var52.hasNext())
                    {
                        var6 = (IFMLLoadingPlugin)var52.next();
                        downloadMonitor.updateProgressString("Running coremod plugin %s", new Object[] {var6.getClass().getSimpleName()});
                        var53.put("coremodLocation", pluginLocations.get(var6));
                        var6.injectData(var53);
                        String var64 = var6.getSetupClass();

                        if (var64 != null)
                        {
                            try
                            {
                                IFMLCallHook var75 = (IFMLCallHook)Class.forName(var64, true, var1).newInstance();
                                HashMap var72 = new HashMap();
                                var72.put("mcLocation", var0);
                                var72.put("classLoader", var1);
                                var72.put("coremodLocation", pluginLocations.get(var6));
                                var72.put("deobfuscationFileName", FMLInjectionData.debfuscationDataName());
                                var75.injectData(var72);
                                var75.call();
                            }
                            catch (Exception var36)
                            {
                                throw new RuntimeException(var36);
                            }
                        }

                        downloadMonitor.updateProgressString("Coremod plugin %s run successfully", new Object[] {var6.getClass().getSimpleName()});
                        String var77 = var6.getModContainerClass();

                        if (var77 != null)
                        {
                            FMLInjectionData.containers.add(var77);
                        }
                    }

                    try
                    {
                        downloadMonitor.updateProgressString("Validating minecraft", new Object[0]);
                        Class var60 = Class.forName("cpw.mods.fml.common.Loader", true, var1);
                        Method var63 = var60.getMethod("injectData", new Class[] {Object[].class});
                        var63.invoke((Object)null, new Object[] {FMLInjectionData.data()});
                        var63 = var60.getMethod("instance", new Class[0]);
                        var63.invoke((Object)null, new Object[0]);
                        downloadMonitor.updateProgressString("Minecraft validated, launching...", new Object[0]);
                        downloadBuffer = null;
                    }
                    catch (Exception var35)
                    {
                        System.out.println("A CRITICAL PROBLEM OCCURED INITIALIZING MINECRAFT - LIKELY YOU HAVE AN INCORRECT VERSION FOR THIS FML");
                        throw new RuntimeException(var35);
                    }
                }
            }
        }
    }

    private static void discoverCoreMods(File var0, RelaunchClassLoader var1, List var2, List var3)
    {
        downloadMonitor.updateProgressString("Discovering coremods", new Object[0]);
        File var4 = setupCoreModDir(var0);
        RelaunchLibraryManager$1 var5 = new RelaunchLibraryManager$1();
        File[] var6 = var4.listFiles(var5);
        Arrays.sort(var6);
        File[] var7 = var6;
        int var8 = var6.length;

        for (int var9 = 0; var9 < var8; ++var9)
        {
            File var10 = var7[var9];
            downloadMonitor.updateProgressString("Found a candidate coremod %s", new Object[] {var10.getName()});
            Attributes var12;

            try
            {
                JarFile var11 = new JarFile(var10);

                if (var11.getManifest() == null)
                {
                    FMLRelaunchLog.warning("Found an un-manifested jar file in the coremods folder : %s, it will be ignored.", new Object[] {var10.getName()});
                    continue;
                }

                var12 = var11.getManifest().getMainAttributes();
            }
            catch (IOException var28)
            {
                FMLRelaunchLog.log(Level.SEVERE, (Throwable)var28, "Unable to read the coremod jar file %s - ignoring", new Object[] {var10.getName()});
                continue;
            }

            String var13 = var12.getValue("FMLCorePlugin");

            if (var13 == null)
            {
                FMLRelaunchLog.severe("The coremod %s does not contain a valid jar manifest- it will be ignored", new Object[] {var10.getName()});
            }
            else
            {
                try
                {
                    var1.addURL(var10.toURI().toURL());
                }
                catch (MalformedURLException var27)
                {
                    FMLRelaunchLog.log(Level.SEVERE, (Throwable)var27, "Unable to convert file into a URL. weird", new Object[0]);
                    continue;
                }

                try
                {
                    downloadMonitor.updateProgressString("Loading coremod %s", new Object[] {var10.getName()});
                    var1.addTransformerExclusion(var13);
                    Class var14 = Class.forName(var13, true, var1);
                    IFMLLoadingPlugin$MCVersion var15 = (IFMLLoadingPlugin$MCVersion)var14.getAnnotation(IFMLLoadingPlugin$MCVersion.class);
                    String var16 = "";

                    if (var15 == null)
                    {
                        FMLRelaunchLog.log(Level.WARNING, "The coremod %s does not have a MCVersion annotation, it may cause issues with this version of Minecraft", new Object[] {var13});
                    }
                    else
                    {
                        var16 = var15.value();
                    }

                    if (!"".equals(var16) && !FMLInjectionData.mccversion.equals(var16))
                    {
                        FMLRelaunchLog.log(Level.SEVERE, "The coremod %s is requesting minecraft version %s and minecraft is %s. It will be ignored.", new Object[] {var13, var16, FMLInjectionData.mccversion});
                    }
                    else
                    {
                        if (!"".equals(var16))
                        {
                            FMLRelaunchLog.log(Level.FINE, "The coremod %s requested minecraft version %s and minecraft is %s. It will be loaded.", new Object[] {var13, var16, FMLInjectionData.mccversion});
                        }

                        IFMLLoadingPlugin$TransformerExclusions var17 = (IFMLLoadingPlugin$TransformerExclusions)var14.getAnnotation(IFMLLoadingPlugin$TransformerExclusions.class);
                        int var20;

                        if (var17 != null)
                        {
                            String[] var18 = var17.value();
                            int var19 = var18.length;

                            for (var20 = 0; var20 < var19; ++var20)
                            {
                                String var21 = var18[var20];
                                var1.addTransformerExclusion(var21);
                            }
                        }

                        IFMLLoadingPlugin var30 = (IFMLLoadingPlugin)var14.newInstance();
                        var2.add(var30);
                        pluginLocations.put(var30, var10);

                        if (var30.getLibraryRequestClass() != null)
                        {
                            String[] var29 = var30.getLibraryRequestClass();
                            var20 = var29.length;

                            for (int var31 = 0; var31 < var20; ++var31)
                            {
                                String var22 = var29[var31];
                                var3.add((ILibrarySet)Class.forName(var22, true, var1).newInstance());
                            }
                        }

                        downloadMonitor.updateProgressString("Loaded coremod %s", new Object[] {var10.getName()});
                    }
                }
                catch (ClassNotFoundException var23)
                {
                    FMLRelaunchLog.log(Level.SEVERE, (Throwable)var23, "Coremod %s: Unable to class load the plugin %s", new Object[] {var10.getName(), var13});
                }
                catch (ClassCastException var24)
                {
                    FMLRelaunchLog.log(Level.SEVERE, (Throwable)var24, "Coremod %s: The plugin %s is not an implementor of IFMLLoadingPlugin", new Object[] {var10.getName(), var13});
                }
                catch (InstantiationException var25)
                {
                    FMLRelaunchLog.log(Level.SEVERE, (Throwable)var25, "Coremod %s: The plugin class %s was not instantiable", new Object[] {var10.getName(), var13});
                }
                catch (IllegalAccessException var26)
                {
                    FMLRelaunchLog.log(Level.SEVERE, (Throwable)var26, "Coremod %s: The plugin class %s was not accessible", new Object[] {var10.getName(), var13});
                }
            }
        }
    }

    private static File setupLibDir(File var0)
    {
        File var1 = new File(var0, "lib");

        try
        {
            var1 = var1.getCanonicalFile();
        }
        catch (IOException var3)
        {
            throw new RuntimeException(String.format("Unable to canonicalize the lib dir at %s", new Object[] {var0.getName()}), var3);
        }

        if (!var1.exists())
        {
            var1.mkdir();
        }
        else if (var1.exists() && !var1.isDirectory())
        {
            throw new RuntimeException(String.format("Found a lib file in %s that\'s not a directory", new Object[] {var0.getName()}));
        }

        return var1;
    }

    private static File setupCoreModDir(File var0)
    {
        File var1 = new File(var0, "coremods");

        try
        {
            var1 = var1.getCanonicalFile();
        }
        catch (IOException var3)
        {
            throw new RuntimeException(String.format("Unable to canonicalize the coremod dir at %s", new Object[] {var0.getName()}), var3);
        }

        if (!var1.exists())
        {
            var1.mkdir();
        }
        else if (var1.exists() && !var1.isDirectory())
        {
            throw new RuntimeException(String.format("Found a coremod file in %s that\'s not a directory", new Object[] {var0.getName()}));
        }

        return var1;
    }

    private static void downloadFile(File var0, String var1, String var2, String var3)
    {
        try
        {
            URL var4 = new URL(String.format(var1, new Object[] {var2}));
            downloadMonitor.updateProgressString("Downloading file %s", new Object[] {var4.toString()});
            FMLRelaunchLog.info("Downloading file %s", new Object[] {var4.toString()});
            URLConnection var5 = var4.openConnection();
            var5.setConnectTimeout(5000);
            var5.setReadTimeout(5000);
            var5.setRequestProperty("User-Agent", "FML Relaunch Downloader");
            int var6 = var5.getContentLength();
            performDownload(var5.getInputStream(), var6, var3, var0);
            downloadMonitor.updateProgressString("Download complete", new Object[0]);
            FMLRelaunchLog.info("Download complete", new Object[0]);
        }
        catch (Exception var7)
        {
            if (downloadMonitor.shouldStopIt())
            {
                FMLRelaunchLog.warning("You have stopped the downloading operation before it could complete", new Object[0]);
            }
            else if (var7 instanceof RuntimeException)
            {
                throw(RuntimeException)var7;
            }
            else
            {
                FMLRelaunchLog.severe("There was a problem downloading the file %s automatically. Perhaps you have an environment without internet access. You will need to download the file manually or restart and let it try again\n", new Object[] {var0.getName()});
                var0.delete();
                throw new RuntimeException("A download error occured", var7);
            }
        }
    }

    public static List getLibraries()
    {
        return loadedLibraries;
    }

    private static void performDownload(InputStream var0, int var1, String var2, File var3)
    {
        if (var1 > downloadBuffer.capacity())
        {
            throw new RuntimeException(String.format("The file %s is too large to be downloaded by FML - the coremod is invalid", new Object[] {var3.getName()}));
        }
        else
        {
            downloadBuffer.clear();
            int var5 = 0;
            downloadMonitor.resetProgress(var1);

            try
            {
                downloadMonitor.setPokeThread(Thread.currentThread());
                byte[] var6 = new byte[1024];

                while (true)
                {
                    int var4;

                    if ((var4 = var0.read(var6)) >= 0)
                    {
                        downloadBuffer.put(var6, 0, var4);
                        var5 += var4;

                        if (!downloadMonitor.shouldStopIt())
                        {
                            downloadMonitor.updateProgress(var5);
                            continue;
                        }
                    }

                    var0.close();
                    downloadMonitor.setPokeThread((Thread)null);
                    downloadBuffer.limit(var5);
                    downloadBuffer.position(0);
                    break;
                }
            }
            catch (InterruptedIOException var9)
            {
                Thread.interrupted();
                return;
            }
            catch (IOException var10)
            {
                throw new RuntimeException(var10);
            }

            try
            {
                String var11 = generateChecksum(downloadBuffer);

                if (var11.equals(var2))
                {
                    downloadBuffer.position(0);
                    FileOutputStream var7 = new FileOutputStream(var3);
                    var7.getChannel().write(downloadBuffer);
                    var7.close();
                }
                else
                {
                    throw new RuntimeException(String.format("The downloaded file %s has an invalid checksum %s (expecting %s). The download did not succeed correctly and the file has been deleted. Please try launching again.", new Object[] {var3.getName(), var11, var2}));
                }
            }
            catch (Exception var8)
            {
                if (var8 instanceof RuntimeException)
                {
                    throw(RuntimeException)var8;
                }
                else
                {
                    throw new RuntimeException(var8);
                }
            }
        }
    }

    private static String generateChecksum(ByteBuffer var0)
    {
        return CertificateHelper.getFingerprint(var0);
    }
}
