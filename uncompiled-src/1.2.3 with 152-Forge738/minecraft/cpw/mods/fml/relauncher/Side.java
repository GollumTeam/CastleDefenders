package cpw.mods.fml.relauncher;

public enum Side
{
    CLIENT,
    SERVER,
    BUKKIT;

    public boolean isServer()
    {
        return !this.isClient();
    }

    public boolean isClient()
    {
        return this == CLIENT;
    }
}
