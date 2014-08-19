package forge;

public class MinecartKey
{
    public final Class minecart;
    public final int type;

    public MinecartKey(Class class1, int i)
    {
        minecart = class1;
        type = i;
    }

    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }

        if (getClass() != obj.getClass())
        {
            return false;
        }

        MinecartKey minecartkey = (MinecartKey)obj;

        if (minecart != minecartkey.minecart && (minecart == null || !minecart.equals(minecartkey.minecart)))
        {
            return false;
        }
        else
        {
            return type == minecartkey.type;
        }
    }

    public int hashCode()
    {
        int i = 7;
        i = 59 * i + (minecart == null ? 0 : minecart.hashCode());
        i = 59 * i + type;
        return i;
    }
}
