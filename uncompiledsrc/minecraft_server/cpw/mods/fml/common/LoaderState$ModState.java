package cpw.mods.fml.common;

public enum LoaderState$ModState
{
    UNLOADED("Unloaded"),
    LOADED("Loaded"),
    CONSTRUCTED("Constructed"),
    PREINITIALIZED("Pre-initialized"),
    INITIALIZED("Initialized"),
    POSTINITIALIZED("Post-initialized"),
    AVAILABLE("Available"),
    DISABLED("Disabled"),
    ERRORED("Errored");
    private String label;

    private LoaderState$ModState(String var3)
    {
        this.label = var3;
    }

    public String toString()
    {
        return this.label;
    }
}
