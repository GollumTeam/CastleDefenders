package cpw.mods.fml.common;

import cpw.mods.fml.relauncher.Side;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;

public class ILanguageAdapter$ScalaAdapter implements ILanguageAdapter
{
    public Object getNewInstance(FMLModContainer var1, Class var2, ClassLoader var3) throws Exception
    {
        Class var4 = Class.forName(var2.getName() + "$", true, var3);
        return var4.getField("MODULE$").get((Object)null);
    }

    public boolean supportsStatics()
    {
        return false;
    }

    public void setProxy(Field var1, Class var2, Object var3) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException
    {
        try
        {
            if (!var2.getName().endsWith("$"))
            {
                var2 = Class.forName(var2.getName() + "$", true, var2.getClassLoader());
            }
        }
        catch (ClassNotFoundException var12)
        {
            FMLLog.log(Level.INFO, (Throwable)var12, "An error occured trying to load a proxy into %s.%s. Did you declare your mod as \'class\' instead of \'object\'?", new Object[] {var2.getSimpleName(), var1.getName()});
            return;
        }

        Object var4 = var2.getField("MODULE$").get((Object)null);

        try
        {
            String var5 = var1.getName() + "_$eq";
            Method[] var6 = var2.getMethods();
            int var7 = var6.length;

            for (int var8 = 0; var8 < var7; ++var8)
            {
                Method var9 = var6[var8];
                Class[] var10 = var9.getParameterTypes();

                if (var5.equals(var9.getName()) && var10.length == 1 && var10[0].isAssignableFrom(var3.getClass()))
                {
                    var9.invoke(var4, new Object[] {var3});
                    return;
                }
            }
        }
        catch (InvocationTargetException var11)
        {
            FMLLog.log(Level.SEVERE, (Throwable)var11, "An error occured trying to load a proxy into %s.%s", new Object[] {var2.getSimpleName(), var1.getName()});
            throw new LoaderException(var11);
        }

        FMLLog.severe("Failed loading proxy into %s.%s, could not find setter function. Did you declare the field with \'val\' instead of \'var\'?", new Object[] {var2.getSimpleName(), var1.getName()});
        throw new LoaderException();
    }

    public void setInternalProxies(ModContainer var1, Side var2, ClassLoader var3)
    {
        Class var4 = var1.getMod().getClass();

        if (var4.getName().endsWith("$"))
        {
            Field[] var5 = var4.getDeclaredFields();
            int var6 = var5.length;

            for (int var7 = 0; var7 < var6; ++var7)
            {
                Field var8 = var5[var7];

                if (var8.getAnnotation(SidedProxy.class) != null)
                {
                    String var9 = var2.isClient() ? ((SidedProxy)var8.getAnnotation(SidedProxy.class)).clientSide() : ((SidedProxy)var8.getAnnotation(SidedProxy.class)).serverSide();

                    try
                    {
                        Object var10 = Class.forName(var9, true, var3).newInstance();

                        if (!var8.getType().isAssignableFrom(var10.getClass()))
                        {
                            FMLLog.severe("Attempted to load a proxy type %s into %s.%s, but the types don\'t match", new Object[] {var9, var4.getSimpleName(), var8.getName()});
                            throw new LoaderException();
                        }

                        this.setProxy(var8, var4, var10);
                    }
                    catch (Exception var11)
                    {
                        FMLLog.log(Level.SEVERE, (Throwable)var11, "An error occured trying to load a proxy into %s.%s", new Object[] {var4.getSimpleName(), var8.getName()});
                        throw new LoaderException(var11);
                    }
                }
            }
        }
        else
        {
            FMLLog.finer("Mod does not appear to be a singleton.", new Object[0]);
        }
    }
}
