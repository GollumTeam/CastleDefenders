package cpw.mods.fml.common.discovery;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.LoaderException;
import cpw.mods.fml.common.MetadataCollection;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.ModContainerFactory;
import cpw.mods.fml.common.discovery.DirectoryDiscoverer$1;
import cpw.mods.fml.common.discovery.DirectoryDiscoverer$ClassFilter;
import cpw.mods.fml.common.discovery.asm.ASMModParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;

public class DirectoryDiscoverer implements ITypeDiscoverer
{
    private ASMDataTable table;

    public List discover(ModCandidate var1, ASMDataTable var2)
    {
        this.table = var2;
        ArrayList var3 = Lists.newArrayList();
        FMLLog.fine("Examining directory %s for potential mods", new Object[] {var1.getModContainer().getName()});
        this.exploreFileSystem("", var1.getModContainer(), var3, var1, (MetadataCollection)null);
        Iterator var4 = var3.iterator();

        while (var4.hasNext())
        {
            ModContainer var5 = (ModContainer)var4.next();
            var2.addContainer(var5);
        }

        return var3;
    }

    public void exploreFileSystem(String var1, File var2, List var3, ModCandidate var4, MetadataCollection var5)
    {
        if (var1.length() == 0)
        {
            File var6 = new File(var2, "mcmod.info");

            try
            {
                FileInputStream var7 = new FileInputStream(var6);
                var5 = MetadataCollection.from(var7, var2.getName());
                var7.close();
                FMLLog.fine("Found an mcmod.info file in directory %s", new Object[] {var2.getName()});
            }
            catch (Exception var16)
            {
                var5 = MetadataCollection.from((InputStream)null, "");
                FMLLog.fine("No mcmod.info file found in directory %s", new Object[] {var2.getName()});
            }
        }

        File[] var17 = var2.listFiles(new DirectoryDiscoverer$ClassFilter(this, (DirectoryDiscoverer$1)null));
        Arrays.sort(var17);
        File[] var18 = var17;
        int var8 = var17.length;

        for (int var9 = 0; var9 < var8; ++var9)
        {
            File var10 = var18[var9];

            if (var10.isDirectory())
            {
                FMLLog.finest("Recursing into package %s", new Object[] {var1 + var10.getName()});
                this.exploreFileSystem(var1 + var10.getName() + ".", var10, var3, var4, var5);
            }
            else
            {
                Matcher var11 = classFile.matcher(var10.getName());

                if (var11.matches())
                {
                    ASMModParser var12 = null;

                    try
                    {
                        FileInputStream var13 = new FileInputStream(var10);
                        var12 = new ASMModParser(var13);
                        var13.close();
                    }
                    catch (LoaderException var14)
                    {
                        FMLLog.log(Level.SEVERE, (Throwable)var14, "There was a problem reading the file %s - probably this is a corrupt file", new Object[] {var10.getPath()});
                        throw var14;
                    }
                    catch (Exception var15)
                    {
                        Throwables.propagate(var15);
                    }

                    var12.validate();
                    var12.sendToTable(this.table, var4);
                    ModContainer var19 = ModContainerFactory.instance().build(var12, var4.getModContainer(), var4);

                    if (var19 != null)
                    {
                        var3.add(var19);
                        var19.bindMetadata(var5);
                    }
                }
            }
        }
    }
}
