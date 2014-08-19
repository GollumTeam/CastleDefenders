package cpw.mods.fml.relauncher;

import java.util.Map;
import java.util.concurrent.Callable;

public interface IFMLCallHook extends Callable
{
    void injectData(Map var1);
}
