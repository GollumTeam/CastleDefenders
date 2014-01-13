package cpw.mods.fml.common.registry;

import com.google.common.base.Function;
import com.google.common.collect.Tables;
import com.google.common.collect.Table.Cell;

final class GameData$2 implements Function
{
    public Cell apply(ItemData var1)
    {
        return !"Minecraft".equals(var1.getModId()) && var1.isOveridden() ? Tables.immutableCell(var1.getModId(), var1.getItemType(), Integer.valueOf(var1.getItemId())) : null;
    }

    public Object apply(Object var1)
    {
        return this.apply((ItemData)var1);
    }
}
