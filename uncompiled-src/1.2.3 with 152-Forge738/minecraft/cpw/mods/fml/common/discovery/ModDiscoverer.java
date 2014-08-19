package cpw.mods.fml.common.discovery;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.LoaderException;
import cpw.mods.fml.common.ModClassLoader;
import cpw.mods.fml.relauncher.RelaunchLibraryManager;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModDiscoverer
{
    private static Pattern zipJar = Pattern.compile("(.+).(zip|jar)$");
    private List candidates = Lists.newArrayList();
    private ASMDataTable dataTable = new ASMDataTable();
    private List nonModLibs = Lists.newArrayList();

    public void findClasspathMods(ModClassLoader var1)
    {
        ImmutableList var2 = ImmutableList.builder().addAll(var1.getDefaultLibraries()).addAll(RelaunchLibraryManager.getLibraries()).build();
        File[] var3 = var1.getParentSources();

        if (var3.length == 1 && var3[0].isFile())
        {
            FMLLog.fine("Minecraft is a file at %s, loading", new Object[] {var3[0].getAbsolutePath()});
            this.candidates.add(new ModCandidate(var3[0], var3[0], ContainerType.JAR, true, true));
        }
        else
        {
            for (int var4 = 0; var4 < var3.length; ++var4)
            {
                if (var3[var4].isFile())
                {
                    if (var2.contains(var3[var4].getName()))
                    {
                        FMLLog.finer("Skipping known library file %s", new Object[] {var3[var4].getAbsolutePath()});
                    }
                    else
                    {
                        FMLLog.fine("Found a minecraft related file at %s, examining for mod candidates", new Object[] {var3[var4].getAbsolutePath()});
                        this.candidates.add(new ModCandidate(var3[var4], var3[var4], ContainerType.JAR, var4 == 0, true));
                    }
                }
                else if (var3[var4].isDirectory())
                {
                    FMLLog.fine("Found a minecraft related directory at %s, examining for mod candidates", new Object[] {var3[var4].getAbsolutePath()});
                    this.candidates.add(new ModCandidate(var3[var4], var3[var4], ContainerType.DIR, var4 == 0, true));
                }
            }
        }
    }

    public void findModDirMods(File var1)
    {
        File[] var2 = var1.listFiles();
        Arrays.sort(var2);
        File[] var3 = var2;
        int var4 = var2.length;

        for (int var5 = 0; var5 < var4; ++var5)
        {
            File var6 = var3[var5];

            if (var6.isDirectory())
            {
                FMLLog.fine("Found a candidate mod directory %s", new Object[] {var6.getName()});
                this.candidates.add(new ModCandidate(var6, var6, ContainerType.DIR));
            }
            else
            {
                Matcher var7 = zipJar.matcher(var6.getName());

                if (var7.matches())
                {
                    FMLLog.fine("Found a candidate zip or jar file %s", new Object[] {var7.group(0)});
                    this.candidates.add(new ModCandidate(var6, var6, ContainerType.JAR));
                }
                else
                {
                    FMLLog.fine("Ignoring unknown file %s in mods directory", new Object[] {var6.getName()});
                }
            }
        }
    }

    public List identifyMods()
    {
        ArrayList var1 = Lists.newArrayList();
        Iterator var2 = this.candidates.iterator();

        while (var2.hasNext())
        {
            ModCandidate var3 = (ModCandidate)var2.next();

            try
            {
                List var4 = var3.explore(this.dataTable);

                if (var4.isEmpty() && !var3.isClasspath())
                {
                    this.nonModLibs.add(var3.getModContainer());
                }
                else
                {
                    var1.addAll(var4);
                }
            }
            catch (LoaderException var5)
            {
                FMLLog.log(Level.WARNING, (Throwable)var5, "Identified a problem with the mod candidate %s, ignoring this source", new Object[] {var3.getModContainer()});
            }
            catch (Throwable var6)
            {
                Throwables.propagate(var6);
            }
        }

        return var1;
    }

    public ASMDataTable getASMTable()
    {
        return this.dataTable;
    }

    public List getNonModLibs()
    {
        return this.nonModLibs;
    }
}
