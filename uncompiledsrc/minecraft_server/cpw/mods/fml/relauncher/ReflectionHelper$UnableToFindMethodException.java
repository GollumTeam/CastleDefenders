package cpw.mods.fml.relauncher;

public class ReflectionHelper$UnableToFindMethodException extends RuntimeException
{
    private String[] methodNames;

    public ReflectionHelper$UnableToFindMethodException(String[] var1, Exception var2)
    {
        super(var2);
        this.methodNames = var1;
    }
}
