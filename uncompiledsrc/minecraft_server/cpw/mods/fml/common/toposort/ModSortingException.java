package cpw.mods.fml.common.toposort;

import cpw.mods.fml.common.toposort.ModSortingException$SortingExceptionData;
import java.util.Set;

public class ModSortingException extends RuntimeException
{
    private ModSortingException$SortingExceptionData sortingExceptionData;

    public ModSortingException(String var1, Object var2, Set var3)
    {
        super(var1);
        this.sortingExceptionData = new ModSortingException$SortingExceptionData(this, var2, var3);
    }

    public ModSortingException$SortingExceptionData getExceptionData()
    {
        return this.sortingExceptionData;
    }
}
