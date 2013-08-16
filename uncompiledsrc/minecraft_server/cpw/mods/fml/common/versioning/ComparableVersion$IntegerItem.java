package cpw.mods.fml.common.versioning;

import cpw.mods.fml.common.versioning.ComparableVersion$Item;
import java.math.BigInteger;

class ComparableVersion$IntegerItem implements ComparableVersion$Item
{
    private static final BigInteger BigInteger_ZERO = new BigInteger("0");
    private final BigInteger value;
    public static final ComparableVersion$IntegerItem ZERO = new ComparableVersion$IntegerItem();

    private ComparableVersion$IntegerItem()
    {
        this.value = BigInteger_ZERO;
    }

    public ComparableVersion$IntegerItem(String var1)
    {
        this.value = new BigInteger(var1);
    }

    public int getType()
    {
        return 0;
    }

    public boolean isNull()
    {
        return BigInteger_ZERO.equals(this.value);
    }

    public int compareTo(ComparableVersion$Item var1)
    {
        if (var1 == null)
        {
            return BigInteger_ZERO.equals(this.value) ? 0 : 1;
        }
        else
        {
            switch (var1.getType())
            {
                case 0:
                    return this.value.compareTo(((ComparableVersion$IntegerItem)var1).value);

                case 1:
                    return 1;

                case 2:
                    return 1;

                default:
                    throw new RuntimeException("invalid item: " + var1.getClass());
            }
        }
    }

    public String toString()
    {
        return this.value.toString();
    }
}
