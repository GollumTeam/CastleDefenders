package cpw.mods.fml.common.discovery;

import java.util.List;
import java.util.regex.Pattern;

public interface ITypeDiscoverer
{
    Pattern classFile = Pattern.compile("([^\\s$]+).class$");

    List discover(ModCandidate var1, ASMDataTable var2);
}
