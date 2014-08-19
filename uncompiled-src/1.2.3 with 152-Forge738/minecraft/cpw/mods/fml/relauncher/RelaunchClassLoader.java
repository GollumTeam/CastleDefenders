package cpw.mods.fml.relauncher;

import cpw.mods.fml.common.FMLLog;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.security.CodeSigner;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.jar.Attributes.Name;
import java.util.logging.Level;

public class RelaunchClassLoader extends URLClassLoader
{
    private List sources;
    private ClassLoader parent;
    private List transformers;
    private Map cachedClasses;
    private Set invalidClasses;
    private Set classLoaderExceptions = new HashSet();
    private Set transformerExceptions = new HashSet();
    private Map packageManifests = new HashMap();
    private IClassNameTransformer renameTransformer;
    private static Manifest EMPTY = new Manifest();
    private ThreadLocal loadBuffer = new ThreadLocal();
    private static final String[] RESERVED = new String[] {"CON", "PRN", "AUX", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9"};
    private static final boolean DEBUG_CLASSLOADING = Boolean.parseBoolean(System.getProperty("fml.debugClassLoading", "false"));
    private static final boolean DEBUG_CLASSLOADING_FINER = DEBUG_CLASSLOADING && Boolean.parseBoolean(System.getProperty("fml.debugClassLoadingFiner", "false"));
    private static final boolean DEBUG_CLASSLOADING_SAVE = DEBUG_CLASSLOADING && Boolean.parseBoolean(System.getProperty("fml.debugClassLoadingSave", "false"));
    private static File temp_folder = null;

    public RelaunchClassLoader(URL[] var1)
    {
        super(var1, (ClassLoader)null);
        this.sources = new ArrayList(Arrays.asList(var1));
        this.parent = this.getClass().getClassLoader();
        this.cachedClasses = new HashMap(1000);
        this.invalidClasses = new HashSet(1000);
        this.transformers = new ArrayList(2);
        Thread.currentThread().setContextClassLoader(this);
        this.addClassLoaderExclusion("java.");
        this.addClassLoaderExclusion("sun.");
        this.addClassLoaderExclusion("org.lwjgl.");
        this.addClassLoaderExclusion("cpw.mods.fml.relauncher.");
        this.addClassLoaderExclusion("net.minecraftforge.classloading.");
        this.addTransformerExclusion("javax.");
        this.addTransformerExclusion("argo.");
        this.addTransformerExclusion("org.objectweb.asm.");
        this.addTransformerExclusion("com.google.common.");
        this.addTransformerExclusion("org.bouncycastle.");
        this.addTransformerExclusion("cpw.mods.fml.common.asm.transformers.deobf.");

        if (DEBUG_CLASSLOADING_SAVE)
        {
            int var2 = 1;

            for (temp_folder = new File(FMLRelaunchLog.minecraftHome, "CLASSLOADER_TEMP"); temp_folder.exists() && var2 <= 10; temp_folder = new File(FMLRelaunchLog.minecraftHome, "CLASSLOADER_TEMP" + var2++))
            {
                ;
            }

            if (temp_folder.exists())
            {
                FMLRelaunchLog.info("DEBUG_CLASSLOADING_SAVE enabled,  but 10 temp directories already exist, clean them and try again.", new Object[0]);
                temp_folder = null;
            }
            else
            {
                FMLRelaunchLog.info("DEBUG_CLASSLOADING_SAVE Enabled, saving all classes to \"%s\"", new Object[] {temp_folder.getAbsolutePath().replace('\\', '/')});
                temp_folder.mkdirs();
            }
        }
    }

    public void registerTransformer(String var1)
    {
        try
        {
            IClassTransformer var2 = (IClassTransformer)this.loadClass(var1).newInstance();
            this.transformers.add(var2);

            if (var2 instanceof IClassNameTransformer && this.renameTransformer == null)
            {
                this.renameTransformer = (IClassNameTransformer)var2;
            }
        }
        catch (Exception var3)
        {
            FMLRelaunchLog.log(Level.SEVERE, (Throwable)var3, "A critical problem occured registering the ASM transformer class %s", new Object[] {var1});
        }
    }

    public Class findClass(String var1) throws ClassNotFoundException
    {
        if (this.invalidClasses.contains(var1))
        {
            throw new ClassNotFoundException(var1);
        }
        else
        {
            Iterator var2 = this.classLoaderExceptions.iterator();
            String var3;

            while (var2.hasNext())
            {
                var3 = (String)var2.next();

                if (var1.startsWith(var3))
                {
                    return this.parent.loadClass(var1);
                }
            }

            if (this.cachedClasses.containsKey(var1))
            {
                return (Class)this.cachedClasses.get(var1);
            }
            else
            {
                var2 = this.transformerExceptions.iterator();

                while (var2.hasNext())
                {
                    var3 = (String)var2.next();

                    if (var1.startsWith(var3))
                    {
                        try
                        {
                            Class var4 = super.findClass(var1);
                            this.cachedClasses.put(var1, var4);
                            return var4;
                        }
                        catch (ClassNotFoundException var15)
                        {
                            this.invalidClasses.add(var1);
                            throw var15;
                        }
                    }
                }

                try
                {
                    CodeSigner[] var17 = null;
                    var3 = this.transformName(var1);
                    String var18 = this.untransformName(var1);
                    int var5 = var18.lastIndexOf(46);
                    String var6 = var5 == -1 ? "" : var18.substring(0, var5);
                    String var7 = var18.replace('.', '/').concat(".class");
                    String var8 = var6.replace('.', '/');
                    URLConnection var9 = this.findCodeSourceConnectionFor(var7);

                    if (var9 instanceof JarURLConnection && var5 > -1 && !var18.startsWith("net.minecraft."))
                    {
                        JarURLConnection var21 = (JarURLConnection)var9;
                        JarFile var11 = var21.getJarFile();

                        if (var11 != null && var11.getManifest() != null)
                        {
                            Manifest var12 = var11.getManifest();
                            JarEntry var13 = var11.getJarEntry(var7);
                            Package var14 = this.getPackage(var6);
                            this.getClassBytes(var18);
                            var17 = var13.getCodeSigners();

                            if (var14 == null)
                            {
                                var14 = this.definePackage(var6, var12, var21.getJarFileURL());
                                this.packageManifests.put(var14, var12);
                            }
                            else if (var14.isSealed() && !var14.isSealed(var21.getJarFileURL()))
                            {
                                FMLLog.severe("The jar file %s is trying to seal already secured path %s", new Object[] {var11.getName(), var6});
                            }
                            else if (this.isSealed(var6, var12))
                            {
                                FMLLog.severe("The jar file %s has a security seal for path %s, but that path is defined and not secure", new Object[] {var11.getName(), var6});
                            }
                        }
                    }
                    else if (var5 > -1 && !var18.startsWith("net.minecraft."))
                    {
                        Package var10 = this.getPackage(var6);

                        if (var10 == null)
                        {
                            var10 = this.definePackage(var6, (String)null, (String)null, (String)null, (String)null, (String)null, (String)null, (URL)null);
                            this.packageManifests.put(var10, EMPTY);
                        }
                        else if (var10.isSealed())
                        {
                            FMLLog.severe("The URL %s is defining elements for sealed path %s", new Object[] {var9.getURL(), var6});
                        }
                    }

                    byte[] var19 = this.getClassBytes(var18);
                    byte[] var20 = this.runTransformers(var18, var3, var19);
                    this.saveTransformedClass(var20, var3);
                    Class var22 = this.defineClass(var3, var20, 0, var20.length, var9 == null ? null : new CodeSource(var9.getURL(), var17));
                    this.cachedClasses.put(var3, var22);
                    return var22;
                }
                catch (Throwable var16)
                {
                    this.invalidClasses.add(var1);

                    if (DEBUG_CLASSLOADING)
                    {
                        FMLLog.log(Level.FINEST, var16, "Exception encountered attempting classloading of %s", new Object[] {var1});
                    }

                    throw new ClassNotFoundException(var1, var16);
                }
            }
        }
    }

    private void saveTransformedClass(byte[] var1, String var2)
    {
        if (DEBUG_CLASSLOADING_SAVE && temp_folder != null)
        {
            File var3 = new File(temp_folder, var2.replace('.', File.separatorChar) + ".class");
            File var4 = var3.getParentFile();

            if (!var4.exists())
            {
                var4.mkdirs();
            }

            if (var3.exists())
            {
                var3.delete();
            }

            try
            {
                FMLRelaunchLog.fine("Saving transformed class \"%s\" to \"%s\"", new Object[] {var2, var3.getAbsolutePath().replace('\\', '/')});
                FileOutputStream var5 = new FileOutputStream(var3);
                var5.write(var1);
                var5.close();
            }
            catch (IOException var6)
            {
                FMLRelaunchLog.log(Level.WARNING, (Throwable)var6, "Could not save transformed class \"%s\"", new Object[] {var2});
            }
        }
    }

    private String untransformName(String var1)
    {
        return this.renameTransformer != null ? this.renameTransformer.unmapClassName(var1) : var1;
    }

    private String transformName(String var1)
    {
        return this.renameTransformer != null ? this.renameTransformer.remapClassName(var1) : var1;
    }

    private boolean isSealed(String var1, Manifest var2)
    {
        Attributes var3 = var2.getAttributes(var1);
        String var4 = null;

        if (var3 != null)
        {
            var4 = var3.getValue(Name.SEALED);
        }

        if (var4 == null && (var3 = var2.getMainAttributes()) != null)
        {
            var4 = var3.getValue(Name.SEALED);
        }

        return "true".equalsIgnoreCase(var4);
    }

    private URLConnection findCodeSourceConnectionFor(String var1)
    {
        URL var2 = this.findResource(var1);

        if (var2 != null)
        {
            try
            {
                return var2.openConnection();
            }
            catch (IOException var4)
            {
                throw new RuntimeException(var4);
            }
        }
        else
        {
            return null;
        }
    }

    private byte[] runTransformers(String var1, String var2, byte[] var3)
    {
        Iterator var4;
        IClassTransformer var5;

        if (DEBUG_CLASSLOADING_FINER)
        {
            FMLRelaunchLog.finest("Beginning transform of %s (%s) Start Length: %d", new Object[] {var1, var2, Integer.valueOf(var3 == null ? 0 : var3.length)});
            var4 = this.transformers.iterator();

            while (var4.hasNext())
            {
                var5 = (IClassTransformer)var4.next();
                String var6 = var5.getClass().getName();
                FMLRelaunchLog.finest("Before Transformer %s: %d", new Object[] {var6, Integer.valueOf(var3 == null ? 0 : var3.length)});
                var3 = var5.transform(var1, var2, var3);
                FMLRelaunchLog.finest("After  Transformer %s: %d", new Object[] {var6, Integer.valueOf(var3 == null ? 0 : var3.length)});
            }

            FMLRelaunchLog.finest("Ending transform of %s (%s) Start Length: %d", new Object[] {var1, var2, Integer.valueOf(var3 == null ? 0 : var3.length)});
        }
        else
        {
            for (var4 = this.transformers.iterator(); var4.hasNext(); var3 = var5.transform(var1, var2, var3))
            {
                var5 = (IClassTransformer)var4.next();
            }
        }

        return var3;
    }

    public void addURL(URL var1)
    {
        super.addURL(var1);
        this.sources.add(var1);
    }

    public List getSources()
    {
        return this.sources;
    }

    private byte[] readFully(InputStream var1)
    {
        try
        {
            byte[] var2 = (byte[])this.loadBuffer.get();

            if (var2 == null)
            {
                this.loadBuffer.set(new byte[4096]);
                var2 = (byte[])this.loadBuffer.get();
            }

            int var4 = 0;
            int var3;
            byte[] var5;

            while ((var3 = var1.read(var2, var4, var2.length - var4)) != -1)
            {
                var4 += var3;

                if (var4 >= var2.length - 1)
                {
                    var5 = var2;
                    var2 = new byte[var2.length + 4096];
                    System.arraycopy(var5, 0, var2, 0, var5.length);
                }
            }

            var5 = new byte[var4];
            System.arraycopy(var2, 0, var5, 0, var4);
            return var5;
        }
        catch (Throwable var6)
        {
            FMLRelaunchLog.log(Level.WARNING, var6, "Problem loading class", new Object[0]);
            return new byte[0];
        }
    }

    public List getTransformers()
    {
        return Collections.unmodifiableList(this.transformers);
    }

    private void addClassLoaderExclusion(String var1)
    {
        this.classLoaderExceptions.add(var1);
    }

    void addTransformerExclusion(String var1)
    {
        this.transformerExceptions.add(var1);
    }

    public byte[] getClassBytes(String var1) throws IOException
    {
        if (var1.indexOf(46) == -1)
        {
            String[] var2 = RESERVED;
            int var3 = var2.length;

            for (int var4 = 0; var4 < var3; ++var4)
            {
                String var5 = var2[var4];

                if (var1.toUpperCase(Locale.ENGLISH).startsWith(var5))
                {
                    byte[] var6 = this.getClassBytes("_" + var1);

                    if (var6 != null)
                    {
                        return var6;
                    }
                }
            }
        }

        InputStream var15 = null;
        Object var17;

        try
        {
            URL var16 = this.findResource(var1.replace('.', '/').concat(".class"));

            if (var16 != null)
            {
                var15 = var16.openStream();

                if (DEBUG_CLASSLOADING)
                {
                    FMLLog.finest("Loading class %s from resource %s", new Object[] {var1, var16.toString()});
                }

                byte[] var18 = this.readFully(var15);
                return var18;
            }

            if (DEBUG_CLASSLOADING)
            {
                FMLLog.finest("Failed to find class resource %s", new Object[] {var1.replace('.', '/').concat(".class")});
            }

            var17 = null;
        }
        finally
        {
            if (var15 != null)
            {
                try
                {
                    var15.close();
                }
                catch (IOException var13)
                {
                    ;
                }
            }
        }

        return (byte[])var17;
    }
}
