package cpw.mods.fml.common;

public interface IKeyHandler
{
    public abstract Object getKeyBinding();

    public abstract ModContainer getOwningContainer();

    public abstract void onEndTick();
}
