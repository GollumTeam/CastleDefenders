package cpw.mods.fml.common.functions;

import com.google.common.base.Function;
import cpw.mods.fml.common.ModContainer;

public class ModNameFunction implements Function
{
    public String apply(ModContainer var1)
    {
        return var1.getName();
    }

    public Object apply(Object var1)
    {
        return this.apply((ModContainer)var1);
    }
}
