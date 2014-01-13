package cpw.mods.fml.common;

public interface ICraftingHandler
{
    public abstract void onCrafting(Object aobj[]);

    public abstract void onSmelting(Object aobj[]);
}
