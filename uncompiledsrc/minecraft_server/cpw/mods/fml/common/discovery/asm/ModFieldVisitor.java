package cpw.mods.fml.common.discovery.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.FieldVisitor;

public class ModFieldVisitor extends FieldVisitor
{
    private String fieldName;
    private ASMModParser discoverer;

    public ModFieldVisitor(String var1, ASMModParser var2)
    {
        super(262144);
        this.fieldName = var1;
        this.discoverer = var2;
    }

    public AnnotationVisitor visitAnnotation(String var1, boolean var2)
    {
        this.discoverer.startFieldAnnotation(this.fieldName, var1);
        return new ModAnnotationVisitor(this.discoverer);
    }
}
