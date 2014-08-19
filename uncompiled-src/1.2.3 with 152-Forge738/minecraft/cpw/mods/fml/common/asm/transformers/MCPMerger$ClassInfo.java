package cpw.mods.fml.common.asm.transformers;

import java.util.ArrayList;

class MCPMerger$ClassInfo
{
    public String name;
    public ArrayList cField = new ArrayList();
    public ArrayList sField = new ArrayList();
    public ArrayList cMethods = new ArrayList();
    public ArrayList sMethods = new ArrayList();

    public MCPMerger$ClassInfo(String var1)
    {
        this.name = var1;
    }

    public boolean isSame()
    {
        return this.cField.size() == 0 && this.sField.size() == 0 && this.cMethods.size() == 0 && this.sMethods.size() == 0;
    }
}
