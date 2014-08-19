package cpw.mods.fml.common.asm.transformers;

import cpw.mods.fml.relauncher.FMLRelauncher;
import cpw.mods.fml.relauncher.IClassTransformer;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

public class SideTransformer implements IClassTransformer
{
    private static String SIDE = FMLRelauncher.side();
    private static final boolean DEBUG = false;

    public byte[] transform(String var1, String var2, byte[] var3)
    {
        if (var3 == null)
        {
            return null;
        }
        else
        {
            ClassNode var4 = new ClassNode();
            ClassReader var5 = new ClassReader(var3);
            var5.accept(var4, 0);

            if (this.remove(var4.visibleAnnotations, SIDE))
            {
                throw new RuntimeException(String.format("Attempted to load class %s for invalid side %s", new Object[] {var4.name, SIDE}));
            }
            else
            {
                Iterator var6 = var4.fields.iterator();

                while (var6.hasNext())
                {
                    FieldNode var7 = (FieldNode)var6.next();

                    if (this.remove(var7.visibleAnnotations, SIDE))
                    {
                        var6.remove();
                    }
                }

                Iterator var9 = var4.methods.iterator();

                while (var9.hasNext())
                {
                    MethodNode var8 = (MethodNode)var9.next();

                    if (this.remove(var8.visibleAnnotations, SIDE))
                    {
                        var9.remove();
                    }
                }

                ClassWriter var10 = new ClassWriter(1);
                var4.accept(var10);
                return var10.toByteArray();
            }
        }
    }

    private boolean remove(List var1, String var2)
    {
        if (var1 == null)
        {
            return false;
        }
        else
        {
            Iterator var3 = var1.iterator();

            while (var3.hasNext())
            {
                AnnotationNode var4 = (AnnotationNode)var3.next();

                if (var4.desc.equals(Type.getDescriptor(SideOnly.class)) && var4.values != null)
                {
                    for (int var5 = 0; var5 < var4.values.size() - 1; var5 += 2)
                    {
                        Object var6 = var4.values.get(var5);
                        Object var7 = var4.values.get(var5 + 1);

                        if (var6 instanceof String && var6.equals("value") && var7 instanceof String[] && !((String[])((String[])var7))[1].equals(var2))
                        {
                            return true;
                        }
                    }
                }
            }

            return false;
        }
    }
}
