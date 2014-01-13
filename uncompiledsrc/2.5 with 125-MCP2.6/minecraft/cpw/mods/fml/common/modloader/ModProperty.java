package cpw.mods.fml.common.modloader;

public class ModProperty
{
    private String info;
    private double min;
    private double max;
    private String name;

    public ModProperty(String s, double d, double d1, String s1)
    {
        info = s;
        min = d;
        max = d1;
        name = s1;
    }

    public String name()
    {
        return name;
    }

    public double min()
    {
        return min;
    }

    public double max()
    {
        return max;
    }

    public String info()
    {
        return info;
    }
}
