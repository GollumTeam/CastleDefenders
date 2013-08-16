package cpw.mods.fml.common.asm.transformers;

import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import cpw.mods.fml.common.asm.transformers.deobf.FMLRemappingAdapter;
import cpw.mods.fml.relauncher.IClassNameTransformer;
import cpw.mods.fml.relauncher.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

public class DeobfuscationTransformer implements IClassTransformer, IClassNameTransformer
{
    public byte[] transform(String var1, String var2, byte[] var3)
    {
        if (var3 == null)
        {
            return null;
        }
        else
        {
            ClassReader var4 = new ClassReader(var3);
            ClassWriter var5 = new ClassWriter(1);
            FMLRemappingAdapter var6 = new FMLRemappingAdapter(var5);
            var4.accept(var6, 8);
            return var5.toByteArray();
        }
    }

    public String remapClassName(String var1)
    {
        return FMLDeobfuscatingRemapper.INSTANCE.map(var1.replace('.', '/')).replace('/', '.');
    }

    public String unmapClassName(String var1)
    {
        return FMLDeobfuscatingRemapper.INSTANCE.unmap(var1.replace('.', '/')).replace('/', '.');
    }
}
