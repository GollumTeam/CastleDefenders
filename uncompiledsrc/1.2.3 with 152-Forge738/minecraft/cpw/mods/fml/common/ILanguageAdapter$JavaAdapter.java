package cpw.mods.fml.common;

import cpw.mods.fml.relauncher.Side;
import java.lang.reflect.Field;

public class ILanguageAdapter$JavaAdapter implements ILanguageAdapter
{
    public Object getNewInstance(FMLModContainer var1, Class var2, ClassLoader var3) throws Exception
    {
        return var2.newInstance();
    }

    public boolean supportsStatics()
    {
        return true;
    }

    public void setProxy(Field var1, Class var2, Object var3) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException
    {
        var1.set((Object)null, var3);
    }

    public void setInternalProxies(ModContainer var1, Side var2, ClassLoader var3) {}
}
