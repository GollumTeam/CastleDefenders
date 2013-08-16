package cpw.mods.fml.common;

import com.google.common.collect.SetMultimap;

public class DuplicateModsFoundException extends LoaderException
{
    public SetMultimap dupes;

    public DuplicateModsFoundException(SetMultimap var1)
    {
        this.dupes = var1;
    }
}
