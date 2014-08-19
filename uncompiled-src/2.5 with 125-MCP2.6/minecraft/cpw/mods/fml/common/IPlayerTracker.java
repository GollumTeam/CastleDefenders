package cpw.mods.fml.common;

public interface IPlayerTracker
{
    public abstract void onPlayerLogin(Object obj);

    public abstract void onPlayerLogout(Object obj);

    public abstract void onPlayerChangedDimension(Object obj);
}
