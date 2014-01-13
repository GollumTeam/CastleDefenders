package cpw.mods.fml.common;

import java.lang.reflect.Field;
import java.util.logging.Logger;

public class ReflectionHelper
{
    public static boolean obfuscation;

    public ReflectionHelper()
    {
    }

    public static Object getPrivateValue(Class class1, Object obj, int i)
    {
        try
        {
            Field field = class1.getDeclaredFields()[i];
            field.setAccessible(true);
            return field.get(obj);
        }
        catch (Exception exception)
        {
            FMLCommonHandler.instance().getFMLLogger().severe(String.format("There was a problem getting field %d from %s", new Object[]
                    {
                        Integer.valueOf(i), class1.getName()
                    }));
            FMLCommonHandler.instance().getFMLLogger().throwing("ReflectionHelper", "getPrivateValue", exception);
            throw new RuntimeException(exception);
        }
    }

    public static Object getPrivateValue(Class class1, Object obj, String s)
    {
        try
        {
            Field field = class1.getDeclaredField(s);
            field.setAccessible(true);
            return field.get(obj);
        }
        catch (Exception exception)
        {
            if (s.length() > 3 && !obfuscation || s.length() <= 3 && obfuscation)
            {
                FMLCommonHandler.instance().getFMLLogger().severe(String.format("There was a problem getting field %s from %s", new Object[]
                        {
                            s, class1.getName()
                        }));
                FMLCommonHandler.instance().getFMLLogger().throwing("ReflectionHelper", "getPrivateValue", exception);
            }

            throw new RuntimeException(exception);
        }
    }

    public static void setPrivateValue(Class class1, Object obj, int i, Object obj1)
    {
        try
        {
            Field field = class1.getDeclaredFields()[i];
            field.setAccessible(true);
            field.set(obj, obj1);
        }
        catch (Exception exception)
        {
            FMLCommonHandler.instance().getFMLLogger().severe(String.format("There was a problem setting field %d from %s", new Object[]
                    {
                        Integer.valueOf(i), class1.getName()
                    }));
            FMLCommonHandler.instance().getFMLLogger().throwing("ReflectionHelper", "getPrivateValue", exception);
            throw new RuntimeException(exception);
        }
    }

    public static void setPrivateValue(Class class1, Object obj, String s, Object obj1)
    {
        try
        {
            Field field = class1.getDeclaredField(s);
            field.setAccessible(true);
            field.set(obj, obj1);
        }
        catch (Exception exception)
        {
            if (s.length() > 3 && !obfuscation || s.length() <= 3 && obfuscation)
            {
                FMLCommonHandler.instance().getFMLLogger().severe(String.format("There was a problem setting field %s from %s", new Object[]
                        {
                            s, class1.getName()
                        }));
                FMLCommonHandler.instance().getFMLLogger().throwing("ReflectionHelper", "getPrivateValue", exception);
            }

            throw new RuntimeException(exception);
        }
    }

    public static void detectObfuscation(Class class1)
    {
        obfuscation = !class1.getSimpleName().equals("World");
    }
}
