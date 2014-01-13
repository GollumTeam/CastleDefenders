package cpw.mods.fml.common.asm.transformers;

import cpw.mods.fml.common.asm.transformers.AccessTransformer$1;

class AccessTransformer$Modifier
{
    public String name;
    public String desc;
    public int oldAccess;
    public int newAccess;
    public int targetAccess;
    public boolean changeFinal;
    public boolean markFinal;
    protected boolean modifyClassVisibility;

    final AccessTransformer this$0;

    private AccessTransformer$Modifier(AccessTransformer var1)
    {
        this.this$0 = var1;
        this.name = "";
        this.desc = "";
        this.oldAccess = 0;
        this.newAccess = 0;
        this.targetAccess = 0;
        this.changeFinal = false;
        this.markFinal = false;
    }

    private void setTargetAccess(String var1)
    {
        if (var1.startsWith("public"))
        {
            this.targetAccess = 1;
        }
        else if (var1.startsWith("private"))
        {
            this.targetAccess = 2;
        }
        else if (var1.startsWith("protected"))
        {
            this.targetAccess = 4;
        }

        if (var1.endsWith("-f"))
        {
            this.changeFinal = true;
            this.markFinal = false;
        }
        else if (var1.endsWith("+f"))
        {
            this.changeFinal = true;
            this.markFinal = true;
        }
    }

    AccessTransformer$Modifier(AccessTransformer var1, AccessTransformer$1 var2)
    {
        this(var1);
    }

    static void access$100(AccessTransformer$Modifier var0, String var1)
    {
        var0.setTargetAccess(var1);
    }
}
