package cpw.mods.fml.common;

public class WrongMinecraftVersionException extends RuntimeException
{
    public ModContainer mod;

    public WrongMinecraftVersionException(ModContainer var1)
    {
        this.mod = var1;
    }
}
