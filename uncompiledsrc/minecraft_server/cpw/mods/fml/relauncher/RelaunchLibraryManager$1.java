package cpw.mods.fml.relauncher;

import java.io.File;
import java.io.FilenameFilter;

final class RelaunchLibraryManager$1 implements FilenameFilter
{
    public boolean accept(File var1, String var2)
    {
        return var2.endsWith(".jar");
    }
}
