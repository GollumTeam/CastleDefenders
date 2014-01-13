package cpw.mods.fml.common.discovery;

import com.google.common.collect.Lists;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.LoaderException;
import cpw.mods.fml.common.MetadataCollection;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.ModContainerFactory;
import cpw.mods.fml.common.discovery.asm.ASMModParser;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class JarDiscoverer implements ITypeDiscoverer
{
    public List discover(ModCandidate var1, ASMDataTable var2)
    {
        ArrayList var3 = Lists.newArrayList();
        FMLLog.fine("Examining file %s for potential mods", new Object[] {var1.getModContainer().getName()});
        ZipFile var4 = null;

        try
        {
            var4 = new ZipFile(var1.getModContainer());
            ZipEntry var5 = var4.getEntry("mcmod.info");
            MetadataCollection var6 = null;

            if (var5 != null)
            {
                FMLLog.finer("Located mcmod.info file in file %s", new Object[] {var1.getModContainer().getName()});
                var6 = MetadataCollection.from(var4.getInputStream(var5), var1.getModContainer().getName());
            }
            else
            {
                FMLLog.fine("The mod container %s appears to be missing an mcmod.info file", new Object[] {var1.getModContainer().getName()});
                var6 = MetadataCollection.from((InputStream)null, "");
            }

            Iterator var7 = Collections.list(var4.entries()).iterator();

            while (var7.hasNext())
            {
                ZipEntry var8 = (ZipEntry)var7.next();

                if (var8.getName() == null || !var8.getName().startsWith("__MACOSX"))
                {
                    Matcher var9 = classFile.matcher(var8.getName());

                    if (var9.matches())
                    {
                        ASMModParser var10;

                        try
                        {
                            var10 = new ASMModParser(var4.getInputStream(var8));
                        }
                        catch (LoaderException var21)
                        {
                            FMLLog.log(Level.SEVERE, (Throwable)var21, "There was a problem reading the entry %s in the jar %s - probably a corrupt zip", new Object[] {var8.getName(), var1.getModContainer().getPath()});
                            var4.close();
                            throw var21;
                        }

                        var10.validate();
                        var10.sendToTable(var2, var1);
                        ModContainer var11 = ModContainerFactory.instance().build(var10, var1.getModContainer(), var1);

                        if (var11 != null)
                        {
                            var2.addContainer(var11);
                            var3.add(var11);
                            var11.bindMetadata(var6);
                        }
                    }
                }
            }
        }
        catch (Exception var22)
        {
            FMLLog.log(Level.WARNING, (Throwable)var22, "Zip file %s failed to read properly, it will be ignored", new Object[] {var1.getModContainer().getName()});
        }
        finally
        {
            if (var4 != null)
            {
                try
                {
                    var4.close();
                }
                catch (Exception var20)
                {
                    ;
                }
            }
        }

        return var3;
    }
}
