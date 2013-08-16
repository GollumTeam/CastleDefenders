package cpw.mods.fml.relauncher;

import java.util.Map;

public interface IFMLLoadingPlugin
{
    String[] getLibraryRequestClass();

    String[] getASMTransformerClass();

    String getModContainerClass();

    String getSetupClass();

    void injectData(Map var1);
}
