package cpw.mods.fml.common.modloader;

import java.lang.reflect.Field;
import java.util.Map;

public class ModProperty
{
    private String info;
    private double min;
    private double max;
    private String name;
    private Field field;

    public ModProperty(Field var1, String var2, Double var3, Double var4, String var5)
    {
        this.field = var1;
        this.info = var2;
        this.min = var3 != null ? var3.doubleValue() : Double.MIN_VALUE;
        this.max = var4 != null ? var4.doubleValue() : Double.MAX_VALUE;
        this.name = var5;
    }

    public ModProperty(Field var1, Map var2)
    {
        this(var1, (String)var2.get("info"), (Double)var2.get("min"), (Double)var2.get("max"), (String)var2.get("name"));
    }

    public String name()
    {
        return this.name;
    }

    public double min()
    {
        return this.min;
    }

    public double max()
    {
        return this.max;
    }

    public String info()
    {
        return this.info;
    }

    public Field field()
    {
        return this.field;
    }
}
