package cpw.mods.fml.common.asm.transformers;

import com.google.common.base.Charsets;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.io.Resources;
import cpw.mods.fml.common.asm.transformers.MarkerTransformer$1;
import cpw.mods.fml.relauncher.IClassTransformer;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

public class MarkerTransformer implements IClassTransformer
{
    private ListMultimap markers;

    public MarkerTransformer() throws IOException
    {
        this("fml_marker.cfg");
    }

    protected MarkerTransformer(String var1) throws IOException
    {
        this.markers = ArrayListMultimap.create();
        this.readMapFile(var1);
    }

    private void readMapFile(String var1) throws IOException
    {
        File var2 = new File(var1);
        URL var3;

        if (var2.exists())
        {
            var3 = var2.toURI().toURL();
        }
        else
        {
            var3 = Resources.getResource(var1);
        }

        Resources.readLines(var3, Charsets.UTF_8, new MarkerTransformer$1(this));
    }

    public byte[] transform(String var1, String var2, byte[] var3)
    {
        if (var3 == null)
        {
            return null;
        }
        else if (!this.markers.containsKey(var1))
        {
            return var3;
        }
        else
        {
            ClassNode var4 = new ClassNode();
            ClassReader var5 = new ClassReader(var3);
            var5.accept(var4, 0);
            Iterator var6 = this.markers.get(var1).iterator();

            while (var6.hasNext())
            {
                String var7 = (String)var6.next();
                var4.interfaces.add(var7);
            }

            ClassWriter var8 = new ClassWriter(1);
            var4.accept(var8);
            return var8.toByteArray();
        }
    }

    public static void main(String[] var0)
    {
        if (var0.length < 2)
        {
            System.out.println("Usage: MarkerTransformer <JarPath> <MapFile> [MapFile2]... ");
        }
        else
        {
            boolean var1 = false;
            MarkerTransformer[] var2 = new MarkerTransformer[var0.length - 1];

            for (int var3 = 1; var3 < var0.length; ++var3)
            {
                try
                {
                    var2[var3 - 1] = new MarkerTransformer(var0[var3]);
                    var1 = true;
                }
                catch (IOException var7)
                {
                    System.out.println("Could not read Transformer Map: " + var0[var3]);
                    var7.printStackTrace();
                }
            }

            if (!var1)
            {
                System.out.println("Culd not find a valid transformer to perform");
            }
            else
            {
                File var8 = new File(var0[0]);
                File var4 = new File(var0[0] + ".ATBack");

                if (!var8.exists() && !var4.exists())
                {
                    System.out.println("Could not find target jar: " + var8);
                }
                else if (!var8.renameTo(var4))
                {
                    System.out.println("Could not rename file: " + var8 + " -> " + var4);
                }
                else
                {
                    try
                    {
                        processJar(var4, var8, var2);
                    }
                    catch (IOException var6)
                    {
                        var6.printStackTrace();
                    }

                    if (!var4.delete())
                    {
                        System.out.println("Could not delete temp file: " + var4);
                    }
                }
            }
        }
    }

    private static void processJar(File var0, File var1, MarkerTransformer[] var2) throws IOException
    {
        ZipInputStream var3 = null;
        ZipOutputStream var4 = null;

        try
        {
            try
            {
                var3 = new ZipInputStream(new BufferedInputStream(new FileInputStream(var0)));
            }
            catch (FileNotFoundException var30)
            {
                throw new FileNotFoundException("Could not open input file: " + var30.getMessage());
            }

            try
            {
                var4 = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(var1)));
            }
            catch (FileNotFoundException var29)
            {
                throw new FileNotFoundException("Could not open output file: " + var29.getMessage());
            }

            ZipEntry var5;

            while ((var5 = var3.getNextEntry()) != null)
            {
                if (var5.isDirectory())
                {
                    var4.putNextEntry(var5);
                }
                else
                {
                    byte[] var6 = new byte[4096];
                    ByteArrayOutputStream var7 = new ByteArrayOutputStream();
                    int var8;

                    do
                    {
                        var8 = var3.read(var6);

                        if (var8 > 0)
                        {
                            var7.write(var6, 0, var8);
                        }
                    }
                    while (var8 != -1);

                    byte[] var9 = var7.toByteArray();
                    String var10 = var5.getName();

                    if (var10.endsWith(".class") && !var10.startsWith("."))
                    {
                        ClassNode var11 = new ClassNode();
                        ClassReader var12 = new ClassReader(var9);
                        var12.accept(var11, 0);
                        String var13 = var11.name.replace('/', '.').replace('\\', '.');
                        MarkerTransformer[] var14 = var2;
                        int var15 = var2.length;

                        for (int var16 = 0; var16 < var15; ++var16)
                        {
                            MarkerTransformer var17 = var14[var16];
                            var9 = var17.transform(var13, var13, var9);
                        }
                    }

                    ZipEntry var32 = new ZipEntry(var10);
                    var4.putNextEntry(var32);
                    var4.write(var9);
                }
            }
        }
        finally
        {
            if (var4 != null)
            {
                try
                {
                    var4.close();
                }
                catch (IOException var28)
                {
                    ;
                }
            }

            if (var3 != null)
            {
                try
                {
                    var3.close();
                }
                catch (IOException var27)
                {
                    ;
                }
            }
        }
    }

    static ListMultimap access$000(MarkerTransformer var0)
    {
        return var0.markers;
    }
}
