package cpw.mods.fml.relauncher;

public class ReflectionHelper$UnableToFindClassException extends RuntimeException
{
    private String[] classNames;

    public ReflectionHelper$UnableToFindClassException(String[] var1, Exception var2)
    {
        super(var2);
        this.classNames = var1;
    }
}
