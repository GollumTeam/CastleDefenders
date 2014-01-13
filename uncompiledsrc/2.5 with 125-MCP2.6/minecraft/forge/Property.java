package forge;

public class Property
{
    public String name;
    public String value;
    public String comment;

    public Property()
    {
    }

    public int getInt()
    {
        return getInt(-1);
    }

    public int getInt(int i)
    {
        try
        {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException numberformatexception)
        {
            return i;
        }
    }

    public boolean isIntValue()
    {
        try
        {
            Integer.parseInt(value);
            return true;
        }
        catch (NumberFormatException numberformatexception)
        {
            return false;
        }
    }

    public boolean getBoolean(boolean flag)
    {
        if (isBooleanValue())
        {
            return Boolean.parseBoolean(value);
        }
        else
        {
            return flag;
        }
    }

    public boolean isBooleanValue()
    {
        return "true".equals(value.toLowerCase()) || "false".equals(value.toLowerCase());
    }
}
