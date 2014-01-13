package forge;

import net.minecraft.src.World;

public interface IOverrideReplace
{
    public abstract boolean canReplaceBlock(World world, int i, int j, int k, int l);

    public abstract boolean getReplacedSuccess();
}
