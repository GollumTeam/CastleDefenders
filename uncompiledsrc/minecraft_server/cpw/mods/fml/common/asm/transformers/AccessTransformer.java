package cpw.mods.fml.common.asm.transformers;

import com.google.common.base.Charsets;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.io.Resources;
import cpw.mods.fml.common.asm.transformers.AccessTransformer$1;
import cpw.mods.fml.common.asm.transformers.AccessTransformer$Modifier;
import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
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
import java.util.Collection;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

public class AccessTransformer implements IClassTransformer
{
    private static final boolean DEBUG = false;
    private Multimap modifiers;

    public AccessTransformer() throws IOException
    {
        this("fml_at.cfg");
    }

    protected AccessTransformer(String var1) throws IOException
    {
        this.modifiers = ArrayListMultimap.create();
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

        Resources.readLines(var3, Charsets.UTF_8, new AccessTransformer$1(this));
    }

    public byte[] transform(String var1, String var2, byte[] var3)
    {
        if (var3 == null)
        {
            return null;
        }
        else
        {
            boolean var4 = FMLDeobfuscatingRemapper.INSTANCE.isRemappedClass(var1);

            if (!var4 && !this.modifiers.containsKey(var1))
            {
                return var3;
            }
            else
            {
                ClassNode var5 = new ClassNode();
                ClassReader var6 = new ClassReader(var3);
                var6.accept(var5, 0);

                if (var4)
                {
                    AccessTransformer$Modifier var7 = new AccessTransformer$Modifier(this, (AccessTransformer$1)null);
                    var7.targetAccess = 1;
                    var7.modifyClassVisibility = true;
                    this.modifiers.put(var1, var7);
                    var7 = new AccessTransformer$Modifier(this, (AccessTransformer$1)null);
                    var7.targetAccess = 1;
                    var7.name = "*";
                    this.modifiers.put(var1, var7);
                    var7 = new AccessTransformer$Modifier(this, (AccessTransformer$1)null);
                    var7.targetAccess = 1;
                    var7.name = "*";
                    var7.desc = "<dummy>";
                    this.modifiers.put(var1, var7);
                }

                Collection var12 = this.modifiers.get(var1);
                Iterator var8 = var12.iterator();

                while (var8.hasNext())
                {
                    AccessTransformer$Modifier var9 = (AccessTransformer$Modifier)var8.next();

                    if (var9.modifyClassVisibility)
                    {
                        var5.access = this.getFixedAccess(var5.access, var9);
                    }
                    else
                    {
                        Iterator var10;

                        if (var9.desc.isEmpty())
                        {
                            var10 = var5.fields.iterator();

                            while (var10.hasNext())
                            {
                                FieldNode var14 = (FieldNode)var10.next();

                                if (var14.name.equals(var9.name) || var9.name.equals("*"))
                                {
                                    var14.access = this.getFixedAccess(var14.access, var9);

                                    if (!var9.name.equals("*"))
                                    {
                                        break;
                                    }
                                }
                            }
                        }
                        else
                        {
                            var10 = var5.methods.iterator();

                            while (var10.hasNext())
                            {
                                MethodNode var11 = (MethodNode)var10.next();

                                if (var11.name.equals(var9.name) && var11.desc.equals(var9.desc) || var9.name.equals("*"))
                                {
                                    var11.access = this.getFixedAccess(var11.access, var9);

                                    if (!var9.name.equals("*"))
                                    {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }

                ClassWriter var13 = new ClassWriter(1);
                var5.accept(var13);
                return var13.toByteArray();
            }
        }
    }

    private String toBinary(int var1)
    {
        return String.format("%16s", new Object[] {Integer.toBinaryString(var1)}).replace(' ', '0');
    }

    private int getFixedAccess(int var1, AccessTransformer$Modifier var2)
    {
        var2.oldAccess = var1;
        int var3 = var2.targetAccess;
        int var4 = var1 & -8;

        switch (var1 & 7)
        {
            case 0:
                var4 |= var3 != 2 ? var3 : 0;
                break;

            case 1:
                var4 |= var3 != 2 && var3 != 0 && var3 != 4 ? var3 : 1;
                break;

            case 2:
                var4 |= var3;
                break;

            case 3:
            default:
                throw new RuntimeException("The fuck?");

            case 4:
                var4 |= var3 != 2 && var3 != 0 ? var3 : 4;
        }

        if (var2.changeFinal && var2.desc == "")
        {
            if (var2.markFinal)
            {
                var4 |= 16;
            }
            else
            {
                var4 &= -17;
            }
        }

        var2.newAccess = var4;
        return var4;
    }

    public static void main(String[] var0)
    {
        if (var0.length < 2)
        {
            System.out.println("Usage: AccessTransformer <JarPath> <MapFile> [MapFile2]... ");
            System.exit(1);
        }

        boolean var1 = false;
        AccessTransformer[] var2 = new AccessTransformer[var0.length - 1];

        for (int var3 = 1; var3 < var0.length; ++var3)
        {
            try
            {
                var2[var3 - 1] = new AccessTransformer(var0[var3]);
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
            System.exit(1);
        }

        File var8 = new File(var0[0]);
        File var4 = new File(var0[0] + ".ATBack");

        if (!var8.exists() && !var4.exists())
        {
            System.out.println("Could not find target jar: " + var8);
            System.exit(1);
        }

        if (!var8.renameTo(var4))
        {
            System.out.println("Could not rename file: " + var8 + " -> " + var4);
            System.exit(1);
        }

        try
        {
            processJar(var4, var8, var2);
        }
        catch (IOException var6)
        {
            var6.printStackTrace();
            System.exit(1);
        }

        if (!var4.delete())
        {
            System.out.println("Could not delete temp file: " + var4);
        }
    }

    private static void processJar(File var0, File var1, AccessTransformer[] var2) throws IOException
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
                        AccessTransformer[] var14 = var2;
                        int var15 = var2.length;

                        for (int var16 = 0; var16 < var15; ++var16)
                        {
                            AccessTransformer var17 = var14[var16];
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

    public void ensurePublicAccessFor(String var1)
    {
        AccessTransformer$Modifier var2 = new AccessTransformer$Modifier(this, (AccessTransformer$1)null);
        AccessTransformer$Modifier.access$100(var2, "public");
        var2.modifyClassVisibility = true;
        this.modifiers.put(var1, var2);
    }

    static Multimap access$200(AccessTransformer var0)
    {
        return var0.modifiers;
    }
}
