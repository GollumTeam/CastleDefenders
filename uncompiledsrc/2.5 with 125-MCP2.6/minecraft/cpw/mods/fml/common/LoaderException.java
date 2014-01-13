package cpw.mods.fml.common;

public class LoaderException extends RuntimeException
{
    private static final long serialVersionUID = 0xb13d4a95269843beL;

    public LoaderException(Throwable throwable)
    {
        super(throwable);
    }

    public LoaderException()
    {
    }
}
