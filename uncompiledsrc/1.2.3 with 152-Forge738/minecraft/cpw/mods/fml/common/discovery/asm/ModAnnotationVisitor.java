package cpw.mods.fml.common.discovery.asm;

import org.objectweb.asm.AnnotationVisitor;

public class ModAnnotationVisitor extends AnnotationVisitor
{
    private ASMModParser discoverer;
    private boolean array;
    private String name;
    private boolean isSubAnnotation;

    public ModAnnotationVisitor(ASMModParser var1)
    {
        super(262144);
        this.discoverer = var1;
    }

    public ModAnnotationVisitor(ASMModParser var1, String var2)
    {
        this(var1);
        this.array = true;
        this.name = var2;
        var1.addAnnotationArray(var2);
    }

    public ModAnnotationVisitor(ASMModParser var1, boolean var2)
    {
        this(var1);
        this.isSubAnnotation = true;
    }

    public void visit(String var1, Object var2)
    {
        this.discoverer.addAnnotationProperty(var1, var2);
    }

    public void visitEnum(String var1, String var2, String var3)
    {
        this.discoverer.addAnnotationEnumProperty(var1, var2, var3);
    }

    public AnnotationVisitor visitArray(String var1)
    {
        return new ModAnnotationVisitor(this.discoverer, var1);
    }

    public AnnotationVisitor visitAnnotation(String var1, String var2)
    {
        this.discoverer.addSubAnnotation(var1, var2);
        return new ModAnnotationVisitor(this.discoverer, true);
    }

    public void visitEnd()
    {
        if (this.array)
        {
            this.discoverer.endArray();
        }

        if (this.isSubAnnotation)
        {
            this.discoverer.endSubAnnotation();
        }
    }
}
