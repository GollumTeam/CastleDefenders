package cpw.mods.fml.common.discovery;

import com.google.common.base.Predicate;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.discovery.ASMDataTable$ASMData;

class ASMDataTable$ModContainerPredicate implements Predicate
{
    private ModContainer container;

    public ASMDataTable$ModContainerPredicate(ModContainer var1)
    {
        this.container = var1;
    }

    public boolean apply(ASMDataTable$ASMData var1)
    {
        return this.container.getSource().equals(ASMDataTable$ASMData.access$000(var1).getModContainer());
    }

    public boolean apply(Object var1)
    {
        return this.apply((ASMDataTable$ASMData)var1);
    }
}
