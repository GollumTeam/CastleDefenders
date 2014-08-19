package forge;

import net.minecraft.src.IBlockAccess;

public interface IConnectRedstone
{
    public abstract boolean canConnectRedstone(IBlockAccess iblockaccess, int i, int j, int k, int l);
}
