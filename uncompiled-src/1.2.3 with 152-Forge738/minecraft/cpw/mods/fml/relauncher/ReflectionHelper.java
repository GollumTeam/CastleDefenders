package cpw.mods.fml.relauncher;

import cpw.mods.fml.relauncher.ReflectionHelper$UnableToAccessFieldException;
import cpw.mods.fml.relauncher.ReflectionHelper$UnableToFindClassException;
import cpw.mods.fml.relauncher.ReflectionHelper$UnableToFindFieldException;
import cpw.mods.fml.relauncher.ReflectionHelper$UnableToFindMethodException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionHelper
{
    public static Field findField(Class var0, String ... var1)
    {
        Exception var2 = null;
        String[] var3 = var1;
        int var4 = var1.length;
        int var5 = 0;

        while (var5 < var4)
        {
            String var6 = var3[var5];

            try
            {
                Field var7 = var0.getDeclaredField(var6);
                var7.setAccessible(true);
                return var7;
            }
            catch (Exception var8)
            {
                var2 = var8;
                ++var5;
            }
        }

        throw new ReflectionHelper$UnableToFindFieldException(var1, var2);
    }

    public static Object getPrivateValue(Class var0, Object var1, int var2)
    {
        try
        {
            Field var3 = var0.getDeclaredFields()[var2];
            var3.setAccessible(true);
            return var3.get(var1);
        }
        catch (Exception var4)
        {
            throw new ReflectionHelper$UnableToAccessFieldException(new String[0], var4);
        }
    }

    public static Object getPrivateValue(Class var0, Object var1, String ... var2)
    {
        try
        {
            return findField(var0, var2).get(var1);
        }
        catch (Exception var4)
        {
            throw new ReflectionHelper$UnableToAccessFieldException(var2, var4);
        }
    }

    public static void setPrivateValue(Class var0, Object var1, Object var2, int var3)
    {
        try
        {
            Field var4 = var0.getDeclaredFields()[var3];
            var4.setAccessible(true);
            var4.set(var1, var2);
        }
        catch (Exception var5)
        {
            throw new ReflectionHelper$UnableToAccessFieldException(new String[0], var5);
        }
    }

    public static void setPrivateValue(Class var0, Object var1, Object var2, String ... var3)
    {
        try
        {
            findField(var0, var3).set(var1, var2);
        }
        catch (Exception var5)
        {
            throw new ReflectionHelper$UnableToAccessFieldException(var3, var5);
        }
    }

    public static Class getClass(ClassLoader var0, String ... var1)
    {
        Exception var2 = null;
        String[] var3 = var1;
        int var4 = var1.length;
        int var5 = 0;

        while (var5 < var4)
        {
            String var6 = var3[var5];

            try
            {
                return Class.forName(var6, false, var0);
            }
            catch (Exception var8)
            {
                var2 = var8;
                ++var5;
            }
        }

        throw new ReflectionHelper$UnableToFindClassException(var1, var2);
    }

    public static Method findMethod(Class var0, Object var1, String[] var2, Class ... var3)
    {
        Exception var4 = null;
        String[] var5 = var2;
        int var6 = var2.length;
        int var7 = 0;

        while (var7 < var6)
        {
            String var8 = var5[var7];

            try
            {
                Method var9 = var0.getDeclaredMethod(var8, var3);
                var9.setAccessible(true);
                return var9;
            }
            catch (Exception var10)
            {
                var4 = var10;
                ++var7;
            }
        }

        throw new ReflectionHelper$UnableToFindMethodException(var2, var4);
    }
}
