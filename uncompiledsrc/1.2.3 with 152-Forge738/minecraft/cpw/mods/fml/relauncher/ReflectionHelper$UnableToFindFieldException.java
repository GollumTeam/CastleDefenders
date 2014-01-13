package cpw.mods.fml.relauncher;

public class ReflectionHelper$UnableToFindFieldException extends RuntimeException
{
    private String[] fieldNameList;

    public ReflectionHelper$UnableToFindFieldException(String[] var1, Exception var2)
    {
        super(var2);
        this.fieldNameList = var1;
    }
}
