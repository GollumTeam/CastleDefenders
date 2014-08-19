package cpw.mods.fml.common.versioning;

import cpw.mods.fml.common.versioning.ComparableVersion$1;
import cpw.mods.fml.common.versioning.ComparableVersion$IntegerItem;
import cpw.mods.fml.common.versioning.ComparableVersion$Item;
import cpw.mods.fml.common.versioning.ComparableVersion$ListItem;
import cpw.mods.fml.common.versioning.ComparableVersion$StringItem;
import java.util.Locale;
import java.util.Stack;

public class ComparableVersion implements Comparable
{
    private String value;
    private String canonical;
    private ComparableVersion$ListItem items;

    public ComparableVersion(String var1)
    {
        this.parseVersion(var1);
    }

    public final void parseVersion(String var1)
    {
        this.value = var1;
        this.items = new ComparableVersion$ListItem((ComparableVersion$1)null);
        var1 = var1.toLowerCase(Locale.ENGLISH);
        ComparableVersion$ListItem var2 = this.items;
        Stack var3 = new Stack();
        var3.push(var2);
        boolean var4 = false;
        int var5 = 0;

        for (int var6 = 0; var6 < var1.length(); ++var6)
        {
            char var7 = var1.charAt(var6);

            if (var7 == 46)
            {
                if (var6 == var5)
                {
                    var2.add(ComparableVersion$IntegerItem.ZERO);
                }
                else
                {
                    var2.add(parseItem(var4, var1.substring(var5, var6)));
                }

                var5 = var6 + 1;
            }
            else if (var7 == 45)
            {
                if (var6 == var5)
                {
                    var2.add(ComparableVersion$IntegerItem.ZERO);
                }
                else
                {
                    var2.add(parseItem(var4, var1.substring(var5, var6)));
                }

                var5 = var6 + 1;

                if (var4)
                {
                    var2.normalize();

                    if (var6 + 1 < var1.length() && Character.isDigit(var1.charAt(var6 + 1)))
                    {
                        var2.add(var2 = new ComparableVersion$ListItem((ComparableVersion$1)null));
                        var3.push(var2);
                    }
                }
            }
            else if (Character.isDigit(var7))
            {
                if (!var4 && var6 > var5)
                {
                    var2.add(new ComparableVersion$StringItem(var1.substring(var5, var6), true));
                    var5 = var6;
                }

                var4 = true;
            }
            else
            {
                if (var4 && var6 > var5)
                {
                    var2.add(parseItem(true, var1.substring(var5, var6)));
                    var5 = var6;
                }

                var4 = false;
            }
        }

        if (var1.length() > var5)
        {
            var2.add(parseItem(var4, var1.substring(var5)));
        }

        while (!var3.isEmpty())
        {
            var2 = (ComparableVersion$ListItem)var3.pop();
            var2.normalize();
        }

        this.canonical = this.items.toString();
    }

    private static ComparableVersion$Item parseItem(boolean var0, String var1)
    {
        return (ComparableVersion$Item)(var0 ? new ComparableVersion$IntegerItem(var1) : new ComparableVersion$StringItem(var1, false));
    }

    public int compareTo(ComparableVersion var1)
    {
        return this.items.compareTo(var1.items);
    }

    public String toString()
    {
        return this.value;
    }

    public boolean equals(Object var1)
    {
        return var1 instanceof ComparableVersion && this.canonical.equals(((ComparableVersion)var1).canonical);
    }

    public int hashCode()
    {
        return this.canonical.hashCode();
    }

    public int compareTo(Object var1)
    {
        return this.compareTo((ComparableVersion)var1);
    }
}
