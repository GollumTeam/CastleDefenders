package cpw.mods.fml.common.discovery.asm;

import com.google.common.collect.Lists;
import java.util.LinkedList;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

public class ModMethodVisitor extends MethodVisitor
{
    private ASMModParser discoverer;
    private boolean inCode;
    private LinkedList labels = Lists.newLinkedList();
    private String foundProperties;
    private boolean validProperties;

    public ModMethodVisitor(String var1, ASMModParser var2)
    {
        super(262144);
        this.discoverer = var2;
    }

    public void visitCode()
    {
        this.labels.clear();
    }

    public void visitLdcInsn(Object var1)
    {
        if (var1 instanceof String && this.labels.size() == 1)
        {
            this.foundProperties = (String)var1;
        }
    }

    public void visitInsn(int var1)
    {
        if (176 == var1 && this.labels.size() == 1 && this.foundProperties != null)
        {
            this.validProperties = true;
        }
    }

    public void visitLabel(Label var1)
    {
        this.labels.push(var1);
    }

    public void visitEnd()
    {
        if (this.validProperties)
        {
            this.discoverer.setBaseModProperties(this.foundProperties);
        }
    }
}
