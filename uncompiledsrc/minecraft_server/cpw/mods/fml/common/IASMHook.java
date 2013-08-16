package cpw.mods.fml.common;

import org.objectweb.asm.tree.ClassNode;

public interface IASMHook
{
    ClassNode[] inject(ClassNode var1);

    void modifyClass(String var1, ClassNode var2);
}
