package cpw.mods.fml.common.asm.transformers.deobf;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableBiMap.Builder;
import com.google.common.io.CharStreams;
import com.google.common.io.InputSupplier;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.FMLRelaunchLog;
import cpw.mods.fml.relauncher.RelaunchClassLoader;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.commons.Remapper;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;

public class FMLDeobfuscatingRemapper extends Remapper
{
    public static final FMLDeobfuscatingRemapper INSTANCE = new FMLDeobfuscatingRemapper();
    private BiMap classNameBiMap = ImmutableBiMap.of();
    private BiMap mcpNameBiMap = ImmutableBiMap.of();
    private Map rawFieldMaps;
    private Map rawMethodMaps;
    private Map fieldNameMaps;
    private Map methodNameMaps;
    private RelaunchClassLoader classLoader;
    private Map fieldDescriptions = Maps.newHashMap();

    public void setup(File var1, RelaunchClassLoader var2, String var3)
    {
        this.classLoader = var2;

        try
        {
            File var4 = new File(var1, "lib");
            File var5 = new File(var4, var3);
            var5 = var5.getCanonicalFile();
            ZipFile var6 = new ZipFile(var5);
            ZipEntry var7 = var6.getEntry("joined.srg");
            ZipInputSupplier var8 = new ZipInputSupplier(var6, var7);
            InputSupplier var9 = CharStreams.newReaderSupplier(var8, Charsets.UTF_8);
            List var10 = CharStreams.readLines(var9);
            this.rawMethodMaps = Maps.newHashMap();
            this.rawFieldMaps = Maps.newHashMap();
            Builder var11 = ImmutableBiMap.builder();
            Builder var12 = ImmutableBiMap.builder();
            Splitter var13 = Splitter.on(CharMatcher.anyOf(": ")).omitEmptyStrings().trimResults();
            Iterator var14 = var10.iterator();

            while (var14.hasNext())
            {
                String var15 = (String)var14.next();
                String[] var16 = (String[])Iterables.toArray(var13.split(var15), String.class);
                String var17 = var16[0];

                if ("CL".equals(var17))
                {
                    this.parseClass(var11, var16);
                    this.parseMCPClass(var12, var16);
                }
                else if ("MD".equals(var17))
                {
                    this.parseMethod(var16);
                }
                else if ("FD".equals(var17))
                {
                    this.parseField(var16);
                }
            }

            this.classNameBiMap = var11.build();
            var12.put("BaseMod", "net/minecraft/src/BaseMod");
            var12.put("ModLoader", "net/minecraft/src/ModLoader");
            var12.put("EntityRendererProxy", "net/minecraft/src/EntityRendererProxy");
            var12.put("MLProp", "net/minecraft/src/MLProp");
            var12.put("TradeEntry", "net/minecraft/src/TradeEntry");
            this.mcpNameBiMap = var12.build();
        }
        catch (IOException var18)
        {
            FMLRelaunchLog.log(Level.SEVERE, (Throwable)var18, "An error occurred loading the deobfuscation map data", new Object[0]);
        }

        this.methodNameMaps = Maps.newHashMapWithExpectedSize(this.rawMethodMaps.size());
        this.fieldNameMaps = Maps.newHashMapWithExpectedSize(this.rawFieldMaps.size());
    }

    public boolean isRemappedClass(String var1)
    {
        var1 = var1.replace('.', '/');
        return this.classNameBiMap.containsKey(var1) || this.mcpNameBiMap.containsKey(var1) || !this.classNameBiMap.isEmpty() && var1.indexOf(47) == -1;
    }

    private void parseField(String[] var1)
    {
        String var2 = var1[1];
        int var3 = var2.lastIndexOf(47);
        String var4 = var2.substring(0, var3);
        String var5 = var2.substring(var3 + 1);
        String var6 = var1[2];
        int var7 = var6.lastIndexOf(47);
        String var8 = var6.substring(var7 + 1);

        if (!this.rawFieldMaps.containsKey(var4))
        {
            this.rawFieldMaps.put(var4, Maps.newHashMap());
        }

        ((Map)this.rawFieldMaps.get(var4)).put(var5 + ":" + this.getFieldType(var4, var5), var8);
        ((Map)this.rawFieldMaps.get(var4)).put(var5 + ":null", var8);
    }

    private String getFieldType(String var1, String var2)
    {
        if (this.fieldDescriptions.containsKey(var1))
        {
            return (String)((Map)this.fieldDescriptions.get(var1)).get(var2);
        }
        else
        {
            Map var3 = this.fieldDescriptions;

            synchronized (this.fieldDescriptions)
            {
                try
                {
                    byte[] var4 = this.classLoader.getClassBytes(var1);
                    String var10000;

                    if (var4 == null)
                    {
                        var10000 = null;
                        return var10000;
                    }
                    else
                    {
                        ClassReader var5 = new ClassReader(var4);
                        ClassNode var6 = new ClassNode();
                        var5.accept(var6, 7);
                        HashMap var7 = Maps.newHashMap();
                        Iterator var8 = var6.fields.iterator();

                        while (var8.hasNext())
                        {
                            FieldNode var9 = (FieldNode)var8.next();
                            var7.put(var9.name, var9.desc);
                        }

                        this.fieldDescriptions.put(var1, var7);
                        var10000 = (String)var7.get(var2);
                        return var10000;
                    }
                }
                catch (IOException var11)
                {
                    FMLLog.log(Level.SEVERE, (Throwable)var11, "A critical exception occured reading a class file %s", new Object[] {var1});
                    return null;
                }
            }
        }
    }

    private void parseClass(Builder var1, String[] var2)
    {
        var1.put(var2[1], var2[2]);
    }

    private void parseMCPClass(Builder var1, String[] var2)
    {
        int var3 = var2[2].lastIndexOf(47);
        var1.put("net/minecraft/src/" + var2[2].substring(var3 + 1), var2[2]);
    }

    private void parseMethod(String[] var1)
    {
        String var2 = var1[1];
        int var3 = var2.lastIndexOf(47);
        String var4 = var2.substring(0, var3);
        String var5 = var2.substring(var3 + 1);
        String var6 = var1[2];
        String var7 = var1[3];
        int var8 = var7.lastIndexOf(47);
        String var9 = var7.substring(var8 + 1);

        if (!this.rawMethodMaps.containsKey(var4))
        {
            this.rawMethodMaps.put(var4, Maps.newHashMap());
        }

        ((Map)this.rawMethodMaps.get(var4)).put(var5 + var6, var9);
    }

    public String mapFieldName(String var1, String var2, String var3)
    {
        if (this.classNameBiMap != null && !this.classNameBiMap.isEmpty())
        {
            Map var4 = this.getFieldMap(var1);
            return var4 != null && var4.containsKey(var2 + ":" + var3) ? (String)var4.get(var2 + ":" + var3) : var2;
        }
        else
        {
            return var2;
        }
    }

    public String map(String var1)
    {
        if (this.classNameBiMap != null && !this.classNameBiMap.isEmpty())
        {
            int var2 = var1.indexOf(36);
            String var3 = var2 > -1 ? var1.substring(0, var2) : var1;
            String var4 = var2 > -1 ? var1.substring(var2 + 1) : "";
            String var5 = this.classNameBiMap.containsKey(var3) ? (String)this.classNameBiMap.get(var3) : (this.mcpNameBiMap.containsKey(var3) ? (String)this.mcpNameBiMap.get(var3) : var3);
            var5 = var2 > -1 ? var5 + "$" + var4 : var5;
            return var5;
        }
        else
        {
            return var1;
        }
    }

    public String unmap(String var1)
    {
        if (this.classNameBiMap != null && !this.classNameBiMap.isEmpty())
        {
            int var2 = var1.indexOf(36);
            String var3 = var2 > -1 ? var1.substring(0, var2) : var1;
            String var4 = var2 > -1 ? var1.substring(var2 + 1) : "";
            String var5 = this.classNameBiMap.containsValue(var3) ? (String)this.classNameBiMap.inverse().get(var3) : (this.mcpNameBiMap.containsValue(var3) ? (String)this.mcpNameBiMap.inverse().get(var3) : var3);
            var5 = var2 > -1 ? var5 + "$" + var4 : var5;
            return var5;
        }
        else
        {
            return var1;
        }
    }

    public String mapMethodName(String var1, String var2, String var3)
    {
        if (this.classNameBiMap != null && !this.classNameBiMap.isEmpty())
        {
            Map var4 = this.getMethodMap(var1);
            String var5 = var2 + var3;
            return var4 != null && var4.containsKey(var5) ? (String)var4.get(var5) : var2;
        }
        else
        {
            return var2;
        }
    }

    private Map getFieldMap(String var1)
    {
        if (!this.fieldNameMaps.containsKey(var1))
        {
            this.findAndMergeSuperMaps(var1);
        }

        return (Map)this.fieldNameMaps.get(var1);
    }

    private Map getMethodMap(String var1)
    {
        if (!this.methodNameMaps.containsKey(var1))
        {
            this.findAndMergeSuperMaps(var1);
        }

        return (Map)this.methodNameMaps.get(var1);
    }

    private void findAndMergeSuperMaps(String var1)
    {
        try
        {
            byte[] var2 = this.classLoader.getClassBytes(var1);

            if (var2 == null)
            {
                return;
            }

            ClassReader var3 = new ClassReader(var2);
            String var4 = var3.getSuperName();
            String[] var5 = var3.getInterfaces();

            if (var5 == null)
            {
                var5 = new String[0];
            }

            this.mergeSuperMaps(var1, var4, var5);
        }
        catch (IOException var6)
        {
            var6.printStackTrace();
        }
    }

    public void mergeSuperMaps(String var1, String var2, String[] var3)
    {
        if (this.classNameBiMap != null && !this.classNameBiMap.isEmpty())
        {
            if (!Strings.isNullOrEmpty(var2))
            {
                ImmutableList var4 = ImmutableList.builder().add(var2).addAll(Arrays.asList(var3)).build();
                Iterator var5 = var4.iterator();

                while (var5.hasNext())
                {
                    String var6 = (String)var5.next();

                    if (!this.methodNameMaps.containsKey(var6))
                    {
                        this.findAndMergeSuperMaps(var6);
                    }
                }

                HashMap var9 = Maps.newHashMap();
                HashMap var10 = Maps.newHashMap();
                Iterator var7 = var4.iterator();

                while (var7.hasNext())
                {
                    String var8 = (String)var7.next();

                    if (this.methodNameMaps.containsKey(var8))
                    {
                        var9.putAll((Map)this.methodNameMaps.get(var8));
                    }

                    if (this.fieldNameMaps.containsKey(var8))
                    {
                        var10.putAll((Map)this.fieldNameMaps.get(var8));
                    }
                }

                if (this.rawMethodMaps.containsKey(var1))
                {
                    var9.putAll((Map)this.rawMethodMaps.get(var1));
                }

                if (this.rawFieldMaps.containsKey(var1))
                {
                    var10.putAll((Map)this.rawFieldMaps.get(var1));
                }

                this.methodNameMaps.put(var1, ImmutableMap.copyOf(var9));
                this.fieldNameMaps.put(var1, ImmutableMap.copyOf(var10));
            }
        }
    }
}
