package cpw.mods.fml.common.asm;

import cpw.mods.fml.common.asm.FMLSanityChecker$1;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;

class FMLSanityChecker$MLDetectorClassVisitor extends ClassVisitor
{
    private boolean foundMarker;

    private FMLSanityChecker$MLDetectorClassVisitor()
    {
        super(262144);
        this.foundMarker = false;
    }

    public FieldVisitor visitField(int var1, String var2, String var3, String var4, Object var5)
    {
        if ("fmlMarker".equals(var2))
        {
            this.foundMarker = true;
        }

        return null;
    }

    FMLSanityChecker$MLDetectorClassVisitor(FMLSanityChecker$1 var1)
    {
        this();
    }

    static boolean access$100(FMLSanityChecker$MLDetectorClassVisitor var0)
    {
        return var0.foundMarker;
    }
}
