package cpw.mods.fml.common.modloader;

import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkModHandler;

public class ModLoaderNetworkHandler extends NetworkModHandler
{
    private BaseModProxy baseMod;

    public ModLoaderNetworkHandler(ModLoaderModContainer var1)
    {
        super(var1, (NetworkMod)null);
    }

    public void setBaseMod(BaseModProxy var1)
    {
        this.baseMod = var1;
    }

    public boolean requiresClientSide()
    {
        return false;
    }

    public boolean requiresServerSide()
    {
        return false;
    }

    public boolean acceptVersion(String var1)
    {
        return this.baseMod.getVersion().equals(var1);
    }

    public boolean isNetworkMod()
    {
        return true;
    }
}
