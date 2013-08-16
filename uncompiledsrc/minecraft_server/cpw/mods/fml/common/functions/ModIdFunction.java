package cpw.mods.fml.common.functions;

import com.google.common.base.Function;
import cpw.mods.fml.common.ModContainer;

public final class ModIdFunction implements Function
{
    public String apply(ModContainer var1)
    {
        return var1.getModId();
    }

    public Object apply(Object var1)
    {
        return this.apply((ModContainer)var1);
    }
}
