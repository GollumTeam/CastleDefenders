package cpw.mods.fml.common.asm;

import cpw.mods.fml.common.registry.BlockProxy;
import cpw.mods.fml.relauncher.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

public class ASMTransformer implements IClassTransformer
{
    public byte[] transform(String var1, String var2, byte[] var3)
    {
        if ("net.minecraft.src.Block".equals(var1))
        {
            ClassReader var4 = new ClassReader(var3);
            ClassNode var5 = new ClassNode(262144);
            var4.accept(var5, 8);
            var5.interfaces.add(Type.getInternalName(BlockProxy.class));
            ClassWriter var6 = new ClassWriter(3);
            var5.accept(var6);
            return var6.toByteArray();
        }
        else
        {
            return var3;
        }
    }
}
