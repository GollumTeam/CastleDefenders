package cpw.mods.fml.common.asm.transformers.deobf;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.commons.RemappingClassAdapter;

public class FMLRemappingAdapter extends RemappingClassAdapter
{
    public FMLRemappingAdapter(ClassVisitor var1)
    {
        super(var1, FMLDeobfuscatingRemapper.INSTANCE);
    }

    public void visit(int var1, int var2, String var3, String var4, String var5, String[] var6)
    {
        if (var6 == null)
        {
            var6 = new String[0];
        }

        FMLDeobfuscatingRemapper.INSTANCE.mergeSuperMaps(var3, var5, var6);
        super.visit(var1, var2, var3, var4, var5, var6);
    }
}
