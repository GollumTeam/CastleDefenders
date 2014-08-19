package cpw.mods.fml.common;

import cpw.mods.fml.common.Loader$1;
import java.util.Comparator;

class Loader$ModIdComparator implements Comparator
{
    final Loader this$0;

    private Loader$ModIdComparator(Loader var1)
    {
        this.this$0 = var1;
    }

    public int compare(ModContainer var1, ModContainer var2)
    {
        return var1.getModId().compareTo(var2.getModId());
    }

    public int compare(Object var1, Object var2)
    {
        return this.compare((ModContainer)var1, (ModContainer)var2);
    }

    Loader$ModIdComparator(Loader var1, Loader$1 var2)
    {
        this(var1);
    }
}
