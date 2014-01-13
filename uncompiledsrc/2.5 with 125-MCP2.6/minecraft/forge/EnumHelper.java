package forge;

import java.lang.reflect.*;
import java.util.*;
import net.minecraft.src.*;

public class EnumHelper
{
    private static Object reflectionFactory = null;
    private static Method newConstructorAccessor = null;
    private static Method newInstance = null;
    private static Method newFieldAccessor = null;
    private static Method fieldAccessorSet = null;
    private static boolean isSetup;
    private static Class ctrs[][];
    private static boolean decompiledFlags[];

    public EnumHelper()
    {
    }

    public static EnumAction addAction(String s)
    {
        if (!isSetup)
        {
            setup();
        }

        return (EnumAction)addEnum(decompiledFlags[0], net.minecraft.src.EnumAction.class, s, new Class[0], new Object[0]);
    }

    public static EnumArmorMaterial addArmorMaterial(String s, int i, int ai[], int j)
    {
        if (!isSetup)
        {
            setup();
        }

        return (EnumArmorMaterial)addEnum(decompiledFlags[1], net.minecraft.src.EnumArmorMaterial.class, s, new Class[]
                {
                    Integer.TYPE, int[].class, Integer.TYPE
                }, new Object[]
                {
                    Integer.valueOf(i), ai, Integer.valueOf(j)
                });
    }

    public static EnumArt addArt(String s, String s1, int i, int j, int k, int l)
    {
        if (!isSetup)
        {
            setup();
        }

        return (EnumArt)addEnum(decompiledFlags[2], net.minecraft.src.EnumArt.class, s, new Class[]
                {
                    java.lang.String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE
                }, new Object[]
                {
                    s1, Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k), Integer.valueOf(l)
                });
    }

    public static EnumCreatureAttribute addCreatureAttribute(String s)
    {
        if (!isSetup)
        {
            setup();
        }

        return (EnumCreatureAttribute)addEnum(decompiledFlags[3], net.minecraft.src.EnumCreatureAttribute.class, s, new Class[0], new Object[0]);
    }

    public static EnumCreatureType addCreatureType(String s, Class class1, int i, Material material, boolean flag)
    {
        if (!isSetup)
        {
            setup();
        }

        return (EnumCreatureType)addEnum(decompiledFlags[4], net.minecraft.src.EnumCreatureType.class, s, new Class[]
                {
                    java.lang.Class.class, Integer.TYPE, net.minecraft.src.Material.class, Boolean.TYPE
                }, new Object[]
                {
                    class1, Integer.valueOf(i), material, Boolean.valueOf(flag)
                });
    }

    public static EnumDoor addDoor(String s)
    {
        if (!isSetup)
        {
            setup();
        }

        return (EnumDoor)addEnum(decompiledFlags[5], net.minecraft.src.EnumDoor.class, s, new Class[0], new Object[0]);
    }

    public static EnumEnchantmentType addEnchantmentType(String s)
    {
        if (!isSetup)
        {
            setup();
        }

        return (EnumEnchantmentType)addEnum(decompiledFlags[6], net.minecraft.src.EnumEnchantmentType.class, s, new Class[0], new Object[0]);
    }

    public static EnumMobType addMobType(String s)
    {
        if (!isSetup)
        {
            setup();
        }

        return (EnumMobType)addEnum(decompiledFlags[7], net.minecraft.src.EnumMobType.class, s, new Class[0], new Object[0]);
    }

    public static EnumMovingObjectType addMovingObjectType(String s)
    {
        if (!isSetup)
        {
            setup();
        }

        return (EnumMovingObjectType)addEnum(decompiledFlags[8], net.minecraft.src.EnumMovingObjectType.class, s, new Class[0], new Object[0]);
    }

    public static EnumSkyBlock addSkyBlock(String s, int i)
    {
        if (!isSetup)
        {
            setup();
        }

        return (EnumSkyBlock)addEnum(decompiledFlags[9], net.minecraft.src.EnumSkyBlock.class, s, new Class[]
                {
                    Integer.TYPE
                }, new Object[]
                {
                    Integer.valueOf(i)
                });
    }

    public static EnumStatus addStatus(String s)
    {
        if (!isSetup)
        {
            setup();
        }

        return (EnumStatus)addEnum(decompiledFlags[10], net.minecraft.src.EnumStatus.class, s, new Class[0], new Object[0]);
    }

    public static EnumToolMaterial addToolMaterial(String s, int i, int j, float f, int k, int l)
    {
        if (!isSetup)
        {
            setup();
        }

        return (EnumToolMaterial)addEnum(decompiledFlags[11], net.minecraft.src.EnumToolMaterial.class, s, new Class[]
                {
                    Integer.TYPE, Integer.TYPE, Float.TYPE, Integer.TYPE, Integer.TYPE
                }, new Object[]
                {
                    Integer.valueOf(i), Integer.valueOf(j), Float.valueOf(f), Integer.valueOf(k), Integer.valueOf(l)
                });
    }

    private static void setup()
    {
        if (isSetup)
        {
            return;
        }

        for (int i = 0; i < ctrs.length; i++)
        {
            try
            {
                Class aclass[] = new Class[ctrs[i].length + 3];
                aclass[0] = java.lang.String.class;
                aclass[1] = Integer.TYPE;
                aclass[2] = java.lang.String.class;
                aclass[3] = Integer.TYPE;

                for (int j = 1; j < ctrs[i].length; j++)
                {
                    aclass[3 + j] = ctrs[i][j];
                }

                ctrs[i][0].getDeclaredConstructor(aclass);
                decompiledFlags[i] = true;
            }
            catch (Exception exception1) { }
        }

        try
        {
            Method method = Class.forName("sun.reflect.ReflectionFactory").getDeclaredMethod("getReflectionFactory", new Class[0]);
            reflectionFactory = method.invoke(null, new Object[0]);
            newConstructorAccessor = Class.forName("sun.reflect.ReflectionFactory").getDeclaredMethod("newConstructorAccessor", new Class[]
                    {
                        java.lang.reflect.Constructor.class
                    });
            newInstance = Class.forName("sun.reflect.ConstructorAccessor").getDeclaredMethod("newInstance", new Class[]
                    {
                        java.lang.Object[].class
                    });
            newFieldAccessor = Class.forName("sun.reflect.ReflectionFactory").getDeclaredMethod("newFieldAccessor", new Class[]
                    {
                        java.lang.reflect.Field.class, Boolean.TYPE
                    });
            fieldAccessorSet = Class.forName("sun.reflect.FieldAccessor").getDeclaredMethod("set", new Class[]
                    {
                        java.lang.Object.class, java.lang.Object.class
                    });
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        isSetup = true;
    }

    private static Object getConstructorAccessor(boolean flag, Class class1, Class aclass[]) throws Exception
    {
        Class aclass1[] = null;

        if (flag)
        {
            aclass1 = new Class[aclass.length + 4];
            aclass1[0] = java.lang.String.class;
            aclass1[1] = Integer.TYPE;
            aclass1[2] = java.lang.String.class;
            aclass1[3] = Integer.TYPE;
            System.arraycopy(aclass, 0, aclass1, 4, aclass.length);
        }
        else
        {
            aclass1 = new Class[aclass.length + 2];
            aclass1[0] = java.lang.String.class;
            aclass1[1] = Integer.TYPE;
            System.arraycopy(aclass, 0, aclass1, 2, aclass.length);
        }

        return newConstructorAccessor.invoke(reflectionFactory, new Object[]
                {
                    class1.getDeclaredConstructor(aclass1)
                });
    }

    private static Enum makeEnum(boolean flag, Class class1, String s, int i, Class aclass[], Object aobj[]) throws Exception
    {
        Object aobj1[] = null;

        if (flag)
        {
            aobj1 = new Object[aobj.length + 4];
            aobj1[0] = s;
            aobj1[1] = Integer.valueOf(i);
            aobj1[2] = s;
            aobj1[3] = Integer.valueOf(i);
            System.arraycopy(((Object)(aobj)), 0, ((Object)(aobj1)), 4, aobj.length);
        }
        else
        {
            aobj1 = new Object[aobj.length + 2];
            aobj1[0] = s;
            aobj1[1] = Integer.valueOf(i);
            System.arraycopy(((Object)(aobj)), 0, ((Object)(aobj1)), 2, aobj.length);
        }

        return (Enum)class1.cast(newInstance.invoke(getConstructorAccessor(flag, class1, aclass), new Object[]
                {
                    aobj1
                }));
    }

    private static void setFailsafeFieldValue(Field field, Object obj, Object obj1) throws Exception
    {
        field.setAccessible(true);
        Field field1 = (java.lang.reflect.Field.class).getDeclaredField("modifiers");
        field1.setAccessible(true);
        field1.setInt(field, field.getModifiers() & 0xffffffef);
        Object obj2 = newFieldAccessor.invoke(reflectionFactory, new Object[]
                {
                    field, Boolean.valueOf(false)
                });
        fieldAccessorSet.invoke(obj2, new Object[]
                {
                    obj, obj1
                });
    }

    private static void blankField(Class class1, String s) throws Exception
    {
        Field afield[] = (java.lang.Class.class).getDeclaredFields();
        int i = afield.length;
        int j = 0;

        do
        {
            if (j >= i)
            {
                break;
            }

            Field field = afield[j];

            if (field.getName().contains(s))
            {
                field.setAccessible(true);
                setFailsafeFieldValue(field, class1, null);
                break;
            }

            j++;
        }
        while (true);
    }

    private static void cleanEnumCache(Class class1) throws Exception
    {
        blankField(class1, "enumConstantDirectory");
        blankField(class1, "enumConstants");
    }

    public static Enum addEnum(Class class1, String s, boolean flag)
    {
        return addEnum(flag, class1, s, new Class[0], new Object[0]);
    }

    public static Enum addEnum(boolean flag, Class class1, String s, Class aclass[], Object aobj[])
    {
        if (!isSetup)
        {
            setup();
        }

        Field field = null;
        Field afield[] = class1.getDeclaredFields();
        char c = 4122;
        String s1 = String.format("[L%s;", new Object[]
                {
                    class1.getName().replace('.', '/')
                });
        Field afield1[] = afield;
        int i = afield1.length;

        for (int j = 0; j < i; j++)
        {
            Field field1 = afield1[j];

            if (flag)
            {
                if (!field1.getName().contains("$VALUES"))
                {
                    continue;
                }

                field = field1;
                break;
            }

            if ((field1.getModifiers() & c) != c || !field1.getType().getName().replace('.', '/').equals(s1))
            {
                continue;
            }

            field = field1;
            break;
        }

        field.setAccessible(true);

        try
        {
            Enum aenum[] = (Enum[])field.get(class1);
            ArrayList arraylist = new ArrayList(Arrays.asList(aenum));
            Enum enum_ = makeEnum(flag, class1, s, arraylist.size(), aclass, aobj);
            arraylist.add(enum_);
            setFailsafeFieldValue(field, null, ((Object)(arraylist.toArray((Enum[])Array.newInstance(class1, 0)))));
            cleanEnumCache(class1);
            return enum_;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            throw new RuntimeException(exception.getMessage(), exception);
        }
    }

    static
    {
        isSetup = false;
        ctrs = (new Class[][]
                {
                    new Class[] {
                        net.minecraft.src.EnumAction.class
                    }, new Class[] {
                        net.minecraft.src.EnumArmorMaterial.class, Integer.TYPE, int[].class, Integer.TYPE
                    }, new Class[] {
                        net.minecraft.src.EnumArt.class, java.lang.String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE
                    }, new Class[] {
                        net.minecraft.src.EnumCreatureAttribute.class
                    }, new Class[] {
                        net.minecraft.src.EnumCreatureType.class, java.lang.Class.class, Integer.TYPE, net.minecraft.src.Material.class, Boolean.TYPE
                    }, new Class[] {
                        net.minecraft.src.EnumDoor.class
                    }, new Class[] {
                        net.minecraft.src.EnumEnchantmentType.class
                    }, new Class[] {
                        net.minecraft.src.EnumMobType.class
                    }, new Class[] {
                        net.minecraft.src.EnumMovingObjectType.class
                    }, new Class[] {
                        net.minecraft.src.EnumSkyBlock.class, Integer.TYPE
                    }, new Class[] {
                        net.minecraft.src.EnumStatus.class
                    }, new Class[] {
                        net.minecraft.src.EnumToolMaterial.class, Integer.TYPE, Integer.TYPE, Float.TYPE, Integer.TYPE, Integer.TYPE
                    }
                });
        decompiledFlags = new boolean[ctrs.length];

        if (!isSetup)
        {
            setup();
        }
    }
}
