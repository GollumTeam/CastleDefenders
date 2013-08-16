package cpw.mods.fml.common.discovery;

import com.google.common.base.Throwables;
import java.util.List;

public enum ContainerType
{
    JAR(JarDiscoverer.class),
    DIR(DirectoryDiscoverer.class);
    private ITypeDiscoverer discoverer;

    private ContainerType(Class var3)
    {
        try
        {
            this.discoverer = (ITypeDiscoverer)var3.newInstance();
        }
        catch (Exception var5)
        {
            throw Throwables.propagate(var5);
        }
    }

    public List findMods(ModCandidate var1, ASMDataTable var2)
    {
        return this.discoverer.discover(var1, var2);
    }
}
