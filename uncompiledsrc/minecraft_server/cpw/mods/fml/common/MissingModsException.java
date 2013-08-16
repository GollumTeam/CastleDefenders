package cpw.mods.fml.common;

import java.util.Set;

public class MissingModsException extends RuntimeException
{
    public Set missingMods;

    public MissingModsException(Set var1)
    {
        this.missingMods = var1;
    }
}
