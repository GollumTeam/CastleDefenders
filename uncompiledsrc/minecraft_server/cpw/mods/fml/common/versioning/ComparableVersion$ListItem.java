package cpw.mods.fml.common.versioning;

import cpw.mods.fml.common.versioning.ComparableVersion$1;
import cpw.mods.fml.common.versioning.ComparableVersion$Item;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

class ComparableVersion$ListItem extends ArrayList implements ComparableVersion$Item
{
    private ComparableVersion$ListItem() {}

    public int getType()
    {
        return 2;
    }

    public boolean isNull()
    {
        return this.size() == 0;
    }

    void normalize()
    {
        ListIterator var1 = this.listIterator(this.size());

        while (var1.hasPrevious())
        {
            ComparableVersion$Item var2 = (ComparableVersion$Item)var1.previous();

            if (!var2.isNull())
            {
                break;
            }

            var1.remove();
        }
    }

    public int compareTo(ComparableVersion$Item var1)
    {
        if (var1 == null)
        {
            if (this.size() == 0)
            {
                return 0;
            }
            else
            {
                ComparableVersion$Item var7 = (ComparableVersion$Item)this.get(0);
                return var7.compareTo((ComparableVersion$Item)null);
            }
        }
        else
        {
            switch (var1.getType())
            {
                case 0:
                    return -1;

                case 1:
                    return 1;

                case 2:
                    Iterator var2 = this.iterator();
                    Iterator var3 = ((ComparableVersion$ListItem)var1).iterator();
                    int var6;

                    do
                    {
                        if (!var2.hasNext() && !var3.hasNext())
                        {
                            return 0;
                        }

                        ComparableVersion$Item var4 = var2.hasNext() ? (ComparableVersion$Item)var2.next() : null;
                        ComparableVersion$Item var5 = var3.hasNext() ? (ComparableVersion$Item)var3.next() : null;
                        var6 = var4 == null ? -1 * var5.compareTo(var4) : var4.compareTo(var5);
                    }
                    while (var6 == 0);

                    return var6;

                default:
                    throw new RuntimeException("invalid item: " + var1.getClass());
            }
        }
    }

    public String toString()
    {
        StringBuilder var1 = new StringBuilder("(");
        Iterator var2 = this.iterator();

        while (var2.hasNext())
        {
            var1.append(var2.next());

            if (var2.hasNext())
            {
                var1.append(',');
            }
        }

        var1.append(')');
        return var1.toString();
    }

    ComparableVersion$ListItem(ComparableVersion$1 var1)
    {
        this();
    }
}
