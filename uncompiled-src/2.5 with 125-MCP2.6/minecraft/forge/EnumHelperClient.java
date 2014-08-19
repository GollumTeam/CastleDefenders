package forge;

import argo.jdom.JsonNodeType;
import net.minecraft.src.*;

public class EnumHelperClient extends EnumHelper
{
    private static Class ctrs[][];
    private static boolean decompiled[];
    private static boolean isSetup;

    public EnumHelperClient()
    {
    }

    public static JsonNodeType addJsonNodeType(String s)
    {
        if (!isSetup)
        {
            setup();
        }

        return (JsonNodeType)addEnum(decompiled[0], argo.jdom.JsonNodeType.class, s, new Class[0], new Object[0]);
    }

    public static EnumOptions addOptions(String s, String s1, boolean flag, boolean flag1)
    {
        if (!isSetup)
        {
            setup();
        }

        return (EnumOptions)addEnum(decompiled[1], net.minecraft.src.EnumOptions.class, s, new Class[]
                {
                    java.lang.String.class, Boolean.TYPE, Boolean.TYPE
                }, new Object[]
                {
                    s1, Boolean.valueOf(flag), Boolean.valueOf(flag1)
                });
    }

    public static EnumOS2 addOS2(String s)
    {
        if (!isSetup)
        {
            setup();
        }

        return (EnumOS2)addEnum(decompiled[2], net.minecraft.src.EnumOS2.class, s, new Class[0], new Object[0]);
    }

    public static EnumRarity addRarity(String s, int i, String s1)
    {
        if (!isSetup)
        {
            setup();
        }

        return (EnumRarity)addEnum(decompiled[3], net.minecraft.src.EnumRarity.class, s, new Class[]
                {
                    Integer.TYPE, java.lang.String.class
                }, new Object[]
                {
                    Integer.valueOf(i), s1
                });
    }

    private static void setup()
    {
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
                decompiled[i] = true;
            }
            catch (Exception exception) { }
        }

        isSetup = true;
    }

    static
    {
        ctrs = (new Class[][]
                {
                    new Class[] {
                        argo.jdom.JsonNodeType.class
                    }, new Class[] {
                        net.minecraft.src.EnumOptions.class, java.lang.String.class, Boolean.TYPE, Boolean.TYPE
                    }, new Class[] {
                        net.minecraft.src.EnumOS2.class
                    }, new Class[] {
                        net.minecraft.src.EnumRarity.class, Integer.TYPE, java.lang.String.class
                    }
                });
        decompiled = new boolean[ctrs.length];
        isSetup = false;

        if (!isSetup)
        {
            setup();
        }
    }
}
