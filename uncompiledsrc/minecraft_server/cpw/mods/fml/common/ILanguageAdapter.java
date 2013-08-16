package cpw.mods.fml.common;

import cpw.mods.fml.relauncher.Side;
import java.lang.reflect.Field;

public interface ILanguageAdapter
{
    Object getNewInstance(FMLModContainer var1, Class var2, ClassLoader var3) throws Exception;

    boolean supportsStatics();

    void setProxy(Field var1, Class var2, Object var3) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException;

    void setInternalProxies(ModContainer var1, Side var2, ClassLoader var3);
}
