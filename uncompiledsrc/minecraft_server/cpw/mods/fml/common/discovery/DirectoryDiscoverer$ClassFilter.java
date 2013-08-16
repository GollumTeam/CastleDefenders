package cpw.mods.fml.common.discovery;

import cpw.mods.fml.common.discovery.DirectoryDiscoverer$1;
import java.io.File;
import java.io.FileFilter;

class DirectoryDiscoverer$ClassFilter implements FileFilter
{
    final DirectoryDiscoverer this$0;

    private DirectoryDiscoverer$ClassFilter(DirectoryDiscoverer var1)
    {
        this.this$0 = var1;
    }

    public boolean accept(File var1)
    {
        return var1.isFile() && ITypeDiscoverer.classFile.matcher(var1.getName()).find() || var1.isDirectory();
    }

    DirectoryDiscoverer$ClassFilter(DirectoryDiscoverer var1, DirectoryDiscoverer$1 var2)
    {
        this(var1);
    }
}
