package cpw.mods.fml.common.versioning;

import cpw.mods.fml.common.versioning.ComparableVersion$Item;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

class ComparableVersion$StringItem implements ComparableVersion$Item
{
    private static final String[] QUALIFIERS = new String[] {"alpha", "beta", "milestone", "rc", "snapshot", "", "sp"};
    private static final List _QUALIFIERS = Arrays.asList(QUALIFIERS);
    private static final Properties ALIASES = new Properties();
    private static final String RELEASE_VERSION_INDEX;
    private String value;

    public ComparableVersion$StringItem(String var1, boolean var2)
    {
        if (var2 && var1.length() == 1)
        {
            switch (var1.charAt(0))
            {
                case 97:
                    var1 = "alpha";
                    break;

                case 98:
                    var1 = "beta";
                    break;

                case 109:
                    var1 = "milestone";
            }
        }

        this.value = ALIASES.getProperty(var1, var1);
    }

    public int getType()
    {
        return 1;
    }

    public boolean isNull()
    {
        return comparableQualifier(this.value).compareTo(RELEASE_VERSION_INDEX) == 0;
    }

    public static String comparableQualifier(String var0)
    {
        int var1 = _QUALIFIERS.indexOf(var0);
        return var1 == -1 ? _QUALIFIERS.size() + "-" + var0 : String.valueOf(var1);
    }

    public int compareTo(ComparableVersion$Item var1)
    {
        if (var1 == null)
        {
            return comparableQualifier(this.value).compareTo(RELEASE_VERSION_INDEX);
        }
        else
        {
            switch (var1.getType())
            {
                case 0:
                    return -1;

                case 1:
                    return comparableQualifier(this.value).compareTo(comparableQualifier(((ComparableVersion$StringItem)var1).value));

                case 2:
                    return -1;

                default:
                    throw new RuntimeException("invalid item: " + var1.getClass());
            }
        }
    }

    public String toString()
    {
        return this.value;
    }

    static
    {
        ALIASES.put("ga", "");
        ALIASES.put("final", "");
        ALIASES.put("cr", "rc");
        RELEASE_VERSION_INDEX = String.valueOf(_QUALIFIERS.indexOf(""));
    }
}
