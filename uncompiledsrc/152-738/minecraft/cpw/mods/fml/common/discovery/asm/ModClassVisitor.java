package cpw.mods.fml.common.discovery.asm;

import java.util.Collections;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

public class ModClassVisitor extends ClassVisitor
{
    private ASMModParser discoverer;

    public ModClassVisitor(ASMModParser var1)
    {
        super(262144);
        this.discoverer = var1;
    }

    public void visit(int var1, int var2, String var3, String var4, String var5, String[] var6)
    {
        this.discoverer.beginNewTypeName(var3, var1, var5);
    }

    public AnnotationVisitor visitAnnotation(String var1, boolean var2)
    {
        this.discoverer.startClassAnnotation(var1);
        return new ModAnnotationVisitor(this.discoverer);
    }

    public FieldVisitor visitField(int var1, String var2, String var3, String var4, Object var5)
    {
        return new ModFieldVisitor(var2, this.discoverer);
    }

    public MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5)
    {
        return this.discoverer.isBaseMod(Collections.emptyList()) && var2.equals("getPriorities") && var3.equals(Type.getMethodDescriptor(Type.getType(String.class), new Type[0])) ? new ModMethodVisitor(var2, this.discoverer) : null;
    }
}
