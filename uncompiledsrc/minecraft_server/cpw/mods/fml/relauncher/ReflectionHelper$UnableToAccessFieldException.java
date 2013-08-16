package cpw.mods.fml.relauncher;

public class ReflectionHelper$UnableToAccessFieldException extends RuntimeException
{
    private String[] fieldNameList;

    public ReflectionHelper$UnableToAccessFieldException(String[] var1, Exception var2)
    {
        super(var2);
        this.fieldNameList = var1;
    }
}
