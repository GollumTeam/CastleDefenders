package cpw.mods.fml.common.asm.transformers;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import cpw.mods.fml.common.asm.transformers.MCPMerger$ClassInfo;
import cpw.mods.fml.common.asm.transformers.MCPMerger$MethodWrapper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

public class MCPMerger
{
    private static Hashtable clients = new Hashtable();
    private static Hashtable shared = new Hashtable();
    private static Hashtable servers = new Hashtable();
    private static HashSet copyToServer = new HashSet();
    private static HashSet copyToClient = new HashSet();
    private static HashSet dontAnnotate = new HashSet();
    private static final boolean DEBUG = false;

    public static void main(String[] var0)
    {
        if (var0.length != 3)
        {
            System.out.println("Usage: MCPMerger <MapFile> <minecraft.jar> <minecraft_server.jar>");
            System.exit(1);
        }

        File var1 = new File(var0[0]);
        File var2 = new File(var0[1]);
        File var3 = new File(var0[2]);
        File var4 = new File(var0[1] + ".MergeBack");
        File var5 = new File(var0[2] + ".MergeBack");

        if (var4.exists() && !var4.delete())
        {
            System.out.println("Could not delete temp file: " + var4);
        }

        if (var5.exists() && !var5.delete())
        {
            System.out.println("Could not delete temp file: " + var5);
        }

        if (!var2.exists())
        {
            System.out.println("Could not find minecraft.jar: " + var2);
            System.exit(1);
        }

        if (!var3.exists())
        {
            System.out.println("Could not find minecraft_server.jar: " + var3);
            System.exit(1);
        }

        if (!var2.renameTo(var4))
        {
            System.out.println("Could not rename file: " + var2 + " -> " + var4);
            System.exit(1);
        }

        if (!var3.renameTo(var5))
        {
            System.out.println("Could not rename file: " + var3 + " -> " + var5);
            System.exit(1);
        }

        if (!readMapFile(var1))
        {
            System.out.println("Could not read map file: " + var1);
            System.exit(1);
        }

        try
        {
            processJar(var4, var5, var2, var3);
        }
        catch (IOException var7)
        {
            var7.printStackTrace();
            System.exit(1);
        }

        if (!var4.delete())
        {
            System.out.println("Could not delete temp file: " + var4);
        }

        if (!var5.delete())
        {
            System.out.println("Could not delete temp file: " + var5);
        }
    }

    private static boolean readMapFile(File var0)
    {
        try
        {
            FileInputStream var1 = new FileInputStream(var0);
            DataInputStream var2 = new DataInputStream(var1);
            BufferedReader var3 = new BufferedReader(new InputStreamReader(var2));
            String var4;

            while ((var4 = var3.readLine()) != null)
            {
                var4 = var4.split("#")[0];
                char var5 = var4.charAt(0);
                var4 = var4.substring(1).trim();

                switch (var5)
                {
                    case 33:
                        dontAnnotate.add(var4);
                        break;

                    case 60:
                        copyToClient.add(var4);
                        break;

                    case 62:
                        copyToServer.add(var4);
                }
            }

            var2.close();
            return true;
        }
        catch (Exception var6)
        {
            System.err.println("Error: " + var6.getMessage());
            return false;
        }
    }

    public static void processJar(File var0, File var1, File var2, File var3) throws IOException
    {
        ZipFile var4 = null;
        ZipFile var5 = null;
        ZipOutputStream var6 = null;
        ZipOutputStream var7 = null;

        try
        {
            try
            {
                var4 = new ZipFile(var0);
                var5 = new ZipFile(var1);
            }
            catch (FileNotFoundException var40)
            {
                throw new FileNotFoundException("Could not open input file: " + var40.getMessage());
            }

            try
            {
                var6 = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(var2)));
                var7 = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(var3)));
            }
            catch (FileNotFoundException var39)
            {
                throw new FileNotFoundException("Could not open output file: " + var39.getMessage());
            }

            Hashtable var8 = getClassEntries(var4, var6);
            Hashtable var9 = getClassEntries(var5, var7);
            HashSet var10 = new HashSet();
            HashSet var11 = new HashSet();
            Iterator var12 = var8.entrySet().iterator();
            Entry var13;

            while (var12.hasNext())
            {
                var13 = (Entry)var12.next();
                String var14 = (String)var13.getKey();
                ZipEntry var15 = (ZipEntry)var13.getValue();
                ZipEntry var16 = (ZipEntry)var9.get(var14);

                if (var16 == null)
                {
                    if (!copyToServer.contains(var14))
                    {
                        copyClass(var4, var15, var6, (ZipOutputStream)null, true);
                        var10.add(var14);
                    }
                    else
                    {
                        copyClass(var4, var15, var6, var7, true);
                        var10.add(var14);
                        var11.add(var14);
                    }
                }
                else
                {
                    var9.remove(var14);
                    MCPMerger$ClassInfo var17 = new MCPMerger$ClassInfo(var14);
                    shared.put(var14, var17);
                    byte[] var18 = readEntry(var4, (ZipEntry)var13.getValue());
                    byte[] var19 = readEntry(var5, var16);
                    byte[] var20 = processClass(var18, var19, var17);
                    ZipEntry var21 = new ZipEntry(var15.getName());
                    var6.putNextEntry(var21);
                    var6.write(var20);
                    var7.putNextEntry(var21);
                    var7.write(var20);
                    var10.add(var14);
                    var11.add(var14);
                }
            }

            var12 = var9.entrySet().iterator();

            while (var12.hasNext())
            {
                var13 = (Entry)var12.next();
                copyClass(var5, (ZipEntry)var13.getValue(), var6, var7, false);
            }

            String[] var42 = new String[] {SideOnly.class.getName(), Side.class.getName()};
            int var43 = var42.length;

            for (int var45 = 0; var45 < var43; ++var45)
            {
                String var44 = var42[var45];
                String var46 = var44.replace(".", "/");
                byte[] var47 = getClassBytes(var44);
                ZipEntry var48 = new ZipEntry(var44.replace(".", "/").concat(".class"));

                if (!var10.contains(var46))
                {
                    var6.putNextEntry(var48);
                    var6.write(var47);
                }

                if (!var11.contains(var46))
                {
                    var7.putNextEntry(var48);
                    var7.write(var47);
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
                catch (IOException var38)
                {
                    ;
                }
            }

            if (var5 != null)
            {
                try
                {
                    var5.close();
                }
                catch (IOException var37)
                {
                    ;
                }
            }

            if (var6 != null)
            {
                try
                {
                    var6.close();
                }
                catch (IOException var36)
                {
                    ;
                }
            }

            if (var7 != null)
            {
                try
                {
                    var7.close();
                }
                catch (IOException var35)
                {
                    ;
                }
            }
        }
    }

    private static void copyClass(ZipFile var0, ZipEntry var1, ZipOutputStream var2, ZipOutputStream var3, boolean var4) throws IOException
    {
        ClassReader var5 = new ClassReader(readEntry(var0, var1));
        ClassNode var6 = new ClassNode();
        var5.accept(var6, 0);

        if (!dontAnnotate.contains(var6.name))
        {
            if (var6.visibleAnnotations == null)
            {
                var6.visibleAnnotations = new ArrayList();
            }

            var6.visibleAnnotations.add(getSideAnn(var4));
        }

        ClassWriter var7 = new ClassWriter(1);
        var6.accept(var7);
        byte[] var8 = var7.toByteArray();
        ZipEntry var9 = new ZipEntry(var1.getName());

        if (var2 != null)
        {
            var2.putNextEntry(var9);
            var2.write(var8);
        }

        if (var3 != null)
        {
            var3.putNextEntry(var9);
            var3.write(var8);
        }
    }

    private static AnnotationNode getSideAnn(boolean var0)
    {
        AnnotationNode var1 = new AnnotationNode(Type.getDescriptor(SideOnly.class));
        var1.values = new ArrayList();
        var1.values.add("value");
        var1.values.add(new String[] {Type.getDescriptor(Side.class), var0 ? "CLIENT" : "SERVER"});
        return var1;
    }

    private static Hashtable getClassEntries(ZipFile var0, ZipOutputStream var1) throws IOException
    {
        Hashtable var2 = new Hashtable();
        Iterator var3 = Collections.list(var0.entries()).iterator();

        while (var3.hasNext())
        {
            ZipEntry var4 = (ZipEntry)var3.next();

            if (var4.isDirectory())
            {
                var1.putNextEntry(var4);
            }
            else
            {
                String var5 = var4.getName();

                if (var5.endsWith(".class") && !var5.startsWith("."))
                {
                    var2.put(var5.replace(".class", ""), var4);
                }
                else
                {
                    ZipEntry var6 = new ZipEntry(var4.getName());
                    var1.putNextEntry(var6);
                    var1.write(readEntry(var0, var4));
                }
            }
        }

        return var2;
    }

    private static byte[] readEntry(ZipFile var0, ZipEntry var1) throws IOException
    {
        return readFully(var0.getInputStream(var1));
    }

    private static byte[] readFully(InputStream var0) throws IOException
    {
        byte[] var1 = new byte[4096];
        ByteArrayOutputStream var2 = new ByteArrayOutputStream();
        int var3;

        do
        {
            var3 = var0.read(var1);

            if (var3 > 0)
            {
                var2.write(var1, 0, var3);
            }
        }
        while (var3 != -1);

        return var2.toByteArray();
    }

    public static byte[] processClass(byte[] var0, byte[] var1, MCPMerger$ClassInfo var2)
    {
        ClassNode var3 = getClassNode(var0);
        ClassNode var4 = getClassNode(var1);
        processFields(var3, var4, var2);
        processMethods(var3, var4, var2);
        ClassWriter var5 = new ClassWriter(1);
        var3.accept(var5);
        return var5.toByteArray();
    }

    private static ClassNode getClassNode(byte[] var0)
    {
        ClassReader var1 = new ClassReader(var0);
        ClassNode var2 = new ClassNode();
        var1.accept(var2, 0);
        return var2;
    }

    private static void processFields(ClassNode var0, ClassNode var1, MCPMerger$ClassInfo var2)
    {
        List var3 = var0.fields;
        List var4 = var1.fields;
        int var5 = 0;
        int var6;
        FieldNode var7;

        for (var6 = 0; var6 < var3.size(); ++var6)
        {
            var7 = (FieldNode)var3.get(var6);

            if (var5 < var4.size())
            {
                if (!var7.name.equals(((FieldNode)var4.get(var5)).name))
                {
                    boolean var8 = false;

                    for (int var9 = var5 + 1; var9 < var4.size(); ++var9)
                    {
                        if (var7.name.equals(((FieldNode)var4.get(var9)).name))
                        {
                            var8 = true;
                            break;
                        }
                    }

                    if (var8)
                    {
                        boolean var12 = false;
                        FieldNode var10 = (FieldNode)var4.get(var5);

                        for (int var11 = var6 + 1; var11 < var3.size(); ++var11)
                        {
                            if (var10.name.equals(((FieldNode)var3.get(var11)).name))
                            {
                                var12 = true;
                                break;
                            }
                        }

                        if (!var12)
                        {
                            if (var10.visibleAnnotations == null)
                            {
                                var10.visibleAnnotations = new ArrayList();
                            }

                            var10.visibleAnnotations.add(getSideAnn(false));
                            var3.add(var6++, var10);
                            var2.sField.add(var10);
                        }
                    }
                    else
                    {
                        if (var7.visibleAnnotations == null)
                        {
                            var7.visibleAnnotations = new ArrayList();
                        }

                        var7.visibleAnnotations.add(getSideAnn(true));
                        var4.add(var5, var7);
                        var2.cField.add(var7);
                    }
                }
            }
            else
            {
                if (var7.visibleAnnotations == null)
                {
                    var7.visibleAnnotations = new ArrayList();
                }

                var7.visibleAnnotations.add(getSideAnn(true));
                var4.add(var5, var7);
                var2.cField.add(var7);
            }

            ++var5;
        }

        if (var4.size() != var3.size())
        {
            for (var6 = var3.size(); var6 < var4.size(); ++var6)
            {
                var7 = (FieldNode)var4.get(var6);

                if (var7.visibleAnnotations == null)
                {
                    var7.visibleAnnotations = new ArrayList();
                }

                var7.visibleAnnotations.add(getSideAnn(true));
                var3.add(var6++, var7);
                var2.sField.add(var7);
            }
        }
    }

    private static void processMethods(ClassNode var0, ClassNode var1, MCPMerger$ClassInfo var2)
    {
        List var3 = var0.methods;
        List var4 = var1.methods;
        LinkedHashSet var5 = Sets.newLinkedHashSet();
        int var6 = 0;
        int var7 = 0;
        int var8 = var3.size();
        int var9 = var4.size();
        String var10 = "";
        String var11 = var10;
        String var12 = "";
        MCPMerger$MethodWrapper var14;
        label62:

        while (var6 < var8 || var7 < var9)
        {
            while (true)
            {
                MethodNode var13;

                if (var7 < var9)
                {
                    var13 = (MethodNode)var4.get(var7);
                    var12 = var13.name;

                    if (var12.equals(var11) || var6 == var8)
                    {
                        var14 = new MCPMerger$MethodWrapper(var13);
                        var14.server = true;
                        var5.add(var14);
                        ++var7;

                        if (var7 < var9)
                        {
                            continue;
                        }
                    }
                }

                while (true)
                {
                    if (var6 >= var8)
                    {
                        continue label62;
                    }

                    var13 = (MethodNode)var3.get(var6);
                    var11 = var10;
                    var10 = var13.name;

                    if (!var10.equals(var11) && var7 != var9)
                    {
                        continue label62;
                    }

                    var14 = new MCPMerger$MethodWrapper(var13);
                    var14.client = true;
                    var5.add(var14);
                    ++var6;

                    if (var6 >= var8)
                    {
                        continue label62;
                    }
                }
            }
        }

        var3.clear();
        var4.clear();
        Iterator var15 = var5.iterator();

        while (var15.hasNext())
        {
            var14 = (MCPMerger$MethodWrapper)var15.next();
            var3.add(MCPMerger$MethodWrapper.access$000(var14));
            var4.add(MCPMerger$MethodWrapper.access$000(var14));

            if (!var14.server || !var14.client)
            {
                if (MCPMerger$MethodWrapper.access$000(var14).visibleAnnotations == null)
                {
                    MCPMerger$MethodWrapper.access$000(var14).visibleAnnotations = Lists.newArrayListWithExpectedSize(1);
                }

                MCPMerger$MethodWrapper.access$000(var14).visibleAnnotations.add(getSideAnn(var14.client));

                if (var14.client)
                {
                    var2.sMethods.add(MCPMerger$MethodWrapper.access$000(var14));
                }
                else
                {
                    var2.cMethods.add(MCPMerger$MethodWrapper.access$000(var14));
                }
            }
        }
    }

    public static byte[] getClassBytes(String var0) throws IOException
    {
        InputStream var1 = null;
        byte[] var2;

        try
        {
            var1 = MCPMerger.class.getResourceAsStream("/" + var0.replace('.', '/').concat(".class"));
            var2 = readFully(var1);
        }
        finally
        {
            if (var1 != null)
            {
                try
                {
                    var1.close();
                }
                catch (IOException var9)
                {
                    ;
                }
            }
        }

        return var2;
    }
}
