package cpw.mods.fml.common.registry;

import com.google.common.base.Function;

final class GameData$1 implements Function
{
    public Integer apply(ItemData var1)
    {
        return Integer.valueOf(var1.getItemId());
    }

    public Object apply(Object var1)
    {
        return this.apply((ItemData)var1);
    }
}
